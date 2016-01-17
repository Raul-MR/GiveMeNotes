package demo.com.givemenotes.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.evernote.edam.type.Notebook;

import java.util.List;

import demo.com.givemenotes.R;
import demo.com.givemenotes.fragment.NotebookListFragment.OnNotebookListFragmentListener;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Notebook} and makes a call to the
 * specified {@link OnNotebookListFragmentListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class NotebooksRecyclerViewAdapter extends RecyclerView.Adapter<NotebooksRecyclerViewAdapter.NotebookHolder> {

    private List<Notebook> notebookList;
    private final OnNotebookListFragmentListener mListener;

    public NotebooksRecyclerViewAdapter(List<Notebook> items, OnNotebookListFragmentListener listener) {
        notebookList = items;
        mListener = listener;
    }

    @Override
    public NotebookHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_notebook, parent, false);
        return new NotebookHolder(view);
    }

    @Override
    public void onBindViewHolder(final NotebookHolder holder, int position) {
        holder.mItem = notebookList.get(position);
        holder.mContentView.setText(notebookList.get(position).getName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return notebookList.size();
    }

    public Notebook getItem(int position) {
        return notebookList.get(position);
    }

    public class NotebookHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContentView;
        public Notebook mItem;

        public NotebookHolder(View view) {
            super(view);
            mView = view;
            mContentView = (TextView) view.findViewById(R.id.notebook_title);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
