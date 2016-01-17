package demo.com.givemenotes.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.evernote.client.android.EvernoteSession;
import com.evernote.edam.type.Notebook;

import net.vrallev.android.task.TaskResult;

import java.util.List;

import demo.com.givemenotes.R;
import demo.com.givemenotes.fragment.CreateNotebookDialogFragment;
import demo.com.givemenotes.fragment.EmptyFragment;
import demo.com.givemenotes.fragment.NotebookListFragment;
import demo.com.givemenotes.task.FindNotebooksTask;

/**
 * Created by ramuñoz on 07/01/2016.
 */
public class ContainerNotebookActivity extends AppCompatActivity implements NotebookListFragment.OnNotebookListFragmentListener {


    public static String TAG = "ContainerNotebookActivity";

    public static Intent createIntent(Context context) {
        return new Intent(context, ContainerNotebookActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!EvernoteSession.getInstance().isLoggedIn()) {
            return;
        }
        if (getSupportActionBar() == null) {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
        }
        getSupportActionBar().setTitle(R.string.title_notebook);
        if (!isTaskRoot()) {
            //noinspection ConstantConditions
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        new FindNotebooksTask().start(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CreateNotebookDialogFragment().show(getSupportFragmentManager(),
                        CreateNotebookDialogFragment.TAG);
            }
        });
    }

    @Override
    public void onListFragmentInteraction(Notebook item) {
        startActivity(ContainerNoteActivity.createIntent(this, item));
    }

    @TaskResult
    public void onFindNotebooks(List<Notebook> notebooks) {
        if (notebooks == null || notebooks.isEmpty()) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, EmptyFragment.create("notebooks"))
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, NotebookListFragment.create(notebooks))
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        }
    }

    @TaskResult
    public void onCreateNewNotebook(Notebook notebook) {
        new FindNotebooksTask().start(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_logout) {
            EvernoteSession.getInstance().logOut();
            finish();
            return true;
        } else if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
