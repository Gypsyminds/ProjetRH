package com.projet.RH.Repository;


import com.projet.RH.Entities.Avance;
import com.projet.RH.Entities.Conges;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvanceRepository extends CrudRepository<Avance,Long> {


}
