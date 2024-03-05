package com.sagar.train.scheduling.system;

import com.sagar.train.scheduling.system.controller.TrainController;
import com.sagar.train.scheduling.system.entity.Station;
import com.sagar.train.scheduling.system.entity.Train;
import com.sagar.train.scheduling.system.model.TrainModel;
import com.sagar.train.scheduling.system.service.TrainService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@SpringBootTest(classes = TrainSchedulingSystemApplication.class)
@ExtendWith(MockitoExtension.class)
public class TrainControllerTest {

    @InjectMocks
    private TrainController trainController;

    @Mock
    private TrainService trainService; // Assuming you have a TrainService implementation

    @Test
    void testAddTrain() {
        // Arrange
        TrainModel trainModel = new TrainModel();
        trainModel.setNumber("30662");
        trainModel.setName("SBC VANDE BHARAT Train");

        // Create Station entities for each station name
        List<Station> stations = Arrays.asList(
                new Station("Dharwar Railway Station"),
                new Station("Hubli Junction"),
                new Station("Davangere Railway Station"),
                new Station("Bangalore Yesvantpur Junction"),
                new Station("Bangalore City Junction")
        );

        trainModel.setStations(stations);

        Map<String, Object> expectedResponse = new HashMap<>();
        expectedResponse.put("message", "Train added successfully");

        doNothing().when(trainService).addTrain(trainModel);

        // Act
        ResponseEntity<Map<String, Object>> response = trainController.addTrain(trainModel);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }


    @Test
    void testUpdateTrain_Success() {
        // Arrange
        TrainModel trainModel = new TrainModel();
        trainModel.setNumber("20662");
        trainModel.setName("SBC VANDE BHARAT");

        List<Station> stations = Arrays.asList(
                new Station("Dharwar Railway Station"),
                new Station("Davangere Railway Station"),
                new Station("Bangalore Yesvantpur Junction"),
                new Station("Bangalore City Junction")
        );
        trainModel.setStations(stations);

        // Mock the behavior of the trainService.updateTrain method
        when(trainService.updateTrain(trainModel)).thenReturn(createUpdatedTrain());

        // Act
        ResponseEntity<Object> response = trainController.updateTrain(trainModel);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Train updated successfully", response.getBody());
    }

    @Test
    void testUpdateTrain_NotFound() {
        // Arrange
        TrainModel trainModel = new TrainModel();
        trainModel.setNumber("20662");
        trainModel.setName("SBC VANDE BHARAT");

        List<Station> stations = Arrays.asList(
                new Station("Dharwar Railway Station"),
                new Station("Davangere Railway Station"),
                new Station("Bangalore Yesvantpur Junction"),
                new Station("Bangalore City Junction")
        );
        trainModel.setStations(stations);

        // Mock the behavior of the trainService.updateTrain method to return null
        when(trainService.updateTrain(trainModel)).thenReturn(null);

        // Act
        ResponseEntity<Object> response = trainController.updateTrain(trainModel);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Train not found", response.getBody());
    }

    private Train createUpdatedTrain() {
        // Assuming Train has a default constructor, you can use mockito to create a mock object for Train
        return mock(Train.class);
    }

    @Test
    void testRemoveTrain_Success() {
        // Arrange
        String trainNumber = "20664";

        // Mock the behavior of the trainService.removeTrain method
        doNothing().when(trainService).removeTrain(trainNumber);

        // Act
        ResponseEntity<Map<String, Object>> response = trainController.removeTrain(trainNumber);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Train removed successfully", response.getBody().get("message"));

        // Verify that the service method was called with the correct parameters
        verify(trainService).removeTrain(trainNumber);
    }

    @Test
    void testFindTrainsBetweenStations_Success() {
        // Arrange
        String sourceStation = "Davangere";
        String destinationStation = "Bangalore Yesvantpur Junction";

        List<Train> expectedTrains = Arrays.asList(
                createSampleTrain(),
                createSampleTrain()
                // Add more Train objects as needed
        );

        // Mock the behavior of the trainService.findTrainsBetweenStations method
        when(trainService.findTrainsBetweenStations(sourceStation, destinationStation))
                .thenReturn(expectedTrains);

        // Act
        ResponseEntity<List<Train>> response = trainController.findTrainsBetweenStations(
                sourceStation, destinationStation);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedTrains, response.getBody());
    }

    @Test
    void testFindTrainsBetweenStations_NotFound() {
        // Arrange
        String sourceStation = "Davangere";
        String destinationStation = "Bangalore Yesvantpur Junction";

        // Mock the behavior of the trainService.findTrainsBetweenStations method to return an empty list
        when(trainService.findTrainsBetweenStations(sourceStation, destinationStation))
                .thenReturn(Arrays.asList());

        // Act
        ResponseEntity<List<Train>> response = trainController.findTrainsBetweenStations(
                sourceStation, destinationStation);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // Helper method to create a sample Train object (replace with your actual Train class details)
    private Train createSampleTrain() {
        return new Train(/* provide necessary details for Train object */);
    }

}
