package demo.com.givemenotes.fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import com.evernote.edam.type.Notebook;

import demo.com.givemenotes.R;
import demo.com.givemenotes.task.CreateNewNoteTask;

/**
 * Created by ramuñoz on 14/01/2016.
 */
public class CreateNoteDialogFragment extends DialogFragment {

    public static final String TAG = "CreateNoteDialogFragment";
    private Notebook mNotebook;
    private static final String KEY_NOTEBOOK = "KEY_NOTEBOOK";

    public static CreateNoteDialogFragment create(Notebook notebook) {
        Bundle args = new Bundle();
        args.putSerializable(KEY_NOTEBOOK, notebook);
        CreateNoteDialogFragment fragment = new CreateNoteDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNotebook = (Notebook) getArguments().getSerializable(KEY_NOTEBOOK);
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
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        new CreateNewNoteTask(titleView.getEditText().getText().toString(),
                                contentView.getEditText().getText().toString(), mNotebook)
                                .start(getChildFragmentManager().findFragmentByTag(NoteListFragment.TAG));
                        break;
                }
            }
        };
        builder.setTitle(R.string.create_new_note)
                .setView(view)
                .setPositiveButton(R.string.create, onClickListener)
                .setNegativeButton(android.R.string.cancel, onClickListener);
        return builder.create();
    }
}
