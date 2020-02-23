package com.aminek.todoc.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.aminek.todoc.model.Task;

import java.util.List;

/**
 * Created by Amine K. on 09/02/20.
 */
//Le DAO (Objet d'Accès aux Données) est un design pattern qui propose de regrouper les accès
// aux données persistantes dans des classes à part, plutôt que de les disperser.
@Dao
public interface TaskDao {

    //Nous y plaçons les actions CRUD en bdd qui nous intéressent via les méthodes suivantes :


    @Query("SELECT * FROM Task")
    LiveData<List<Task>> getTasks();

    @Insert
    void insertTask(Task task);

    @Delete
    void deleteTask(Task task);

}
