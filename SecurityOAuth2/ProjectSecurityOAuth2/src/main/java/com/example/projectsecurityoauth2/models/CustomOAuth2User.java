package com.example.projectsecurityoauth2.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Класс CustomOAuth2User реализует интерфейс OAuth2User и используется для представления пользователя OAuth2
 * с дополнительными ролями, которые можно назначать и обрабатывать.
 */
public class CustomOAuth2User implements OAuth2User {

    private final OAuth2User oAuth2User;  // Объект OAuth2User, содержащий информацию о пользователе, полученную от провайдера
    private final List<String> roles;     // Список ролей пользователя

    /**
     * Конструктор для создания экземпляра CustomOAuth2User.
     *
     * @param oAuth2User объект OAuth2User, представляющий пользователя, полученного от провайдера
     * @param roles список ролей, связанных с пользователем
     */
    public CustomOAuth2User(OAuth2User oAuth2User, List<String> roles) {
        this.oAuth2User = oAuth2User;
        this.roles = roles;
    }

    /**
     * Возвращает атрибуты пользователя, предоставленные провайдером OAuth2.
     *
     * @return карта атрибутов пользователя, например, имя, email и т.д.
     */
    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes(); // Возвращаем атрибуты, полученные от провайдера
    }

    /**
     * Возвращает коллекцию прав доступа пользователя (GrantedAuthority), основанных на списке ролей.
     *
     * @return коллекция GrantedAuthority, представляющая права доступа пользователя
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)  // Преобразуем роли в SimpleGrantedAuthority
                .collect(Collectors.toList());      // Сохраняем результаты в список
    }

    /**
     * Возвращает имя пользователя.
     *
     * @return имя пользователя, полученное из атрибутов
     */
    @Override
    public String getName() {
        return oAuth2User.getAttribute("name"); // Извлекаем и возвращаем имя пользователя из атрибутов
    }

    /**
     * Возвращает email пользователя.
     *
     * @return email пользователя, если он доступен в атрибутах
     */
    public String getEmail() {
        return oAuth2User.getAttribute("email"); // Извлекаем и возвращаем email пользователя
    }
}
