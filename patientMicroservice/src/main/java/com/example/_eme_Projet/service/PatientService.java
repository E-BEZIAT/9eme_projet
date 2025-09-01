package com.example._eme_Projet.service;

import com.example._eme_Projet.model.Patient;
import com.example._eme_Projet.model.parameter.PatientParameter;
import com.example._eme_Projet.model.response.PatientDTO;
import com.example._eme_Projet.repository.PatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    /** Méthode pour créer un patient
     *
     * @param patientParameter body for create
     */
    public void createNewPatient(PatientParameter patientParameter) {
        Optional<Patient> patient = patientRepository.findByFirstNameAndLastName(
                patientParameter.getFirstName(),
                patientParameter.getLastName()
        );

        if (patient.isPresent()) {
            throw new IllegalArgumentException("L'utilisateur éxiste déjà");
        }

        Patient newPatient = new Patient(
                patientParameter.getLastName(),
                patientParameter.getFirstName(),
                patientParameter.getDateOfBirth(),
                patientParameter.getGender(),
                patientParameter.getAddress(), 
                patientParameter.getPhoneNumber()
                );

        patientRepository.save(newPatient);
    }

    /** Méthode pour modifier les informations d'un patient
     *
     * @param patientParameter body for update
     * @param id id of patient
     */
    public void updatePatient(PatientParameter patientParameter, int id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient introuvable"));

        patient.setLastName(patientParameter.getLastName());
        patient.setFirstName(patientParameter.getFirstName());
        patient.setDateOfBirth(patientParameter.getDateOfBirth());
        patient.setGender(patientParameter.getGender());
        patient.setAddress(patientParameter.getAddress());
        patient.setPhoneNumber(patientParameter.getPhoneNumber());

        patientRepository.save(patient);
    }

    /** Méthode pour supprimer un patient
     *
     * @param id id of patient
     */
    public void deletePatient(int id) {
        patientRepository.deleteById(id);
    }

    /** Méthode pour récupérer les informations d'un patient
     *
     * @param id id of patient
     * @return patient informations
     */
    public Patient readPatient(int id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient introuvable"));

        return new Patient(
                patient.getId(),
                patient.getLastName(),
                patient.getFirstName(),
                patient.getDateOfBirth(),
                patient.getGender(),
                patient.getAddress(),
                patient.getPhoneNumber()
        );
    }

    /** Méthode pour récupérer tous les patients
     *
     * @return All patients
     */
    public List<PatientDTO> readAllPatients() {
        return patientRepository.findAll()
                .stream()
                .map(patient -> new PatientDTO(
                        patient.getId(),
                        patient.getLastName(),
                        patient.getFirstName(),
                        patient.getDateOfBirth(),
                        patient.getGender(),
                        patient.getAddress(),
                        patient.getPhoneNumber()))
                .collect(Collectors.toList());
    }
}
