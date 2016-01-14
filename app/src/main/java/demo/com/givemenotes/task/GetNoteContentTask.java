package demo.com.givemenotes.task;

import com.evernote.client.android.type.NoteRef;
import com.evernote.edam.type.Note;

/**
 * Created by ramuñoz on 13/01/2016.
 */
public class GetNoteContentTask extends BaseTask<Note> {

    private final NoteRef mNoteRef;

    public GetNoteContentTask(NoteRef noteRef) {
        super(Note.class);
        mNoteRef = noteRef;
    }

    @Override
    protected Note checkedExecute() throws Exception {
        return mNoteRef.loadNote(true, false, false, false);
    }
}
