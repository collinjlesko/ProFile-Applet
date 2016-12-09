package lesko.arnaud.ProFile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class UnZip
{
    List<String> fileList;
//    private static final String INPUT_ZIP_FILE = "C:\\MyFile.zip";
    private static final String OUTPUT_FOLDER = "C:\\outputzip";

//    public static void main( String[] args )
//    {
//    	UnZip unZip = new UnZip();
//    	unZip.unZipIt(INPUT_ZIP_FILE,OUTPUT_FOLDER);
//    }

    /**
     * Unzip it
     * @param zipFile input zip file
     * @param output zip file output folder
     */
    public void unZipIt(String zipFile, String outputFolder){

     byte[] buffer = new byte[1024];

     try{

    	//create output directory is not exists
    	File folder = new File(outputFolder);
    	if(!folder.exists()){
    		folder.mkdir();
    	}

    	//get the zip file content
    	ZipInputStream zis =
    		new ZipInputStream(new FileInputStream(zipFile));
    	//get the zipped file list entry
    	ZipEntry ze = zis.getNextEntry();

    	while(ze!=null){

    	   String fileName = ze.getName();
           File newFile = new File(outputFolder + File.separator + fileName);

           System.out.println("file unzip : " + newFile.getAbsoluteFile());

            //create all non exists folders
            //else you will hit FileNotFoundException for compressed folder
            new File(newFile.getParent()).mkdirs();

            FileOutputStream fos = new FileOutputStream(newFile);

            int len;
            while ((len = zis.read(buffer)) > 0) {
       		fos.write(buffer, 0, len);
            }

            fos.close();
            ze = zis.getNextEntry();
    	}

        zis.closeEntry();
    	zis.close();

    	System.out.println("Done Unzipping");
    	
    	Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Progress");
        alert.setHeaderText("Data is being loaded...");
        alert.setContentText("Please wait for your dashboard data to be loaded. You may press OK, and your counts will increase depending on usage.");
        
        alert.showAndWait();
    	
    }catch(IOException ex){
    	
    	Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Could not load data");
        alert.setContentText("Could not load data from file");
        System.out.println("EXCEPTION: " + ex);
        alert.showAndWait();
        
       ex.printStackTrace();
    }
   }
    
    public static void unZipAll(File source, File destination) throws IOException 
    {
        System.out.println("Unzipping - " + source.getName());
        int BUFFER = 2048;

        ZipFile zip = new ZipFile(source);
        try{
            destination.getParentFile().mkdirs();
            Enumeration zipFileEntries = zip.entries();

            // Process each entry
            while (zipFileEntries.hasMoreElements())
            {
                // grab a zip file entry
                ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();
                String currentEntry = entry.getName();
                File destFile = new File(destination, currentEntry);
                //destFile = new File(newPath, destFile.getName());
                File destinationParent = destFile.getParentFile();

                // create the parent directory structure if needed
                destinationParent.mkdirs();

                if (!entry.isDirectory())
                {
                    BufferedInputStream is = null;
                    FileOutputStream fos = null;
                    BufferedOutputStream dest = null;
                    try{
                        is = new BufferedInputStream(zip.getInputStream(entry));
                        int currentByte;
                        // establish buffer for writing file
                        byte data[] = new byte[BUFFER];

                        // write the current file to disk
                        fos = new FileOutputStream(destFile);
                        dest = new BufferedOutputStream(fos, BUFFER);

                        // read and write until last byte is encountered
                        while ((currentByte = is.read(data, 0, BUFFER)) != -1) {
                            dest.write(data, 0, currentByte);
                        }
                    } catch (Exception e){
                        System.out.println("unable to extract entry:" + entry.getName());
                        throw e;
                    } finally{
                        if (dest != null){
                            dest.close();
                        }
                        if (fos != null){
                            fos.close();
                        }
                        if (is != null){
                            is.close();
                        }
                    }
                }else{
                    //Create directory
                    destFile.mkdirs();
                }

                if (currentEntry.endsWith(".zip"))
                {
                    // found a zip file, try to extract
                    unZipAll(destFile, destinationParent);
                    if(!destFile.delete()){
                        System.out.println("Could not delete zip");
                    }
                }
            }
        } catch(Exception e){
            e.printStackTrace();
            System.out.println("Failed to successfully unzip:" + source.getName());
        } finally {
            zip.close();
        }
        System.out.println("Done Unzipping:" + source.getName());
    }
}
