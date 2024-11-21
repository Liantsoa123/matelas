package com.example.matelas.model.block;

import com.example.matelas.dto.blockgroupbymachinedto.BlockGroupDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    @Query(value = "SELECT nextval('block_id_seq')", nativeQuery = true)
    long getNextSequence();

    /*@Query("SELECT b.machine.id AS machineId, b.machine.name AS machineName, COUNT(b) AS quantite " +
            "FROM Block b " +
            "GROUP BY b.machine.id, b.machine.name")
    List<BlockGroupDTO> findAllBlocksGroupedByMachine();*/

    @Query(value = "SELECT b.machine_id AS machineId, m.name AS machineName, " +
            "COUNT(b.id) AS quantite, SUM(b.prix_revient) AS totalPrixRevient, " +
            "SUM(b.prix_theorique) AS totalPrixTheorique, " +
            "(SUM(b.prix_revient) - SUM(b.prix_theorique)) AS difference " +
            "FROM block b JOIN machine m ON m.id = b.machine_id " +
            "GROUP BY b.machine_id, m.name", nativeQuery = true)
    List<Object[]> findAllBlocksGroupedByMachineNative();

}
