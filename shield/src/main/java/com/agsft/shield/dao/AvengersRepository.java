package com.agsft.shield.dao;

import com.agsft.shield.entitiy.Avengers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AvengersRepository extends JpaRepository<Avengers, Long> {
    Optional<Avengers> findByName(String name);

}
