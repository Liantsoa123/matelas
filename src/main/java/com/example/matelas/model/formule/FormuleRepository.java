package com.example.matelas.model.formule;

import com.example.matelas.model.formule.Formule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormuleRepository extends JpaRepository<Formule, Integer> {
}
