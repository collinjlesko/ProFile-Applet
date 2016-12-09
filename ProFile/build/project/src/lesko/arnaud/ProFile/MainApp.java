package lesko.arnaud.ProFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lesko.arnaud.ProFile.model.Measure;
import lesko.arnaud.ProFile.model.MeasureListWrapper;
import lesko.arnaud.ProFile.model.TransactionListWrapper;
import lesko.arnaud.ProFile.view.RootLayoutController;
import lesko.arnaud.ProFile.view.UploadViewController;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private List<Measure> check1;
    private File jarpath;
    public File[] listOfFiles;
    public int dashleft;
    
    /**
     * The data as an observable list of Persons.
     */
    private ObservableList<Measure> measureData = FXCollections.observableArrayList();

    /**
     * Constructor
     */
    public MainApp() {
        // Add some sample data
    	measureData.add(new Measure("Failed Actions Count", "EndUserVisitMeasure", false, 0));
    	measureData.add(new Measure("Failed Actions Count1", "EndUserVisitMeasure", false, 0));
    	measureData.add(new Measure("Failed Actions Count2", "EndUserVisitMeasure", false, 0));
    	measureData.add(new Measure("Failed Actions Count3", "EndUserVisitMeasure", false, 0));
    	measureData.add(new Measure("Failed Actions Count4", "EndUserVisitMeasure", false, 0));
    }

    /**
     * Returns the data as an observable list of Persons. 
     * @return
     */
    public ObservableList<Measure> getPersonData() {
        return measureData;
    }

    @Override
    public void start(Stage primaryStage) throws URISyntaxException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Pro-File");
        
        this.primaryStage.getIcons().add(new Image("file:resources/images/profile_2.png"));

        initRootLayout();
        
        showPersonOverview();

    }

    /**
     * Initializes the root layout and tries to load the last opened
     * person file.
     * @throws URISyntaxException 
     */
    public void initRootLayout() throws URISyntaxException {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            
//            primaryStage.initStyle(StageStyle.UNDECORATED);
            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();

//            primaryStage.setX(bounds.getMinX());
//            primaryStage.setY(bounds.getMinY());
//            primaryStage.setWidth(bounds.getWidth());
//            primaryStage.setHeight(bounds.getHeight());

         // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            
            //MOVE AROUND
            // allow the clock background to be used to drag the clock around.
            final Delta dragDelta = new Delta();
            rootLayout.setOnMousePressed(new EventHandler<MouseEvent>() {
              @Override public void handle(MouseEvent mouseEvent) {
                // record a delta distance for the drag and drop operation.
                dragDelta.x = primaryStage.getX() - mouseEvent.getScreenX();
                dragDelta.y = primaryStage.getY() - mouseEvent.getScreenY();
                scene.setCursor(Cursor.MOVE);
              }
            });
            rootLayout.setOnMouseReleased(new EventHandler<MouseEvent>() {
              @Override public void handle(MouseEvent mouseEvent) {
                scene.setCursor(Cursor.HAND);
              }
            });
            rootLayout.setOnMouseDragged(new EventHandler<MouseEvent>() {
              @Override public void handle(MouseEvent mouseEvent) {
            	  primaryStage.setX(mouseEvent.getScreenX() + dragDelta.x);
            	  primaryStage.setY(mouseEvent.getScreenY() + dragDelta.y);
              }
            });
            rootLayout.setOnMouseEntered(new EventHandler<MouseEvent>() {
              @Override public void handle(MouseEvent mouseEvent) {
                if (!mouseEvent.isPrimaryButtonDown()) {
                  scene.setCursor(Cursor.DEFAULT);
                }
              }
            });
            rootLayout.setOnMouseExited(new EventHandler<MouseEvent>() {
              @Override public void handle(MouseEvent mouseEvent) {
                if (!mouseEvent.isPrimaryButtonDown()) {
                  scene.setCursor(Cursor.DEFAULT);
                }
              }
            });
            
            ///DONE
            
            
            primaryStage.setScene(scene);
                        

         // Give the controller access to the main app.
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);
            
            // Give the controller access to the main app.
            primaryStage.show();
            
