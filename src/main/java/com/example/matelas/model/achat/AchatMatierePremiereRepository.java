package com.example.matelas.model.achat;

import com.example.matelas.model.achat.AchatMatierePremiere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AchatMatierePremiereRepository extends JpaRepository<AchatMatierePremiere, Integer> {
    // Add custom query methods if needed
}
