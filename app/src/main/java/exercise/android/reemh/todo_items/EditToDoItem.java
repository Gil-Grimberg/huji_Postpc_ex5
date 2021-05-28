package exercise.android.reemh.todo_items;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompatSideChannelService;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class EditToDoItem extends AppCompatActivity {

    public TodoItemsHolderImpl database = null;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_todo_item);
        if (database == null) {
            database = ToDoItemsApp.getInstance().getDataBase();
        }

        TextView createdTime = findViewById(R.id.creatTime);
        TextView lastModified = findViewById(R.id.LastModified);
        EditText editTask = findViewById(R.id.edit_task);
        CheckBox checkBox = findViewById(R.id.checkBoxEditor);
        FloatingActionButton okButton = findViewById(R.id.doneButton);

        Intent intentOpenedMe = getIntent();
        String itemAsString = intentOpenedMe.getStringExtra("toDoItem");
        TodoItem item = TodoItem.string_to_Item(itemAsString);
        database.addItem(item);
        createdTime.setText("Time Of Creation: " + item.getCreatedTime().toString());
        callLastModified(item,lastModified);
        editTask.setText(item.getDescription());
        checkBox.setChecked(item.isDone());

        if (item.isDone()) {
            checkBox.setText("Done");
        } else {
            checkBox.setText("In Progress");
        }
        // todo:
        // 2. render the view
        // 3. imidiately update view

        editTask.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                // text did change
                database.deleteItem(item);
                String newText = String.valueOf(editTask.getText());
                item.setDescription(newText);
                item.setLastModified(LocalDateTime.now());
                database.addItem(item);
                callLastModified(item,lastModified);

            }
        });

        checkBox.setOnClickListener(v -> {
            if (checkBox.isChecked()) {
                database.deleteItem(item);
                checkBox.setText("Done");
                item.setStatus(item.DONE);
                item.setLastModified(LocalDateTime.now());
                database.addItem(item);
                callLastModified(item,lastModified);


            } else {
                database.deleteItem(item);
                checkBox.setText("In Progress");
                item.setStatus(item.INPROGRESS);
                item.setLastModified(LocalDateTime.now());
                database.addItem(item);
                callLastModified(item,lastModified);

            }

        });


        okButton.setOnClickListener(v -> {
            Intent resultsIntent = new Intent(v.getContext(), MainActivity.class);
            v.getContext().startActivity(resultsIntent);

//            finish();

        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void callLastModified(TodoItem myItem, TextView lastModified) {
        long minutes = ChronoUnit.MINUTES.between(myItem.getlastModifiedTime(), LocalDateTime.now());
        long days = ChronoUnit.DAYS.between(myItem.getlastModifiedTime(), LocalDateTime.now());
        if (minutes < 60) {
            lastModified.setText("Last Modified: " + String.valueOf(minutes) + " Minutes ago");

        } else if (days == 0) {
            lastModified.setText("Last Modified: Today at " + String.valueOf(myItem.getlastModifiedTime().getHour()));

        } else {
            lastModified.setText("Last Modified: " + String.valueOf(myItem.getlastModifiedTime().toLocalDate()) + " at " + String.valueOf(myItem.getlastModifiedTime().getHour()));

        }
    }

}
