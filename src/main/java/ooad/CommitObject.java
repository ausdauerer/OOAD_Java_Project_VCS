package ooad;

import java.util.ArrayList;

public class CommitObject {
    RepositoryObject repo_snapshot;

    public CommitObject(){
        
    } 
    public CommitObject(RepositoryObject repo){
        repo_snapshot=repo;
    }
}
