package ooad;

import java.io.Serializable;
import java.util.ArrayList;

public class CommitObject implements Serializable{
    private static final long serialVersionUID =978646748198429860L;
    public ArrayList<FileObject> files;
    public String change;

    public CommitObject(){
        
    } 
    public CommitObject(ArrayList<FileObject> files,String changes){
        this.files=new ArrayList<FileObject>();
        this.files.addAll(files);
        this.change=changes;
    }
}
