/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package giuseppevitolo;
import java.time.LocalDateTime;

/**
 *
 * @author giuseppe
 */
public class SensorMeasure extends Measure{
    private LocalDateTime datetime;
    private int sensorID;

    public SensorMeasure(int sensorID,LocalDateTime datetime, double value, String description, String unit) {
        super(value, description, unit);
        this.datetime = datetime;
        this.sensorID = sensorID;
    }

    public LocalDateTime getDateTime() {
        return datetime;
    }

    public int getSensorID() {
        return sensorID;
    }
    
    
    
    
}

