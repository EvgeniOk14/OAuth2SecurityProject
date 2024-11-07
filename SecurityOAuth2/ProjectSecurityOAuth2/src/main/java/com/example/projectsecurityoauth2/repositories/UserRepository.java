package com.example.projectsecurityoauth2.repositories;

import com.example.projectsecurityoauth2.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


/**
 * Интерфейс UserRepository предоставляет методы для доступа к данным пользователя в базе данных.
 * Он расширяет JpaRepository, обеспечивая стандартные методы CRUD и возможности поиска.
 */
@Repository  // Аннотация, обозначающая интерфейс как репозиторий данных Spring
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Поиск пользователя по email.
     *
     * @param email email пользователя, который требуется найти
     * @return объект Optional, содержащий пользователя, если он существует, или пустой Optional, если нет
     */
    Optional<User> findByEmail(String email);  // Метод для поиска пользователя по email
}
