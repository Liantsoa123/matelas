package com.example.matelas.model.block;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlockRepository extends JpaRepository<Block, Integer> {
    @Query("SELECT b FROM Block b WHERE b.mere.id = :mereId")
    Optional<Block> findBlocksByMereId(@Param("mereId") int mereId);
    @Query("SELECT b FROM Block b WHERE b.id NOT IN (SELECT b2.mere.id FROM Block b2 WHERE b2.mere.id IS NOT NULL ) and b.longueur * b.epaisseur * b.largeur != 0  ")
    List<Block> findBlocksNotInMereId();
    @Query("SELECT b FROM Block b WHERE b.mere.id IS null ")
    List<Block> findBlocksIsMere();

}
