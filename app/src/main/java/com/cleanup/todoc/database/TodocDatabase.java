package com.cleanup.todoc.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

/**
 * Created by Amine K. on 09/02/20.
 */
@Database(entities = {Task.class, Project.class}, version = 1, exportSchema = false)
public abstract class TodocDatabase extends RoomDatabase {

    // --- SINGLETON ---
    //un design pattern permettant de créer UNE SEULE FOIS la classe responsable de notre base de données et n'obtenir
    // qu'une seule et unique instance de référence
    private static volatile TodocDatabase INSTANCE;

    // --- DAO ---
    public abstract TaskDao taskDao();
    public abstract ProjectDao projectDao();

    // --- INSTANCE ---
    //Créera un nouvel objet RoomDatabase grâce à son builder Room.databaseBuilder et créera un fichier qui contiendra notre base
    // de données SQLite. Si jamais cette méthode est rappelée par la suite, nous renverrons uniquement la référence de notre
    // base de données.
    public static TodocDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (TodocDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TodocDatabase.class, "MyTodocDatabase.db")
                            .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    //Permet de remplir notre base de données avec un projet de test
    private static Callback prepopulateDatabase(){
        return new Callback() {

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                ContentValues contentValues = new ContentValues();
                contentValues.put("id", 9);
                contentValues.put("name", "Project neuf");
                contentValues.put("color", 0xFFEADAD1);

                db.insert("Project", OnConflictStrategy.IGNORE, contentValues);
            }
        };
    }
}
