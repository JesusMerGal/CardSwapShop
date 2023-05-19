package com.cardswapshop.repository;

import com.cardswapshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    default User getUser(String currentUser) {
        return getUserByName(currentUser);
    }

    default User getUserByName(String username) {
        return findByEmail(username).isEmpty() ? null : findByEmail(username).get();

    }
}
