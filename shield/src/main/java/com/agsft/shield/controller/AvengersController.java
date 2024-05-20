package com.agsft.shield.controller;

import com.agsft.shield.dto.ResponseDTO;
import com.agsft.shield.dto.request.AvengersRequestDTO;
import com.agsft.shield.dto.response.AvengersResponseDTO;
import com.agsft.shield.dto.response.MissionResponseDTO;
import com.agsft.shield.entitiy.Avengers;
import com.agsft.shield.entitiy.Mission;
import com.agsft.shield.entitiy.User;
import com.agsft.shield.exception.BindingResultExceptionHandler;
import com.agsft.shield.exception.ShieldException;
import com.agsft.shield.service.AvengersService;
import com.agsft.shield.service.UserService;
import com.agsft.shield.util.MappingUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "shield/avengers")
public class AvengersController {

    @Autowired
    private AvengersService avengersService;

    @Autowired
    private BindingResultExceptionHandler bindingResultExceptionHandler;

    @Autowired
    private MappingUtil mappingUtil;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST)
    //Admin
    public ResponseEntity<?> addNewAvenger(@RequestBody AvengersRequestDTO avengersRequestDTO, BindingResult bindingResult) {
       try {
           if (bindingResult.hasErrors()) {
               bindingResultExceptionHandler.bindingException(bindingResult);
           }
           Avengers avengers = modelMapper.map(avengersRequestDTO, Avengers.class);
           User user = userService.findUserById(avengersRequestDTO.getUserId());
           avengers.setUser(user);
           Avengers dbAvengers = avengersService.createNewAvenger(avengers);
           if (dbAvengers != null) {
               AvengersResponseDTO avengersResponseDTO = mappingUtil.entityToResponseDTO(dbAvengers);
               return ResponseEntity.ok(new ResponseDTO(200, avengersResponseDTO, "Ok"));
           }
       }catch (Exception exception){
           exception.printStackTrace();
       }
        throw new ShieldException(HttpStatus.BAD_REQUEST, "Error !!");
    }

    @RequestMapping(method = RequestMethod.GET)
    //Admin
    public ResponseEntity<?> getAllAvengersDetails() {
        List<Avengers> avengersList = avengersService.viewAllAvengers();
        if (avengersList != null) {
            List<AvengersResponseDTO> avengersResponseDTOS = avengersList.stream().map(a -> mappingUtil.entityToResponseDTO(a)).collect(Collectors.toList());
            return ResponseEntity.ok(new ResponseDTO(200, avengersResponseDTOS, "OK"));
        }
        throw new ShieldException(HttpStatus.BAD_REQUEST, "Error !!");
    }

    @RequestMapping(value = "/{avengerId}/active/{notificationStatus}/service/{contact}/{mail}", method = RequestMethod.POST)
    //Admin
    public ResponseEntity<?> activeAvengerNotificationService(@PathVariable("avengerId") Long avengerId, @PathVariable("notificationStatus") String status, @PathVariable("contact") String contact, @PathVariable("mail") String mail) {
        Avengers avengers = avengersService.activateNotifyService(avengerId, status, contact, mail);
        if (avengers != null) {
            AvengersResponseDTO avengersResponseDTO = mappingUtil.entityToResponseDTO(avengers);
            return ResponseEntity.ok(new ResponseDTO(200, avengersResponseDTO, "OK"));
        }
        throw new ShieldException(HttpStatus.BAD_REQUEST, "Error !!");
    }

    @RequestMapping(value = "/{avengerId}/mission/{status}", method = RequestMethod.POST)
    //Both
    public ResponseEntity<?> avengerMissionCheck(@PathVariable("avengerId") Long id, @PathVariable("status") String status) {
        List<Mission> missionList = avengersService.avengerMission(id, status);
        if (!missionList.isEmpty()) {
            List<MissionResponseDTO> missionResponseDTOList = missionList.stream().map(mission -> mappingUtil.entityToResponseDTO(mission)).collect(Collectors.toList());
            return ResponseEntity.ok(new ResponseDTO(200, missionResponseDTOList, "OK"));
        }
        throw new ShieldException(HttpStatus.BAD_REQUEST, "Error !!");
    }
}
