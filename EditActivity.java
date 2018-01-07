package com.example.rbk.notatnik.git;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rbk.notatnik.R;

public class EditActivity extends AppCompatActivity {

    static List_entry entry;
    static EditText editText;
    static ImageButton bt_back;
    static ImageButton bt_delete;
    static ImageButton bt_save;
    static TextView title_caption;
    static int position;
    static Context context;
    static Toast toast = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        context = this;
        final Intent intent = getIntent();
        final String date = intent.getExtras().get("date").toString();
        final String note = intent.getExtras().get("note").toString();
        position = Integer.parseInt(intent.getExtras().get("position").toString());
        entry = new List_entry(date,note);

        editText = (EditText)findViewById(R.id.editText);
        editText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(256)});
        bt_back = (ImageButton)findViewById(R.id.bt_edit_back);
        bt_delete = (ImageButton)findViewById(R.id.bt_edit_delete);
        bt_save = (ImageButton)findViewById(R.id.bt_edit_save);
        title_caption = (TextView)findViewById(R.id.EditionTitle);

        title_caption.setText(title_caption.getText()+" "+MainActivity.getDisplayDate(MainActivity.getDate_pattern(),MainActivity.getCalendarFromFile(entry.getDate())));
        editText.setText(entry.getNote());


        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent result = new Intent();
                setResult(RESULT_CANCELED,result);
                finish();
            }
        });

        bt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editText.setText("");
            }
        });

        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent result = new Intent();
                result.putExtra("position",position);
                result.putExtra("date",date);
                result.putExtra("note",editText.getText());
                setResult(RESULT_OK,result);
                finish();
            }
        });

        setEditTextFieldProperties();
    }

    private void setEditTextFieldProperties()
    {
        editText.addTextChangedListener(new TextWatcher() {
            // shows EditText window text lenght limit toast
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

                if(editText.getText().length()>255)
                {
                    if(toast!=null) {
                        if (toast.getView().isShown()) {
                            toast.cancel();
                        }
                    }

                    toast = Toast.makeText(context,"Text lenght limit is 256",Toast.LENGTH_SHORT);
                    toast.show();

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editText.setCustomSelectionActionModeCallback(new ActionMode.Callback()
        // prevent edit window to copy/paste (text limit)
        {

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }
            @Override
            public void onDestroyActionMode(ActionMode mode) {
            }
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }
            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }
        });

    }
}
