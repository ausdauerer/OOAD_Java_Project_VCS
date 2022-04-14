package ooad;

import java.util.*;
import java.io.Serializable;

public class File implements Serializable{
    ArrayList<Version> versions;
    public File(){
        versions=new ArrayList<Version>();
    }
    public void addTestVersions(){
        versions.add(new Version("Version1"));
        versions.add(new Version("Version2"));
        versions.add(new Version("Version3"));
        versions.add(new Version("Version4"));
    }
}
