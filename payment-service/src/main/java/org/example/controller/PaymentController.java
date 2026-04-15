package org.example.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import org.example.conf.AlipayConfig;
import org.example.dto.OrderDto;
import org.example.feign.IInventoryFeign;
import org.example.feign.IOrderFeign;
import org.example.util.Log;
import org.example.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/alipay")
public class PaymentController {

    @Autowired
    public AlipayConfig alipayConfig;

    @Autowired
    public AlipayClient alipayClient;

    @Autowired
    public IOrderFeign orderFeign;

    @Autowired
    public IInventoryFeign inventoryFeign;

    @PostMapping("/trade/page/pay")
    public String createPayment(@RequestParam("WIDout_trade_no") String outTradeNoParam,
                                @RequestParam("WIDtotal_amount") String totalAmountParam,
                                @RequestParam("WIDsubject") String subjectParam,
                                @RequestParam(value = "WIDbody", required = false) String bodyParam) {

        // 设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(alipayConfig.getReturnUrl());
        alipayRequest.setNotifyUrl(alipayConfig.getNotifyUrl());

        // 构建业务参数
        String bizContent = String.format("{\"out_trade_no\":\"%s\","
                        + "\"total_amount\":\"%s\","
                        + "\"subject\":\"%s\","
                        + "\"body\":\"%s\","
                        + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}",
                outTradeNoParam, totalAmountParam, subjectParam, bodyParam);

        alipayRequest.setBizContent(bizContent);

        // 执行请求
        try {
            String result = alipayClient.sdkExecute(alipayRequest).getBody();
            return alipayConfig.getGatewayUrl() + "?" + result;
        } catch (AlipayApiException e) {
            e.printStackTrace();
            Log.error("🔥 支付宝回调处理异常: " + e.getMessage());
            return "Error occurred while processing payment";
        }
    }

    @GetMapping("/return")
    public R<Map<String, String>> handleAlipayReturn(HttpServletRequest request, Model model) {
        // 获取支付宝GET过来反馈信息
        Map<String, String> params = request.getParameterMap().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> String.join(",", e.getValue())
                ));

        try {
            // 调用SDK验证签名
            boolean signVerified = AlipaySignature.rsaCheckV1(
                    params,
                    alipayConfig.getAlipayPublicKey(),
                    alipayConfig.getCharset(),
                    alipayConfig.getSignType()
            );

            model.addAttribute("signVerified", signVerified);

            if (!signVerified) {
                return R.error(400, "支付宝签名验证失败");
            }

            Map<String, String> map = new HashMap<>();
            map.put("outTradeNo", params.get("out_trade_no"));
            map.put("tradeNo", params.get("trade_no"));
            map.put("totalAmount", params.get("total_amount"));

            R<OrderDto> result = orderFeign.getOrderById(params.get("out_trade_no"));
            if (result.getCode() == 400 || result.getCode() == 500) {
                Log.error("🔥 订单更新失败: 未查询到订单信息");
                return R.error(500, "未查询到订单信息");
            }

            OrderDto order = result.getData();
            if (order != null && order.getStatus() == 0) {
                order.setStatus(1); // 1-已支付
                orderFeign.updateOrderById(order.getId(), order);

                // 调用库存服务减少库存（RPC 调用库存服务）
                inventoryFeign.reduceInventory(order.getProductId(), order.getQuantity());
                return R.ok(map);
            }
            return R.error(500, "订单更新失败");
        } catch (AlipayApiException e) {
            return R.error(500, "支付宝回调处理异常");
        }
    }
}
