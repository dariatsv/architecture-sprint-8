package ru.yandex.practicum.api.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer
import org.springframework.security.config.core.GrantedAuthorityDefaults
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import org.springframework.security.web.SecurityFilterChain
import java.util.stream.Stream

@Configuration
class SecurityConfig {
    @Bean
    fun grantedAuthorityDefaults(): GrantedAuthorityDefaults = GrantedAuthorityDefaults("")

    @Bean
    fun securityFilterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        httpSecurity.oauth2ResourceServer { oauth2: OAuth2ResourceServerConfigurer<HttpSecurity?> ->
            oauth2.jwt(
                Customizer.withDefaults()
            )
        }
        return httpSecurity
            .authorizeHttpRequests { custom ->
                custom.requestMatchers("/error").permitAll()
                    .requestMatchers("/reports").hasRole("prothetic_user")
                    .anyRequest().authenticated()
            }
            .build()
    }

    @Bean
    fun jwtAuthenticationConverter(): JwtAuthenticationConverter {
        val converter = JwtAuthenticationConverter()
        val jwtGrantedAuthoritiesConverter = JwtGrantedAuthoritiesConverter()
        converter.setPrincipalClaimName("preferred_username")
        converter.setJwtGrantedAuthoritiesConverter { jwt: Jwt ->
            val grantedAuthorities = jwtGrantedAuthoritiesConverter.convert(jwt)
            val roles = jwt.getClaimAsMap("realm_access")["roles"] as List<String>?
            Stream.concat(
                grantedAuthorities!!.stream(), roles!!.stream()
                    .filter { role -> role.startsWith("prothetic_") }
                    .map { role -> SimpleGrantedAuthority(role) }
                    .map { obj: SimpleGrantedAuthority? -> GrantedAuthority::class.java.cast(obj) })
                .toList()
        }
        return converter
    }
}
