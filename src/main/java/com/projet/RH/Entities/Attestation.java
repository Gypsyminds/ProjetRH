package com.projet.RH.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Attestation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description ;
    private Date Date_debut;
    private Date Date_fin;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Type_Attestation type;
    @ManyToOne
    User userattestation ;
}
