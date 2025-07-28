package com.mall.config;

import cn.hutool.core.io.IoUtil;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.Base64Utils;

import javax.annotation.PostConstruct;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Data
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class RsaKeyProperties {

    private String privateKeyPath;
    private String publicKeyPath;
    private Long expiration;

    private PrivateKey privateKey;
    private PublicKey publicKey;

    @PostConstruct
    public void init() {
        try {
            this.privateKey = loadPrivateKey(privateKeyPath);
            this.publicKey = loadPublicKey(publicKeyPath);
        } catch (Exception e) {
            throw new RuntimeException("初始化密钥失败", e);
        }
    }

    private PrivateKey loadPrivateKey(String path) throws Exception {
        // 读取文件内容
        ClassPathResource resource = new ClassPathResource(path.replace("classpath:", ""));
        String keyString = IoUtil.readUtf8(resource.getInputStream());

        // 去除头尾和换行符
        String privateKeyPEM = keyString
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s+", "");

        // Base64 解码
        byte[] encoded = Base64Utils.decodeFromString(privateKeyPEM);

        // 使用 PKCS#8 规范还原 Key
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encoded));
    }

    private PublicKey loadPublicKey(String path) throws Exception {
        ClassPathResource resource = new ClassPathResource(path.replace("classpath:", ""));
        String keyString = IoUtil.readUtf8(resource.getInputStream());

        String publicKeyPEM = keyString
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s+", "");

        byte[] encoded = Base64Utils.decodeFromString(publicKeyPEM);

        // 使用 X.509 规范还原 Key
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(new X509EncodedKeySpec(encoded));
    }
}
