package com.agsft.shield.service;

import com.agsft.shield.entitiy.Avengers;
import com.agsft.shield.entitiy.Mission;
import com.agsft.shield.status.AvengerStatus;

import java.util.List;

public interface AvengersService {
    Avengers createNewAvenger(Avengers avengers);

    List<Avengers> viewAllAvengers();

    Avengers activateNotifyService(Long avengerId, String status, String contact, String mail);

    Avengers findAvengerById(Long id);

    List<Mission> avengerMission(Long avengerId, String status);

    AvengerStatus findAvengerStatus(Avengers avengers);

}
