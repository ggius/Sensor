/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package giuseppevitolo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

/**
 *
 * @author giuseppe
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Button startButtonS1;
    @FXML
    private Button startButtonS2;
    @FXML
    private Button hubButton;
    @FXML
    private Button exportButton;
    @FXML
    private TableView<SensorMeasure> sensorTable;
    @FXML
    private TableColumn<SensorMeasure, Integer> sensorClm;
    @FXML
    private TableColumn<SensorMeasure, LocalDateTime> timeClm;
    @FXML
    private TableColumn<SensorMeasure, Double> valueClm;
    @FXML
    private TableColumn<SensorMeasure, String> unitClm;
    @FXML
    private TableColumn<SensorMeasure, String> descriptionClm;
    @FXML
    private ProgressIndicator indicator1;
    @FXML
    private ProgressIndicator indicator2;
    @FXML
    private ProgressIndicator indicator3;
    
    private HubQueue hubQueue;
    private SensorService sensorService1;
    private SensorService sensorService2;
    private HubService hubService;
    private ObservableList<SensorMeasure> sensors;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        hubQueue = new HubQueue(4);
        sensors=FXCollections.observableArrayList();
        sensorService1=new SensorService(hubQueue,1);
        sensorService2 = new SensorService(hubQueue, 2);
        hubService=new HubService(hubQueue);
        // Inizializzazione della tabella
        sensorClm.setCellValueFactory(new PropertyValueFactory<>("sensorID"));
        timeClm.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        valueClm.setCellValueFactory(new PropertyValueFactory<>("value"));
        unitClm.setCellValueFactory(new PropertyValueFactory<>("unit"));
        descriptionClm.setCellValueFactory(new PropertyValueFactory<>("description"));
        sensorTable.setItems(sensors);
        exportButton.disableProperty().bind(sensorTable.itemsProperty().isNull());
        
        startButtonS1.setText("Start");
        startButtonS2.setText("Start");
        hubButton.setText("Start");
        indicator1.setVisible(false);
        indicator2.setVisible(false);
        indicator3.setVisible(false);
    }    

    @FXML
    private void startActionS1(ActionEvent event) {
        Button button=((Button) event.getSource());  
     if (sensorService1.isRunning()) {
            sensorService1.cancel();
            button.setText("Start");
        } else {
            sensorService1 = new SensorService(hubQueue, 1);
            indicator1.visibleProperty().bind(sensorService1.runningProperty());
            sensorService1.start();
            button.setText("Stop");
        }

    }

    @FXML
    private void startActionS2(ActionEvent event) {
        Button button=((Button) event.getSource());

        if (sensorService2.isRunning()) {
            sensorService2.cancel();
            button.setText("Start");
        } else {
            sensorService2 = new SensorService(hubQueue, 2);
            indicator2.visibleProperty().bind(sensorService2.runningProperty());
            sensorService2.start();
            button.setText("Stop");
        }

    }

    @FXML
    private void hubAction(ActionEvent event) {
        Button button = (Button) event.getSource();
    if (hubService != null && hubService.isRunning()) {
        hubService.cancel();
        button.setText("Start");
        sensorTable.itemsProperty().unbind();
        indicator3.setVisible(false);
    } else {
        hubService = new HubService(hubQueue);
        sensorTable.itemsProperty().bind(hubService.valueProperty());
        indicator3.visibleProperty().bind(hubService.runningProperty());
        hubService.start();
        button.setText("Stop");
    }
    }

    @FXML
    private void exportAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Data");
        File file = fileChooser.showSaveDialog(exportButton.getScene().getWindow());

        if (file != null) {
            try (BufferedWriter bw=new BufferedWriter(new FileWriter(file))) {
             
                bw.append("sensorID;datetime;value;unit;description\n");

                for (SensorMeasure measure : sensorTable.getItems()) {
                  bw.append(measure.getSensorID()+";");
                    bw.append(measure.getDateTime()+";");
                    bw.append(measure.getValue()+";");
                    bw.append(measure.getUnit()+";");
                    bw.append(measure.getDescription()+"\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
    }
    
}
