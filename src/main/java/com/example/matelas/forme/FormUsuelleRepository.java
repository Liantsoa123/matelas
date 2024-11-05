package com.example.matelas.forme;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormUsuelleRepository extends JpaRepository<FormUsuelle, Integer> {
}
