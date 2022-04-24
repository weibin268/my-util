package com.zhuang.hutool;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.AsymmetricCrypto;
import cn.hutool.crypto.asymmetric.KeyType;
import org.junit.Test;
import sun.security.rsa.RSAPrivateKeyImpl;
import sun.security.rsa.RSAPublicKeyImpl;
import sun.security.util.DerValue;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class SecureUtilTest {

    @Test
    public void test() throws IOException {
        // 生成密钥对
        KeyPair rsaKeyPair = SecureUtil.generateKeyPair("RSA", 512);
        // 公钥私钥字节
        byte[] publicKeyBytes = rsaKeyPair.getPublic().getEncoded();
        byte[] privateKeyBytes = rsaKeyPair.getPrivate().getEncoded();
        // 公钥私钥对象
        PublicKey publicKey = RSAPublicKeyImpl.parse(new DerValue(publicKeyBytes));
        PrivateKey privateKey = RSAPrivateKeyImpl.parse(new DerValue(privateKeyBytes));
        // 公钥私钥base64
        System.out.println(Base64.getEncoder().encodeToString(publicKeyBytes));
        System.out.println(Base64.getEncoder().encodeToString(privateKeyBytes));
        // 加密解密
        AsymmetricCrypto asymmetricCrypto = SecureUtil.rsa()
                .setPublicKey(publicKey)
                .setPrivateKey(privateKey);
        String s = asymmetricCrypto.encryptBase64("123456", KeyType.PublicKey);
        byte[] ss = asymmetricCrypto.decrypt(Base64.getDecoder().decode(s), KeyType.PrivateKey);
        System.out.println(s);
        System.out.println(new String(ss));
    }

    @Test
    public void test2() throws IOException {
        PrivateKey privateKey = RSAPrivateKeyImpl.parse(new DerValue(Base64.getDecoder().decode("MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAweMv96sXH4ByCTww2Xf57mQeilJKzHsUPcTae9L7nmx7/Z/clGo9DggEugfS2xLTxDl20k2F9rv/B4l0SagNkQIDAQABAkB5HyD/Go2ma0pSBJ0LrcyfpUIC7Se3GAhsEgbfJc10iH8QLIgvvpk9uCco1oiQlQlqAxomqXUeEhstmmAqtU8lAiEA9w04AbDhaPDljFVwiMvnMmXvfoSe0/Khz/GlAmuFxgsCIQDI6QE5Uxs4uJKytrsieK8ymQ6FyHXm0yDPeWdgLxmIUwIhALmhAhMrL8OlNiWi0SLIaxiQRUrAJYNFzVWs0PqnheWPAiBOMbmjOQA4REX8Pnh5AyWU+NMZKJsSuotjlKmyWsKCNwIgCm2EJpboNurNIvS9pL4F+TWLNmfiPtP6ae4Kze0dm3k=")));
        AsymmetricCrypto asymmetricCrypto = SecureUtil.rsa()
                .setPrivateKey(privateKey);
        byte[] s = asymmetricCrypto.decrypt(Base64.getDecoder().decode("iGcaS+tD7PvaWi9qI71nqyHU72eVYwNK+tfKFALR7YLy7XBGLxvf3xloGngZLglFXINVC4RwMIpzbChM7ezHUA=="), KeyType.PrivateKey);
        System.out.println(new String(s));
    }

    @Test
    public void generateKey() {
        SecretKey aes = SecureUtil.generateKey("AES", 128);
        System.out.println(Base64.getEncoder().encodeToString(aes.getEncoded()));
    }
}
