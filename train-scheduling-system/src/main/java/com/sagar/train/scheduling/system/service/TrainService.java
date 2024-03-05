package com.sagar.train.scheduling.system.service;
import com.sagar.train.scheduling.system.entity.Train;
import com.sagar.train.scheduling.system.model.TrainModel;
import java.util.List;

public interface TrainService {

     void addTrain(TrainModel trainModel);

     List<Train> findTrainsBetweenStations(String sourceStation, String destinationStation);

     Train updateTrain(TrainModel trainModel);

     void removeTrain(String trainNumber);
}
