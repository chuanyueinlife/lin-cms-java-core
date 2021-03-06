package io.github.talelin.core.token;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import io.github.talelin.core.constant.TokenConstant;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

@Slf4j
public class DoubleJWTTest {

    @Test
    public void generateToken() {
        DoubleJWT jwt = new DoubleJWT("secret", 1000, 2000);
        String token = jwt.generateToken("test", 1, "test", 1000);
        assertNotNull(token);
        log.info(token);
    }

    @Test
    public void decodeAccessToken() {
        DoubleJWT jwt = new DoubleJWT("secret", 10000, 20000);
        String token = jwt.generateAccessToken(1);
        assertNotNull(token);
        log.info(token);
        Map<String, Claim> claimMap = jwt.decodeAccessToken(token);
        Assert.assertEquals(TokenConstant.LIN_SCOPE, claimMap.get("scope").asString());
        Assert.assertEquals(TokenConstant.ACCESS_TYPE, claimMap.get("type").asString());
    }

    @Test
    public void decodeRefreshToken() {
        DoubleJWT jwt = new DoubleJWT("secret", 10000, 20000);
        String token = jwt.generateRefreshToken(1);
        assertNotNull(token);
        log.info(token);
        Map<String, Claim> claimMap = jwt.decodeRefreshToken(token);
        Assert.assertEquals(TokenConstant.LIN_SCOPE, claimMap.get("scope").asString());
        Assert.assertEquals(TokenConstant.REFRESH_TYPE, claimMap.get("type").asString());
    }

    @Test
    public void generateAccessToken() {
        DoubleJWT jwt = new DoubleJWT("secret", 10000, 20000);
        String token = jwt.generateAccessToken(1);
        assertNotNull(token);
        log.info(token);
    }


    @Test
    public void decodeAccessToken1() throws InterruptedException {
        DoubleJWT jwt = new DoubleJWT("secret", 1000, 2000);
        String token = jwt.generateAccessToken(1);
        assertNotNull(token);
        log.info(token);
        Thread.sleep(1000);
        try {
            Map<String, Claim> claimMap = jwt.decodeAccessToken(token);
        } catch (JWTVerificationException e) {
            assertEquals("token is expired", e.getMessage());
        }
    }

    @Test
    public void decodeRefreshToken1() throws InterruptedException {
        DoubleJWT jwt = new DoubleJWT("secret", 1000, 2000);
        String token = jwt.generateRefreshToken(1);
        assertNotNull(token);
        log.info(token);
        Thread.sleep(2100);
        try {
            Map<String, Claim> claimMap = jwt.decodeRefreshToken(token);
        } catch (JWTVerificationException e) {
            assertEquals("token is expired", e.getMessage());
        }
    }

    @Test
    public void decodeRefreshToken2() {
        DoubleJWT jwt = new DoubleJWT("secret", 6000, 2000);
        String token = jwt.generateRefreshToken(1);
        assertNotNull(token);
        log.info(token);
        try {
            Map<String, Claim> claimMap = jwt.decodeAccessToken(token);
        } catch (JWTVerificationException e) {
            assertEquals("token type is invalid", e.getMessage());
        }
    }

    @Test
    public void generateRefreshToken() {
        DoubleJWT jwt = new DoubleJWT("secret", 1000, 2000);
        String token = jwt.generateRefreshToken(1);
        assertNotNull(token);
        log.info(token);
    }

    @Test
    public void generateTokens() {
        DoubleJWT jwt = new DoubleJWT("secret", 10000, 20000);
        Tokens tokens = jwt.generateTokens(1);
        assertNotNull(tokens.getAccessToken());
        assertNotNull(tokens.getRefreshToken());
        log.info("{}", tokens);

        Map<String, Claim> claimMap = jwt.decodeAccessToken(tokens.getAccessToken());
        Assert.assertEquals(TokenConstant.LIN_SCOPE, claimMap.get("scope").asString());
        Assert.assertEquals(TokenConstant.ACCESS_TYPE, claimMap.get("type").asString());
    }

    @Test
    public void getAccessVerifier() {
        DoubleJWT jwt = new DoubleJWT("secret", 1000, 2000);
        assertNotNull(jwt.getAccessVerifier());
    }

    @Test
    public void getRefreshVerifier() {
        DoubleJWT jwt = new DoubleJWT("secret", 1000, 2000);
        assertNotNull(jwt.getRefreshVerifier());
    }

    @Test
    public void getBuilder() {
        DoubleJWT jwt = new DoubleJWT("secret", 1000, 2000);
        assertNotNull(jwt.getBuilder());
    }

    @Test
    public void getAlgorithm() {
        DoubleJWT jwt = new DoubleJWT("secret", 1000, 2000);
        assertNotNull(jwt.getAlgorithm());
    }

    @Test
    public void getAccessExpire() {
        Algorithm algorithm = Algorithm.HMAC256("secret");
        DoubleJWT jwt = new DoubleJWT(algorithm, 1000, 2000);
        assertTrue(jwt.getAccessExpire() == 1000L);
    }

    @Test
    public void getRefreshExpire() {
        Algorithm algorithm = Algorithm.HMAC256("secret");
        DoubleJWT jwt = new DoubleJWT(algorithm, 1000, 2000);
        assertTrue(jwt.getRefreshExpire() == 2000);
    }
}