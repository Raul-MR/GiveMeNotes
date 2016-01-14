package demo.com.givemenotes.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.evernote.client.android.type.NoteRef;
import com.evernote.edam.type.Note;
import com.evernote.edam.type.Notebook;

import net.vrallev.android.task.TaskResult;

import java.util.ArrayList;
import java.util.List;

import demo.com.givemenotes.R;
import demo.com.givemenotes.task.CreateNewNoteTask;
import demo.com.givemenotes.task.FindNotesTask;
import demo.com.givemenotes.util.ParcelableUtil;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListNotesFragmentListener}
 * interface.
 */
public class NoteListFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String KEY_NOTE_LIST = "KEY_NOTE_LIST";
    private static final String KEY_NOTEBOOK = "KEY_NOTEBOOK";
    private static int MIN_NOTES = 0;
    private static int MAX_NOTES = 20;
    private int mColumnCount = 1;
    private List<NoteRef> mNoteList;
    private Notebook mNotebook;
    private OnListNotesFragmentListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public NoteListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static NoteListFragment create(Notebook notebook) {
        NoteListFragment fragment = new NoteListFragment();
        Bundle args = new Bundle();
        //ParcelableUtil.putParcelableList(args, notes, KEY_NOTE_LIST);
        //args.putInt(ARG_COLUMN_COUNT, notes.size());
        args.putSerializable(KEY_NOTEBOOK, notebook);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new FindNotesTask(MIN_NOTES, MAX_NOTES, mNotebook).start(this);
        if (getArguments() != null) {
            mNotebook = (Notebook) getArguments().getSerializable(KEY_NOTEBOOK);
        } else {
            mNotebook = new Notebook();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FloatingActionButton fab = (FloatingActionButton) container.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        View view = inflater.inflate(R.layout.fragment_note_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 10) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new NoteRecyclerViewAdapter(mNoteList, mListener));
        }
        return view;
    }

    public void createNewNote(String title, String content) {
        new CreateNewNoteTask(title, content, mNotebook);
    }

    @TaskResult
    public void onFindNotes(List<NoteRef> noteRefList) {
        mNoteList = noteRefList;
        mColumnCount = noteRefList.size();
    }

    @TaskResult
    public void onCreateNewNote(Note note) {
        if (note != null) {
            new FindNotesTask(MIN_NOTES, MAX_NOTES, mNotebook).start(this);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnListNotesFragmentListener) {
            mListener = (OnListNotesFragmentListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListNotesFragmentListener {
        void onListFragmentInteraction(NoteRef item);
    }
}
