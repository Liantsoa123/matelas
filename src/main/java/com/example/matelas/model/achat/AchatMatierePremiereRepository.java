package com.example.matelas.model.achat;

import com.example.matelas.model.achat.AchatMatierePremiere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AchatMatierePremiereRepository extends JpaRepository<AchatMatierePremiere, Integer> {
    @Query(value ="SELECT\n" +
            "    amp.id,\n" +
            "    amp.id AS achat_matiere_premiere,\n" +
            "    (amp.quantite - COALESCE(SUM(sm.quantite), 0)) AS quantite_reste,\n" +
            "    amp.date_achat AS date_achat_matiere_premiere\n" +
            "FROM\n" +
            "    achat_matiere_premiere amp\n" +
            "        LEFT JOIN\n" +
            "    sortie_matiere_premiere sm\n" +
            "    ON amp.id = sm.achat_matiere_premiere_id\n" +
            "WHERE\n" +
            "    amp.date_achat <= :date\n" +
            "  AND amp.matiere_premier_id = :matierePremiereId\n" +
            "GROUP BY\n" +
            "    amp.date_achat, amp.quantite, amp.id\n" +
            "HAVING\n" +
            "    (amp.quantite - COALESCE(SUM(sm.quantite), 0)) > 0\n" +
            "ORDER BY\n" +
            "    amp.date_achat ASC;\n", nativeQuery = true)
    List<Object[]> getRestesStockWithAchatMatierePremiere(
            @Param("date") java.sql.Date date,
            @Param("matierePremiereId") int matierePremiereId);
}
