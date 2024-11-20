package com.example.matelas.model.matierepremier;

import com.example.matelas.model.matierepremier.MatierePremier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatierePremierRepository extends JpaRepository<MatierePremier, Integer> {
    // Additional query methods (if needed) can be added here
}
