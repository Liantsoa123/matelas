package com.example.matelas.model.block;

import com.example.matelas.model.transformation.Transformation;
import com.example.matelas.model.transformation.TransformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlockService {

    private final BlockRepository blockRepository;

    private final TransformationService transformationService;

    @Autowired
    public BlockService(BlockRepository blockRepository, @Lazy TransformationService transformationService) {
        this.blockRepository = blockRepository;
        this.transformationService = transformationService;
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

    public  Block getBLockSrc( int idBLock ){
        Transformation t = transformationService.findTransformationbyIDReste(idBLock);
        if ( t == null ){
            System.out.println("Block src found "+idBLock);
            return getBlockById(idBLock).get();
        }
        Block mere = null;
        while ( t!=null ){
            mere = t.getMere();
            t = transformationService.findTransformationbyIDReste(mere.getId());
        }
        return  mere ;
    }

}
