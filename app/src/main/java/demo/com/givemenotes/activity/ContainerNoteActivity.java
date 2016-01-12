package demo.com.givemenotes.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.evernote.client.android.EvernoteSession;
import com.evernote.client.android.type.NoteRef;
import com.evernote.edam.type.Notebook;

import net.vrallev.android.task.TaskResult;

import java.util.List;

import demo.com.givemenotes.R;
import demo.com.givemenotes.fragment.EmptyFragment;
import demo.com.givemenotes.fragment.NoteListFragment;
import demo.com.givemenotes.task.FindNotesTask;

/**
 * Created by ramuñoz on 10/01/2016.
 */
public class ContainerNoteActivity  extends AppCompatActivity implements NoteListFragment.OnListNotesFragmentListener {

    private static String KEY_NOTEBOOK ="KEY_NOTEBOOK";
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
        getSupportActionBar().setTitle(mNotebook.getName());
        if (!isTaskRoot()) {
            //noinspection ConstantConditions
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        new FindNotesTask(MIN_NOTES, MAX_NOTES, mNotebook).start(this);
    }

    @Override
    public void onListFragmentInteraction(NoteRef item) {

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
                    .replace(R.id.fragment_container, NoteListFragment.create(noteRefList))
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        }
    }
}
