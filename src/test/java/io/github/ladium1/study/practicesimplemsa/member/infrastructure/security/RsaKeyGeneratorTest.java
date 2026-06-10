package io.github.ladium1.study.practicesimplemsa.member.infrastructure.security;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;

class RsaKeyGeneratorTest {

    @Disabled("RSA 키페어 생성 필요 시 수동 실행")
    @Test
    void generateRsaKeyPair() throws Exception {
        KeyPair keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();
        System.out.println("private : " + Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded()));
        System.out.println("public : " + Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()));
    }
}
