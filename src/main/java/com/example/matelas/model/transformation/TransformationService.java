package com.example.matelas.model.transformation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransformationService {

    private final TransformationRepository transformationRepository;

    @Autowired
    public TransformationService(TransformationRepository transformationRepository) {
        this.transformationRepository = transformationRepository;
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
}
