package com.example._eme_Projet.serviceTest;

import com.example._eme_Projet.model.Patient;
import com.example._eme_Projet.model.parameter.PatientParameter;
import com.example._eme_Projet.model.response.PatientDTO;
import com.example._eme_Projet.repository.PatientRepository;
import com.example._eme_Projet.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PatientServiceTest {

    @InjectMocks
    private PatientService patientService;

    @Mock
    private PatientRepository patientRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        patientService = new PatientService(patientRepository);
    }

    @Test
    public void createPatientTest() {
        PatientParameter patientParameter = new PatientParameter();
        patientParameter.setFirstName("firstName");
        patientParameter.setLastName("lastName");
        patientParameter.setDateOfBirth(LocalDate.of(1990, 1, 1));
        patientParameter.setGender("M");
        patientParameter.setAddress("address");
        patientParameter.setPhoneNumber("phoneNumber");

        when(patientRepository.findByFirstNameAndLastName("firstName", "lastName"))
                .thenReturn(Optional.empty());

        patientService.createNewPatient(patientParameter);

        verify(patientRepository, times(1)).save(any(Patient.class));
    }

    @Test
    public void createPatientErrorAlreadyExistTest() {
        PatientParameter patientParameter = new PatientParameter();
        patientParameter.setFirstName("firstName");
        patientParameter.setLastName("lastName");
        patientParameter.setDateOfBirth(LocalDate.of(1990, 1, 1));
        patientParameter.setGender("M");
        patientParameter.setAddress("address");
        patientParameter.setPhoneNumber("phoneNumber");

        Patient existingPatient = new Patient(
                "lastName",
                "firstName",
                LocalDate.of(1990, 1, 1),
                "M",
                "address",
                "phoneNumber"
        );

        when(patientRepository.findByFirstNameAndLastName("firstName", "lastName"))
                .thenReturn(Optional.of(existingPatient));

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                patientService.createNewPatient(patientParameter)
        );

        assertEquals("L'utilisateur éxiste déjà", exception.getMessage());

        verify(patientRepository, never()).save(any(Patient.class));
        verify(patientRepository, times(1)).findByFirstNameAndLastName("firstName", "lastName");
    }

    @Test
    public void updatePatientTest() {
        int id = 1;
        Patient existingPatient = new Patient(
                "oldLastName",
                "oldFirstName",
                LocalDate.of(1980, 1, 1),
                "M",
                "oldAddress",
                "oldPhoneNumber"
        );

        PatientParameter param = new PatientParameter();
        param.setLastName("lastName");
        param.setFirstName("firstName");
        param.setDateOfBirth(LocalDate.of(1990, 5, 10));
        param.setGender("F");
        param.setAddress("address");
        param.setPhoneNumber("phoneNumber");

        when(patientRepository.findById(id)).thenReturn(Optional.of(existingPatient));

        patientService.updatePatient(param, id);

        assertEquals("lastName", existingPatient.getLastName());
        assertEquals("firstName", existingPatient.getFirstName());
        assertEquals(LocalDate.of(1990, 5, 10), existingPatient.getDateOfBirth());
        assertEquals("F", existingPatient.getGender());
        assertEquals("address", existingPatient.getAddress());
        assertEquals("phoneNumber", existingPatient.getPhoneNumber());

        verify(patientRepository, times(1)).save(existingPatient);
    }

    @Test
    public void updatePatientErrorTest() {
        int id = 1;
        PatientParameter param = new PatientParameter();
        param.setLastName("lastName");

        when(patientRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                patientService.updatePatient(param, id)
        );

        assertEquals("Patient introuvable", ex.getMessage());
        verify(patientRepository, never()).save(any(Patient.class));
    }

    @Test
    public void deletePatientTest() {
        int id = 1;

        patientService.deletePatient(id);

        verify(patientRepository, times(1)).deleteById(id);
    }

    @Test
    public void readPatientTest() {

        int id = 1;
        Patient patient = new Patient(
                id,
                "lastName",
                "firstName",
                LocalDate.of(1990, 1, 1),
                "M",
                "address",
                "phoneNumber"
        );

        when(patientRepository.findById(id)).thenReturn(Optional.of(patient));

        Patient result = patientService.readPatient(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("lastName", result.getLastName());
        assertEquals("firstName", result.getFirstName());
        assertEquals(LocalDate.of(1990, 1, 1), result.getDateOfBirth());
        assertEquals("M", result.getGender());
        assertEquals("address", result.getAddress());
        assertEquals("phoneNumber", result.getPhoneNumber());
    }

    @Test
    public void readPatientErrorTest() {
        int id = 1;
        when(patientRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> patientService.readPatient(id));

        assertEquals("Patient introuvable", exception.getMessage());
    }

    @Test
    public void readAllPatientsTest() {
        Patient patient1 = new Patient(1, "Doe", "John", LocalDate.of(1990, 1, 1), "M", "123 Street", "0102030405");
        Patient patient2 = new Patient(2, "Smith", "Jane", LocalDate.of(1992, 5, 10), "F", "456 Avenue", "0203040506");

        List<Patient> patients = Arrays.asList(patient1, patient2);

        when(patientRepository.findAll()).thenReturn(patients);

        List<PatientDTO> result = patientService.readAllPatients();

        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals(1, result.get(0).getId());
        assertEquals("Doe", result.get(0).getLastName());
        assertEquals("John", result.get(0).getFirstName());

        assertEquals(2, result.get(1).getId());
        assertEquals("Smith", result.get(1).getLastName());
        assertEquals("Jane", result.get(1).getFirstName());

        verify(patientRepository, times(1)).findAll();
    }

    @Test
    public void readAllPatientsErrorTest() {
        when(patientRepository.findAll()).thenReturn(Collections.emptyList());

        List<PatientDTO> result = patientService.readAllPatients();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(patientRepository, times(1)).findAll();
    }


}
