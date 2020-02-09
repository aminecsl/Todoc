package com.cleanup.todoc.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.cleanup.todoc.model.Project;

/**
 * Created by Amine K. on 09/02/20.
 */
@Dao
public interface ProjectDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createProject(Project project);

    //TODO : ATTENTION A ADAPTER EN FONCTION DES PROJECTS QU'ON VEUT BIEN RECUPERER
    @Query("SELECT * FROM Project WHERE id = :projectId")
    LiveData<Project> getProject(long projectId);

}
