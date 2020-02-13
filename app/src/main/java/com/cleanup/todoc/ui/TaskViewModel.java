package com.cleanup.todoc.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.ProjectDataRepository;
import com.cleanup.todoc.repositories.TaskDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * Created by Amine K. on 11/02/20.
 */
//Son rôle sera de fournir notre activity les données utilisées par l'interface graphique. Une des spécificités de la classe ViewModel
// est sa capacité à "survivre" aux changements de configuration, comme la rotation de l'écran par exemple, sans perdre ses données...
public class TaskViewModel extends ViewModel {

    // REPOSITORIES
    private final TaskDataRepository taskDataSource;
    private final ProjectDataRepository projectDataSource;
    //la classe Executor permet de réaliser de manière asynchrone notamment les requêtes de mise à jour de nos tables SQLite.
    private final Executor executor;

    // DATA
    @Nullable
    private LiveData<List<Project>> allProjectsList;

    public TaskViewModel(TaskDataRepository taskDataSource, ProjectDataRepository projectDataSource, Executor executor){

        this.taskDataSource = taskDataSource;
        this.projectDataSource = projectDataSource;
        this.executor = executor;
    }

    public void init() {
        if (allProjectsList != null) {
            return;
        }
        allProjectsList = projectDataSource.getAllTheProjects();
    }

    // -------------
    // FOR PROJECTS
    // -------------

    public LiveData<List<Project>> getAllTheProjetcs() { return allProjectsList;  }


    // -------------
    // FOR TASKS
    // -------------

    public LiveData<List<Task>> getTasks() {
        return taskDataSource.getTasks();
    }

    public void createTask(Task task) {
        executor.execute(() -> {
            taskDataSource.createTask(task);
        });
    }

    public void deleteTask(Task task) {
        executor.execute(() -> {
            taskDataSource.deleteTask(task);
        });
    }

}
