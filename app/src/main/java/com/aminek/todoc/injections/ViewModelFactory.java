package com.aminek.todoc.injections;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.aminek.todoc.repositories.ProjectDataRepository;
import com.aminek.todoc.repositories.TaskDataRepository;
import com.aminek.todoc.ui.TaskViewModel;

import java.util.concurrent.Executor;

/**
 * Created by Amine K. on 11/02/20.
 */
//Permet de déporter la construction de notre ViewModel pour ne pas surcharger de code notre activity (contrôleur principal)
    //Pattern dont le role est d'instancier des classes
public class ViewModelFactory implements ViewModelProvider.Factory {

    private final TaskDataRepository taskDataSource;
    private final ProjectDataRepository projectDataSource;
    private final Executor executor;

    public ViewModelFactory(TaskDataRepository taskDataSource, ProjectDataRepository projectDataSource, Executor executor) {
        this.taskDataSource = taskDataSource;
        this.projectDataSource = projectDataSource;
        this.executor = executor;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TaskViewModel.class)) {
            return (T) new TaskViewModel(taskDataSource, projectDataSource, executor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
