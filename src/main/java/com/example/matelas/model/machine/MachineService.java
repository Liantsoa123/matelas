package com.example.matelas.model.machine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MachineService {

    private final MachineRepository machineRepository;

    @Autowired
    public MachineService(MachineRepository machineRepository) {
        this.machineRepository = machineRepository;
    }

    // Create a new machine
    public Machine saveMachine(Machine machine) {
        return machineRepository.save(machine);
    }

    // Get all machines
    public List<Machine> getAllMachines() {
        return machineRepository.findAll();
    }

    // Get a machine by ID
    public Optional<Machine> getMachineById(int id) {
        return machineRepository.findById(id);
    }

    // Update a machine
    public Machine updateMachine(int id, Machine updatedMachine) {
        return machineRepository.findById(id)
                .map(machine -> {
                    machine.setName(updatedMachine.getName());
                    return machineRepository.save(machine);
                }).orElseThrow(() -> new RuntimeException("Machine not found with ID " + id));
    }

    // Delete a machine
    public void deleteMachine(int id) {
        if (!machineRepository.existsById(id)) {
            throw new RuntimeException("Machine not found with ID " + id);
        }
        machineRepository.deleteById(id);
    }
}
