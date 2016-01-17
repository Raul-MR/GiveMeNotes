package demo.com.givemenotes.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.evernote.client.android.type.NoteRef;

import java.util.List;

import demo.com.givemenotes.R;
import demo.com.givemenotes.fragment.NoteListFragment.OnListNotesFragmentListener;

/**
 * {@link RecyclerView.Adapter} that can display a {@link NoteRef} and makes a call to the
 * specified {@link OnListNotesFragmentListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class NoteRecyclerViewAdapter extends RecyclerView.Adapter<NoteRecyclerViewAdapter.NoteRefHolder> {


    private List<NoteRef> mNoteList;
    private final NoteListFragment.OnListNotesFragmentListener mListener;

    public NoteRecyclerViewAdapter(List<NoteRef> items, OnListNotesFragmentListener listener) {
        mNoteList = items;
        mListener = listener;
    }

    @Override
    public NoteRefHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_note, parent, false);
        return new NoteRefHolder(view);
    }

    @Override
    public void onBindViewHolder(final NoteRefHolder holder, int position) {
        holder.mItem = mNoteList.get(position);
        holder.mContentView.setText(mNoteList.get(position).getTitle());

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
        return mNoteList.size();
    }

    public NoteRef getItem(int position) {
        return mNoteList.get(position);
    }

    public class NoteRefHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContentView;
        public NoteRef mItem;

        public NoteRefHolder(View view) {
            super(view);
            mView = view;
            mContentView = (TextView) view.findViewById(R.id.note_title);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
