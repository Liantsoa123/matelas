package com.example.matelas.model.transformation;

import com.example.matelas.model.block.Block;
import com.example.matelas.model.block.BlockService;
import com.example.matelas.model.transformationdetails.TransformationDetails;
import com.example.matelas.model.transformationdetails.TransformationDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransformationService {

    private final TransformationRepository transformationRepository;
    private final TransformationDetailsService transformationDetailsService;
    private final BlockService blockService;

    @Autowired
    public TransformationService(TransformationRepository transformationRepository, TransformationDetailsService transformationDetailsService, BlockService blockService) {
        this.transformationRepository = transformationRepository;
        this.transformationDetailsService = transformationDetailsService;
        this.blockService = blockService;
    }

    // Get all transformations
    public List<Transformation> getAllTransformations() {
        return transformationRepository.findAll();
    }

    // Get a Transformation by ID
    public Optional<Transformation> getTransformationById(int id) {
        return transformationRepository.findById(id);
    }

    // Save a new Transformation
    public Transformation saveTransformation(Transformation transformation) {
        return transformationRepository.save(transformation);
    }

    // Update an existing Transformation
    public Transformation updateTransformation(int id, Transformation transformationDetails) {
        return transformationRepository.findById(id).map(transformation -> {
            transformation.setMere(transformationDetails.getMere());
            transformation.setReste(transformationDetails.getReste());
            transformation.setDateTransformation(transformationDetails.getDateTransformation());
            return transformationRepository.save(transformation);
        }).orElseThrow(() -> new RuntimeException("Transformation not found with id " + id));
    }

    // Delete a Transformation by ID
    public void deleteTransformation(int id) {
        if (transformationRepository.existsById(id)) {
            transformationRepository.deleteById(id);
        } else {
            throw new RuntimeException("Transformation not found with id " + id);
        }
    }

    public Transformation findTransformationByMereBlockId(int idMere) {
        Optional<Transformation> transformationOpt = transformationRepository.findTransformationsByMereBlockId(idMere);
        return transformationOpt.orElse(null);  // Returns the Transformation if present, otherwise returns null
    }

    public Transformation findTransformationbyIDReste(int idreste) {
        Optional<Transformation> t = transformationRepository.findTransformationsByResteBlockId(idreste);
        return  t.orElse(null);
    }


    public void updatePrixRevient(double newPrixRevient, int idmere) {
        Optional<Block> mere = blockService.getBlockById(idmere);
        double rapport = newPrixRevient / mere.get().getPrixRevient();
        System.out.println("Rapport: " + rapport);
        mere.get().setPrixRevient(newPrixRevient);  // Update the 'mere' block's price
        blockService.updateBlock(mere.get().getId(), mere.orElse(null));

        // Retrieve the first transformation for the mere block
        Transformation transformation = findTransformationByMereBlockId(mere.get().getId());

        // Iterate through each transformation and its child blocks
        while (transformation != null) {
            Block child = transformation.getReste();
            if (child != null) {
                // Update the prixRevient for the child block based on the rapport
                child.setPrixRevient(child.getPrixRevient() * rapport);
                blockService.updateBlock(child.getId(), child);

                // Update each TransformationDetails for the current transformation
                List<TransformationDetails> transformationDetails = transformationDetailsService
                        .getTransformationDetailsByTransformationId(transformation.getId());
                for (TransformationDetails td : transformationDetails) {
                    td.setPrixRevient(td.getPrixRevient() * rapport);  // Apply the rapport
                    transformationDetailsService.updateTransformationDetails(td.getId(), td);  // Save updated details
                }
            }
            // Move to the next child transformation
            transformation = findTransformationByMereBlockId(child.getId());
        }
    }


}
