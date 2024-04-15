/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package giuseppevitolo;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author giuseppe
 */
public class HubQueue {
    private final int hsize;
    private final BlockingQueue<SensorMeasure> buffer;

    public HubQueue(int hsize) {
        this.hsize = hsize;
        this.buffer = new LinkedBlockingQueue<>(hsize);
    }
    
     public synchronized void add(SensorMeasure measure){ 
        if(buffer.size()< hsize)
            buffer.add(measure);
    }
    
    public synchronized SensorMeasure remove(){
        return buffer.poll(); 
    }
    
    
    
}


