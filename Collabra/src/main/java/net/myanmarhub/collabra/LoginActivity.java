package net.myanmarhub.collabra;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import net.myanmarhub.collabra.util.Utils;
import net.myanmarhub.collabra.widget.EButton;
import net.myanmarhub.collabra.widget.EEditText;

/**
 * Tin Htoo Aung (Myanmar Hub) on 20/11/13.
 */
public class LoginActivity extends SherlockFragmentActivity {

    private EEditText txtName;
    private EButton btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        init();

        if(Utils.checkPlayService(this)){
            Utils.registerGCM(this);
        }
    }

    private void init(){
        txtName = (EEditText) findViewById(R.id.login_txtName);

        btnSignIn = (EButton) findViewById(R.id.login_btnSignIn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateInput()){
                    txtName.setText("");
                    txtName.setHintTextColor(Color.GRAY);
                    txtName.setHint(getString(R.string.hint_txtName));
                }else{
                    txtName.setText("");
                    txtName.setHintTextColor(Color.RED);
                    txtName.setHint("No space is allowed!");
                }
            }
        });
    }

    private boolean validateInput(){
        String username = txtName.getText().toString();
        return !username.contains(" ");
    }
}
