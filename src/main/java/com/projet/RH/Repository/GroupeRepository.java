package com.projet.RH.Repository;


import com.projet.RH.Entities.Conges;
import com.projet.RH.Entities.Groupe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupeRepository extends CrudRepository<Groupe,Long> {


}
