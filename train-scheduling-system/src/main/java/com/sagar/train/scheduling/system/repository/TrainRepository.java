package com.sagar.train.scheduling.system.repository;

import com.sagar.train.scheduling.system.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrainRepository extends JpaRepository<Train, Integer> {

    @Query(value = "SELECT t FROM Train t")
    List<Train> getAll();

    @Query("SELECT t FROM Train t JOIN t.stations s1 JOIN t.stations s2 " +
            "WHERE s1.name = :sourceStation AND s2.name = :destinationStation")
    List<Train> findTrainsBetweenStations(
            @Param("sourceStation") String sourceStation,
            @Param("destinationStation") String destinationStation
    );

    Train findByNumber(String number);
}
