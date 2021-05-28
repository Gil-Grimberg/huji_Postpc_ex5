package exercise.android.reemh.todo_items;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final int DONE = 1;
    final int INPROGRESS = 2;
    public TodoItemsHolderImpl holder = null;
    ToDoItemAdapter adapter;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (holder == null) {
//            holder = new TodoItemsHolderImpl();
            holder = ToDoItemsApp.getInstance().getDataBase(); // replace the last row with this??
        }

        adapter = new ToDoItemAdapter((TodoItemsHolderImpl)holder);
        adapter.setToDoItems(holder.getCurrentItems());
        RecyclerView recyclerTodoItemsList = findViewById(R.id.recyclerTodoItemsList);
        recyclerTodoItemsList.setAdapter(adapter);
        recyclerTodoItemsList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        EditText taskText = findViewById(R.id.editTextInsertTask);
        FloatingActionButton addButton = findViewById(R.id.buttonCreateTodoItem);
        EditText finalTaskText = taskText;
        addButton.setOnClickListener(v -> {
            if (!finalTaskText.getText().toString().equals("")) {
                // add a new task
                holder.addNewInProgressItem(finalTaskText.getText().toString());
                adapter.setToDoItems(holder.getCurrentItems());
                finalTaskText.setText("");
            }
        });
        finalTaskText.setOnClickListener(v-> {
            Intent resultsIntent = new Intent(MainActivity.this, ResultsActivity.class);
            resultsIntent.putExtra("original_number", original_number);
            resultsIntent.putExtra("root1", root1);
            resultsIntent.putExtra("root2", root2);
            startActivity(resultsIntent);
        });


        holder.toDoItemsLiveDataPublic.observe(this, new Observer<ArrayList<TodoItem>>() {
            @Override
            public void onChanged(ArrayList<TodoItem> todoItemsList) {
                // todo: do all changes necessary to activity
                int i = 1;
//                adapter.setToDoItems(todoItemsList);
//                holder.clear();
//                holder.addAll(todoItemsList);
            }
        });
        if (savedInstanceState != null) {
            taskText = findViewById(R.id.editTextInsertTask);
            taskText.setText(savedInstanceState.getString("taskText"));
        }
//        if (savedInstanceState != null) {
//            EditText taskText = findViewById(R.id.editTextInsertTask);
//            taskText.setText(savedInstanceState.getString("taskText"));
//            holder.clear();
//            List<TodoItem> savedItemsList = holder.getCurrentItems();
//            int size = savedInstanceState.getInt("size");
//            for (int i = 0; i < size; i++) {
//                savedItemsList.add((TodoItem) savedInstanceState.getSerializable("task_" + String.valueOf(i)));
//            }
//            holder.addAll(savedItemsList);
//        }



    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        EditText taskText = findViewById(R.id.editTextInsertTask);
        outState.putString("taskText", taskText.getText().toString());
        List<TodoItem> savedItemsList;
        savedItemsList = holder.getCurrentItems();
        int size = holder.size();
        outState.putInt("size", size);
        for (int i = 0; i < size; i++) {
            outState.putSerializable("task_" + String.valueOf(i), savedItemsList.get(i));
        }

    }

}

/*

SPECS:

- the screen starts out empty (no items shown, edit-text input should be empty)
- every time the user taps the "add TODO item" button:
    * if the edit-text is empty (no input), nothing happens
    * if there is input:
        - a new TodoItem (checkbox not checked) will be created and added to the items list
        - the new TodoItem will be shown as the first item in the Recycler view
        - the edit-text input will be erased
- the "TodoItems" list is shown in the screen
  * every operation that creates/edits/deletes a TodoItem should immediately be shown in the UI
  * the order of the TodoItems in the UI is:
    - all IN-PROGRESS items are shown first. items are sorted by creation time,
      where the last-created item is the first item in the list
    - all DONE items are shown afterwards, no particular sort is needed (but try to think about what's the best UX for the user)
  * every item shows a checkbox and a description. you can decide to show other data as well (creation time, etc)
  * DONE items should show the checkbox as checked, and the description with a strike-through text
  * IN-PROGRESS items should show the checkbox as not checked, and the description text normal
  * upon click on the checkbox, flip the TodoItem's state (if was DONE will be IN-PROGRESS, and vice versa)
  * add a functionality to remove a TodoItem. either by a button, long-click or any other UX as you want
- when a screen rotation happens (user flips the screen):
  * the UI should still show the same list of TodoItems
  * the edit-text should store the same user-input (don't erase input upon screen change)

Remarks:
- you should use the `holder` field of the activity
- you will need to create a class extending from RecyclerView.Adapter and use it in this activity
- notice that you have the "row_todo_item.xml" file and you can use it in the adapter
- you should add tests to make sure your activity works as expected. take a look at file `MainActivityTest.java`



(optional, for advanced students:
- save the TodoItems list to file, so the list will still be in the same state even when app is killed and re-launched
)

*/
