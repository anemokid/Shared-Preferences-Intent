package c4q.nyc.idk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * This app does something - the user gets moves to another activity after a successful login.
 * I've created a new activity , in a file called SecondActivity with its corresponding XML file
 */

public class SecondActivity extends AppCompatActivity {

    // create fields:
    private Button funnyMemeButton;
    private Button sadMemeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // we can access that String from the intent that brought that activity into view with the intent.getIntent() method:
        // the String that brought the user to the Second Activity is the : userName
        TextView textView = (TextView)findViewById(R.id.session_message_textview);
        Intent intent = getIntent();
        // the intent.getStringExtra("currentUser"): is the key we've entered in our 1st Activity to pass data to the second acitivty in our intent call in the oncreate method.
        String user = intent.getStringExtra("currentUser");
        textView.setText("You're currently signed in as: " + user );

        //instantiating button fields:
        funnyMemeButton = (Button)findViewById(R.id.funny_meme_button);
        sadMemeButton = (Button)findViewById(R.id.sad_meme_button);

        //call the button methods:
        onClickButtons();
    }

    // method to setOnClickListener for Funny meme button + Sad meme button:
    public void onClickButtons(){

        funnyMemeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // create a new intent to go to the third activity class [ FUNNY MEME  CLASS ] :
                    Intent funnyMemeIntent = new Intent(SecondActivity.this, FunnyThirdActivity.class);
                    startActivity(funnyMemeIntent);
            }
        });

        sadMemeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // create a new intent to go to the fourth activity class [ SAD MEME CLASS ] :
                    Intent sadMemeIntent = new Intent(SecondActivity.this, SadThirdActivity.class);
                    startActivity(sadMemeIntent);
            }
        });
    }
} // ends Second Activity class
