package com.example.diabeteMicroservice.service;

import com.example.diabeteMicroservice.feign.MedecinFeign;
import com.example.diabeteMicroservice.feign.PatientFeign;
import com.example.diabeteMicroservice.model.RiskLevel;
import com.example.diabeteMicroservice.model.response.DiabetesDTO;
import com.example.diabeteMicroservice.model.response.NoteDTO;
import com.example.diabeteMicroservice.model.response.PatientDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class DiabetesService {

    private final PatientFeign patientFeign;
    private final MedecinFeign medecinFeign;

    public DiabetesService(PatientFeign patientFeign, MedecinFeign medecinFeign) {
        this.patientFeign = patientFeign;
        this.medecinFeign = medecinFeign;
    }

    private static final String[] TRIGGERSLIST = {
            "Hémoglobine A1C", "Microalbumine", "Taille", "Poids",
            "Fumeur", "Fumeuse", "Fume", "Fumer", "Anormal", "Cholestérol",
            "Vertiges", "Rechute", "Réaction", "Anticorps"
    };

    public int calculateAge(String birthDate) {
        LocalDate birth = LocalDate.parse(birthDate);
        return Period.between(birth, LocalDate.now()).getYears();
    }

    public int countTriggersWords(List<NoteDTO> noteDTO) {
        int counter = 0;
        for (NoteDTO note : noteDTO) {
            String text = note.getNote().toLowerCase();
            for (String trigger : TRIGGERSLIST) {
                if (text.contains(trigger.toLowerCase())) {
                    counter++;
                }
            }
        }
        return counter;
    }

    public RiskLevel determineRiskLevel(String gender, int age, int triggerCount) {
        if (triggerCount == 0) {
            return RiskLevel.NONE;
        }

        if (age > 30) {
            if (triggerCount >= 2 && triggerCount <= 5) {
                return RiskLevel.BORDERLINE;
            }
            if (triggerCount >= 6 && triggerCount <= 7) {
                return RiskLevel.IN_DANGER;
            }
            if (triggerCount >= 8) {
                return RiskLevel.EARLY_ONSET;
            }
        }
        else
            if(gender.equals("M")) {
                if (triggerCount >= 3 && triggerCount <= 4) {
                    return RiskLevel.IN_DANGER;
                }
                if (triggerCount >= 5) {
                    return RiskLevel.EARLY_ONSET;
                }
            }
            if (gender.equals("F")) {
                if (triggerCount >= 4 && triggerCount <= 6) {
                    return RiskLevel.IN_DANGER;
                }
                if (triggerCount >= 7) {
                    return RiskLevel.EARLY_ONSET;
                }
            }
            return RiskLevel.NONE;
    }

    public DiabetesDTO diabetesReport(int patientId) {
        PatientDTO patientDTO = patientFeign.getPatientById(patientId);
        List<NoteDTO> notesDTO = medecinFeign.getNotesByPatientId(patientId);

        int age = calculateAge(patientDTO.getDateOfBirth().toString());
        int triggerCount = countTriggersWords(notesDTO);

        RiskLevel riskLevel = determineRiskLevel(patientDTO.getGender(), age, triggerCount);

        DiabetesDTO diabetesDTO = new DiabetesDTO();
        diabetesDTO.setPatientId(patientDTO.getId());
        diabetesDTO.setLastName(patientDTO.getLastName());
        diabetesDTO.setFirstName(patientDTO.getFirstName());
        diabetesDTO.setAge(age);
        diabetesDTO.setRiskLevel(riskLevel);

        return diabetesDTO;
    }

}
