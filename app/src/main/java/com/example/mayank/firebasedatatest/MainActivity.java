package com.example.mayank.firebasedatatest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ServerValue;
import com.firebase.ui.FirebaseListAdapter;
import com.google.firebase.database.FirebaseDatabase;


/**
 * Created by mayank on 05-08-2016.
 */
    public class MainActivity extends AppCompatActivity {

    public Button button;
    public ListView listView;
    public EditText editText;
    public String text;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            setTitle("My Messaging App");
            button = (Button) findViewById(R.id.button);
            listView = (ListView) findViewById(R.id.listView);
            editText = (EditText) findViewById(R.id.editText);
            Firebase.setAndroidContext(this);

            //FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
            //final DatabaseReference databaseReference=firebaseDatabase.getReference("message");

            final Firebase firebase = new Firebase("https://mydatabaseproject-6ca4b.firebaseio.com/");

            requestUsername();

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ChatMessage chatMessage = new ChatMessage(text.toString(), editText.getText().toString());
                    firebase.push().setValue(chatMessage, new Firebase.CompletionListener() {
                        @Override
                        public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                            if (firebaseError != null)
                                Toast.makeText(getApplicationContext(), "Error!!", Toast.LENGTH_LONG).show();

                            Toast.makeText(getApplicationContext(), "Sent", Toast.LENGTH_LONG).show();
                        }
                    });
                    editText.setText("");
                }
            });

            com.firebase.client.Query query = firebase.limitToLast(1000);
            final FirebaseListAdapter<ChatMessage> firebaseListAdapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage.class,
                    android.R.layout.simple_list_item_1, query) {
                @Override
                protected void populateView(View view, ChatMessage chatMessage, int i) {
                        ((TextView) view.findViewById(android.R.id.text1)).setText(chatMessage.getName() + ": " + chatMessage.getMessage());

                }
            };
            listView.setAdapter(firebaseListAdapter);

        }
    public void requestUsername(){

            AlertDialog.Builder alertDialogbuilder = new AlertDialog.Builder(this);
            alertDialogbuilder.setTitle("Enter Your Name");
            final EditText entername=new EditText(this);
            alertDialogbuilder.setView(entername);
            alertDialogbuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if(entername.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(),"You have to enter your name",Toast.LENGTH_SHORT).show();
                        requestUsername();
                    }
                    text=entername.getText().toString();
                }
            });
            alertDialogbuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                    Toast.makeText(getApplicationContext(),"You have to enter your name",Toast.LENGTH_SHORT).show();
                   requestUsername();
                }
            });
        alertDialogbuilder.create().show();
        }

    }

