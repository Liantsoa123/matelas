package com.example.matelas.transformationdetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransformationDetailsRepository extends JpaRepository<TransformationDetails, Integer> {
}
