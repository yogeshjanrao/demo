package com.agsft.shield.controller;

import com.agsft.shield.dto.ResponseDTO;
import com.agsft.shield.dto.request.MissionRequestDTO;
import com.agsft.shield.dto.response.MissionResponseDTO;
import com.agsft.shield.entitiy.Mission;
import com.agsft.shield.exception.BindingResultExceptionHandler;
import com.agsft.shield.exception.ShieldException;
import com.agsft.shield.service.MissionService;
import com.agsft.shield.util.MappingUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RestController
@RequestMapping(value = "shield/mission")
public class MissionController {
    @Autowired
    private MissionService missionService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MappingUtil mappingUtil;

    @Autowired
    private BindingResultExceptionHandler bindingResultExceptionHandler;

    @RequestMapping(method = RequestMethod.POST)
    //Admin
    public ResponseEntity<?> addNewMission(@RequestBody MissionRequestDTO missionRequestDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResultExceptionHandler.bindingException(bindingResult);
        }
        Mission mission = modelMapper.map(missionRequestDTO, Mission.class);
        if (mission != null) {
            Mission dbMission = missionService.createNewMission(mission);
            if (dbMission != null) {
                MissionResponseDTO missionResponseDTO = mappingUtil.entityToResponseDTO(dbMission);
                return ResponseEntity.ok(new ResponseDTO(200, missionResponseDTO, "OK"));
            }
        }
        throw new ShieldException(HttpStatus.BAD_REQUEST, "Error !!");
    }

    @RequestMapping(method = RequestMethod.GET)
    //Avengers
    public ResponseEntity<?> showMissionDetails() {
        List<Mission> mission = missionService.showAllMission();
        if (mission != null) {
            List<MissionResponseDTO> missionResponseDTOList = mission.stream().map(m -> mappingUtil.entityToResponseDTO(m)).collect(Collectors.toList());
            return ResponseEntity.ok(new ResponseDTO(200, missionResponseDTOList, "OK"));
        }
        throw new ShieldException(HttpStatus.BAD_REQUEST, "Error !!");
    }

    @RequestMapping(value = "/notify/{missionId}/all", method = RequestMethod.POST)
    //Admin
    public ResponseEntity<?> notifyToAvengers(@PathVariable("missionId") Long missionId) {
        String response = missionService.notifyAllAboutMission(missionId);
        if (response != null) {
            return ResponseEntity.ok(new ResponseDTO(200, response, "OK"));
        }
        throw new ShieldException(HttpStatus.BAD_REQUEST, "Error !!");
    }

    @RequestMapping(value = "/complete/{missionId}", method = RequestMethod.POST)
    //Avengers
    public ResponseEntity<?> completeMission(@PathVariable("missionId") Long missionId) {
        Mission mission = missionService.completeMission(missionId);
        mission.setStatus(missionService.findMissionStatus(mission));
        if (mission != null) {
            MissionResponseDTO missionResponseDTO = mappingUtil.entityToResponseDTO(mission);
            return ResponseEntity.ok(new ResponseDTO(200, missionResponseDTO, "OK"));
        }
        throw new ShieldException(HttpStatus.BAD_REQUEST, "Error !!");
    }

}
