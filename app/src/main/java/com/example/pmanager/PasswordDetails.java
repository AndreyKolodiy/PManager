package com.example.pmanager;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.os.Bundle;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.pmanager.objects.Document;

public class PasswordDetails extends AppCompatActivity {

    public static final int RESULT_SAVE = 100;
    public static final int RESULT_DELETE = 101;
    private static final int NAME_LENGTH = 20;
    private EditText txtName;
    private EditText txtLogin;
    private EditText txtPassword;
    private ImageButton btnCopyLogin;
    private ImageButton btnCopyPassword;
    private Document document;
    ClipboardManager clipboardManager;
    ClipData clipData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        txtName = (EditText) findViewById(R.id.txtName);
        txtLogin = (EditText) findViewById(R.id.txtlogin);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        document = (Document) getIntent().getSerializableExtra(MainActivity.DOCUMENT);
        txtName.setText(document.getName());
        txtLogin.setText(document.getLogin());
        txtPassword.setText(document.getPassword());
        btnCopyLogin = (ImageButton) findViewById(R.id.copyLogin);
        btnCopyPassword = (ImageButton) findViewById(R.id.copyPassword);
        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        btnCopyLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = txtLogin.getText().toString();
                clipData = ClipData.newPlainText("text", text);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(getApplicationContext(), "Text Copied ", Toast.LENGTH_SHORT).show();
            }
        });
        btnCopyPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = txtPassword.getText().toString();
                clipData = ClipData.newPlainText("text", text);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(getApplicationContext(), "Text Copied ", Toast.LENGTH_SHORT).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detalis, menu);
        return true;
    }

    private void saveDocument() {
        StringBuilder sb = new StringBuilder(txtName.getText());
        document.setName(sb.toString());
        if (sb.length() > NAME_LENGTH) {
            sb.delete(NAME_LENGTH, sb.length()).append("...");
        }
        String tmpName = sb.toString().trim().split("\n")[0];
        String name = (tmpName.length() > 0) ? tmpName : document
                .getName();
        document.setName(name);
        document.setPassword(txtPassword.getText().toString());
        document.setLogin(txtLogin.getText().toString());
        setResult(RESULT_SAVE, getIntent());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                if ((txtName.getText().toString().trim().length() == 0)) {
                    setResult(RESULT_CANCELED);
                } else {
                    saveDocument();
                }
                finish();
                return true;
            }
            case R.id.save: {
                saveDocument();
                finish();
                return true;
            }
            case R.id.delete: {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.confirm_delete);
                builder.setPositiveButton(R.string.delete,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                setResult(RESULT_DELETE, getIntent());
                                finish();
                            }
                        });
                builder.setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            }
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}





