package ooad;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

//import com.google.protobuf.compiler.PluginProtos.CodeGeneratorResponse.File;


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
        ArrayList<FileObject> changedFiles=new ArrayList<FileObject>();
        ArrayList<String> filepaths=ut.getFilesFromPath(this.path);
        ArrayList<String> prevfilepaths=getFilesInRepo();
        String changeString=getChangesString();
        for (int i = 0; i < filepaths.size(); i++){
            if(!prevfilepaths.contains(filepaths.get(i))){
                FileObject obj=new FileObject(filepaths.get(i));    
                files.add(obj);
                changedFiles.add(obj);
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
                            changedFiles.add(f);
                        }
                    }
                }
            }
        }
        commits.add(new CommitObject(changedFiles,changeString));
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
                    System.out.println(filepaths.get(i)+" File Changed");
                }
            }
        }
    }

    public String getChangesString() throws FileNotFoundException, IOException, SQLException{
        String changes="";
        FileUtils ut=FileUtils.getInstance();
        ArrayList<String> filepaths=ut.getFilesFromPath(this.path);
        ArrayList<String> prevfilepaths=getFilesInRepo();
        for (int i = 0; i < filepaths.size(); i++){
            if(!prevfilepaths.contains(filepaths.get(i))){
                changes+=filepaths.get(i)+"  New File\n";
            } 
            else{
                Blob b=getLatestFileBlob(filepaths.get(i));
                byte[] b1=b.getBytes(1, (int) b.length());
                byte[] b2=FileUtils.getByteArrayFromFile(filepaths.get(i));
                if(!Arrays.equals(b1,b2)){
                    changes+=filepaths.get(i)+"  File Changed\n";
                }
            }
        }
        return(changes);
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

    public void commitRepository() throws FileNotFoundException, IOException, SQLException{
        updateFilesInPath();
        Database db=Database.getInstance();
        db.serializeRepositoryToDatabase(this);
    }

    public void showCommits() throws FileNotFoundException, IOException, SQLException{
        for(int i=0;i<commits.size();i++){
            CommitObject com=commits.get(i);
            System.out.println("\n\nCommit "+(i+1)+"\n"+com.change);
        }
    }
    
    public void openInVSCode(){
        try {
            ProcessBuilder processBuilder=new ProcessBuilder();
            processBuilder.command("code", this.path);
            Process process = processBuilder.start();
    
            StringBuilder output = new StringBuilder();
    
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
    
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }
    
            int exitVal = process.waitFor();
            if (exitVal == 0) {
                System.out.println("Success!");
                System.out.println(output);
            } else {
                //abnormal...
            }
    
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void rollback() throws Exception{
        if(commits.size()==0){
            System.out.println("There is no commit");
            return;
        }
        //commits.remove(commits.size()-1);
        CommitObject co=commits.get(commits.size()-1);
        for(int i=0;i<co.files.size();i++){
            FileObject f=co.files.get(i);
            System.out.println("Updating file -> "+f.path);
            if(!f.updateToPreviousVersion()){
                File dFile=new File(f.path);
                dFile.delete();
                for(int j=0;j<files.size();j++){
                    if(files.get(j).path.compareTo(f.path)==0){
                        files.remove(i);
                    }
                }
            }
        }
        if(commits.size()>=1)
            commits.remove(commits.size()-1);
        Database db=Database.getInstance();
        db.serializeRepositoryToDatabase(this);
    }

    public void writeFiles() throws FileNotFoundException, IOException, SQLException{
        for(int i=0;i<files.size();i++){
            FileObject file=files.get(i);
            int blobid=file.getLatestBlobId();
            Database db=Database.getInstance();
            Blob b=db.getBlob(blobid);
            byte[] byt=b.getBytes(1, (int) b.length());
            OutputStream os = new FileOutputStream(file.path);
 
            // Starting writing the bytes in it
            os.write(byt);
 
            // Dislay message onconsole for successful
            // execution
            System.out.println("Successfully replaced file "+file.path);
 
            // Close the file connections
            os.close();
        }
    }
    public RepositoryObject clone(){
        RepositoryObject r=new RepositoryObject();
        r.path=this.path;
        r.files=this.files;
        r.commits=this.commits;
        return(r);
    }
}
