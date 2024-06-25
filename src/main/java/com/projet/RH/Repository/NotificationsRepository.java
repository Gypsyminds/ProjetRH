package com.projet.RH.Repository;


import com.projet.RH.Entities.Conges;
import com.projet.RH.Entities.Notifications;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationsRepository extends CrudRepository<Notifications,Long> {


}
