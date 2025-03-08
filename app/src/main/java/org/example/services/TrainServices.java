package org.example.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entities.Ticket;
import org.example.entities.Train;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class TrainServices {
    ObjectMapper objectMapper = new ObjectMapper();
    private final String TRAIN_PATH = "app/src/main/java/org/example/db/trains.json";
    List<Train> listOfTrains;
    Scanner scanner = new Scanner(System.in);
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
        } else {
            System.out.println("Enter the train id for check seat availability: ");
            String trainId = scanner.next();
            availableSeats(trainId);
        }
    }

    private void availableSeats(String trainId) {
        for (Train t : listOfTrains) {
            if(t.getTrainId().equals(trainId)) {
               printAllSeats(t);
            }
        }
    }

    private void printAllSeats(Train t) {
        for (List<Integer> rows : t.getSeats()) {
            for(Integer col : rows) {
                System.out.print(col + " ");
            }
            System.out.println();
        }
    }

    public Ticket bookTickets() {
        Ticket ticket = new Ticket();
        System.out.print("Enter the trainId: ");
        String trainId = scanner.next();
        System.out.print("Enter the source: ");
        String source = scanner.next();
        System.out.println("Enter the destination: ");
        String destination = scanner.next();
        System.out.println("Note: Enter the Row and Column Index Value for Booking Seat");
        for (Train t : listOfTrains) {
            if(t.getTrainId().equals(trainId)) {
                printAllSeats(t);
            }
        }
        System.out.print("Enter Row: ");
        int row = scanner.nextInt();
        System.out.print("Enter Column: ");
        int column = scanner.nextInt();
        for (Train t : listOfTrains) {
            if(t.getTrainId().equals(trainId)) {
                List<List<Integer>> availSeats = t.getSeats();
                availSeats.get(row).set(column,1);
                t.setSeats(availSeats);
            }
        }
        for (Train t : listOfTrains) {
            if(t.getTrainId().equals(trainId)) {
                printAllSeats(t);
                ticket = new Ticket(trainId, UUID.randomUUID().toString(), source, destination, new Date().toString(), t);
                return ticket;
            }
        }

        return ticket;
    }
}
