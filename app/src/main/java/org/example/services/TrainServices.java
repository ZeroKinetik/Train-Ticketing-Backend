package org.example.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entities.Train;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class TrainServices {
    ObjectMapper objectMapper = new ObjectMapper();
    private final String TRAIN_PATH = "app/src/main/java/org/example/db/trains.json";
    List<Train> listOfTrains;
    public TrainServices() throws IOException {
        loadTrains();
    }
    private void loadTrains() throws IOException {
        listOfTrains = objectMapper.readValue(new File(TRAIN_PATH), new TypeReference<List<Train>>() {});
    }
    public void findTrains(String source, String destination) {
        boolean isTrainAvail = false;
        for (Train t : listOfTrains) {
            if((t.getStations().contains(source) && t.getStations().contains(destination)) && (t.getStations().indexOf(source) < t.getStations().indexOf(destination))) {
                System.out.println(t.getTrainId());
                isTrainAvail = true;
            }
        }
        if(!isTrainAvail) {
            System.out.println("Sorry!!, Your Search of Train is not Available 😕");
        }
    }
}
