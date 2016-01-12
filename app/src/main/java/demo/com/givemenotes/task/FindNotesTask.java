package demo.com.givemenotes.task;

import android.support.annotation.Nullable;

import com.evernote.client.android.EvernoteSession;
import com.evernote.client.android.asyncclient.EvernoteSearchHelper;
import com.evernote.client.android.type.NoteRef;
import com.evernote.edam.notestore.NoteFilter;
import com.evernote.edam.type.NoteSortOrder;
import com.evernote.edam.type.Notebook;

import java.util.List;

/**
 * Created by ramuñoz on 08/01/2016.
 */
public class FindNotesTask extends BaseTask<List<NoteRef>> {

    private final EvernoteSearchHelper.Search mSearch;

    @SuppressWarnings("unchecked")
    public FindNotesTask(int offset, int maxNotes, @Nullable Notebook notebook) {
        super((Class) List.class);

        NoteFilter noteFilter = new NoteFilter();
        noteFilter.setOrder(NoteSortOrder.UPDATED.getValue());

        if (notebook != null) {
            noteFilter.setNotebookGuid(notebook.getGuid());
        }

        mSearch = new EvernoteSearchHelper.Search()
                .setOffset(offset)
                .setMaxNotes(maxNotes)
                .setNoteFilter(noteFilter);

        mSearch.addScope(EvernoteSearchHelper.Scope.PERSONAL_NOTES);
    }

    @Override
    protected List<NoteRef> checkedExecute() throws Exception {
        EvernoteSearchHelper.Result searchResult = EvernoteSession.getInstance()
                .getEvernoteClientFactory()
                .getEvernoteSearchHelper()
                .execute(mSearch);

        return searchResult.getAllAsNoteRef();
    }
}
