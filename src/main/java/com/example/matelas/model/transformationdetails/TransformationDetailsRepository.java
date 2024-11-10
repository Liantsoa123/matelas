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


    @Query(value = """
        WITH RECURSIVE block_hierarchy AS (
            SELECT id FROM block WHERE id = :block_id
            UNION ALL
            SELECT b.id FROM block b
            INNER JOIN block_hierarchy bh ON b.mere_id = bh.id
        )
        SELECT td.* FROM transformation_details td
        JOIN transformation t ON td.transformation_id = t.id
        JOIN block_hierarchy bh ON t.mere_block_id = bh.id
        """, nativeQuery = true)
    List<TransformationDetails> findAllByBlockIdRecursive(@Param("block_id") int blockId);

    @Query(value = """
        WITH RECURSIVE block_hierarchy AS (
            SELECT id FROM block WHERE id = :block_id
            UNION ALL
            SELECT b.id FROM block b
            INNER JOIN block_hierarchy bh ON b.mere_id = bh.id
        )
        SELECT SUM(td.quantite) FROM transformation_details td
        JOIN transformation t ON td.transformation_id = t.id
        JOIN block_hierarchy bh ON t.mere_block_id = bh.id
        WHERE td.usuelle_id = :idUsuelle
        """, nativeQuery = true)
    Double findSumQuantiteByBlockIdAndUsuelleId(@Param("block_id") int blockId, @Param("idUsuelle") int idUsuelle);


}
