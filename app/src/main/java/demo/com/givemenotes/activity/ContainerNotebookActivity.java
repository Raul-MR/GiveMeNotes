package demo.com.givemenotes.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.evernote.client.android.EvernoteSession;
import com.evernote.edam.type.Notebook;

import net.vrallev.android.task.TaskResult;

import java.util.List;

import demo.com.givemenotes.R;
import demo.com.givemenotes.fragment.EmptyFragment;
import demo.com.givemenotes.fragment.NotebookListFragment;
import demo.com.givemenotes.task.FindNotebooksTask;

/**
 * Created by ramuñoz on 07/01/2016.
 */
public class ContainerNotebookActivity extends AppCompatActivity implements NotebookListFragment.OnNotebookListFragmentListener {

    /**
     * @param context
     * @return
     */
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
        getSupportActionBar().setTitle(R.string.title_notebook);
        if (!isTaskRoot()) {
            //noinspection ConstantConditions
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        new FindNotebooksTask().start(this);
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
}
