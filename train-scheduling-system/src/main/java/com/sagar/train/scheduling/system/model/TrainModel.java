package com.sagar.train.scheduling.system.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sagar.train.scheduling.system.entity.Station;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer trainId;

    @JsonProperty
    private String number;

    @JsonProperty
    private String name;

    @JsonProperty
    private List<Station> stations = new ArrayList<>();
}
