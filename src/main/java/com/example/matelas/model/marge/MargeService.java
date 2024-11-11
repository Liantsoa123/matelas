package com.example.matelas.model.marge;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MargeService {

    private final MargeRepository margeRepository;

    @Autowired
    public MargeService(MargeRepository margeRepository) {
        this.margeRepository = margeRepository;
    }

    // Find all Marge entries
    public List<Marge> findAllMarges() {
        return margeRepository.findAll();
    }

    // Find a Marge by ID
    public Optional<Marge> findMargeById(int id) {
        return margeRepository.findById(id);
    }

    // Save or update a Marge
    public Marge saveMarge(Marge marge) {
        return margeRepository.save(marge);
    }

    // Delete a Marge by ID
    public void deleteMargeById(int id) {
        margeRepository.deleteById(id);
    }
}
