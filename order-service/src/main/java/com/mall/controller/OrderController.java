package com.mall.controller;

import com.mall.api.R;
import com.mall.entiry.OrderInfo;
import com.mall.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController extends BaseController<IOrderService, OrderInfo> {

    @Autowired
    private IOrderService orderService;

    protected OrderController(IOrderService service) {
        super(service);
    }

    @PostMapping("/create")
    public R<OrderInfo> createOrder(@RequestParam(value = "orderToken") String orderToken,
                                    @RequestParam("userId") Integer userId,
                                    @RequestParam("productId") Integer productId,
                                    @RequestParam("quantity") Integer quantity) {
        OrderInfo orderInfo = orderService.createOrder(orderToken, userId, productId, quantity);
        if (orderInfo == null) {
            return R.error(400, "Error creating order");
        }
        return R.ok(orderInfo);
    }
}
