package com.projet.RH.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date birth_date;
    private String adresse;
    @NotBlank
    @Size(max = 20)
    private String username;
    @Column(unique = true)
    private String email;

    private String password;
    private String token;
    private String tel1;
    private String tel2;
    private String code_postal;
    private String nom;
    private String prenom;
    private String ville;
    private String etat;
    @Lob
    @Column(length = 100000)
    private byte[] photo_profil;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<Conges> conges;

    @OneToMany(mappedBy = "users")
    @JsonIgnore
    private Set<Autorisation> autorisations;

    @OneToMany(mappedBy = "usergroupe")
    @JsonIgnore
    private Set<Groupe> groupes;

    @OneToMany(mappedBy = "useravance")
    @JsonIgnore
    private Set<Avance> avance;

    @OneToMany(mappedBy = "userattestation")
    @JsonIgnore
    private Set<Attestation> attestations;

    @OneToMany(mappedBy = "userfiche")
    @JsonIgnore
    private Set<Fiche_de_paie> ficheDePaies;

    @OneToMany(mappedBy = "usernotif")
    @JsonIgnore
    private Set<Notifications> notifications;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(  name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
}
