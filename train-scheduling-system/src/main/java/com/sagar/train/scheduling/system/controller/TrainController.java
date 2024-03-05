package com.sagar.train.scheduling.system.controller;

import com.sagar.train.scheduling.system.entity.Train;
import com.sagar.train.scheduling.system.model.TrainModel;
import com.sagar.train.scheduling.system.repository.TrainRepository;
import com.sagar.train.scheduling.system.service.TrainService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping("/train")
@AllArgsConstructor
public class TrainController {

    @Autowired
    private TrainService trainService;

    @Autowired
    private TrainRepository trainRepository;

    @PostMapping("/addTrain")
    public ResponseEntity<Map<String, Object>> addTrain(@RequestBody TrainModel trainModel) {
        log.info("add Train api called");
        trainService.addTrain(trainModel);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Train added successfully");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @GetMapping("/findTrains")
    public ResponseEntity<List<Train>> findTrainsBetweenStations(
            @RequestParam("sourceStation") String sourceStation,
            @RequestParam("destinationStation") String destinationStation
    ) {
        log.info("find Trains api called");
        List<Train> trains = trainService.findTrainsBetweenStations(sourceStation, destinationStation);

        if (trains.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(trains, HttpStatus.OK);
    }

    @PutMapping("/updateTrain")
    public ResponseEntity<Object> updateTrain(@RequestBody TrainModel trainModel) {
        log.info("update Train api called");
        Train updatedTrain = trainService.updateTrain(trainModel);
        if (updatedTrain != null) {
            return ResponseEntity.ok("Train updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Train not found");
        }
    }

    @DeleteMapping("/removeTrain")
    public ResponseEntity<Map<String, Object>> removeTrain(@RequestParam String number) {
        log.info("remove Train api called");
        trainService.removeTrain(number);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Train removed successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}


