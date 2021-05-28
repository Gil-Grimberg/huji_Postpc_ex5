package exercise.android.reemh.todo_items;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class ToDoItemsApp extends Application {

    private TodoItemsHolderImpl dataBase;

    public TodoItemsHolderImpl getDataBase(){
        return dataBase;
    }
    public static ToDoItemsApp instance = null;

    public static ToDoItemsApp getInstance(){
        return instance;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        dataBase = new TodoItemsHolderImpl(this);
    }
}
