package exercise.android.reemh.todo_items;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import net.bytebuddy.TypeCache;

import java.time.LocalDateTime;
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
    private final MutableLiveData<ArrayList<TodoItem>> toDoItemsLiveDataMutable = new MutableLiveData<>();
    public final LiveData<ArrayList<TodoItem>> toDoItemsLiveDataPublic = toDoItemsLiveDataMutable;
    // ToDo 1: generate ids using UUID
    // todo 2: add a real timeStamp, that ToDoItemsHolder will hold, instead of ToDOItem itself??

    @RequiresApi(api = Build.VERSION_CODES.O)
    public TodoItemsHolderImpl(Context context) {
        this.context = context;
        this.sp = context.getSharedPreferences("local_db", Context.MODE_PRIVATE);
        initializeFromSp();
    }
    /*

     */
    @RequiresApi(api = Build.VERSION_CODES.O)
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
        toDoItemsLiveDataMutable.setValue(new ArrayList<TodoItem>(itemsList));
    }

    @Override
    public List<TodoItem> getCurrentItems() {

        return new ArrayList<>(itemsList);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addItem(TodoItem item)
    {
        itemsList.add(item);
        Collections.sort(itemsList, new ToDoItemsCompartor());
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(item.getId(), item.serializable());
        editor.apply();
        toDoItemsLiveDataMutable.setValue(new ArrayList<TodoItem>(itemsList));
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void addNewInProgressItem(String description) {
        String id = UUID.randomUUID().toString();
        LocalDateTime createdTime = LocalDateTime.now();
        TodoItem newTodoItem = new TodoItem(INPROGRESS, description, createdTime, createdTime, id); //todo: add id and time stamp!
        itemsList.add(newTodoItem);
        Collections.sort(itemsList, new ToDoItemsCompartor());
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(newTodoItem.getId(), newTodoItem.serializable());
        editor.apply();
        toDoItemsLiveDataMutable.setValue(new ArrayList<TodoItem>(itemsList));
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void markItemDone(TodoItem item) {
        LocalDateTime lastModifiedTime = LocalDateTime.now();
        item.setLastModified(lastModifiedTime);
        item.setStatus(DONE);
        Collections.sort(itemsList, new ToDoItemsCompartor());
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(item.getId(), item.serializable());
        editor.apply();
        toDoItemsLiveDataMutable.setValue(new ArrayList<TodoItem>(itemsList));
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void markItemInProgress(TodoItem item) {
        LocalDateTime lastModifiedTime = LocalDateTime.now();
        item.setLastModified(lastModifiedTime);
        item.setStatus(INPROGRESS);
        Collections.sort(itemsList, new ToDoItemsCompartor());
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(item.getId(), item.serializable());
        editor.apply();
        toDoItemsLiveDataMutable.setValue(new ArrayList<TodoItem>(itemsList));
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void deleteItem(TodoItem item) {
        if (item==null) return;
        itemsList.remove(item);
        Collections.sort(itemsList, new ToDoItemsCompartor());
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(item.getId());
        editor.apply();
        toDoItemsLiveDataMutable.setValue(new ArrayList<TodoItem>(itemsList));

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void clear() {
        SharedPreferences.Editor editor = sp.edit();
        for (int i = 0; i < itemsList.size(); i++) {
            editor.remove(itemsList.get(i).getId());
        }
        editor.apply();
        itemsList.clear();
//        toDoItemsLiveDataMutable.setValue(new ArrayList<TodoItem>(itemsList));
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void addAll(List<TodoItem> items) {
        if (!itemsList.isEmpty())
        {
            itemsList.clear();
        }
        itemsList.addAll(items);
        Collections.sort(itemsList, new ToDoItemsCompartor());
        SharedPreferences.Editor editor = sp.edit();
        for (int i = 0; i < itemsList.size(); i++) {
            editor.putString(itemsList.get(i).getId(), itemsList.get(i).serializable());
        }
        editor.apply();
//        toDoItemsLiveDataMutable.setValue(new ArrayList<TodoItem>(itemsList));
    }

    @Override
    public int size() {
        return itemsList.size();
    }
}
