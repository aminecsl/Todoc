package com.aminek.todoc.repositories;

import android.arch.lifecycle.LiveData;

import com.aminek.todoc.database.dao.TaskDao;
import com.aminek.todoc.model.Task;

import java.util.List;

/**
 * Created by Amine K. on 11/02/20.
 */

//Sera utilisé dans notre ViewModel, dans la logique de l'Architecture Components, afin de manipuler les DAO de notre table Task
public class TaskDataRepository {

    private final TaskDao taskDao;


    public TaskDataRepository (TaskDao dao) {

        taskDao = dao;
    }

    // -- GET ALL THE TASKS FROM DB --
    public LiveData<List<Task>> getTasks() { return taskDao.getTasks(); }

    // --- CREATE ---

    public void createTask(Task task){ taskDao.insertTask(task); }

    // --- DELETE ---
    public void deleteTask(Task task){ taskDao.deleteTask(task); }

}
