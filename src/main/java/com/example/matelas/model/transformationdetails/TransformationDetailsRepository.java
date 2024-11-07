package com.example.matelas.model.transformationdetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransformationDetailsRepository extends JpaRepository<TransformationDetails, Integer> {
    @Query("SELECT SUM(td.quantite * fu.prixVente) FROM TransformationDetails td JOIN td.usuelle fu")
     Double getTotalValeurVente();


}
