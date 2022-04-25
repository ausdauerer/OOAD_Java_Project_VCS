package ooad;

import java.util.*;

import javax.xml.crypto.Data;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;

public class FileObject implements Serializable{
    private static final long serialVersionUID =-8577938667472526097L;
    public ArrayList<Version> versions;
    public String path;
    public int latestVersionIndex;

    //This method present only for serialization purposes
    public FileObject(){
        versions=new ArrayList<Version>();
    }
    public FileObject(String path) throws SQLException{
        versions=new ArrayList<Version>();
        this.path=path;
        this.latestVersionIndex=-1;
        updateVersion();
    }
    public void updateVersion() throws SQLException{
        Database db=Database.getInstance();
        int blobid=0;
        try {
            blobid=db.saveFile(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Version version=new Version(blobid);
        this.versions.add(version);
        latestVersionIndex+=1;
    }

    //Note this is only for text files
    public void displayLatestVersion() throws SQLException{ 
        Database db=Database.getInstance();
        System.out.println(path+"---------------------------");
        try {
            Blob bb=db.getBlob(versions.get(latestVersionIndex).blobid);
            byte[] is = bb.getBytes(1,(int) bb.length());
            System.out.println();
            System.out.println(new String(is));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean updateToPreviousVersion() throws SQLException, IOException, Exception{ 
        Database db=Database.getInstance();
        if(versions.size()==1){
            System.out.println("No previous version of file found to be replaced, hence deleting file");
            return false;
        }
        versions.remove(versions.size()-1);
        latestVersionIndex-=1;
        int blobid=versions.get(latestVersionIndex).blobid;
        Blob b=db.getBlob(blobid);
        byte[] byt=b.getBytes(1, (int) b.length());
        OutputStream os = new FileOutputStream(this.path);
 
            // Starting writing the bytes in it
            os.write(byt);
 
            // Dislay message onconsole for successful
            // execution
            System.out.println("Successfully replaced file "+this.path);
 
            // Close the file connections
            os.close();
            return(true);
    }

    public int getLatestBlobId(){
        return(versions.get(latestVersionIndex).blobid);
    }

    public int getPrevBlobId(){
        return(versions.get(latestVersionIndex).blobid);
    }
    
}
