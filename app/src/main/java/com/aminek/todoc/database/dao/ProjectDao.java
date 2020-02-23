package com.aminek.todoc.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.aminek.todoc.model.Project;

import java.util.List;

/**
 * Created by Amine K. on 09/02/20.
 */
//Le DAO (Objet d'Accès aux Données) est un design pattern qui propose de regrouper les accès
// aux données persistantes dans des classes à part, plutôt que de les disperser.
@Dao
public interface ProjectDao {

    //Nous y plaçons les actions CRUD en bdd qui nous intéressent via les méthodes suivantes :


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createProject(Project project);

    //Used for tests
    @Query("SELECT * FROM Project WHERE id = :projectId")
    LiveData<Project> getProject(long projectId);

    @Query("SELECT * FROM Project")
    LiveData<List<Project>> getAllTheProjects();

}
