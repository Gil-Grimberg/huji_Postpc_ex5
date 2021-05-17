package exercise.android.reemh.todo_items;

import android.content.Context;
import android.graphics.Paint;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

class ToDoViewHolder extends RecyclerView.ViewHolder {
    EditText textView;
    CheckBox checkBox;
    FloatingActionButton deleteButton;

    public ToDoViewHolder(View view) {
        super(view);
        textView = view.findViewById(R.id.textOfTask);
        checkBox = view.findViewById(R.id.checkbox);
        deleteButton = view.findViewById(R.id.deleteButton);

    }
}

class ToDoItemAdapter extends RecyclerView.Adapter<ToDoViewHolder> {


    private TodoItemsHolderImpl _todoItemArrayList;
    public ToDoItemAdapter(TodoItemsHolderImpl holder)
    {
        _todoItemArrayList = holder;
    }

    public void setToDoItems(List<TodoItem> items) {
        if (!_todoItemArrayList.getCurrentItems().isEmpty())
            _todoItemArrayList.clear();
        _todoItemArrayList.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return _todoItemArrayList.size();
    }

    public @NotNull ToDoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.row_todo_item, parent, false);
        return new ToDoViewHolder(view);
    }


    public void onBindViewHolder(ToDoViewHolder holder, int position) {

        TodoItem item = _todoItemArrayList.getCurrentItems().get(position);
        holder.textView.setText(item.description);
        holder.checkBox.setChecked(item.isDone());  // mark task as checked if the status of the item is DONE
        notifyDataSetChanged();

        holder.checkBox.setOnClickListener(v->{
            if (holder.checkBox.isChecked()) {
                holder.textView.setPaintFlags(holder.textView.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
                _todoItemArrayList.markItemDone(item); // make sure the real list is updated!

            }
            else {
                holder.textView.setPaintFlags(holder.textView.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                _todoItemArrayList.markItemInProgress(item);

            }
            notifyDataSetChanged();
        });
        holder.deleteButton.setOnClickListener(v->{
            _todoItemArrayList.deleteItem(item);
            notifyDataSetChanged();
        });
    }
}
