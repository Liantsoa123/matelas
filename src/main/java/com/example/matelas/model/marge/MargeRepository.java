package com.example.matelas.model.marge;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MargeRepository extends JpaRepository<Marge, Integer> {

}
