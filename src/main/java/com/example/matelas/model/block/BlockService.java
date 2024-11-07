package com.example.matelas.model.block;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlockService {

    private final BlockRepository blockRepository;

    @Autowired
    public BlockService(BlockRepository blockRepository) {
        this.blockRepository = blockRepository;
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
}
