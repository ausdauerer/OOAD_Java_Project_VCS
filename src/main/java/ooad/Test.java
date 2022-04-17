package ooad;
import java.sql.Blob;
import java.sql.SQLException;

import javax.xml.crypto.Data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;

public class Test {
    public static void main(String[] args) throws SQLException, FileNotFoundException{
        //FileObject file=new FileObject("/home/harshavardhan/abc.txt");
        //file.displayLatestVersion();
        RepositoryObject repo=new RepositoryObject("/home/harshavardhan/testing");
        Database db=Database.getInstance();
        db.saveRepository(repo);

        RepositoryObject obj=db.getRepository("/home/harshavardhan/testing");
        obj.displayRepository();
    }
}
