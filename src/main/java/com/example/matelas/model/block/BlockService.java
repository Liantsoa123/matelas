package com.example.matelas.model.block;

import com.example.matelas.dto.blockgroupbymachinedto.BlockGroupDTO;
import com.example.matelas.dto.restesstockdto.RestesStockDTO;
import com.example.matelas.model.achat.AchatMatierePremiere;
import com.example.matelas.model.achat.AchatMatierePremiereService;
import com.example.matelas.model.csv.CsvService;
import com.example.matelas.dto.restesstockdto.RestesStockService;
import com.example.matelas.model.formuleDetails.FormuleDetails;
import com.example.matelas.model.matierepremier.MatierePremierService;
import com.example.matelas.model.sortie.SortieMatierePremiere;
import com.example.matelas.model.sortie.SortieMatierePremiereService;
import com.example.matelas.model.transformation.Transformation;
import com.example.matelas.model.transformation.TransformationService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BlockService {

    private final BlockRepository blockRepository;

    private final TransformationService transformationService;

    private final EntityManager entityManager;
    private final MatierePremierService matierePremierService;
    private final SortieMatierePremiereService sortieMatierePremiereService;
    private final AchatMatierePremiereService achatMatierePremiereService;

    @Autowired
    public BlockService(BlockRepository blockRepository, @Lazy TransformationService transformationService, EntityManager entityManager, MatierePremierService matierePremierService, SortieMatierePremiereService sortieMatierePremiereService, AchatMatierePremiereService achatMatierePremiereService) {
        this.blockRepository = blockRepository;
        this.transformationService = transformationService;
        this.entityManager = entityManager;
        this.matierePremierService = matierePremierService;
        this.sortieMatierePremiereService = sortieMatierePremiereService;
        this.achatMatierePremiereService = achatMatierePremiereService;
    }

    // Get all blocks
    public List<Block> getAllBlocks() {
        return blockRepository.findAll();
    }


    // Get a block by ID
    public Optional<Block> getBlockById(int id) {
        return blockRepository.findById(id);
    }


    public List<Block> getBlocksNotInMereId() {
        return blockRepository.findBlocksNotInMereId();
    }

    public List<Block> getAllPrinicpalMere() {
        return blockRepository.findBlocksIsMere();
    }

    // Save a new block
    public Block saveBlock(Block block) {
        return blockRepository.save(block);
    }

    public Block saveBlockAutoName(Block block) {
        block.setName("block" + blockRepository.getNextSequence());
        return blockRepository.save(block);
    }

    // Update an existing block
    public Block updateBlock(int id, Block blockDetails) {
        return blockRepository.findById(id).map(block -> {
            block.setLongueur(blockDetails.getLongueur());
            block.setLargeur(blockDetails.getLargeur());
            block.setEpaisseur(blockDetails.getEpaisseur());
            block.setPrixRevient(blockDetails.getPrixRevient());
            block.setCreationBlock(blockDetails.getCreationBlock());
            block.setMere(blockDetails.getMere());
            block.setName(blockDetails.getName());
            return blockRepository.save(block);
        }).orElseThrow(() -> new RuntimeException("Block not found with id " + id));
    }

    // Delete a block by ID
    public void deleteBlock(int id) {
        if (blockRepository.existsById(id)) {
            blockRepository.deleteById(id);
        } else {
            throw new RuntimeException("Block not found with id " + id);
        }
    }

    public Optional<Block> getBlocksByMereId(Integer mereId) {
        return blockRepository.findBlocksByMereId(mereId);
    }

    public Block getBLockSrc(int idBLock) {
        Transformation t = transformationService.findTransformationbyIDReste(idBLock);
        if (t == null) {
            System.out.println("Block src found " + idBLock);
            return getBlockById(idBLock).get();
        }
        Block mere = null;
        while (t != null) {
            mere = t.getMere();
            t = transformationService.findTransformationbyIDReste(mere.getId());
        }
        return mere;
    }


    public double getprixRevientTheorique(Date date, double volume, List<FormuleDetails> formuleDetails, List<AchatMatierePremiere> achatMatierePremiereList) {
        double prixRevient = 0;
        System.out.println("volume=" + volume);
        for (FormuleDetails formuleDetails1 : formuleDetails) {
            double quantiteIlaina = formuleDetails1.getQuantite() * volume;
            System.out.println("volume ilaina = " + quantiteIlaina);
            for (AchatMatierePremiere achatMatierePremiere : achatMatierePremiereList) {
                double ilainaachat = 0;
                if (achatMatierePremiere.getMatierePremier().getId() == formuleDetails1.getMatierePremier().getId() && !achatMatierePremiere.getDateAchat().after(date)) {
                    if (achatMatierePremiere.getQuantite() > 0) {
                        ilainaachat = Math.min(achatMatierePremiere.getQuantite(), quantiteIlaina);
                        quantiteIlaina -= ilainaachat;
                        achatMatierePremiere.setQuantite(achatMatierePremiere.getQuantite() - ilainaachat);
                        prixRevient += ilainaachat * achatMatierePremiere.getPrixRevient();
                    }
                }

            }
            if (quantiteIlaina > 0) {
                throw new RuntimeException("Pas de stock suffisant pour la matiere premiere " + formuleDetails1.getMatierePremier().getNom());
            }
        }
        return prixRevient;
    }

    /*@Transactional
    public void importCsv (  String csvPath  ){
        String query = csvService.cvsToQueryBlock(csvPath);
        System.out.println("Query = "+query);
        entityManager.createNativeQuery(query).executeUpdate();
        System.out.println("Insertion finished");
    }*/

    @Transactional
    public void importCsv(String query) {
        entityManager.createNativeQuery(query).executeUpdate();
        System.out.println("Insertion finished");
    }

    /*public List<BlockGroupDTO> getAllBlocksGroupedByMachine() {
        return blockRepository.findAllBlocksGroupedByMachine();
    }*/

    public List<BlockGroupDTO> getAllBlocksGroupedByMachine() {
        List<Object[]> results = blockRepository.findAllBlocksGroupedByMachineNative();
        return results.stream().map(row -> new BlockGroupDTO(
                (int) ((Number) row[0]).longValue(),
                (String) row[1],
                ((Number) row[2]).longValue(),
                ((Number) row[3]).doubleValue(),
                ((Number) row[4]).doubleValue(),
                ((Number) row[5]).doubleValue(),
                ((Number) row[6]).doubleValue()
        )).collect(Collectors.toList());
    }

    public List<BlockGroupDTO> getAllBlocksGroupedByMachine(int year) {
        List<Object[]> results = blockRepository.findAllBlocksGroupedByMachineNative(year);
        return results.stream().map(row -> new BlockGroupDTO(
                (int) ((Number) row[0]).longValue(),
                (String) row[1],
                ((Number) row[2]).longValue(),
                ((Number) row[3]).doubleValue(),
                ((Number) row[4]).doubleValue(),
                ((Number) row[5]).doubleValue(),
                ((Number) row[6]).doubleValue()
        )).collect(Collectors.toList());
    }


}
