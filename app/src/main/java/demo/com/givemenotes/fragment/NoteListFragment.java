package demo.com.givemenotes.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.evernote.client.android.type.NoteRef;

import java.util.ArrayList;
import java.util.List;

import demo.com.givemenotes.R;
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
    private int mColumnCount = 1;
    private List<NoteRef> mNoteList;
    private OnListNotesFragmentListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public NoteListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static NoteListFragment create(List<NoteRef> notes) {
        NoteListFragment fragment = new NoteListFragment();
        Bundle args = new Bundle();
        ParcelableUtil.putParcelableList(args, notes, KEY_NOTE_LIST);
        args.putInt(ARG_COLUMN_COUNT, notes.size());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            mNoteList = getArguments().getParcelableArrayList(KEY_NOTE_LIST);
        } else {
            mColumnCount = 1;
            mNoteList = new ArrayList<NoteRef>();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
