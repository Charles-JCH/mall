package org.example.conf;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "alipay")
@Data
public class AlipayConfig {
    // 应用ID
    private String appId;
    // 商户私钥
    private String merchantPrivateKey;
    // 支付宝公钥
    private String alipayPublicKey;
    // 服务器异步通知页面路径
    private String notifyUrl;
    // 页面跳转同步通知页面路径
    private String returnUrl;
    // 签名方式
    private String signType;
    // 字符编码格式
    private String charset;
    // 支付宝网关
    private String gatewayUrl;
    // 返回格式
    private String format;

    @Bean
    public AlipayClient alipayClient() {
        return new DefaultAlipayClient(this.gatewayUrl, this.appId, this.merchantPrivateKey, this.format, this.charset, this.alipayPublicKey, this.signType);
    }
}
