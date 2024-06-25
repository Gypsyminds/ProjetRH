package com.projet.RH.Repository;


import com.projet.RH.Entities.Conges;
import com.projet.RH.Entities.Fiche_de_paie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FicheRepository extends CrudRepository<Fiche_de_paie,Long> {


}
