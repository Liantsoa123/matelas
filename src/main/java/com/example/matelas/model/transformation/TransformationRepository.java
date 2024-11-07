package com.example.matelas.model.transformation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransformationRepository extends JpaRepository<Transformation, Integer> {
    @Query("SELECT t FROM Transformation t WHERE   t.mere.id = :blockMereId")
    Optional<Transformation> findAllTransformationsByMereBlockId(@Param("blockMereId") int  blockMereId);

}
