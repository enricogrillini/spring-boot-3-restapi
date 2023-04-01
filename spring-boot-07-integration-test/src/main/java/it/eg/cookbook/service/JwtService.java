package it.eg.cookbook.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SecurityException;
import it.eg.cookbook.error.ApiException;
import it.eg.cookbook.error.ResponseCode;
import it.eg.cookbook.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Date;


@Component
@Slf4j
public class JwtService implements InitializingBean {

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    @Value("${cookbook.private_key_jwt}")
    private String privateKeyResource;

    @Value("${cookbook.public_key_jwt}")
    Resource publicKeyResource;

    @Autowired
    private ResourceLoader resourceLoader;

    private PrivateKey privateKey;

    private PublicKey publicKey;

    @Override
    public void afterPropertiesSet() throws Exception {
        // Private Key
        Resource resource = resourceLoader.getResource(privateKeyResource);

        byte[] bdata = FileCopyUtils.copyToByteArray(resource.getInputStream());
        String pem = new String(bdata, StandardCharsets.UTF_8);
        pem = pem.replace("-----BEGIN PRIVATE KEY-----", "");
        pem = pem.replace("-----END PRIVATE KEY-----", "");

        privateKey = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(new Base64().decode(pem)));

        // PublicKey
        try (InputStream inputStream = publicKeyResource.getInputStream()) {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            X509Certificate cert = (X509Certificate) certificateFactory.generateCertificate(inputStream);
            publicKey = cert.getPublicKey();
        }
    }

    public String createJWT(User user) {
        // The JWT signature algorithm we will be using to sign the token
        long nowMillis = System.currentTimeMillis();

        // Let's set the JWT Claims
        return Jwts.builder()
                .setSubject(user.getSubject())
                .setIssuer(user.getIssuer())
                .setAudience(user.getAudience())
                .claim("customClaim", user.getCustomClaim())
                .setIssuedAt(new Date(nowMillis))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .setExpiration(new Date(nowMillis + user.getTtlMillis()))
                .compact();
    }

    public Jws<Claims> parseToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(publicKey)
                    .build()
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""));

        } catch (ExpiredJwtException exception) {
            log.warn("Request to parse expired JWT : {} failed : {}", token, exception.getMessage());
            throw new ApiException(ResponseCode.TOKEN_ERRATO, "Request to parse expired JWT: " + exception.getMessage());
        } catch (UnsupportedJwtException exception) {
            log.warn("Request to parse unsupported JWT : {} failed : {}", token, exception.getMessage());
            throw new ApiException(ResponseCode.TOKEN_ERRATO, "Request to parse unsupported JWT : " + exception.getMessage());
        } catch (MalformedJwtException exception) {
            log.warn("Request to parse invalid JWT : {} failed : {}", token, exception.getMessage());
            throw new ApiException(ResponseCode.TOKEN_ERRATO, "Request to parse invalid JWT: " + exception.getMessage());
        } catch (SecurityException exception) {
            log.warn("Request to parse JWT with invalid signature : {} failed : {}", token, exception.getMessage());
            throw new ApiException(ResponseCode.TOKEN_ERRATO, "Request to parse JWT with invalid signature: " + exception.getMessage());
        } catch (IllegalArgumentException exception) {
            log.warn("Request to parse empty or null JWT : {} failed : {}", token, exception.getMessage());
            throw new ApiException(ResponseCode.TOKEN_ERRATO, "Request to parse empty or null JWT: " + exception.getMessage());
        }
    }


}
