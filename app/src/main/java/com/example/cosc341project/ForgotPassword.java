package com.example.cosc341project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ForgotPassword extends AppCompatActivity {

    // Views
    TextView usernameInput, passwordInput, rePasswordInput;
    Button returnButton, confirmButton;
    Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // Views
        usernameInput = findViewById(R.id.username_text_input);
        passwordInput = findViewById(R.id.password_input);
        rePasswordInput = findViewById(R.id.repeat_password_input);
        returnButton = findViewById(R.id.return_button);
        confirmButton = findViewById(R.id.confirmPassBtn);

        returnButton.setOnClickListener(v -> { finish(); });
        confirmButton.setOnClickListener(this::onConfirmClick);

        res = getResources();
    }

    public void onConfirmClick(View v) {

        // Get username and password
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (!password.equals(rePasswordInput.getText().toString())) {
            Toast.makeText(this, res.getString(R.string.toast_password_mismatch), Toast.LENGTH_SHORT).show();
            return;
        }


        // Check that the inputted username exists, first by checking is the file exists
        File f = new File(getApplicationContext().getFilesDir(), res.getString(R.string.user_data));
        if (!f.exists()) {
            Toast.makeText(this, res.getString(R.string.toast_username_does_not_exist), Toast.LENGTH_SHORT).show();
            return;
        }


        // Search the file for the username, while recording info into arraylist. If found, replace the following line with the new password.
        ArrayList<String> lines = new ArrayList<>();
        boolean usernameFound = false;
        int count = 0, passLocation = -1;
        try {

            FileInputStream fis = openFileInput(res.getString(R.string.user_data));
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            String line, usernameIn;

            while ((line = br.readLine()) != null) {
                count++;
                lines.add(line);
                if (line.charAt(0) == '@') { // For now, use an @ sign to indicate a user

                    usernameIn = line.substring(1).split(" ")[0];
                    if (username.equals(usernameIn)) {
                        usernameFound = true;
                        passLocation = count; // Don't add +1, since arraylist starts from 0
                        break;
                    }
                }
            }
        } catch(IOException e) {
            Toast.makeText(this, res.getString(R.string.toast_missing_login_file), Toast.LENGTH_SHORT).show();
        }

        // Check if the username was found
        if(!usernameFound) {
            Toast.makeText(this, res.getString(R.string.toast_username_does_not_exist), Toast.LENGTH_SHORT).show();
            return;
        }

        // Modify lines, rewrite to file
        lines.set(passLocation, password);
        FileWriter fw;
        try {
            fw = new FileWriter(f, false);
            for (int i = 0; i < lines.size(); i++)
                fw.write(lines.get(i) + "\n");
            fw.close();

        } catch (Exception e) {
            e.printStackTrace();
            finish();
        }

        Toast.makeText(this, res.getString(R.string.toast_password_changed), Toast.LENGTH_SHORT).show();

        // Success :)
        finish();
    }
}