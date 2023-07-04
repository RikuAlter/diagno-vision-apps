package com.diagno.vision.webapps.diagnovision.repository;

import com.diagno.vision.webapps.diagnovision.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    public Optional<User> findByEmail(final String userEmail);

}