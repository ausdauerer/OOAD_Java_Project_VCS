package ooad;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JavaType;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;
import java.sql.SQLException;


public class FileUtils {
    static FileUtils instance;
    ArrayList<String> files;

    public FileUtils(){
        files=new ArrayList<String>();
    }

    public ArrayList<String> getFilesFromPath(String path){
        files.clear();
        searchFiles(path);
        return(this.files);
    }
    public static FileUtils getInstance(){
        if(instance==null){
            instance=new FileUtils();
        }
        return(instance);
    }

    public void searchFiles(String path){
        try{
            File directory=new File(path);
            String[] f=directory.list();
            for(int i=0;i<f.length;i++){
                File a=new File(path+"/"+f[i]);
                if(a.isDirectory()==true){
                    searchFiles(path+"/"+f[i]);
                }
                else{
                    this.files.add(path+"/"+f[i]);
                }
            }
        }
        catch(Exception e){}
    }
    
    public void displayFiles(){
        for (int i = 0; i < files.size(); i++)
            System.out.println(files.get(i) + " ");
    }

    public static <T> String getJsonStringFromObject(T obj){
        ObjectMapper mapper=new ObjectMapper();
        String jsonString="";
        try {
            jsonString = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return(jsonString);
    }

    public static <T> T getObjectFromJsonString(String str,Class<T> toClass){
        try {
            ObjectMapper mapper = new ObjectMapper();
            return (T)mapper.readValue(str, toClass);
            }catch(Exception e)
            {
                e.printStackTrace();
                return null;
            }
    }

    public static byte[] getByteArrayFromFile(String path){
        byte bytes[]=null;
        try {

            bytes = Files.readAllBytes(Paths.get(path));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return(bytes);
    }

    public static Blob getBlobFromByteArray(byte[] b) throws SQLException{
        Blob blob=null;
        try {
            blob=new SerialBlob(b);
        } catch (SerialException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return(blob);
    }
}
