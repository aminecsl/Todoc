package com.aminek.todoc;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.aminek.todoc.database.TodocDatabase;
import com.aminek.todoc.model.Project;
import com.aminek.todoc.model.Task;
import com.aminek.todoc.utils.LiveDataTestUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by Amine K. on 09/02/20.
 */
@RunWith(AndroidJUnit4.class)
public class DaoTests {

    // FOR DATA
    private TodocDatabase database;

    // DATA SET FOR TEST
    private static long PROJECT_ID = 77;
    private static Project PROJECT_DEMO = new Project(PROJECT_ID, "Projet test", 0xFFB4CDBA);
    private static Task TASK_DEMO_1 = new Task(PROJECT_ID, "task 1", 123);
    private static Task TASK_DEMO_2 = new Task(PROJECT_ID, "task 2", 456);


    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception {
        //Room nous fournit un builder appelé inMemoryDatabaseBuilder. Ce dernier permet de créer une instance de notre base
        // de données directement en mémoire (et non dans un fichier sur le périphérique)
        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                TodocDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    //Teste l'ajout et la récupération d'un nouveau Projet dans notre base SQLite.
    @Test
    public void insertAndGetProject() throws InterruptedException {
        database.projectDao().createProject(PROJECT_DEMO);
        Project project = LiveDataTestUtil.getValue(database.projectDao().getProject(PROJECT_ID));
        assertTrue(project.getName().equals(PROJECT_DEMO.getName()) && project.getId() == PROJECT_ID);
    }

    //Teste l'existence de notre table Task dans notre base SQLite et qu'elle est bien vide tant qu'on n'a pas inséré de tâche.
    @Test
    public void getTasksWhenNoTaskInserted() throws InterruptedException {
        List<Task> tasks = LiveDataTestUtil.getValue(database.taskDao().getTasks());
        assertTrue(tasks.isEmpty());
    }

    //Teste l'ajout et la récupération de tâches dans notre base de données
    @Test
    public void insertAndGetTasks() throws InterruptedException {
        database.projectDao().createProject(PROJECT_DEMO);
        database.taskDao().insertTask(TASK_DEMO_1);
        database.taskDao().insertTask(TASK_DEMO_2);

        List<Task> tasks = LiveDataTestUtil.getValue(database.taskDao().getTasks());
        assertTrue(tasks.size() == 2);
        assertTrue(tasks.get(0).equals(TASK_DEMO_1) && tasks.get(1).equals(TASK_DEMO_2));

    }

    //Teste la suppression d'une tâche dans notre base de données
    @Test
    public void insertAndDeleteTask() throws InterruptedException {
        database.projectDao().createProject(PROJECT_DEMO);
        database.taskDao().insertTask(TASK_DEMO_1);
        Task taskAdded = LiveDataTestUtil.getValue(database.taskDao().getTasks()).get(0);
        database.taskDao().deleteTask(taskAdded);

        List<Task> tasks = LiveDataTestUtil.getValue(database.taskDao().getTasks());
        assertTrue(tasks.isEmpty());
    }

    @After
    public void closeDb() throws Exception {
        database.close();
    }
}
