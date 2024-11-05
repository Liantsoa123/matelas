package com.example.matelas.model.transformationdetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransformationDetailsRepository extends JpaRepository<TransformationDetails, Integer> {
}
