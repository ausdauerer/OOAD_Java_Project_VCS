package ooad;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    RepositoryObject repo;
    public static void main( String[] args ) throws Exception
    {
        App app=new App();
        //Scanner sc= new Scanner(System.in);
        InputStreamReader r=new InputStreamReader(System.in);    
        BufferedReader br=new BufferedReader(r);  
        int choice=0;
        while(choice!=5){
        System.out.println("1 - Create Repository");
        System.out.println("2 - Load Repository");
        System.out.println("3 - Display Files in Repository");
        System.out.println("4 - Update Repository");
        System.out.println("6 - Show Changes");
        System.out.println("5 - Exit");
        System.out.println("7 - Show commits");
        System.out.println("8 - commit");
        System.out.println("9 - delete repo");
        System.out.println("10 - open in Editor");
        System.out.println("Enter Choice :");

        choice=Integer.parseInt(br.readLine());
        switch(choice)
        {
            case 1:
                System.out.print("Enter Path :");
                String in=br.readLine();
                app.CreateRepository(in);
            break;
            case 2:
                System.out.print("Enter Path :");
                String in1=br.readLine();
                app.OpenRepository(in1);
            break;
            case 3:
                app.displayFiles();
            break;
            case 4:
                app.updateRepository();
            break;
            case 5:
                break;
            case 6:
                app.displayChanges();
            break;
            case 7:
                app.repo.showCommits();
            break;
            case 8:
                app.repo.commitRepository();
            break;
            case 9:
                Database db=Database.getInstance();
                db.deleteRepository(app.repo.path);
                app.repo=null;
                System.out.println("Successfully deleted");
            break;
            case 10:
                app.repo.openInVSCode();
            break;
            case 11:
                app.repo.rollback();
            break;
        }
        }
    }
    public void CreateRepository(String path) throws SQLException{
        Database db=Database.getInstance();
        if(!(db.getAllRepositories().contains(path))){
            RepositoryObject repo=new RepositoryObject(path);
            db.serializeRepositoryToDatabase(repo);
        }
        else{
            System.out.println("Error : Repository Already present");
        }
    }
    public void OpenRepository(String path) throws SQLException, ClassNotFoundException, IOException{
        Database db=Database.getInstance();
        RepositoryObject obj=db.deserializeRepositoryFromDatabase(path);
        repo=obj;
        System.out.println("Repository successfully loaded");
    }
    public void displayFiles() throws SQLException, ClassNotFoundException, IOException{
        repo.displayRepository();
    }
    public void updateRepository() throws SQLException, FileNotFoundException, IOException{
        repo.updateFilesInPath();
    }
    public void deleteRepository(String path) throws SQLException {
        Database db=Database.getInstance();
        db.deleteRepository(path);
    }

    public void displayChanges() throws SQLException, FileNotFoundException, IOException {
        repo.displayChanges();
    }
}
