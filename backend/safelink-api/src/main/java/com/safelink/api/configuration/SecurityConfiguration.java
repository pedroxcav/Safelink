package com.safelink.api.configuration;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.function.client.WebClient;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.List;

import static java.util.Base64.getMimeDecoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Value("${keys.public}")
    private String publicKey;
    @Value("${keys.private}")
    private String privateKey;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET,
                                "/empresa",
                                "/link").hasAuthority("SCOPE_EMPRESA")
                        .requestMatchers(HttpMethod.PUT,
                                "/empresa").hasAuthority("SCOPE_EMPRESA")
                        .requestMatchers(HttpMethod.DELETE,
                                "/empresa",
                                "/link").hasAuthority("SCOPE_EMPRESA")
                        .requestMatchers(HttpMethod.POST,
                                "/link").hasAuthority("SCOPE_EMPRESA")
                        .requestMatchers(HttpMethod.GET,
                                "/cliente",
                                "/relato").hasAuthority("SCOPE_CLIENTE")
                        .requestMatchers(HttpMethod.PUT,
                                "/cliente").hasAuthority("SCOPE_CLIENTE")
                        .requestMatchers(HttpMethod.DELETE,
                                "/cliente",
                                "/relato/{id}").hasAuthority("SCOPE_CLIENTE")
                        .requestMatchers(HttpMethod.POST,
                                "/relato").hasAuthority("SCOPE_CLIENTE")
                        .requestMatchers(HttpMethod.GET,
                                "/relato/dado",
                                "/relato/verifica").permitAll()
                        .requestMatchers(HttpMethod.POST,
                                "/empresa",
                                "/cliente",
                                "/cliente/login",
                                "/empresa/login").permitAll()
                        .anyRequest().authenticated())
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public JwtDecoder decoder() throws InvalidKeySpecException, NoSuchAlgorithmException {
        KeyFactory kf = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(getMimeDecoder().decode(publicKey));
        RSAPublicKey pubKey = (RSAPublicKey) kf.generatePublic(keySpecX509);

        return NimbusJwtDecoder.withPublicKey(pubKey).build();
    }
    @Bean
    public JwtEncoder encoder() throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(getMimeDecoder().decode(privateKey));
        PrivateKey privKey = kf.generatePrivate(keySpecPKCS8);

        X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(getMimeDecoder().decode(publicKey));
        RSAPublicKey pubKey = (RSAPublicKey) kf.generatePublic(keySpecX509);

        JWK jwk = new RSAKey.Builder(pubKey).privateKey(privKey).build();
        var jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    public WebClient webClient1(WebClient.Builder builder) {
        return builder.baseUrl("https://safelink-cybh.onrender.com").build();
    }

    @Bean
    public WebClient webClient2(WebClient.Builder builder) {
        return builder.baseUrl("https://pedro-migsxch2-swedencentral.cognitiveservices.azure.com/openai/deployments/gpt-5-chat/chat/completions?api-version=2025-01-01-preview").build();
    }
}