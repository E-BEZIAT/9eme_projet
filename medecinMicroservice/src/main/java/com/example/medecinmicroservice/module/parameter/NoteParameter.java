package com.example.medecinmicroservice.module.parameter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;


@Getter
@Setter
@ToString
public class NoteParameter {

    @Id
    private String id;
    @NotNull
    private int patientId;
    @NotNull(message = "La date de la note ne peut pas être vide")
    private LocalDateTime dateOfVisite;
    @NotBlank(message = "Le contenu de la note ne peut pas être vide")
    private String note;
}
