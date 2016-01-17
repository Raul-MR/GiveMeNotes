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
import com.evernote.client.android.type.NoteRef;
import com.evernote.edam.type.Note;
import com.evernote.edam.type.Notebook;

import net.vrallev.android.task.TaskResult;

import java.util.List;

import demo.com.givemenotes.R;
import demo.com.givemenotes.fragment.CreateNoteDialogFragment;
import demo.com.givemenotes.fragment.EmptyFragment;
import demo.com.givemenotes.fragment.NoteListFragment;
import demo.com.givemenotes.fragment.ShowNoteDialogFragment;
import demo.com.givemenotes.task.FindNotesTask;
import demo.com.givemenotes.task.GetNoteContentTask;

/**
 * Created by ramuñoz on 10/01/2016.
 */
public class ContainerNoteActivity  extends AppCompatActivity implements NoteListFragment.OnListNotesFragmentListener {

    private static final String KEY_NOTEBOOK = "KEY_NOTEBOOK";
    private static final String TAG = "ContainerNoteActivity";
    private static int MIN_NOTES = 0;
    private static int MAX_NOTES = 20;
    private Notebook mNotebook;

    public static Intent createIntent(Context context, Notebook notebook) {
        Intent activity = new Intent(context, ContainerNoteActivity.class);
        activity.putExtra(KEY_NOTEBOOK, notebook);
        return activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!EvernoteSession.getInstance().isLoggedIn()) {
            return;
        }
        mNotebook = (Notebook) getIntent().getSerializableExtra(KEY_NOTEBOOK);
        if (getSupportActionBar() == null) {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
        }
        getSupportActionBar().setTitle(mNotebook.getName());
        if (!isTaskRoot()) {
            //noinspection ConstantConditions
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateNoteDialogFragment.create(mNotebook)
                        .show(getSupportFragmentManager(), CreateNoteDialogFragment.TAG);
            }
        });
        findNoteList();
    }

    @Override
    public void onListFragmentInteraction(NoteRef item) {
        new GetNoteContentTask(item).start(this, "content");
    }

    public void findNoteList() {
        new FindNotesTask(MIN_NOTES, MAX_NOTES, mNotebook).start(this);
    }

    @TaskResult
    public void onFindNotes(List<NoteRef> noteRefList) {
        if (noteRefList == null || noteRefList.isEmpty()) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, EmptyFragment.create("Notes"))
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, NoteListFragment.create(noteRefList, mNotebook))
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        }
    }

    @TaskResult(id = "content")
    public void onGetNoteContent(Note note) {
        ShowNoteDialogFragment dialog = ShowNoteDialogFragment.create(note);
        dialog.show(getFragmentManager(), ShowNoteDialogFragment.TAG);
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
