package demo.com.givemenotes.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;

import com.evernote.edam.type.Note;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

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
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_base, null);
        TextInputLayout title = (TextInputLayout) view.findViewById(R.id.textInputLayout_title);
        TextInputLayout content = (TextInputLayout) view.findViewById(R.id.textInputLayout_content);
        if (mNote != null) {
            builder.setTitle(mNote.getTitle());
            title.getEditText().setText(mNote.getTitle());
            title.getEditText().setEnabled(false);
            title.getEditText().setKeyListener(null);
            title.setClickable(false);
            title.setFocusable(false);
            content.getEditText().setText(getContent(mNote.getContent()));
            content.getEditText().setEnabled(false);
            content.getEditText().setKeyListener(null);
            content.setClickable(false);
            content.setFocusable(false);
        }
        builder.setView(view);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        return builder.create();
    }

    private String getContent(String contentXml) {
        //contentXml = contentXml.replaceAll("\"", "");
        String content = "";

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(contentXml));
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if(eventType == XmlPullParser.TEXT) {
                    content = xpp.getText();
                }
                eventType = xpp.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}
