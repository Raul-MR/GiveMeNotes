package demo.com.givemenotes.task;

import com.evernote.client.android.EvernoteSession;
import com.evernote.client.android.EvernoteUtil;
import com.evernote.client.android.asyncclient.EvernoteNoteStoreClient;
import com.evernote.edam.type.Note;
import com.evernote.edam.type.Notebook;

/**
 * Created by ramuñoz on 14/01/2016.
 */
public class CreateNewNoteTask extends BaseTask<Note> {

    private final String mTitle;
    private final String mContent;
    private final Notebook mNotebook;

    public CreateNewNoteTask(String title, String content, Notebook notebook) {
        super(Note.class);
        mTitle = title;
        mContent = content;
        mNotebook = notebook;
    }

    @Override
    protected Note checkedExecute() throws Exception {
        Note note = new Note();
        note.setTitle(mTitle);
        if (mNotebook != null) {
            note.setNotebookGuid(mNotebook.getGuid());
        }
        String content = EvernoteUtil.NOTE_PREFIX + mContent + EvernoteUtil.NOTE_SUFFIX;
        note.setContent(content);
        EvernoteNoteStoreClient noteStoreClient = EvernoteSession.getInstance().getEvernoteClientFactory().getNoteStoreClient();
        return noteStoreClient.createNote(note);
    }
}
