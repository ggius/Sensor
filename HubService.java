/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package giuseppevitolo;

import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @author giuseppe
 */

public class HubService extends Service<ObservableList<SensorMeasure>> {
    private final HubQueue hubQueue; 

  
    public HubService(HubQueue hubQueue) {
        this.hubQueue = hubQueue;
    }

    @Override
    protected Task<ObservableList<SensorMeasure>> createTask() {
        return new Task<ObservableList<SensorMeasure>>() {
            @Override
            protected ObservableList<SensorMeasure> call() throws Exception {
                ObservableList<SensorMeasure> sensors=FXCollections.observableArrayList();
                while (true) {
                    SensorMeasure measurement = hubQueue.remove();
                    if (measurement != null) {
                        System.out.println("hubService"+measurement.toString());
                        sensors.add(measurement);
                        updateValue(sensors);
                    }

                    TimeUnit.SECONDS.sleep(1);
                }   
            }
        };
    }
}

