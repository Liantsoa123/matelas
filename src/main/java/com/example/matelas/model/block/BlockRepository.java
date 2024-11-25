package com.example.matelas.model.block;

import com.example.matelas.dto.blockgroupbymachinedto.BlockGroupDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Pageable;
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


    @Query("SELECT b FROM Block b")
    List<Block> getBlockWithLimit(Pageable pageable);


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

    @Query(value = "SELECT\n" +
            "    machine.id AS machineId,\n" +
            "    machine.name AS machineName,\n" +
            "    COUNT(block.id) AS quantite,\n" +
            "    SUM(block.prix_revient) AS totalPrixRevient,\n" +
            "    SUM(block.prix_theorique) AS totalPrixTheorique,\n" +
            "    SUM(block.longueur * block.largeur * block.epaisseur) AS totalVolume,\n" +
            "    ABS ((SUM(block.prix_revient) - SUM(block.prix_theorique))) AS difference\n" +
            "FROM\n" +
            "    block\n" +
            "        LEFT JOIN\n" +
            "    machine ON block.machine_id = machine.id\n" +
            "GROUP BY\n" +
            "    machine.id, machine.name\n" +
            "ORDER BY\n" +
            "    ABS(SUM(block.prix_revient) - SUM(block.prix_theorique)) ASC ;", nativeQuery = true)
    List<Object[]> findAllBlocksGroupedByMachineNative();


    @Query(value = "SELECT\n" +
            "    machine.id AS machineId,\n" +
            "    machine.name AS machineName,\n" +
            "    COUNT(block.id) AS quantite,\n" +
            "    SUM(block.prix_revient) AS totalPrixRevient,\n" +
            "    SUM(block.prix_theorique) AS totalPrixTheorique,\n" +
            "    SUM(block.longueur * block.largeur * block.epaisseur) AS totalVolume,\n" +
            "    ABS(SUM(block.prix_revient) - SUM(block.prix_theorique)) AS difference\n" +
            "FROM\n" +
            "    block\n" +
            "        LEFT JOIN\n" +
            "    machine ON block.machine_id = machine.id\n" +
            "WHERE\n" +
            "    EXTRACT(YEAR FROM block.creation_block) = :year \n" +
            "GROUP BY\n" +
            "    machine.id, machine.name\n" +
            "ORDER BY\n" +
            "    ABS(SUM(block.prix_revient) - SUM(block.prix_theorique)) ASC;" , nativeQuery = true)
    List<Object[]> findAllBlocksGroupedByMachineNative( @Param("year") int year );

    @Query(value =
            "WITH volume_possible AS (" +
                    "    SELECT " +
                    "        apm.matiere_premier_id, " +
                    "        mp.nom AS matiere_nom, " +
                    "        fd.quantite AS quantite_par_formule, " +
                    "        SUM(apm.quantite) AS total_achetee, " +
                    "        (SUM(apm.quantite) / fd.quantite) AS volume_possible " +
                    "    FROM " +
                    "        achat_matiere_premiere apm " +
                    "    JOIN " +
                    "        formule_details fd ON apm.matiere_premier_id = fd.matiere_premier_id " +
                    "    JOIN " +
                    "        matiere_premier mp ON mp.id = apm.matiere_premier_id " +
                    "    WHERE " +
                    "        fd.formule_id = 1 " +
                    "    GROUP BY " +
                    "        apm.matiere_premier_id, fd.quantite, mp.nom " +
                    ") " +
                    "SELECT " +
                    "    MIN(volume_possible) AS max_volume_possible " +
                    "FROM " +
                    "    volume_possible",
            nativeQuery = true)
    double getMaxVolumePossible();

}
