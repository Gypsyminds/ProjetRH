package com.projet.RH.Repository;


import com.projet.RH.Entities.Autorisation;
import com.projet.RH.Entities.Conges;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorisationRepository extends CrudRepository<Autorisation,Long> {


}
