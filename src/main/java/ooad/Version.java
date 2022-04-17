package ooad;
import java.io.Serializable;

public class Version implements Serializable{
    public int blobid;
    public Version(int blobid){
        this.blobid=blobid;
    }
    public Version(){
    }
}
