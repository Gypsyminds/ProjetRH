package com.projet.RH.payload.request;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;
@Getter
@Setter
public class SignupRequest {
  @NotBlank
  @Size(min = 4, max = 20)
  private String username;
  private Date birth_date ;
  private String ville ;
  private String adresse ;


  @Lob
  @Column(length = 100000)
  private byte[] photo_profil;
  private String tel1;
  private String tel2;
  private String code_postal;
  private String nom;
  private String prenom;

  private String etat;
  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  private Set<String> role;

  @NotBlank
  @Size(min = 6, max = 40)
  private String password;


}
