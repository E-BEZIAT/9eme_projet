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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class DiabetesService {

    private final PatientFeign patientFeign;
    private final MedecinFeign medecinFeign;

    public DiabetesService(PatientFeign patientFeign, MedecinFeign medecinFeign) {
        this.patientFeign = patientFeign;
        this.medecinFeign = medecinFeign;
    }

    private static final Map<String, String> TRIGGERS_MAP = Map.ofEntries(
            Map.entry("fumeur", "tabac"),
            Map.entry("fumeuse", "tabac"),
            Map.entry("fume", "tabac"),
            Map.entry("fumer", "tabac"),
            Map.entry("hémoglobine a1c", "hemoglobine"),
            Map.entry("microalbumine", "microalbumine"),
            Map.entry("taille", "taille"),
            Map.entry("poids", "poids"),
            Map.entry("anormal", "anormal"),
            Map.entry("cholestérol", "cholesterol"),
            Map.entry("vertiges", "vertiges"),
            Map.entry("rechute", "rechute"),
            Map.entry("réaction", "reaction"),
            Map.entry("anticorps", "anticorps")
    );

    /** Ce service calcul l'âge en faisant la différence entre la date du jour et la date de naissance
     *
     * @param birthDate birthDate
     * @return age of patient
     */
    public int calculateAge(String birthDate) {
        LocalDate birth = LocalDate.parse(birthDate);
        return Period.between(birth, LocalDate.now()).getYears();
    }

    /** Ce service compte le nombre de mot trigger dans les notes des patients
     *
     * @param noteDTO note
     * @return number of triggers words
     */
    public int countTriggersWords(List<NoteDTO> noteDTO) {
        Set<String> foundTriggers = new HashSet<>();
        for (NoteDTO note : noteDTO) {
            String text = note.getNote().toLowerCase();
            for (Map.Entry<String, String> entry : TRIGGERS_MAP.entrySet()) {
                if (text.contains(entry.getKey().toLowerCase())) {
                    foundTriggers.add(entry.getValue());
                }
            }
        }
        return foundTriggers.size();
    }

    /** Ce service détermine le niveau de risque en fonction du nombre de mots triggers comptés dans le service
     * précédent
     *
     * @param gender Male or female
     * @param age age of patient
     * @param triggerCount triggers words
     * @return risk of diabetes
     */
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

    /** Ce service renvoie les patients avec leur risque de diabète
     *
     * @param patientId id of patient
     * @return diabetesDTO
     */
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
