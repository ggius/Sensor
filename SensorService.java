/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package giuseppevitolo;

import java.time.LocalDateTime;
import java.util.Random;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @author giuseppe
 */
public class SensorService extends Service<Void>{ 
    private final HubQueue hubQueue;
    private final int sensorID;

    public SensorService(HubQueue hubQueue, int sensorID) {
        this.hubQueue = hubQueue;
        this.sensorID = sensorID;
    }
    
    
    @Override
    protected Task<Void> createTask() {
       return new Task<Void>() {
           @Override
           protected Void call() throws Exception {
               Random random=new Random();
               while(!isCancelled()){
                   double value;
                   String description;
                   String unit;
                   
                   if(sensorID==1){
                       value=random.nextDouble()*40;
                       description= "Temperature";
                       unit= "°C";
                   }else if(sensorID==2){
                       value=random.nextInt(61)+20; 
                       description="Humidity";
                       unit="%";                  
                   } else {
                        throw new IllegalStateException("Invalid sensor ID: " + sensorID);
                    }
                   SensorMeasure measure = new SensorMeasure(sensorID, LocalDateTime.now(), value, description, unit);
                   
                   hubQueue.add(measure);
                   
                   Thread.sleep(random.nextInt(6000));
               }
               return null;
           }
       };
    }
    
    
}

