package c4q.nyc.idk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/**
 * In this MainActivity.java file we'll store references to our views, and our SharedPreferences
 * file, so that we may interact with them.
 * - SharedPreferences are cool because you can do so much with them;
 *    - essentially you can use them as : DATABASES!!!!!!
 */

public class MainActivity extends AppCompatActivity {

    private static final String SHARED_PREFS_KEY = "sharedPrefsTesting"; // a constant variable that will exist for the whole of this activity ; we will be using it several times
    private EditText username;
    private EditText password;
    private CheckBox checkBox;
    private Button submitButton;
    private Button registerButton;
    private SharedPreferences login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.username_edittext);
        password = (EditText) findViewById(R.id.password_edittext);
        checkBox = (CheckBox) findViewById(R.id.remember_me_checkbox);
        submitButton = (Button) findViewById(R.id.submit_button);
        registerButton = (Button) findViewById(R.id.register_button);

        /**
         * .getApplicationContext():
         * We use this because we want this data to be accessible to all of the activities that
         * might run within this applications process. Then we call the method .getSharedPreferences("mySharedPrefs", MODE_PRIVATE);
         * so that we can :
         * - 1st: get back a file associated with the String "mySharedPrefs"
         * - 2nd: if there's no file associated with the String " mySharedPrefs"
         * - 3rd: make sure only this app has access to this data, since SharedPreferences are
         *        data collections which can potentially be accessed anywhere in the device, when given the right/ wrong permissions
         */
        // this gives us access to shared preferences using the key
        login = getApplicationContext().getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE);

        /**
         * If the user ticks the checkbox, they'll expect that the next time they open the app, their informtion will be autofilled in.
         * Do this in the onCreate() ;
         * - 1st: we check to see if the checbox was ticked thre alst time the login info was submitted by running the login.getBoolean("isChecked", false ) as a condition for the if
         *       - this method takes two paramters: [ this backup value returns a value no matter what ]
         *       - 1. the key for the value that we want ,
         *       - 2. a back up value should the key not be there.
         *       ** default value for a boolean: false
         * - 2nd: if the checkbox was ticked previously, we can set the Textvalues of these editTexts by using the keys associated with the username + password last entered.
         *       - in the getString() method, we also pass in two arguments as parameters.
         *       - you can also just pass a backup String default value of "" [ empty string literal ] rather than null ; * programmer preference
         *  ** THIS IF STATEMENT : will only run if the SharedPreferences reference has an isChecked jey with a true calue associated with it
         */
        if (login.getBoolean("isChecked", false)) {
            username.setText(login.getString("username", null));
            password.setText(login.getString("password", null));
            checkBox.setChecked(login.getBoolean("isChecked", false));
        }

        /**
         * We want to store the username and password entered into our EditTxts for future use only if the checkbox next to "remember me " is ticked & the submit button has been clicked.
         * We can do that by taking the SharedPreferences reference, and adding it to a SharedPreferences.Editor reference in the submit button's onClickListener.
         * - 1st: we check whether the checkbox has been ticked ( checkbox.isChecked() ) ,
         * - 2nd: then we pass the SharedPreferences reference to the Editor, because like Arrays & Strings,
         *    [ SHARE PREFERENCES ARE IMMUTABLE, AND MUST BE EDITED IN A UNIQUE WAY ]
         */
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            SharedPreferences.Editor editor = login.edit();
                /**
                 * For our logic : we're adding an if/then statemnt, so that if the checkbox is not ticked, we can
                 * store that fact for anyone who wishes to check that in the future .
                 */
            if( checkBox.isChecked()){
                // TO STORE THE VALUES :
                // we use a put method( like a map ) , corresponding to its data type : int, boolean, String
                // we pass in a String key, and a value we want to associate with that key - in this case the username, password and whethr or not the checkbox has been ticked
                // whenever we put in any of these keys, we expect back one username, one password, and one isChecked.
                editor.putString("username", username.getText().toString());
                editor.putString("password", password.getText().toString());
                editor.putBoolean("isChecked", checkBox.isChecked()); // as long as isChecked is true put this data in the view of the activity the next time; and if its not checked you dont have to do it

                // pattern for design when working with fragments + stringbuilder ;
                // essentially : means that you're done adding key/value pairs ; close it ( not necessarily ) ;
                // you want to commit this immutable object.
                // WHY TO USE COMMIT INSTEAD OF APPLY : [ COMMIT VS. APPLY ]
                // commit is when you want to store your sharedPreferences immediately. You're blocking the line of execution , and telling the compiler to save immediately.
                // apply: is when you want to store data, but you're not going to use it immediately; you're technically storing it in the background
                // you're letting the compiler + android choose the
                // ! --- We run editor.commit() everytime we run .edit() on the SharedPreferences references, whenevre we have finished adding key/value pairs of data.
                editor.commit();
            } else {
                editor.putBoolean("isChecked", checkBox.isChecked());
                editor.commit();
            }

            //
                String checkUser = "user" + username.getText().toString();
                String checkPassword = "password" + username.getText().toString();

                if (username.getText().toString().equalsIgnoreCase(login.getString(checkUser, null))
                        && password.getText().toString().equals(login.getString(checkPassword, null))) {

                    Toast.makeText(getApplicationContext(),"Authentication Successful", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    intent.putExtra("currentUser", username.getText().toString());
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(),"Username or Password incorrect - pls try again", Toast.LENGTH_SHORT).show();
                }

                /**
                 * to get to the second activity; with an intent :
                 * - 1st: instantiate an intent object and pass it two arguments:
                 *       1. the activity we started int: MainActivity.this
                 *       2. the activity we want to go to : SecondActivty.class
                 * - 2nd: then we pass that reference to the startActivity() method, which stops the current activity,
                 *        adds it to a backstack, and brings the next activity into view.
                 * * * : in the mainActivitys intent, we can pass along the username to the next activity, by passing an intent
                 *      - and using user data from the mainActivity;
                 *      - using .putExtra() allows us to share data from the main activity to the second activity.
                 */
                // MainActivity.this  [ current instance of the first activity ] ---> then at runtime u can tell it to move to the second activity [ SecondActivity.class ]
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("currentUser", username.getText().toString());
                startActivity(intent);
            }
        });


        /**  registerButton onClick logic:
         * 1st: checks to see if the password entered is consistent
         * 2nd: that we're using the same sharedPreferrences file as our login activity
         * 3rd: updates sharedPreferences with unique key/value pairs so that, when correctly entered, will allow the user to log in successfully
         */
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // in the main activity you can use an intent to move to the RegisterActivity,
                // and pass it the same SharedPreferences key we used to remember a user's credentials.
                // we can do that with an extra, placed in the registerButton's onClickListener()
                // main activity is where you are and register activity is where you go
                // reference: ** Cotton Eye Joe **
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                intent.putExtra("testKey", SHARED_PREFS_KEY);
                startActivity(intent);
            }
        });
    }


}