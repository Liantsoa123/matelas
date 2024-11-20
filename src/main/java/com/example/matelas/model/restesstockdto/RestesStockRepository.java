package com.example.matelas.model.restesstockdto;


import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestesStockRepository {

    @Query(value = "SELECT " +
            "amp.id AS id, " +
            "amp.id_achat AS achat_matiere_premiere, " +
            "(amp.quantite - COALESCE(sm.quantite, 0)) AS quantite_reste, " +
            "amp.date_achat AS date_achat_matiere_premiere " +
            "FROM achat_matiere_premiere amp " +
            "LEFT JOIN sorti_matiere_premiere sm ON amp.id = sm.id_achat " +
            "WHERE (amp.quantite - COALESCE(sm.quantite, 0)) > 0 " +
            "AND amp.date_achat <= :date", nativeQuery = true)
    List<Object[]> getRestesStockWithAchatMatierePremiere(java.sql.Date date);
}