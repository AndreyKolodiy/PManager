package com.example.pmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.pmanager.objects.Document;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static String DOCUMENT = "com.example.pmanager.objects.Document";
    public static int DETAILS_REQUEST = 1;
    private ListView listTasks;
    private ArrayAdapter<Document> arrayAdapter;
    private static List<Document> listDocument = new ArrayList<Document>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        listTasks = (ListView) findViewById(R.id.listPassword);
        listTasks.setOnItemClickListener((AdapterView.OnItemClickListener) new ListViewClickListener());
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        fillList();
    }

    private void fillList() {
        arrayAdapter = new ArrayAdapter<Document>(getApplicationContext(),
                R.layout.listview_row, listDocument);
        listTasks.setAdapter(arrayAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_password: {
                Document document = new Document();
                document.setName(getResources()
                        .getString(R.string.new_login));
                showDocument(document);
                return true;
            }
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDocument(Document todoDocument) {
        Intent intentTodoDetails = new Intent(this, PasswordDetails.class);
        intentTodoDetails.putExtra(DOCUMENT, todoDocument);
        startActivityForResult(intentTodoDetails, DETAILS_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DETAILS_REQUEST) {
            Document document = null;
            switch (resultCode) {
                case RESULT_CANCELED:
                    break;
                case PasswordDetails.RESULT_SAVE:
                    document = (Document) data
                            .getSerializableExtra(DOCUMENT);
                    addDocument(document);
                    break;
                case PasswordDetails.RESULT_DELETE:
                    document = (Document) data
                            .getSerializableExtra(DOCUMENT);
                    deleteDocument((Document) data
                            .getSerializableExtra(DOCUMENT));
                    break;
                default:
                    break;
            }
        }
    }

    private void addDocument(Document document) {
        if (document.getNumber() == null) {
            listDocument.add(document);
        } else {
            listDocument.set(document.getNumber(), document);
        }
        arrayAdapter.notifyDataSetChanged();
    }

    private void deleteDocument(Document document) {

        listDocument.remove(document.getNumber().intValue());
        arrayAdapter.notifyDataSetChanged();
    }

    class ListViewClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            Document document = (Document) parent.getAdapter().getItem(position);
            document.setNumber(position);
            showDocument(document);
        }
    }
}

