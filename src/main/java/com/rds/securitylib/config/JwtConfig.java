package com.rds.securitylib.config;

import com.rds.securitylib.jwt.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableMethodSecurity
public class JwtConfig {

    private static final Logger log = LoggerFactory.getLogger(JwtConfig.class);

    @Bean
    @ConfigurationProperties(prefix = "security.jwt")
    public JwtProps jwtProps() {return new JwtProps();}

    @Bean
    @ConditionalOnProperty(name = "security.jwt.hs256-secret-base64")
    public JwtService jwtService(JwtProps jwtProps) {
        return JwtService.build(jwtProps.getIssuer(), jwtProps.getExpiration(), jwtProps.getHs256SecretBase64());
    }

    public static class JwtProps {
        private String issuer;
        private Integer expiration;
        private String hs256SecretBase64;
        private String rsaPublicBase64;
        private String rsaPrivateBase64;

        public String getIssuer() {
            return issuer;
        }

        public void setIssuer(String issuer) {
            this.issuer = issuer;
        }

        public Integer getExpiration() {
            return expiration;
        }

        public void setExpiration(Integer expiration) {
            this.expiration = expiration;
        }

        public String getHs256SecretBase64() {
            return hs256SecretBase64;
        }

        public void setHs256SecretBase64(String hs256SecretBase64) {
            this.hs256SecretBase64 = hs256SecretBase64;
        }

        public String getRsaPublicBase64() {
            return rsaPublicBase64;
        }

        public void setRsaPublicBase64(String rsaPublicBase64) {
            this.rsaPublicBase64 = rsaPublicBase64;
        }

        public String getRsaPrivateBase64() {
            return rsaPrivateBase64;
        }

        public void setRsaPrivateBase64(String rsaPrivateBase64) {
            this.rsaPrivateBase64 = rsaPrivateBase64;
        }
    }
}
