package com.cleanup.todoc.repositories;

import android.arch.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

import java.util.List;

/**
 * Created by Amine K. on 11/02/20.
 */
public class ProjectDataRepository {

    private final ProjectDao projectDao;


    public ProjectDataRepository(ProjectDao dao) {

        projectDao = dao;
    }

    // -- CREATE PROJECT --

    public void createProject(Project project) { projectDao.createProject(project); }

    // -- GET ALL PROJECTS --

    public LiveData<List<Project>> getAllTheProjects() { return this.projectDao.getAllTheProjects(); }

}
