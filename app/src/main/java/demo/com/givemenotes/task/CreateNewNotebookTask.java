package demo.com.givemenotes.task;

import com.evernote.client.android.EvernoteSession;
import com.evernote.client.android.asyncclient.EvernoteNoteStoreClient;
import com.evernote.edam.type.Notebook;

/**
 * Created by ramuñoz on 17/01/2016.
 */
public class CreateNewNotebookTask extends BaseTask<Notebook> {

    private final String mName;

    public CreateNewNotebookTask(String name) {
        super(Notebook.class);
        mName = name;
    }

    @Override
    protected Notebook checkedExecute() throws Exception {
        EvernoteNoteStoreClient noteStoreClient = EvernoteSession.getInstance().getEvernoteClientFactory().getNoteStoreClient();

        Notebook notebook = new Notebook();
        notebook.setName(mName);

        return noteStoreClient.createNotebook(notebook);
    }
}
