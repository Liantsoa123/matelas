package com.example.matelas.model.formuleDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FormuleDetailsService {

    @Autowired
    private FormuleDetailsRepository formuleDetailsRepository;

    // Get all FormuleDetails by Formule ID
    public List<FormuleDetails> getFormuleDetailsByFormuleId(int formuleId) {
        return formuleDetailsRepository.findByFormuleId(formuleId);
    }


    // Retrieve all FormuleDetails
    public List<FormuleDetails> getAllFormuleDetails() {
        return formuleDetailsRepository.findAll();
    }

    // Retrieve a FormuleDetails by ID
    public FormuleDetails getFormuleDetailsById(int id) {
        Optional<FormuleDetails> optionalFormuleDetails = formuleDetailsRepository.findById(id);
        if (optionalFormuleDetails.isPresent()) {
            return optionalFormuleDetails.get();
        } else {
            throw new RuntimeException("FormuleDetails not found for ID: " + id);
        }
    }

    // Save or update a FormuleDetails
    public FormuleDetails saveFormuleDetails(FormuleDetails formuleDetails) {
        return formuleDetailsRepository.save(formuleDetails);
    }

    // Delete a FormuleDetails by ID
    public void deleteFormuleDetails(int id) {
        if (formuleDetailsRepository.existsById(id)) {
            formuleDetailsRepository.deleteById(id);
        } else {
            throw new RuntimeException("FormuleDetails not found for ID: " + id);
        }
    }

}
