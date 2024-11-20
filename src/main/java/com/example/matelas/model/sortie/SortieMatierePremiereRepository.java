package com.example.matelas.model.sortie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SortieMatierePremiereRepository extends JpaRepository<SortieMatierePremiere, Integer> {
    // Vous pouvez ajouter des méthodes personnalisées si nécessaire
}
