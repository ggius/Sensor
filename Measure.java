/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package giuseppevitolo;
/**
 *
 * @author giuseppe
 */
public class Measure { 
    private double value;
    private String description;
    private String unit;

    public Measure(double value, String description, String unit) {
        this.value = value;
        this.description = description;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public String getUnit() {
        return unit;
    }
    
    
}

