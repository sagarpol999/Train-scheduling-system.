package com.sagar.train.scheduling.system.service.impl;
import com.sagar.train.scheduling.system.Exception.TrainNotFoundException;
import com.sagar.train.scheduling.system.entity.Station;
import com.sagar.train.scheduling.system.entity.Train;
import com.sagar.train.scheduling.system.model.TrainModel;
import com.sagar.train.scheduling.system.repository.StationRepository;
import com.sagar.train.scheduling.system.repository.TrainRepository;
import com.sagar.train.scheduling.system.service.TrainService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@AllArgsConstructor
public class TrainServiceImpl implements TrainService {

    @Autowired
    private TrainRepository trainRepository;

    @Autowired
    private StationRepository stationRepository;

    @Override
    public void addTrain(TrainModel trainModel) {
        Train train = new Train();
        train.setNumber(trainModel.getNumber());
        train.setName(trainModel.getName());
    List<Station> stations = trainModel.getStations().stream()
            .map(station -> {
                Station existingStation = stationRepository.findByName(station.getName());
                if (existingStation == null) {
                    existingStation = new Station(station.getName());
                    stationRepository.save(existingStation);
                }
                return existingStation;
            })
            .collect(Collectors.toList());
    train.setStations(stations);
    trainRepository.save(train);
}

    @Override
    public List<Train> findTrainsBetweenStations(String sourceStation, String destinationStation) {
        log.info("findTrainsBetweenStations Service Called");
        List<Train> trains = trainRepository.findTrainsBetweenStations(sourceStation, destinationStation);
        if (trains.isEmpty()) {
            throw new TrainNotFoundException("No trains found between " + sourceStation + " and " + destinationStation);
        }
        return trains;
    }

    @Override
    public Train updateTrain(TrainModel trainModel) {
        log.info("update Train Service called");
        Train train = trainRepository.findByNumber(trainModel.getNumber());
        if (train != null) {

            train.setName(trainModel.getName());

            // Update train stations
            List<Station> updatedStations = trainModel.getStations().stream()
                    .map(station -> {
                        Station existingStation = stationRepository.findByName(station.getName());
                        if (existingStation == null) {
                            existingStation = new Station(station.getName());
                            stationRepository.save(existingStation);
                        }
                        return existingStation;
                    })
                    .collect(Collectors.toList());

            // Remove stations not present in the updated list
            List<Station> stationsToRemove = new ArrayList<>(train.getStations());
            stationsToRemove.removeAll(updatedStations);

            for (Station stationToRemove : stationsToRemove) {
                train.getStations().remove(stationToRemove);
                // Delete station from the database
                stationRepository.delete(stationToRemove);
            }

            // Add new stations
            updatedStations.forEach(existingStation -> {
                if (!train.getStations().contains(existingStation)) {
                    train.getStations().add(existingStation);
                }
            });

            return trainRepository.save(train);
        } else {
            throw new TrainNotFoundException("Train with number " + trainModel.getNumber() + " not found");
        }
    }

    @Override
    public void removeTrain(String trainNumber) {
    log.info("remove Train Service called");
        Train train = trainRepository.findByNumber(trainNumber);
        if (train != null) {
            trainRepository.delete(train);
        } else {
            throw new TrainNotFoundException("Train not found with number: " + trainNumber);
        }
    }

}



