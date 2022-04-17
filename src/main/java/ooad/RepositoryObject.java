package ooad;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;

public class RepositoryObject implements Serializable {
    public String path;
    public ArrayList<FileObject> files;
    public ArrayList<CommitObject> commits;

    public RepositoryObject(){

    }

    public RepositoryObject(String path) throws SQLException{
        this.path=path;
        files=new ArrayList<FileObject>();
        commits=new ArrayList<CommitObject>();
        updateFilesInPath();
    }

    private void updateFilesInPath() throws SQLException{
        ArrayList<String> filepaths=getFilesFromPath(this.path);
        for (int i = 0; i < filepaths.size(); i++){
            System.out.println(filepaths.get(i));
            FileObject obj=new FileObject(filepaths.get(i));    
            files.add(obj);  
            obj.displayLatestVersion();
        }
    }
    public static ArrayList<String> getFilesFromPath(String path){
        ArrayList<String> fi=new ArrayList<String>();
        fi.add("/home/harshavardhan/testing/file1.txt");
        fi.add("/home/harshavardhan/testing/file2.txt");
        fi.add("/home/harshavardhan/testing/file3.txt");
        return(fi);
    }

    public void displayRepository(){
        System.out.print("These are the repository contents -----------\n\n");
        for (int i = 0; i < files.size(); i++){
            FileObject obj=files.get(i);
            try {
                obj.displayLatestVersion();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
