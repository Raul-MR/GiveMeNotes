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

import com.evernote.edam.type.Notebook;
import demo.com.givemenotes.R;
import demo.com.givemenotes.util.ParcelableUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NotebookListFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String KEY_NOTEBOOK_LIST = "KEY_NOTEBOOK_LIST";
    private int mColumnCount = 1;
    private List<Notebook> notebookList;
    private OnNotebookListFragmentListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public NotebookListFragment() {
    }

    public static NotebookListFragment create(List<Notebook> notebooks) {
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, notebooks.size());
        ParcelableUtil.putSerializableList(args, new ArrayList<Serializable>(notebooks), KEY_NOTEBOOK_LIST);
        NotebookListFragment fragment = new NotebookListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            notebookList = ParcelableUtil.getSerializableArrayList(getArguments(), KEY_NOTEBOOK_LIST);
        } else {
            mColumnCount = 1;
            notebookList = new ArrayList<Notebook>();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notebooks_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 10) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new NotebooksRecyclerViewAdapter(notebookList, mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnNotebookListFragmentListener) {
            mListener = (OnNotebookListFragmentListener) activity;
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
    public interface OnNotebookListFragmentListener {
        void onListFragmentInteraction(Notebook item);
    }
}
