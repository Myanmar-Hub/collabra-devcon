package net.myanmarhub.collabra;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.widget.ListView;

import net.myanmarhub.collabra.adapter.ConversationAdapter;
import net.myanmarhub.collabra.provider.CollabraKind;

/**
 * Tin Htoo Aung (Myanmar Hub) on 21/11/13.
 */
public class HomeActivity extends BaseFragmentActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private ListView converListView;
    private ConversationAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
        populateData();
    }

    private void init() {
        converListView = (ListView) findViewById(R.id.home_list);
        mAdapter = new ConversationAdapter(getBaseContext(), null);
        converListView.setAdapter(mAdapter);
    }

    private void populateData() {
        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        setProgressBarIndeterminateVisibility(true);
        return new CursorLoader(this, CollabraKind.Conversation.CONTENT_URI,
                null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mAdapter.swapCursor(cursor);
        setProgressBarIndeterminateVisibility(false);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mAdapter.swapCursor(null);
    }
}
