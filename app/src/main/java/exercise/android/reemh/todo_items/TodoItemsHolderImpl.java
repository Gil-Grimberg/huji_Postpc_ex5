package exercise.android.reemh.todo_items;

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
//    ToDoItemsCompartor compartor = new ToDoItemsCompartor();

    @Override
    public List<TodoItem> getCurrentItems() {

        return new ArrayList<>(itemsList);
    }


    @Override
    public void addNewInProgressItem(String description) {
        itemsList.add(new TodoItem(INPROGRESS, description));
//        itemsList.sort(compartor);
    }


    @Override
    public void markItemDone(TodoItem item) {
        item.setStatus(DONE);
//        itemsList.sort(compartor);
    }


    @Override
    public void markItemInProgress(TodoItem item) {
        item.setStatus(INPROGRESS);
//        itemsList.sort(compartor);
    }


    @Override
    public void deleteItem(TodoItem item) {
        itemsList.remove(item);
//        itemsList.sort(compartor);

    }

    @Override
    public void clear() { itemsList.clear(); }


    @Override
    public void addAll(List<TodoItem> items){
        itemsList.addAll(items);
//        itemsList.sort(compartor);
    }
    @Override
    public int size(){ return itemsList.size();}
}
