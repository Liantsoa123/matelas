package com.example.matelas.transformation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransformationRepository extends JpaRepository<Transformation, Integer> {

}
