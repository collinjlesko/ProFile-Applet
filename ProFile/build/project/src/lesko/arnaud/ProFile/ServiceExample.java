package lesko.arnaud.ProFile;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class ServiceExample extends Service<String> {
    private MainApp mainApp;
	@Override
	protected Task<String> createTask() {
	    return new Task<String>() {
	        @Override
	        protected String call() throws Exception {
	            //DO YOU HARD STUFF HERE
	        	int max = mainApp.listOfFiles.length;
             	System.out.println("Max Dashes: " + max);
             	boolean run = true;                	
             	while (run) {
             		if (mainApp.dashleft == max)
                		run = false;
              	  System.out.println("MAX: " + max);
              	  System.out.println("Dashleft: " + mainApp.dashleft);
////                  updateMessage(mainApp.dashleft + " Dashboards Left..");
//                  updateProgress(mainApp.dashleft/max, 1);
              	String res = "toto";
	            Thread.sleep(5000);
	            return res;
             	}    	        	  	        	
	            String res = "toto";
//	            Thread.sleep(5000);
	            return res;
	        }
	    };
	}
}
