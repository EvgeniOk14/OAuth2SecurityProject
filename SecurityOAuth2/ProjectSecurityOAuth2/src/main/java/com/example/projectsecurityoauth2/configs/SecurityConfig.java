package com.example.projectsecurityoauth2.configs;

import com.example.projectsecurityoauth2.services.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ClientRegistrations;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;


/**
 * Конфигурационный класс для настройки безопасности Spring Security с использованием OAuth2.
 * Позволяет аутентифицировать пользователей через Google и защищать ресурсы с помощью JWT.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    /**
     * Конструктор для SecurityConfig.
     *
     * @param customOAuth2UserService пользовательская служба OAuth2, используемая для загрузки информации о пользователе
     */
    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
        this.customOAuth2UserService = customOAuth2UserService;
    }

    /**
     * Конфигурация цепочки фильтров безопасности для приложения.
     * Настраивает авторизацию и аутентификацию пользователей, использующих Google OAuth2, а также защищает ресурсы JWT.
     *
     * @param http объект HttpSecurity для настройки параметров безопасности
     * @return SecurityFilterChain цепочка фильтров безопасности для приложения
     * @throws Exception может возникнуть при настройке безопасности
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Настройка авторизации запросов
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/public").permitAll() // Разрешить доступ к /public без авторизации
                        .requestMatchers("/protected").authenticated() // Требовать авторизацию для /protected
                        .anyRequest().authenticated() // Требовать авторизацию для любых других запросов
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService) // Настраивает пользовательскую службу для загрузки данных пользователя
                        )
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt()); // Включает JWT для OAuth2 ресурса)

        return http.build();
    }

    /**
     * Определяет репозиторий для хранения регистраций клиентов OAuth2.
     * Здесь используется InMemoryClientRegistrationRepository для Google OAuth2.
     *
     * @return ClientRegistrationRepository репозиторий для регистраций клиентов
     */
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        // Использование in-memory репозитория для регистрации клиента OAuth2
        return new InMemoryClientRegistrationRepository(this.googleClientRegistration());
    }

    /**
     * Создает объект ClientRegistration для Google OAuth2.
     * Использует clientId и clientSecret, зарегистрированные в Google Cloud Console.
     *
     * @return ClientRegistration объект регистрации клиента для Google OAuth2
     */
    private ClientRegistration googleClientRegistration() {
        // Создание регистрации клиента для Google OAuth2, используя URL-адрес поставщика идентификации
        return ClientRegistrations
                .fromIssuerLocation("https://accounts.google.com") // URL-адрес поставщика идентификации
                .clientId("your-client-id") // Идентификатор клиента, полученный из Google Cloud Console
                .clientSecret("your-client-secret") // Секрет клиента, полученный из Google Cloud Console
                .build();
    }

    /**
     * Определяет JwtDecoder для декодирования JWT токенов, полученных от Google.
     * Использует JWK URI Google для проверки JWT.
     *
     * @return JwtDecoder декодер для JWT токенов
     */
    @Bean
    public JwtDecoder jwtDecoder() {
        // Декодирует JWT токены с использованием JWK URI Google
        return NimbusJwtDecoder.withJwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
                .build();
    }
}