//            jarpath = new File(".").getAbsoluteFile();
            jarpath = new File(MainApp.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
            System.out.println("PATH: " + jarpath);
            System.out.println("PARENTfile - PATH: " + jarpath.getParentFile());
            System.out.println("PARENTstring - PATH: " + jarpath.getParentFile());

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Try to load last opened person file.
        File file = getPersonFilePath();
        if (file != null) {
            loadPersonDataFromFile(file);
        }
        
        }
    
    /**
     * Shows the person overview inside the root layout.
     */
    public void showPersonOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/UploadView.fxml"));
            AnchorPane uploadView = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(uploadView);
            
         // Give the controller access to the main app.
            UploadViewController controller = loader.getController();
            controller.setMainApp(this);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     * Returns the person file preference, i.e. the file that was last opened.
     * The preference is read from the OS specific registry. If no such
     * preference can be found, null is returned.
     * 
     * @return
     */
    public File getPersonFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    /**
     * Sets the file path of the currently loaded file. The path is persisted in
     * the OS specific registry.
     * 
     * @param file the file or null to remove the path
     */
    public void setPersonFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // Update the stage title.
            primaryStage.setTitle("ProFile - " + file.getName());
        } else {
            prefs.remove("filePath");

            // Update the stage title.
            primaryStage.setTitle("ProFile");
        }
    }
    
    /**
     * Loads person data from the specified file. The current person data will
     * be replaced.
     * 
     * @param file
     */
    public void loadPersonDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(MeasureListWrapper.class);
//            JAXBContext contextpfile = JAXBContext.newInstance(TransactionListWrapper.class);
            
            XMLInputFactory xif1 = XMLInputFactory.newFactory();
            StreamSource xml1 = new StreamSource(file);
            XMLStreamReader xsr1 = xif1.createXMLStreamReader(xml1);
            XMLStreamReader xsr = xif1.createXMLStreamReader(xml1);
            XMLStreamReader xsr2 = xif1.createXMLStreamReader(xml1);
      
            List<String> transactions = new ArrayList<String>();
            while(xsr.hasNext()) {
                if(xsr.isStartElement() && "transactions".equals(xsr.getLocalName())) {
                    break;
                }
                xsr.next();
             }
            
            while(xsr.hasNext()) {
                xsr.next();
              if(xsr.isStartElement() && "measureref".equals(xsr.getLocalName())) {
              	transactions.add(xsr.getAttributeValue(null, "refmeasure"));
              }
           }
//            System.out.println( "TRANSACTIONS:  " );
//
//            for ( String trans : transactions )
//            {
//                System.out.println( trans );
//            }
            
            while(xsr2.hasNext()) {
                if(xsr2.isStartElement() && "incidentrules".equals(xsr2.getLocalName())) {
                    break;
                }
                xsr2.next();
             }
           
            List<String> incidentrules = new ArrayList<String>();
            while(xsr2.hasNext()) {                
                xsr2.next();
                if (xsr2.isStartElement() && "condition".equals(xsr2.getLocalName())) {
                	incidentrules.add(xsr2.getAttributeValue(null, "refmeasure"));
                }
             }
//            System.out.println( "INCIRULES:  " );
//            for ( String incident : incidentrules )
//            {
//                System.out.println( incident );
//            }
            
            // Advance to the "Measures" element.
            while(xsr1.hasNext()) {
                if(xsr1.isStartElement() && "measures".equals(xsr1.getLocalName())) {
                    break;
                }
                xsr1.next();
             }
            
            //Filter
//            XMLInputFactory xif = XMLInputFactory.newFactory();
//            FileInputStream xmlStream = new FileInputStream(file);
            xsr1 = xif1.createFilteredReader(xsr1, new MeasureFilter());
//            xsr = xif1.createFilteredReader(xsr, new MeasureFilter());
            
            
            
            Unmarshaller um = context.createUnmarshaller();
//            Unmarshaller umpfile = contextpfile.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            MeasureListWrapper wrapper = (MeasureListWrapper) um.unmarshal(xsr1);
//            TransactionListWrapper wrapperpfile = (TransactionListWrapper) umpfile.unmarshal(xsr);

            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//            marshaller.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, "file:///C:/Users/collin.lesko/Desktop/FirstXSD.xml");
//            marshaller.marshal(wrapper, System.out);
            
//            System.out.println("TRANSACTIONDATA:  ");
//            Marshaller marshallerpfile = contextpfile.createMarshaller();
//            marshallerpfile.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//            marshallerpfile.marshal(wrapperpfile, System.out);
            
            check1 = wrapper.getMeasures();
            for ( Measure measure : check1 )
            {
            	String searchthis = measure.getId().trim().toLowerCase();
            	
            	for(String transaction : transactions) {
            	    if(transaction.trim().toLowerCase().contains(searchthis)) {
            	    	measure.setCount(measure.getCount() + 1);
            	    }           	       
            	}
            	
            	for(String incident : incidentrules) {
            	    if(incident.trim().toLowerCase().contains(searchthis)) {
            	    	measure.setCount(measure.getCount() + 1);
            	    }           	       
            	}
            }
            
