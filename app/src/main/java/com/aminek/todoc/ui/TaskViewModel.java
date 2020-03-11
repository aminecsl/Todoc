package com.aminek.todoc.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.aminek.todoc.model.Project;
import com.aminek.todoc.model.Task;
import com.aminek.todoc.repositories.ProjectDataRepository;
import com.aminek.todoc.repositories.TaskDataRepository;

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
    //la classe Executor permet de réaliser de manière asynchrone des tâches pouvant être lourdes/longues à executer
    // et bloque l'interface graphique si une telle tache est effectuée dans le thread principal.
    private final Executor executor;

    // DATA
    @Nullable
    private LiveData<List<Project>> allProjectsList;

    public TaskViewModel(TaskDataRepository taskDataSource, ProjectDataRepository projectDataSource, Executor executor){

        this.taskDataSource = taskDataSource;
        this.projectDataSource = projectDataSource;
        this.executor = executor;
    }

    // méthode init() qui permettra d'initialiser notre TaskViewModel dès que la MainActivity sera ée et qui sera donc appelée à
    // l'intérieur de sa méthode onCreate()
    public void init() {
        //On vérifie dans cette méthode si la liste de projets n'existe pas déjà dans le ViewModel car le ViewModel garde en "mémoire"
        // ses données, même si l'activité qui l'a appelée est détruite
        if (allProjectsList != null) {
            //Si nos projets avaient déjà été récupérés, pas besoin de refaire une query à la base de données
            return;
        }
        allProjectsList = projectDataSource.getAllTheProjects();
    }

    // -------------
    // FOR PROJECTS
    // -------------

    public LiveData<List<Project>> getAllTheProjects() { return allProjectsList;  }


    // -------------
    // FOR TASKS
    // -------------

    public LiveData<List<Task>> getTasks() {
        return taskDataSource.getTasks();
    }

    //Les methodes suivantes sont gérées via l'Executor pour pouvoir être traité de façon asynchrone tandis que les méthodes précédentes
    //qui retournent des données de type LiveData sont déjà, par définition gérées de manière asynchrone
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
