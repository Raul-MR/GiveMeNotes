package demo.com.givemenotes.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.evernote.client.android.EvernoteSession;

import demo.com.givemenotes.BuildConfig;
import demo.com.givemenotes.R;

public class MainActivity extends AppCompatActivity {

    private static final String CONSUMER_KEY = "Your consumer key";
    private static final String CONSUMER_SECRET = "Your consumer secret";
    private static final EvernoteSession.EvernoteService EVERNOTE_SERVICE = EvernoteSession.EvernoteService.SANDBOX;
    private static final boolean SUPPORT_APP_LINKED_NOTEBOOKS = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (!isTaskRoot()) {
            //noinspection ConstantConditions
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        String consumerKey;
        if ("Your consumer key".equals(CONSUMER_KEY)) {
            consumerKey = BuildConfig.EVERNOTE_CONSUMER_KEY;
        } else {
            // isn't the default value anymore
            consumerKey = CONSUMER_KEY;
        }

        String consumerSecret;
        if ("Your consumer secret".equals(CONSUMER_SECRET)) {
            consumerSecret = BuildConfig.EVERNOTE_CONSUMER_SECRET;
        } else {
            // isn't the default value anymore
            consumerSecret = CONSUMER_SECRET;
        }

        //Set up the Evernote singleton session, use EvernoteSession.getInstance() later
        new EvernoteSession.Builder(this)
                .setEvernoteService(EVERNOTE_SERVICE)
                .setSupportAppLinkedNotebooks(SUPPORT_APP_LINKED_NOTEBOOKS)
                .setForceAuthenticationInThirdPartyApp(true)
                .build(consumerKey, consumerSecret)
                .asSingleton();

        EvernoteSession.getInstance().authenticate(MainActivity.this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_logout) {
            EvernoteSession.getInstance().logOut();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case EvernoteSession.REQUEST_CODE_LOGIN:
                if (resultCode == Activity.RESULT_OK) {
                    startActivity(ContainerNotebookActivity.createIntent(this));
                } else {
                    finish();
                }
                break;

            default:
                super.onActivityResult(requestCode, resultCode, data);
                if (resultCode == Activity.RESULT_OK) {
                    startActivity(ContainerNotebookActivity.createIntent(this));
                } else {
                    finish();
                }
                break;
        }
    }
}