//            Iterator<Measure> iter = check1.iterator();
//
//            while (iter.hasNext()) {
//                Measure str = iter.next();
//
//                if (str.getCount() < 1)
//                    iter.remove();
//            }
            
//            for ( Measure measure : check1 ) {
//            	if (measure.getCount() < 1) {
//            		check1.remove(measure);
//            	}
//            }
            
            measureData.clear();
            //measureData.addAll(wrapper.getMeasures());
            measureData.addAll(check1);

            // Save the file path to the registry.
            setPersonFilePath(file);
            
        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load data");
            alert.setContentText("Could not load data from file:\n" + file.getPath());
            System.out.println("EXCEPTION: " + e);

            alert.showAndWait();
        }
    }
    
    /**
     * Loads person data from the specified file. The current person data will
     * be replaced.
     * 
     * @param file
     * @throws IOException 
     */
    public void loadZipDataFromFile(File file) throws IOException {
    	
        File externalpath = new File(jarpath.getParent() + "/tmp");
//        String externalpathS = jarpath.getParentFile()+"/tmp/";
    	
    	UnZip unZip = new UnZip();
//    	unZip.unZipIt(file.getAbsolutePath(),externalpathS);
    	unZip.unZipAll(file, externalpath);
    	
    	Collection<File> files = FileUtils.listFiles(
    				externalpath, 
    			  new RegexFileFilter("^(.*?)"), 
    			  DirectoryFileFilter.DIRECTORY
    			);
    	
    	listOfFiles = files.toArray(new File[files.size()]);
//    	File folder = new File(externalpathS);
//    	File[] listOfFiles = folder.listFiles();

	    System.out.println("Files: " + listOfFiles.length);
        List<String> dashboarddata = new ArrayList<String>();
        
        Thread unzipper = new Thread() {
            public void run() {
                try {                          
    	for (int i = 0; i < listOfFiles.length; i++) {
    	  File file2 = listOfFiles[i];  	  
    	  if (file2.isFile() && file2.getName().endsWith(".xml")) {
    	    dashboarddata.add(FileUtils.readFileToString(file2));
//    	    dashleft = listOfFiles.length-i;
//    	    if (dashboarddata.get(i) != null)
//    	    	System.out.println(dashboarddata.get(i));
    	  } 
    	}
    	
    	int index = 0;
        for ( Measure measure : check1 )
        {
        	String searchthis = measure.getId().trim().toLowerCase();
        	index++;
        	dashleft = check1.size() - index;
        	for(String dash : dashboarddata) {
        	    if(dash.trim().toLowerCase().contains(searchthis)) {
        	    	measure.setCount(measure.getCount() + 1);
        	    }           	       
        	}     	
        }
        
        measureData.clear();
        measureData.addAll(check1);
        
                } catch(IOException v) {
                    System.out.println(v);
                }
            }  
        };

        unzipper.start();

    }

    /**
     * Saves the current person data to the specified file.
     * 
     * @param file
     */
    public void savePersonDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(MeasureListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Wrapping our person data.
            MeasureListWrapper wrapper = new MeasureListWrapper();
            wrapper.setMeasures(measureData);

            // Marshalling and saving XML to the file.
            m.marshal(wrapper, file);

            // Save the file path to the registry.
            setPersonFilePath(file);
        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not save data");
            alert.setContentText("Could not save data to file:\n" + file.getPath());

            alert.showAndWait();
        }
    }
    
    public void clearData () throws IOException {
    	measureData.clear();
    	check1.clear();
    	
        File externalpath = new File(jarpath.getParent()+"/tmp");

        if (externalpath.exists()) { 
            System.out.println("externalpath:  " + externalpath);

//        	externalpath.delete();
        	FileUtils.deleteDirectory(externalpath);
        	
        	Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Data Cleared");
            alert.setHeaderText("Data Cleared");
            alert.setContentText("All measure data and dashboard data was cleared.\n Dashboards in tmp were also removed.");

            alert.showAndWait();
        }
        else {
    	Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Data Cleared");
        alert.setHeaderText("Data Cleared");
        alert.setContentText("All measure data and dashboard data was cleared.\n Dashboards in tmp remain.");

        alert.showAndWait();
        }
    }
    // records relative x and y co-ordinates.
    class Delta { double x, y; }
	/**
	 * @return the dashleft
	 */
	public int getDashleft() {
		return dashleft;
	}

	/**
	 * @param dashleft the dashleft to set
	 */
	public void setDashleft(int dashleft) {
		this.dashleft = dashleft;
	}  
}