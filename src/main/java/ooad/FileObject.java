package ooad;

import java.util.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;

public class FileObject implements Serializable{
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

    public int getLatestBlobId(){
        return(versions.get(latestVersionIndex).blobid);
    }
    
}
