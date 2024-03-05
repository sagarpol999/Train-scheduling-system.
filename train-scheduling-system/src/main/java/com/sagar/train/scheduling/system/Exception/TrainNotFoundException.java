package com.sagar.train.scheduling.system.Exception;

import lombok.Data;

@Data
public class TrainNotFoundException extends RuntimeException {
    public TrainNotFoundException(String message) {
        super(message);
    }
}