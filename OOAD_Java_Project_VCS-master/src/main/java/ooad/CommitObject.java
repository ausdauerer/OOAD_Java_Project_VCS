package ooad;

import java.util.ArrayList;

public class CommitObject {
    ArrayList<FileObject> files;
    ArrayList<Version> versions;

    public CommitObject(){
        files=new ArrayList<FileObject>();
        versions=new ArrayList<Version>();
    } 

    public void addFile(FileObject file, Version version){
        this.files.add(file);
        this.versions.add(version);
    }
}
