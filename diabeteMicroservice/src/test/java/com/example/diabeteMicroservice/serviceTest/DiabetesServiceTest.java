package com.example.diabeteMicroservice.serviceTest;

import com.example.diabeteMicroservice.feign.MedecinFeign;
import com.example.diabeteMicroservice.feign.PatientFeign;
import com.example.diabeteMicroservice.model.RiskLevel;
import com.example.diabeteMicroservice.model.response.DiabetesDTO;
import com.example.diabeteMicroservice.model.response.NoteDTO;
import com.example.diabeteMicroservice.model.response.PatientDTO;
import com.example.diabeteMicroservice.service.DiabetesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class DiabetesServiceTest {

    @InjectMocks
    private DiabetesService diabetesService;

    @Mock
    private MedecinFeign medecinFeign;

    @Mock
    private PatientFeign patientFeign;

    @BeforeEach
    public void setUp() {

        MockitoAnnotations.openMocks(this);
        diabetesService = new DiabetesService(patientFeign, medecinFeign);
    }

    @Test
    public void calculateAgeTest() {
        LocalDate birthDate = LocalDate.now().minusYears(20);
        String birthDateString = birthDate.toString();

        int age = diabetesService.calculateAge(birthDateString);

        assertEquals(20, age);
    }

    @Test
    public void countTriggerWordsTestZero() {
        NoteDTO note1 = new NoteDTO();
        note1.setNote("Le patient est en bonne santé");

        NoteDTO note2 = new NoteDTO();
        note2.setNote("Pas de symptômes");

        List<NoteDTO> notes = Arrays.asList(note1, note2);

        int result = diabetesService.countTriggersWords(notes);

        assertEquals(0, result);
    }

    @Test
    public void countTriggerWordsTestFour() {
        NoteDTO note1 = new NoteDTO();
        note1.setNote("Le patient est un fumeur avec du poids");

        NoteDTO note2 = new NoteDTO();
        note2.setNote("Le patient a une hémoglobine A1C et un cholestérol très évelés");

        List<NoteDTO> notes = Arrays.asList(note1, note2);

        int result = diabetesService.countTriggersWords(notes);

        assertEquals(4, result);
    }

    @Test
    public void determineRiskLevelTestNone() {
        RiskLevel result = diabetesService.determineRiskLevel("M", 40, 0);
        assertEquals(RiskLevel.NONE, result);
    }

    @Test
    public void determineRiskLevelTestBorderline() {
        assertEquals(RiskLevel.BORDERLINE, diabetesService.determineRiskLevel("M", 40, 2));
        assertEquals(RiskLevel.BORDERLINE, diabetesService.determineRiskLevel("F", 40, 5));
    }

    @Test
    public void determineRiskLevelTestInDanger() {
        assertEquals(RiskLevel.IN_DANGER, diabetesService.determineRiskLevel("M", 40, 6));
        assertEquals(RiskLevel.IN_DANGER, diabetesService.determineRiskLevel("F", 40, 7));
        assertEquals(RiskLevel.IN_DANGER, diabetesService.determineRiskLevel("M", 20, 3));
        assertEquals(RiskLevel.IN_DANGER, diabetesService.determineRiskLevel("F", 20, 5));
    }

    @Test
    public void determineRiskLevelTestEarlyOnset() {
        assertEquals(RiskLevel.EARLY_ONSET, diabetesService.determineRiskLevel("M", 40, 10));
        assertEquals(RiskLevel.EARLY_ONSET, diabetesService.determineRiskLevel("M", 20, 6));
        assertEquals(RiskLevel.EARLY_ONSET, diabetesService.determineRiskLevel("F", 20, 7));
    }

    @Test
    public void diabetesReportTest() {
        LocalDate birthDate = LocalDate.of(1994, 1, 21);

        PatientDTO patientDTO = new PatientDTO(
                1,
                "Doe",
                "John",
                birthDate,
                "M",
                "4 rue du test",
                "111-222-3333"
        );

        NoteDTO note1 = new NoteDTO();
        note1.setNote("Le patient est un fumeur");
        List<NoteDTO> notes = List.of(note1);

        when(patientFeign.getPatientById(1)).thenReturn(patientDTO);
        when(medecinFeign.getNotesByPatientId(1)).thenReturn(notes);

        DiabetesDTO result = diabetesService.diabetesReport(1);

        assertNotNull(result);
        assertEquals(RiskLevel.NONE, result.getRiskLevel());
        assertEquals(result.getAge(), 31);
        assertEquals(result.getFirstName(), patientDTO.getFirstName());
        assertEquals(result.getLastName(), patientDTO.getLastName());
    }


}
