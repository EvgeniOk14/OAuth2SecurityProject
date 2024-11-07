package com.example.projectsecurityoauth2.models;

import jakarta.persistence.*;
import java.util.Set;


/**
 * Класс User представляет сущность пользователя в базе данных с информацией об учетных данных и ролях.
 */
@Entity
@Table(name = "oauth2_users_table")  // Указание имени таблицы, в которой будут храниться данные пользователя
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Автоматическая генерация идентификатора
    private Long id;  // Уникальный идентификатор пользователя

    @Column(nullable = false, unique = true)  // Уникальное и обязательное поле email
    private String email;  // Email пользователя

    @Column(nullable = false)  // Обязательное поле имени пользователя
    private String name;  // Имя пользователя

    @ElementCollection(fetch = FetchType.EAGER)  // Коллекция ролей пользователя, загружаемая сразу вместе с сущностью
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))  // Таблица для хранения ролей пользователя
    @Column(name = "role")  // Колонка для роли в таблице user_roles
    private Set<String> roles;  // Набор ролей пользователя

    /**
     * Пустой конструктор для использования JPA.
     */
    public User() {}

    /**
     * Конструктор для создания пользователя с заданными email, именем и ролями.
     *
     * @param email email пользователя
     * @param name имя пользователя
     * @param roles роли, назначенные пользователю
     */
    public User(String email, String name, Set<String> roles) {
        this.email = email;  // Инициализация email
        this.name = name;    // Инициализация имени
        this.roles = roles;  // Инициализация ролей
    }

    /**
     * Возвращает уникальный идентификатор пользователя.
     *
     * @return идентификатор пользователя
     */
    public Long getId() { return id; }

    /**
     * Устанавливает уникальный идентификатор пользователя.
     *
     * @param id идентификатор пользователя
     */
    public void setId(Long id) { this.id = id; }

    /**
     * Возвращает email пользователя.
     *
     * @return email пользователя
     */
    public String getEmail() { return email; }

    /**
     * Устанавливает email пользователя.
     *
     * @param email email пользователя
     */
    public void setEmail(String email) { this.email = email; }

    /**
     * Возвращает имя пользователя.
     *
     * @return имя пользователя
     */
    public String getName() { return name; }

    /**
     * Устанавливает имя пользователя.
     *
     * @param name имя пользователя
     */
    public void setName(String name) { this.name = name; }

    /**
     * Возвращает набор ролей пользователя.
     *
     * @return роли пользователя
     */
    public Set<String> getRoles() { return roles; }

    /**
     * Устанавливает набор ролей пользователя.
     *
     * @param roles роли пользователя
     */
    public void setRoles(Set<String> roles) { this.roles = roles; }
}
