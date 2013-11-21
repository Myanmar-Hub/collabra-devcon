package net.myanmarhub.collabra;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.actionbarsherlock.view.Window;
import com.appspot.myanhub_collabra.collabra.Collabra;
import com.appspot.myanhub_collabra.collabra.model.User;
import com.google.api.client.util.DateTime;

import net.myanmarhub.collabra.util.Prefs;
import net.myanmarhub.collabra.util.Utils;
import net.myanmarhub.collabra.widget.EButton;
import net.myanmarhub.collabra.widget.EEditText;
import net.myanmarhub.collabra.widget.ETextView;

import java.io.IOException;

/**
 * Tin Htoo Aung (Myanmar Hub) on 20/11/13.
 */
public class LoginActivity extends BaseFragmentActivity {

    private EEditText txtName;
    private EButton btnSignIn;
    private ETextView lblMessage;
    private RegisterTask registerTask;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_login);

        username = Utils.getSettings(this, "username");
        if (username != null) {
            finish();
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        }
        init();
    }

    private void init(){
        txtName = (EEditText) findViewById(R.id.login_txtName);
        lblMessage = (ETextView) findViewById(R.id.login_lblMessage);
        btnSignIn = (EButton) findViewById(R.id.login_btnSignIn);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (registerTask != null) registerTask.cancel(true);
    }

    public void btnRegister(View v) {
        showError(null);
        if ((username = validateInput()) != null) {
            registerTask = new RegisterTask();
            registerTask.execute();
        } else {
            txtName.setText("");
            txtName.setHintTextColor(Color.RED);
            txtName.setHint("No space is allowed!");
        }
    }

    private String validateInput() {
        String username = txtName.getText().toString();
        return (!username.contains(" ")) ? username : null;
    }

    private class RegisterTask extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            txtName.setEnabled(false);
            btnSignIn.setEnabled(false);
            setProgressBarIndeterminateVisibility(true);
        }

        @Override
        protected String doInBackground(Void... voids) {
            Collabra collabra = Utils.getAPIService();
            User user = new User();
            user.setLastLoginTime(new DateTime(System.currentTimeMillis()));
            user.setUsername(username);
            try {
                collabra.user().insert(user).execute();
                Utils.getSettings(LoginActivity.this).edit().putString("username", user.getUsername()).commit();
                createSyncAccount(username);
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return Utils.parseErrMessage(e);
            }
        }

        @Override
        protected void onPostExecute(String errMessage) {
            if (!isCancelled()) {
                if (errMessage != null) {
                    txtName.setText("");
                    txtName.setHintTextColor(Color.GRAY);
                    txtName.setHint(getString(R.string.hint_txtName));
                    txtName.setEnabled(true);
                    btnSignIn.setEnabled(true);
                    showError(errMessage);
                    setProgressBarIndeterminateVisibility(false);
                }else{
                    finish();
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                }
            }
        }
    }

    private void showError(String errMessage) {
        if (errMessage == null) {
            lblMessage.setVisibility(View.GONE);
        } else {
            lblMessage.setVisibility(View.VISIBLE);
            lblMessage.setText(errMessage);
        }
    }

    public Account createSyncAccount(String accountName) {
        Account newAccount = new Account(
                accountName + "@" + Prefs.ACCOUNT_PROVIDER, Prefs.ACCOUNT_PROVIDER);
        AccountManager accountManager =
                (AccountManager) getSystemService(
                        ACCOUNT_SERVICE);
        if (accountManager.addAccountExplicitly(newAccount, null, null)) {
            ContentResolver.setSyncAutomatically(newAccount, Prefs.CONTENT_PROVIDER_AUTHORITY, true);
            ContentResolver.setIsSyncable(newAccount, Prefs.CONTENT_PROVIDER_AUTHORITY, 1);
        } else {
            Toast.makeText(this, "Error creating account", Toast.LENGTH_LONG).show();
        }
        return newAccount;
    }
}
