package c4q.nyc.idk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * REGISTER ACTIVITY; locally stores user information, by registering individual users, and saving that
 * information using SharedPreferences. That way unless a user is registered first, they would be unable to sign in!
 */
public class RegisterActivity extends AppCompatActivity {

    private SharedPreferences registerPrefs;
    private EditText userName;
    private EditText password;
    private EditText confirmPassword;
    private Button submitButton;


    /**
     * In RegisterActivity's onCreate() we include the intent and the SharedPreferences file:
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userName = (EditText)findViewById(R.id.register_username_edittext);
        password = (EditText)findViewById(R.id.register_password_edittext);
        confirmPassword = (EditText)findViewById(R.id.confirm_password_edittext);
        submitButton = (Button)findViewById(R.id.submit_button);

        Intent intent = getIntent();
        // getApplicationContext() : gets us the data thats within our scope
        // getSharedPreferences() : since this is a new activity, we need to get this information from the intent as an extra in the new activity
        // getStringExtra("testkeY") : this is the key stored in Main Activity
        registerPrefs = getApplicationContext().getSharedPreferences(intent.getStringExtra("testKey"), MODE_PRIVATE);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = registerPrefs.edit();
                /**
                 * Checks the username + confirmPassword + password  has text and then it
                 * shares that data to the register activity code.
                 * use : .equals() instead of .equalsIgnoresCase() for passwords, because passwords are case sensitive.
                 * - In order to save the username + password during registtrationg ; we had to make each key unique.
                 * - you can't use the password as the key itself, because you need to compare it to the username first
                 * - This is why there was 2 keys created:
                 *       - a user key resulted: from concat "user: to the actual username ; The value of the key " user " is the actual username
                 *       - a password key resulteD: from concat " pass word" to the actual password; the value of the key " password " is the actual user password.
                 */
                if (userName.getText() != null &&
                        password.getText() != null &&
                        confirmPassword.getText() != null &&
                        password.getText().toString().equals(
                                confirmPassword.getText().toString()
                        )) {
                    editor.putString("user" + userName.getText().toString(), userName.getText().toString());
                    editor.putString("password" + userName.getText().toString(), password.getText().toString());
                    editor.commit();

                    /**
                     * finish() : destroys the activity, since once the data is added to SharedPreferences, we'll no longer need the activity,
                     *            and we'll want our user to be able to immediately sign in after registration.
                     *   - if the person using the app wants to register a new user, they can
                     *   just click the register button again, & repeat the process.
                     */
                    finish();
                }
            }
        });
    }
}
