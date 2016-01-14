package demo.com.givemenotes.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.evernote.edam.type.Note;

import demo.com.givemenotes.R;

/**
 * Created by ramuñoz on 13/01/2016.
 */
public class ShowNoteDialogFragment extends DialogFragment {

    public static final String TAG = "ShowNoteDialogFragment";
    private static final String KEY_NOTE = "KEY_NOTE";
    private Note mNote;

    public static ShowNoteDialogFragment create(Note note) {
        ShowNoteDialogFragment fragment = new ShowNoteDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_NOTE, note);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNote = (Note) getArguments().getSerializable(KEY_NOTE);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //LayoutInflater inflater = getActivity().getLayoutInflater();
        //View view = inflater.inflate(R.layout.dialog_base, null);
        //LinearLayout content = (LinearLayout) view.findViewById(R.id.content_node);
        //EditText title = (EditText) view.findViewById(R.id.title_txt);
        //EditText content = (EditText) view.findViewById(R.id.content_txt);
        if (mNote != null) {
            builder.setTitle(mNote.getTitle());
            builder.setMessage(mNote.getContent());
            //title.setText(mNote.getTitle());
            //content.setText(mNote.getContent());
        }
        //builder.setView(view);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        return builder.create();
    }
}
