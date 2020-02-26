package com.lrowy.utils;

import com.lrowy.pojo.common.enums.SystemConstant;
import com.lrowy.pojo.common.response.BaseResponse;

import io.jsonwebtoken.*;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public class JwtUtil {
    @Value("${token.secret}")
    private static String tokenSecret;

    public static String createJWT(String id, String subject, long ttlMillis) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        SecretKey secretKey = generalKey();
        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setSubject(subject)   // 主题
                .setIssuer("lrowy.com")// 签发者
                .setIssuedAt(now)      // 签发时间
                .signWith(signatureAlgorithm, secretKey); // 签名算法以及密匙
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date expDate = new Date(expMillis);
            builder.setExpiration(expDate); // 过期时间
        }
        return builder.compact();
    }

    public static BaseResponse<Claims> validateJWT(String jwtStr) {
        BaseResponse<Claims> br = new BaseResponse<>();
        Claims claims;
        try {
            claims = parseJWT(jwtStr);
            br.setSuccess(true);
            br.setResult(claims);
        } catch (ExpiredJwtException e) {
            br.setCode(SystemConstant.JWT_ERRCODE_EXPIRE.getCode());
            br.setSuccess(false);
        } catch (Exception e) {
            br.setCode(SystemConstant.JWT_ERRCODE_FAIL.getCode());
            br.setSuccess(false);
        }
        return br;
    }

    private static SecretKey generalKey() {
        byte[] encodedKey = Base64.decodeBase64(tokenSecret);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    private static Claims parseJWT(String jwt) {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }
}
