package com.cleanup.todoc.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.cleanup.todoc.model.Task;

import java.util.List;

/**
 * Created by Amine K. on 09/02/20.
 */
//Le DAO (Objet d'Accès aux Données) est un design pattern qui propose de regrouper les accès
// aux données persistantes dans des classes à part, plutôt que de les disperser.
@Dao
public interface TaskDao {

    //Nous avons à disposition les 4 actions CRUD de notre bdd via les méthodes suivantes :


    @Query("SELECT * FROM Task")
    LiveData<List<Task>> getTasks();

    @Insert
    long insertTask(Task task);

    @Update
    int updateTask(Task task);

    //Il est aussi possible d'utiliser l'annotation @Delete et de passer directement un objet Task pour le supprimer.
    @Delete
    int deleteTask(Task task);

}
