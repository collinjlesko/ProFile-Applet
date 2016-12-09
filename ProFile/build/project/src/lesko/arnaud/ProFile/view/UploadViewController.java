package lesko.arnaud.ProFile.view;

import java.io.File;
import java.io.IOException;

import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.beans.property.IntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import lesko.arnaud.ProFile.MainApp;
import lesko.arnaud.ProFile.ServiceExample;
import lesko.arnaud.ProFile.model.Measure;

public class UploadViewController {
    @FXML
    private TableView<Measure> measureTable;
    @FXML
    private TableColumn<Measure, String> measureNameColumn;
    @FXML
    private TableColumn<Measure, String> measureTypeColumn;
    @FXML
    private TableColumn<Measure, Number> measureCountColumn;
    @FXML
    ProgressIndicator archBar;
    @FXML
    Text dashLeft;
    
    final ServiceExample serviceExample = new ServiceExample();

    private static final double EPSILON = 0.0000005;

    
//    @FXML
//    private Label firstNameLabel;
//    @FXML
//    private Label lastNameLabel;
//    @FXML
//    private Label streetLabel;
//    @FXML
//    private Label postalCodeLabel;
//    @FXML
//    private Label cityLabel;
//    @FXML
//    private Label birthdayLabel;

    // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public UploadViewController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() { 	
        // Initialize the person table with the two columns.
        measureNameColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        measureTypeColumn.setCellValueFactory(cellData -> cellData.getValue().measureTypeProperty());
        measureCountColumn.setCellValueFactory(cellData -> cellData.getValue().countProperty());
        
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        measureTable.setItems(mainApp.getPersonData());
    }
    
    /**
     * Opens a FileChooser to let the user select an address book to load.
     */
    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show open file dialog
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
            mainApp.loadPersonDataFromFile(file);
        }

    }  
    
//    private void beginUnzip() throws IOException {
//    	FileChooser fileChooser = new FileChooser();
//
//        // Set extension filter
//        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
//                "ZIP files (*.zip)", "*.zip");
//        fileChooser.getExtensionFilters().add(extFilter);
//
//        // Show open file dialog
//        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
//        if (file != null) {
//            mainApp.loadZipDataFromFile(file);
//        }   
//    }
    
    /**
     * Opens a FileChooser to let the user select an address book to load.
     * @throws IOException 
     * @throws InterruptedException 
     */
    @FXML
    private void handleOpenZip() throws IOException, InterruptedException {   
//    	serviceExample.start();
    	
    	FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "ZIP files (*.zip)", "*.zip");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show open file dialog
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
              
        if (file != null) {
            mainApp.loadZipDataFromFile(file);
            Thread unzipper = new Thread() {
            	 public void run() {
            		 boolean run = true;
					while (run) {
						int dashleft = mainApp.getDashleft();
//            	System.out.println("Dashleft: " + dashleft);
						if (dashleft == 1) {
								dashLeft.setText("Measures Left To Scan: Complete..." + dashleft);			
								run = false;
						}
						
							dashLeft.setText("Measures Left To Scan: " + dashleft);
//					Thread.sleep(1000);
						}
            }      
            };
            unzipper.start();
        }   
     }
    @FXML
    private void handleClearData() throws IOException {   
    	mainApp.clearData();
    }
}

