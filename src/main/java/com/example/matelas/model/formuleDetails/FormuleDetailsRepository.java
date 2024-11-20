package com.example.matelas.model.formuleDetails;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface FormuleDetailsRepository extends JpaRepository<FormuleDetails, Integer> {

    @Query("SELECT fd FROM FormuleDetails fd WHERE fd.formule.id = :formuleId")
    List<FormuleDetails> findByFormuleId(@Param("formuleId") int formuleId);
}
