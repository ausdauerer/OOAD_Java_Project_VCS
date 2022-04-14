package ooad;
import java.io.FileOutputStream;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;

public class Test {
    File file;
    ObjectMapper mapper;
    public Test(){
        mapper = new ObjectMapper();
        File file=new File();
        file.addTestVersions();
        String jsonInString = mapper.writeValueAsString(new String("Hello"));
    }
    public void storeFile(){
        try{
            FileOutputStream fout=new FileOutputStream("./store.ser");
            ObjectOutputStream out=new ObjectOutputStream(fout);
            out.writeObject(new String("Hello World"));
            out.close();
            fout.close();
            System.out.print("Successfully stored the object");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        file=null;
    }
    public void readFile(){
        try{
            FileInputStream fileIn = new FileInputStream("./store.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            String cont = (String) in.readObject();
            System.out.print(cont);
            in.close();
            fileIn.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        Test obj=new Test();
        obj.storeFile();
        obj.readFile();
        System.out.println(obj.file);
    }
}
