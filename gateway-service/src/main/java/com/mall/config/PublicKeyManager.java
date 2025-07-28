package com.mall.config;

import com.mall.api.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.reactive.function.client.WebClient;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.concurrent.atomic.AtomicReference;

@Component
@Slf4j
public class PublicKeyManager implements CommandLineRunner { // 1. 改为实现 CommandLineRunner

    private final AtomicReference<PublicKey> publicKeyRef = new AtomicReference<>();

    // 2. 注入刚才独立配置类中定义的 Builder
    @Autowired
    private WebClient.Builder webClientBuilder;

    /**
     * 实现 run 方法，在 Spring 容器启动完成后执行
     * 此时所有的 Bean 都已经加载完毕，调用网络更安全
     */
    @Override
    public void run(String... args) {
        this.refreshPublicKey();
    }

    /**
     * 远程调用 Auth 服务拉取公钥
     */
    public void refreshPublicKey() {
        log.info("开始从 Auth 服务拉取公钥...");
        webClientBuilder.build()
                .get()
                .uri("http://auth-service/auth/public-key")
                .retrieve()
                .bodyToMono(R.class) // 假设你的 Result 类叫 R
                .map(r -> (String) r.getData())
                .doOnSuccess(pem -> log.info("公钥拉取成功"))
                .doOnError(e -> log.error("公钥拉取失败: {}", e.getMessage()))
                .subscribe(pemStr -> {
                    try {
                        PublicKey key = convertToPublicKey(pemStr);
                        publicKeyRef.set(key);
                    } catch (Exception e) {
                        log.error("公钥转换异常", e);
                    }
                });
    }

    public PublicKey getPublicKey() {
        PublicKey key = publicKeyRef.get();
        if (key == null) {
            // 3. 避免在这里直接 refreshPublicKey()
            // 因为如果是高并发请求，这里会瞬间发出几千个 Auth 请求把网关或Auth打挂。
            // 建议：返回 null，让 Filter 报错“系统初始化中”，
            // 或者使用 synchronized/Lock 保证同一时间只有一个线程去刷新。
            log.warn("公钥尚未初始化或获取失败");
        }
        return key;
    }

    private PublicKey convertToPublicKey(String keyString) throws Exception {
        String publicKeyPEM = keyString
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s+", "");

        byte[] encoded = Base64Utils.decodeFromString(publicKeyPEM);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(new X509EncodedKeySpec(encoded));
    }
}