package com.example.projectsecurityoauth2.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

/**
 * Контроллер для обработки HTTP-запросов к общедоступным и защищенным ресурсам.
 * <p>
 * Этот контроллер предоставляет два основных эндпоинта:
 * - "/public" — для доступа к общедоступному ресурсу без аутентификации.
 * - "/protected" — для доступа к защищенному ресурсу, доступному только после аутентификации.
 */
@RestController
public class UserController {

    /**
     * Обработчик для общедоступного ресурса.
     * <p>
     * Этот метод доступен без необходимости аутентификации и просто возвращает
     * сообщение, указывающее на то, что ресурс является общедоступным.
     *
     * @return строка с сообщением, что данный ресурс является общедоступным
     */
    @GetMapping("/public")  // Обработка GET-запроса по пути "/public"
    public String publicEndpoint() {
        return "Это общедоступный ресурс"; // Возвращает сообщение об общедоступности
    }

    /**
     * Обработчик для защищенного ресурса.
     * <p>
     * Этот метод требует аутентификацию. При успешной аутентификации он возвращает
     * персонализированное сообщение, приветствующее пользователя.
     *
     * @param oidcUser аутентифицированный пользователь, предоставленный системой Spring Security;
     *                 аннотация @AuthenticationPrincipal указывает на то, что параметр oidcUser
     *                 заполняется автоматически на основе данных авторизованного пользователя
     * @return строка с персонализированным сообщением для аутентифицированного пользователя
     */
    @GetMapping("/protected")  // Обработка GET-запроса по пути "/protected"
    public String protectedEndpoint(@AuthenticationPrincipal OidcUser oidcUser) {
        // Возвращает персонализированное сообщение для аутентифицированного пользователя
        return "Привет, " + oidcUser.getFullName() + "! Это защищенный ресурс.";
    }
}
