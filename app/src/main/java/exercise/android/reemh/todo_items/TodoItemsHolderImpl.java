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

// TODO: implement!
public class TodoItemsHolderImpl implements TodoItemsHolder {
    final int DONE = 1;
    final int INPROGRESS = 2;
    ArrayList<TodoItem> itemsList = new ArrayList<>();
    private final Context context;
    private final SharedPreferences sp;

    public TodoItemsHolderImpl(Context context){
        this.context = context;
        sp = context.getSharedPreferences("local_ToDoItemsHolder_db",Context.MODE_PRIVATE);

    }

    @Override
    public List<TodoItem> getCurrentItems() {

        return new ArrayList<>(itemsList);
    }


    @Override
    public void addNewInProgressItem(String description) {
        itemsList.add(new TodoItem(INPROGRESS, description));
        Collections.sort(itemsList, new ToDoItemsCompartor());
    }


    @Override
    public void markItemDone(TodoItem item) {
        item.setStatus(DONE);
        Collections.sort(itemsList, new ToDoItemsCompartor());
    }


    @Override
    public void markItemInProgress(TodoItem item) {
        item.setStatus(INPROGRESS);
        Collections.sort(itemsList, new ToDoItemsCompartor());
    }


    @Override
    public void deleteItem(TodoItem item) {
        itemsList.remove(item);
        Collections.sort(itemsList, new ToDoItemsCompartor());

    }

    @Override
    public void clear() { itemsList.clear(); }


    @Override
    public void addAll(List<TodoItem> items){
        itemsList.addAll(items);
        Collections.sort(itemsList, new ToDoItemsCompartor());
    }
    @Override
    public int size(){ return itemsList.size();}
}
