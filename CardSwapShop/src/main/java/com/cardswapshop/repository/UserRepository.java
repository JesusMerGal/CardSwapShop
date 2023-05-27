package com.cardswapshop.repository;

import com.cardswapshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    //Devuelve un optional user que puede ser vacio o no dependiendo si encuentra el usuario por el email
    Optional<User> findByEmail(String email);
    //Devuelve un user por el username del usuario, en este servicio el username es el email, se usa para obtener el user en sesión
    default User getUser(String currentUser) {
        return getUserByName(currentUser);
    }
    //Devuelve un user por el email o un null en caso de no encontrarlo
    default User getUserByName(String username) {
        return findByEmail(username).isEmpty() ? null : findByEmail(username).get();

    }
}
