package com.agsft.shield.util;

import com.agsft.shield.dao.AvengerMissionAssignmentRepository;
import com.agsft.shield.dto.response.AvengerMissionAssignmentResponse;
import com.agsft.shield.dto.response.AvengersResponseDTO;
import com.agsft.shield.dto.response.MissionResponseDTO;
import com.agsft.shield.entitiy.AvengerMissionAssignment;
import com.agsft.shield.entitiy.Avengers;
import com.agsft.shield.entitiy.Mission;
import com.agsft.shield.service.AvengersService;
import com.agsft.shield.service.MissionService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class MappingUtil {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MissionService missionService;

    @Autowired
    private AvengersService avengersService;


    public MissionResponseDTO entityToResponseDTO(Mission mission) {
        MissionResponseDTO missionResponseDTO = new MissionResponseDTO();
        TypeMap<Mission, MissionResponseDTO> modelMapperTypeMap = modelMapper.getTypeMap(Mission.class, MissionResponseDTO.class);
        mission.setStatus(missionService.findMissionStatus(mission));
        if (mission != null) {
            mission.setStatus(missionService.findMissionStatus(mission));
            modelMapper.map(mission, missionResponseDTO);
        }
        return missionResponseDTO;
    }

    public AvengersResponseDTO entityToResponseDTO(Avengers avengers) {
        AvengersResponseDTO avengersResponseDTO = new AvengersResponseDTO();
        avengers.setStatus(avengersService.findAvengerStatus(avengers));
        modelMapper.map(avengers, avengersResponseDTO);
        return avengersResponseDTO;
    }

    public AvengerMissionAssignmentResponse entityToResponseDTO(AvengerMissionAssignment avengerMissionAssignment) {
        AvengerMissionAssignmentResponse avengerMissionAssignmentResponse = modelMapper.map(avengerMissionAssignment, AvengerMissionAssignmentResponse.class);
        return avengerMissionAssignmentResponse;
    }
}
