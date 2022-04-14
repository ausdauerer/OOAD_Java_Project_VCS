package ooad;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class FileUtils {
    ArrayList<String> files;

    public FileUtils(){
        files=new ArrayList<String>();
    }

    public void getFiles(String path){
        try{
            System.out.println(path);
            File directory=new File(path);
            String[] f=directory.list();
            for(int i=0;i<f.length;i++){
                File a=new File(path+"/"+f[i]);
                if(a.isDirectory()==true){
                    getFiles(path+"/"+f[i]);
                }
            }
            if(f.length>0){
            this.files.addAll(Arrays.asList(f));}
        }
        catch(Exception e){}
    }
    
    public void displayFiles(){
        for (int i = 0; i < files.size(); i++)
            System.out.println(files.get(i) + " ");
    }

    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        String path=sc.next();
        FileUtils obj=new FileUtils();

        obj.getFiles(path);
        //obj.displayFiles();
        sc.close();
    }
}
