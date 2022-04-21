package ooad;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.protobuf.compiler.PluginProtos.CodeGeneratorResponse.File;


public class RepositoryObject implements Serializable {
    private static final long serialVersionUID = 6121244321953105191L;
    public String path;
    public ArrayList<FileObject> files;
    public ArrayList<CommitObject> commits;

    public RepositoryObject(){

    }

    public RepositoryObject(String path) throws SQLException{
        this.path=path;
        files=new ArrayList<FileObject>();
        commits=new ArrayList<CommitObject>();
        try {
            updateFilesInPath();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void updateFilesInPath() throws SQLException, FileNotFoundException, IOException{
        FileUtils ut=FileUtils.getInstance();
        ArrayList<String> filepaths=ut.getFilesFromPath(this.path);
        ArrayList<String> prevfilepaths=getFilesInRepo();
        for (int i = 0; i < filepaths.size(); i++){
            if(!prevfilepaths.contains(filepaths.get(i))){
                FileObject obj=new FileObject(filepaths.get(i));    
                files.add(obj);
            } 
            else
            {
                Blob b=getLatestFileBlob(filepaths.get(i));
                byte[] b1=b.getBytes(1, (int) b.length());
                byte[] b2=FileUtils.getByteArrayFromFile(filepaths.get(i));
                if(!Arrays.equals(b1,b2)){
                    for(int j=0;j<files.size();j++){
                        FileObject f=files.get(j);
                        if((f.path).compareTo(filepaths.get(i))==0){
                            System.out.println("Updating the file");
                            f.updateVersion();
                        }
                    }
                }
            }
            //obj.displayLatestVersion();
        }
    }

    public ArrayList<String> getFilesInRepo(){
        ArrayList<String> f=new ArrayList<>();
        for (int i = 0; i < files.size(); i++){
            FileObject obj=files.get(i);
            f.add(obj.path);
        }
        return(f);
    }
    public void displayRepository(){
        System.out.print("These are the repository contents -----------\n\n");
        for (int i = 0; i < files.size(); i++){
            FileObject obj=files.get(i);
            System.out.println(obj.path);
            /*try {
                obj.displayLatestVersion();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }*/
        }
    }
    
    public void displayChanges() throws FileNotFoundException, IOException, SQLException{
        FileUtils ut=FileUtils.getInstance();
        ArrayList<String> filepaths=ut.getFilesFromPath(this.path);
        ArrayList<String> prevfilepaths=getFilesInRepo();
        for (int i = 0; i < filepaths.size(); i++){
            if(!prevfilepaths.contains(filepaths.get(i))){
                System.out.println(filepaths.get(i)+"  New File");
            } 
            else{
                Blob b=getLatestFileBlob(filepaths.get(i));
                byte[] b1=b.getBytes(1, (int) b.length());
                byte[] b2=FileUtils.getByteArrayFromFile(filepaths.get(i));
                if(!Arrays.equals(b1,b2)){
                    System.out.println(filepaths.get(i)+"File Changed");
                }
            }
        }
    }

    public Blob getLatestFileBlob(String path) throws FileNotFoundException, IOException{
        Database db=Database.getInstance();
        Blob b=null;
        for(int i=0;i<files.size();i++){
            FileObject f=files.get(i);
            if((f.path).compareTo(path)==0){
                b=db.getBlob(f.getLatestBlobId());
            }
        }
        return b;
    }

}
