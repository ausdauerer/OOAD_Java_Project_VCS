package ooad;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.xml.crypto.Data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;

public class Test {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException{
        //FileObject file=new FileObject("/home/harshavardhan/abc.txt");
        //file.displayLatestVersion();

        String path="/home/harshavardhan/College";
        Database db=Database.getInstance();

        ArrayList<String> list=db.getAllRepositories();
        System.out.println("These are the repositories present ---------\n");
        for(int i=0;i<list.size();i++){
            System.out.println(list.get(i));
        }

        if(!(db.getAllRepositories().contains(path))){
            RepositoryObject repo=new RepositoryObject(path);
            db.serializeRepositoryToDatabase(repo);
        }

        RepositoryObject obj=db.deserializeRepositoryFromDatabase(path);
        obj.displayRepository();


        //FileUtils ut=FileUtils.getInstance();
        // ArrayList<String> files=ut.getFilesFromPath("/home/harshavardhan/testing");
        // for(int i=0;i<files.size();i++){
            // System.out.println(files.get(i));
        // }
    }
}
