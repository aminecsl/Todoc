package com.aminek.todoc.repositories;

import android.arch.lifecycle.LiveData;

import com.aminek.todoc.database.dao.ProjectDao;
import com.aminek.todoc.model.Project;

import java.util.List;

/**
 * Created by Amine K. on 11/02/20.
 */

//Sera utilisé dans notre ViewModel, dans la logique de l'Architecture Components, afin de manipuler les DAO de notre table Project
// On cherche ainsi à isoler la source de données (DAO) du ViewModel, afin que ce dernier ne manipule pas directement la source de données.
public class ProjectDataRepository {

    private final ProjectDao projectDao;


    public ProjectDataRepository(ProjectDao dao) {

        projectDao = dao;
    }

    // -- CREATE PROJECT --

    public void createProject(Project project) { projectDao.createProject(project); }

    // -- GET ALL PROJECTS --

    public LiveData<List<Project>> getAllTheProjects() { return projectDao.getAllTheProjects(); }

}
