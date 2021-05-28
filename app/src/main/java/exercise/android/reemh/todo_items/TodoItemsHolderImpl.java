package exercise.android.reemh.todo_items;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.RequiresApi;

import net.bytebuddy.TypeCache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;


public class TodoItemsHolderImpl implements TodoItemsHolder {
    final int DONE = 1;
    final int INPROGRESS = 2;
    ArrayList<TodoItem> itemsList = new ArrayList<>();
    private final Context context;
    private SharedPreferences sp;

    // ToDo 1: generate ids using UUID
    // todo 2: add a real timeStamp, that ToDoItemsHolder will hold, instead of ToDOItem itself??

    public TodoItemsHolderImpl(Context context) {
        this.context = context;
        this.sp = context.getSharedPreferences("local_db", Context.MODE_PRIVATE);
        initializeFromSp();
    }
    /*

     */
    private void initializeFromSp() {
        Set<String> toDoItems = sp.getAll().keySet();
        for (String key : toDoItems) {
            String itemStringFromSp = sp.getString(key,null);
            TodoItem itemFromString = TodoItem.string_to_Item(itemStringFromSp); // make sure this static method works!
            if (itemFromString!=null){
                itemsList.add(itemFromString);
            }

        }
        Collections.sort(itemsList, new ToDoItemsCompartor());
    }

    @Override
    public List<TodoItem> getCurrentItems() {

        return new ArrayList<>(itemsList);
    }


    @Override
    public void addNewInProgressItem(String description) {
        String id = UUID.randomUUID().toString();
        TodoItem newTodoItem = new TodoItem(INPROGRESS, description); //todo: add id and time stamp!
        itemsList.add(newTodoItem);
        Collections.sort(itemsList, new ToDoItemsCompartor());
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(newTodoItem.getId(), newTodoItem.serializable());
        editor.apply();
    }


    @Override
    public void markItemDone(TodoItem item) {
        item.setStatus(DONE);
        Collections.sort(itemsList, new ToDoItemsCompartor());
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(item.getId(), item.serializable());
        editor.apply();
    }


    @Override
    public void markItemInProgress(TodoItem item) {
        item.setStatus(INPROGRESS);
        Collections.sort(itemsList, new ToDoItemsCompartor());
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(item.getId(), item.serializable());
        editor.apply();
    }


    @Override
    public void deleteItem(TodoItem item) {
        itemsList.remove(item);
        Collections.sort(itemsList, new ToDoItemsCompartor());
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(item.getId());
        editor.apply();

    }

    @Override
    public void clear() {
        SharedPreferences.Editor editor = sp.edit();
        for (int i = 0; i < itemsList.size(); i++) {
            editor.remove(itemsList.get(i).getId());
        }
        editor.apply();
        itemsList.clear();
    }


    @Override
    public void addAll(List<TodoItem> items) {
        itemsList.addAll(items);
        Collections.sort(itemsList, new ToDoItemsCompartor());
        SharedPreferences.Editor editor = sp.edit();
        for (int i = 0; i < itemsList.size(); i++) {
            editor.putString(itemsList.get(i).getId(), itemsList.get(i).serializable());
        }
        editor.apply();
    }

    @Override
    public int size() {
        return itemsList.size();
    }
}
