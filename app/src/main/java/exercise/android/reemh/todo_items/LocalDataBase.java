package exercise.android.reemh.todo_items;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LocalDataBase {

    private final TodoItemsHolderImpl holder = new TodoItemsHolderImpl();
    private final Context context;
    private final SharedPreferences sp;

    public LocalDataBase(Context context) {
        this.context = context;
        this.sp = context.getSharedPreferences("local_db", Context.MODE_PRIVATE);
    }

    public List<TodoItem> getCurrentItems() {

        return holder.getCurrentItems();

    }

    public void addNewInProgressItem(String description) {
        holder.addNewInProgressItem(description);
        SharedPreferences.Editor editor = this.sp.edit();

    }

    public void markItemDone(TodoItem item) {
        holder.markItemDone(item);
    }

    public void markItemInProgress(TodoItem item) {
        holder.markItemInProgress(item);
    }

    public void deleteItem(TodoItem item) {
        holder.deleteItem(item);
    }

    public void clear() {
        holder.clear();
    }

    public void addAll(List<TodoItem> items) {
        holder.addAll(items);
    }

    public int size() {
        return holder.size();
    }

}
