package demo.com.givemenotes.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import demo.com.givemenotes.R;
import demo.com.givemenotes.activity.ContainerNotebookActivity;
import demo.com.givemenotes.task.CreateNewNotebookTask;

/**
 * Created by ramuñoz on 17/01/2016.
 */
public class CreateNotebookDialogFragment extends DialogFragment {

    public static final String TAG = "CreateNotebookDialogFragment";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_base, null);
        final TextInputLayout titleView = (TextInputLayout) view.findViewById(R.id.textInputLayout_title);
        view.findViewById(R.id.textInputLayout_content).setVisibility(View.GONE);

        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        new CreateNewNotebookTask(titleView.getEditText().getText().toString())
                                .start(new ContainerNotebookActivity());
                        break;
                }
            }
        };
        builder.setTitle(R.string.create_new_notebook)
                .setView(view)
                .setPositiveButton(R.string.create, onClickListener)
                .setNegativeButton(android.R.string.cancel, onClickListener);
        return builder.create();
    }
}
