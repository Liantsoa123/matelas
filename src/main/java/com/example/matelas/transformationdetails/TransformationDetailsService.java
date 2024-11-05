package com.example.matelas.transformationdetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransformationDetailsService {

    private final TransformationDetailsRepository transformationDetailsRepository;

    @Autowired
    public TransformationDetailsService(TransformationDetailsRepository transformationDetailsRepository) {
        this.transformationDetailsRepository = transformationDetailsRepository;
    }

    // Get all TransformationDetails
    public List<TransformationDetails> getAllTransformationDetails() {
        return transformationDetailsRepository.findAll();
    }

    // Get a TransformationDetails by ID
    public Optional<TransformationDetails> getTransformationDetailsById(int id) {
        return transformationDetailsRepository.findById(id);
    }

    // Save a new TransformationDetails
    public TransformationDetails saveTransformationDetails(TransformationDetails transformationDetails) {
        return transformationDetailsRepository.save(transformationDetails);
    }

    // Update an existing TransformationDetails
    public TransformationDetails updateTransformationDetails(int id, TransformationDetails transformationDetails) {
        return transformationDetailsRepository.findById(id).map(details -> {
            details.setUsuelle(transformationDetails.getUsuelle());
            details.setTransformation(transformationDetails.getTransformation());
            return transformationDetailsRepository.save(details);
        }).orElseThrow(() -> new RuntimeException("TransformationDetails not found with id " + id));
    }

    // Delete a TransformationDetails by ID
    public void deleteTransformationDetails(int id) {
        if (transformationDetailsRepository.existsById(id)) {
            transformationDetailsRepository.deleteById(id);
        } else {
            throw new RuntimeException("TransformationDetails not found with id " + id);
        }
    }
}
