package com.sagar.train.scheduling.system.repository;
import com.sagar.train.scheduling.system.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<Station, Integer> {

    Station findByName(String name);

}
