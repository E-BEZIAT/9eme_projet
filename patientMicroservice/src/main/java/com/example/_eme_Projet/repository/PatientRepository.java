package com.example._eme_Projet.repository;

import com.example._eme_Projet.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

    Optional<Patient> findByFirstNameAndLastName(String firstName, String lastName);
}
