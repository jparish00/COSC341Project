package com.example.cosc341project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

// NO NAV BAR HERE
// When opening the inbox, all you need to pass in the bundle is the username, vendor name, and account type.
public class Inbox extends AppCompatActivity {

    // Views
    TextView vendorTitle;
    TextView custTextInput;
    Button sendButton;
    ImageButton backButton;
    // Var
    static String accountType;
    static String username, vendorName;
    Resources res;
    static boolean convoFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_inbox);

        // On launch, get username
        Bundle b = getIntent().getExtras();
        username = b.getString("username");
        vendorName = b.getString("vendor_name");
        accountType = b.getString("account_type");

        res = getResources();

        // Views
        vendorTitle = findViewById(R.id.vendorTitleText);
        custTextInput = findViewById(R.id.cust_msg_input);
        backButton = findViewById(R.id.gobackbtn);
        sendButton = findViewById(R.id.request_button);

        vendorTitle.setText(vendorName);

        // Code for inserting all items in market
        LinearLayout itemsContainer = findViewById(R.id.messageLayout);
        // Populating store cards
        ListOfMessages.populateMessages(this, itemsContainer);

        // Set up back button
        backButton.setOnClickListener(this::onBackClick);
        sendButton.setOnClickListener(this::onSendClick);
    }

    public void onBackClick(View v) {

        // Read in entire file to overwrite
        FileOutputStream fout;
        if (!convoFound) {
            try {
                fout = openFileOutput(res.getString(R.string.inbox_data), Context.MODE_APPEND);
                fout.write(("@" + vendorName + "/" + username + "\n").getBytes());
                System.out.println("SIZE: " + ListOfMessages.users.size());
                if (ListOfMessages.users.size() < 1) {
                    fout.write(("none").getBytes());
                    System.out.println("WRITTEN");
                } else {
                    for (int i = 0; i < ListOfMessages.users.size(); i++) {
                        fout.write((ListOfMessages.users.get(i) + ":" + ListOfMessages.messages.get(i)).getBytes());
                        if (i != ListOfMessages.users.size() - 1)
                            fout.write(("/").getBytes());
                    }
                }
                fout.write(("\n").getBytes());
                fout.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            finish();
        }

        // If the convo already exists, pull the entire inbox file, modify the convo line, and rewrite
        ArrayList<String> lines = new ArrayList<>();
        int count = 0, convoLocation = -1;
        try {

            FileInputStream fis = openFileInput(res.getString(R.string.inbox_data));
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            String line, usernameIn, vendorIn;
            while ((line = br.readLine()) != null) {
                count++;
                lines.add(line);
                if (!line.isEmpty() && line.charAt(0) == '@') { // For now, use an @ sign to indicate a user

                    vendorIn = line.substring(1).split("/")[0];
                    usernameIn = line.substring(1).split("/")[1];
                    if (username.equals(usernameIn) && vendorName.equals(vendorIn)) {
                        convoLocation = count; // Don't add +1, since arraylist starts from 0
                    }
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

        String convo = "";
        System.out.println(ListOfMessages.users.size());
        System.out.println(ListOfMessages.messages.size());

        for (int i = 0; i < ListOfMessages.users.size(); i++) {
            convo += ListOfMessages.users.get(i) + ":" + ListOfMessages.messages.get(i);
            if(i != ListOfMessages.users.size()-1)
                convo += "/";
        }
        lines.set(convoLocation, convo);

        FileWriter fw;
        File f = new File(getApplicationContext().getFilesDir(), res.getString(R.string.inbox_data));
        try {
            fw = new FileWriter(f, false);
            for (int i = 0; i < lines.size(); i++)
                fw.write(lines.get(i) + "\n");
            fw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        finish();

    }

    public void onSendClick(View v) {

        // Go into List of messages and update
        LinearLayout itemsContainer = findViewById(R.id.messageLayout);

        String message = custTextInput.getText().toString();
        custTextInput.setText("");

        ListOfMessages.updateMessages(this, itemsContainer, message);

    }
}