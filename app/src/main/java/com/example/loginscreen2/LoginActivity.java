package com.example.loginscreen2;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {
    EditText editTextUserName, editTextEmail, editTextPassword;
    Button buttonLogin;
    TextView textViewCreateAccount;
    SqliteHelper sqliteHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sqliteHelper = new SqliteHelper(this);
        /*initTextViewLogin();
        initView();*/
        editTextUserName = (EditText)findViewById(R.id.editTextUserName);
        editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        textViewCreateAccount = (TextView) findViewById(R.id.textViewCreateAccount);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate())
                {
                    String email = editTextEmail.getText().toString();
                    String password = editTextPassword.getText().toString();
                    User currentUser = sqliteHelper.Authenticator(new User(null, null, email, password));
                    boolean passcheck = sqliteHelper.isPasswordMatch(password);
                    if(currentUser!=null)
                        {
                            if(passcheck)
                            {
                                Snackbar.make(buttonLogin, "Successfully Logged in!", Snackbar.LENGTH_LONG).show();
                            }
                            else
                            {
                                Snackbar.make(buttonLogin, "Password does not match!", Snackbar.LENGTH_LONG).show();
                            }
                        }
                    else
                    {
                        Snackbar.make(buttonLogin, "Failed to log in , please try again", Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });
        textViewCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
    }

    public static Spanned fromHtml(String html)
    {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }
    public boolean validate() {
        boolean valid = false;

        //Get values from EditText fields
        String Email = editTextEmail.getText().toString();
        String Password = editTextPassword.getText().toString();

        //Handling validation for Email field
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            valid = false;
            editTextEmail.setError("Please enter valid email!");
        } else {
            valid = true;
            editTextEmail.setError(null);
        }

        //Handling validation for Password field
        if (Password.isEmpty()) {
            valid = false;
            editTextPassword.setError("Please enter valid password!");
        } else {
            if (Password.length() > 5) {
                valid = true;
                editTextPassword.setError(null);
            } else {
                valid = false;
                editTextPassword.setError("Password is to short!");
            }
        }

        return valid;
    }

}
