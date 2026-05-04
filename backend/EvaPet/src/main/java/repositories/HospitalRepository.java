package com.example.tablascarlos.repositories;

import com.example.tablascarlos.models.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HospitalRepository extends JpaRepository<Hospital, Integer> {

    @Query("SELECT h FROM Hospital h WHERE h.city = :city")
    List<Hospital> findByCity(String city);

    @Query("SELECT h FROM Hospital h WHERE h.name LIKE %:name%")
    List<Hospital> findByNameLike(String name);

    List<Hospital> findByNameContaining(String name);
}