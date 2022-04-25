package ooad;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.jdbc.Driver;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.ByteArrayInputStream;
import java.io.File;

public class Database {
    private static Database instance;
    Connection c;
    public static Database getInstance(){
        if(instance==null){
            instance=new Database();
            return(instance);
        }
        else{
            return(instance);
        }
    }
    public Database(){
        try {
            c = DriverManager.getConnection("jdbc:mysql://localhost/vcsdata","vcsclient", "1234");
        } 
        catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }
    public int saveFile(String path) throws FileNotFoundException, SQLException{
        String SQLinsert = "insert into blobs(data) "+"VALUES(?);";
        try{
            File f=new File(path);
            PreparedStatement pstmt = c.prepareStatement(SQLinsert, Statement.RETURN_GENERATED_KEYS);
            FileInputStream fin = new FileInputStream(f);
            pstmt.setBlob(1, fin);
            pstmt.execute();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        int autoIncKeyFromFunc = -1;
        ResultSet rs = c.createStatement().executeQuery("SELECT LAST_INSERT_ID()");

        if (rs.next()) {
            autoIncKeyFromFunc = rs.getInt(1);
        } else {
            // throw an exception from here
        }
        System.out.println("Successfully inserted data into table with ID : "+autoIncKeyFromFunc);
        return(autoIncKeyFromFunc);
    }
    public void saveRepository(RepositoryObject repo) throws FileNotFoundException, SQLException{
        String SQLinsert = "update repos set data=? where path=?;";
        try{
            PreparedStatement pstmt = c.prepareStatement(SQLinsert, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(2, repo.path);
            ObjectMapper mapper = new ObjectMapper();
            String json="";
            try {
                json = mapper.writeValueAsString(repo);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            pstmt.setString(1,json);
            pstmt.execute();
        }
        catch(SQLException e){
            e.printStackTrace();

        }
    }
    public RepositoryObject getRepository(String path) throws FileNotFoundException, SQLException{
        String selectSQL = "SELECT data FROM repos WHERE path=?";
        ResultSet rs = null;
        RepositoryObject repo=null; 

        try {
            PreparedStatement pstmt = c.prepareStatement(selectSQL);
            // set parameter;
            pstmt.setString(1, path);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                String json=rs.getString("data");
                repo=FileUtils.getObjectFromJsonString(json, RepositoryObject.class);
            }
            
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return(repo);
    }
    public Blob getBlob(int id) throws FileNotFoundException, IOException{
        String selectSQL = "SELECT data FROM blobs WHERE id=?";
        ResultSet rs = null;
        Blob bl=null;

        try {
            PreparedStatement pstmt = c.prepareStatement(selectSQL);
            // set parameter;
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                bl=rs.getBlob("data");
            }
            
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return(bl);
    }

    public void serializeRepositoryToDatabase(RepositoryObject repo) throws SQLException{
        String SQL_SERIALIZE_OBJECT="";
        if(!getAllRepositories().contains(repo.path)){
            SQL_SERIALIZE_OBJECT = "INSERT INTO repositories(path,data) VALUES (?, ?)";
            PreparedStatement pstmt;
        try {
            pstmt = c.prepareStatement(SQL_SERIALIZE_OBJECT);
            pstmt.setString(1, repo.path);
		    pstmt.setObject(2, repo);
		    pstmt.executeUpdate();
		    pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        } 
        else{
            SQL_SERIALIZE_OBJECT = "update repositories set data=? where path=?;";
            PreparedStatement pstmt;
        try {
            pstmt = c.prepareStatement(SQL_SERIALIZE_OBJECT);
            pstmt.setString(2, repo.path);
		    pstmt.setObject(1, repo);
		    pstmt.executeUpdate();
		    pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        }
    }

    public RepositoryObject deserializeRepositoryFromDatabase(String path) throws SQLException, IOException, ClassNotFoundException{
        String SQL_SERIALIZE_OBJECT = "SELECT data FROM repositories WHERE path = ?";
        PreparedStatement pstmt = c.prepareStatement(SQL_SERIALIZE_OBJECT);
		pstmt.setString(1, path);
		ResultSet rs = pstmt.executeQuery();
		rs.next();

		byte[] buf = rs.getBytes(1);
		ObjectInputStream objectIn = null;
		if (buf != null)
			objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));

		RepositoryObject deSerializedObject = (RepositoryObject)objectIn.readObject();

		rs.close();
		pstmt.close();
		return deSerializedObject;
    }

    public ArrayList<String> getAllRepositories() throws SQLException{
        ArrayList<String> abc=new ArrayList<String>();
        String SQL_SERIALIZE_OBJECT = "SELECT path FROM repositories;";
        PreparedStatement pstmt = c.prepareStatement(SQL_SERIALIZE_OBJECT);
		ResultSet rs = pstmt.executeQuery();
		
        while(rs.next()){
            abc.add(rs.getString("path"));
        }

		rs.close();
		pstmt.close();
		return abc;
    }

    public void deleteRepository(String path) throws SQLException{
        String SQL_SERIALIZE_OBJECT = "DELETE FROM repositories WHERE path = ?";
        PreparedStatement pstmt = c.prepareStatement(SQL_SERIALIZE_OBJECT);
		pstmt.setString(1, path);
		pstmt.execute();
    }

}
