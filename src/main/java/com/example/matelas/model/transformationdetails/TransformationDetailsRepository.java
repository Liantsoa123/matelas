package com.example.matelas.model.transformationdetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransformationDetailsRepository extends JpaRepository<TransformationDetails, Integer> {
    @Query("SELECT SUM(td.quantite * fu.prixVente) FROM TransformationDetails td JOIN td.usuelle fu")
     Double getTotalValeurVente();

    @Query("SELECT td FROM TransformationDetails td WHERE td.transformation.id = :transformationId")
    List<TransformationDetails> findAllByTransformationId(@Param("transformationId") int transformationId);

    @Query("SELECT td FROM TransformationDetails td WHERE td.usuelle.id = :usuelleid")
    List<TransformationDetails> findAllByUsuelleId(@Param("usuelleid") int usuelleid);

}
