package demo.com.givemenotes.fragment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;

import demo.com.givemenotes.R;
import demo.com.givemenotes.activity.ContainerNoteActivity;

/**
 * Created by ramuñoz on 14/01/2016.
 */
public class CreateNoteDialogFragment extends DialogFragment {

    public static final String TAG = "CreateNoteDialogFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_base, null);
        final TextInputLayout titleView = (TextInputLayout) view.findViewById(R.id.textInputLayout_title);
        final TextInputLayout contentView = (TextInputLayout) view.findViewById(R.id.textInputLayout_content);

        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((NoteListFragment) getParentFragment()).createNewNote(titleView.getEditText().getText().toString(),
                        contentView.getEditText().getText().toString());
            }
        };
        builder.setTitle(R.string.create_new_note)
                .setPositiveButton(R.string.create, onClickListener)
                .setNegativeButton(android.R.string.cancel, onClickListener);
        return builder.create();
    }
}
