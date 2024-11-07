package com.example.projectsecurityoauth2.services;

import com.example.projectsecurityoauth2.models.CustomOAuth2User;
import com.example.projectsecurityoauth2.models.User;
import com.example.projectsecurityoauth2.repositories.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * CustomOAuth2UserService - Сервис для загрузки пользовательских данных в процессе аутентификации через OAuth2.
 * Класс расширяет DefaultOAuth2UserService и предоставляет возможность аутентифицировать пользователей,
 * проверяя их наличие в базе данных и предоставляя соответствующие роли.
 */
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;  // Репозиторий для поиска пользователей в базе данных

    /**
     * Конструктор CustomOAuth2UserService.
     *
     * @param userRepository - репозиторий для работы с данными пользователей
     */
    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Метод для загрузки пользователя при аутентификации через OAuth2.
     *
     * @param userRequest - запрос от OAuth2 с данными о пользователе, содержит информацию о провайдере и его параметрах.
     * @return аутентифицированный пользователь с нужными правами доступа.
     * @throws OAuth2AuthenticationException - исключение при ошибке аутентификации через OAuth2.
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // Загружаем пользовательские данные, полученные от OAuth2-провайдера
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // Извлекаем email из данных, полученных от провайдера (например, Google)
        String email = oAuth2User.getAttribute("email");

        // Ищем пользователя в базе данных по email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с email " + email + " не зарегистрирован"));

        // Возвращаем аутентифицированного пользователя с соответствующими ролями
        return new CustomOAuth2User(oAuth2User, (List<String>) user.getRoles());
    }
}
