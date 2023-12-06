package com.example.cosc341project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

// For consistency
// ids: snake_case
// java variables/functions: camelCase

// This is the entry point. If the application is not logged in or already running, we start here.

public class MainActivity extends AppCompatActivity {

    // Views
    TextView usernameInput, passwordInput;
    Button forgotPasswordButton, logInButton, createAccountButton;

    Resources res;

    // Var
    String username, password;
    boolean fileExists;
    Button tempbtn; // TEMPORARY

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*---TEMPORARY BUTTON JUST TO TEST NAV
         delete when we can successfully log into custHome--------------*/

        tempbtn = findViewById(R.id.tempButton);
        tempbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the CustHome activity
                Intent intent = new Intent(MainActivity.this, CustHome.class);
                startActivity(intent);
            }
        });

        /*------------------------------------*/


        // check if logged in. If so, immediately load next activity (default market).
        //  checkLoggedIn();

        // if not logged in, init.
        usernameInput = findViewById(R.id.username_text_input);
        passwordInput = findViewById(R.id.password_text_input);
        forgotPasswordButton = findViewById(R.id.forgot_pass_button);
        logInButton = findViewById(R.id.log_in_button);
        createAccountButton = findViewById(R.id.create_account_button);

        forgotPasswordButton.setOnClickListener(this::forgotPassword); // Does nothing atm
        logInButton.setOnClickListener(this::attemptLogin);
        createAccountButton.setOnClickListener(this::createAccount);
        // Leaving vendor button, since we may move that to account creation

        res = getResources();

        // Copy writeable files to android
        File f = new File(getApplicationContext().getFilesDir(), res.getString(R.string.user_data));
        if (!f.exists())
            copyAssetsToAndroid();

        fileExists = true;
    }
//
//    public void checkLoggedIn() {
//
//        String username = "";
//        boolean loggedIn = false;
//        try {
//
//            FileInputStream fis = openFileInput(res.getString(R.string.user_data));
//            InputStreamReader isr = new InputStreamReader(fis);
//            BufferedReader br = new BufferedReader(isr);
//
//            String line;
//            while ((line = br.readLine()) != null) {
//                if (line.charAt(0) == '@') { // For now, use an @ sign to indicate a user
//
//                    username = line;
//                    br.readLine(); // password
//                    line = br.readLine(); // options
//                    if (line.split(" ")[0].equals("true")) {
//                        loggedIn = true;
//                        break;
//                    }
//                }
//            }
//        } catch(IOException e) {
//            Toast.makeText(this, res.getString(R.string.toast_missing_login_file), Toast.LENGTH_SHORT).show();
//        }
//
//        if (!loggedIn)
//            return;
//
//        Intent intent = new Intent(getApplicationContext(), CustHome.class);
//        intent.putExtra("username", username); // Assumption that users cannot share usernames, so username can be used as a primary key
//        startActivity(intent);
//        finish(); // No need to initialize the rest of the activity, since we are skipping it
//    }

    public void forgotPassword(View v) {
        Intent intent = new Intent(getApplicationContext(), ForgotPassword.class);
        startActivity(intent);
    }

    public void attemptLogin(View v) {

        // Get var
        username = usernameInput.getText().toString();
        password = passwordInput.getText().toString();

        // search for username
        // TODO: Consider case insensitive? Would be easy to implement
        if(!fileExists) {
            Toast.makeText(this, res.getString(R.string.toast_add_account), Toast.LENGTH_SHORT).show();
            return;
        }

        boolean loginSuccess = false;
        try {

            FileInputStream fis = openFileInput(res.getString(R.string.user_data));
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            String line, infoCheck;
            while ((line = br.readLine()) != null) {
                if (line.charAt(0) == '@') {

                    infoCheck = line.split(" ")[0].substring(1);
                    if (username.equals(infoCheck)) {
                        infoCheck = br.readLine(); // Assumption that password is the only thing on second line
                        if(password.equals(infoCheck)) {
                            loginSuccess = true;
                            break;
                        }
                    }
                }
            }
        } catch(IOException e) {
            Toast.makeText(this, res.getString(R.string.toast_missing_login_file), Toast.LENGTH_SHORT).show();
        }

        if(!loginSuccess) {
            Toast.makeText(this, res.getString(R.string.toast_incorrect_login), Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(getApplicationContext(), CustHome.class);
        intent.putExtra("username", username);
        startActivity(intent);
        finish(); // Kill activity
    }

    public void createAccount(View v) {

        // Open account creation activity, but do not kill login activity
        Intent intent = new Intent(getApplicationContext(), CustSignUp.class);
        startActivity(intent);

    }

    public void copyAssetsToAndroid() {

        AssetManager am = getAssets();
        try {
            // user_info.txt, consider removing for final apk so there is no base login info
            InputStream is = am.open(res.getString(R.string.user_data));
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            FileOutputStream fout = openFileOutput(res.getString(R.string.user_data), Context.MODE_APPEND);

            String line;
            while ((line = br.readLine()) != null) {
                fout.write((line + "\n").getBytes());
            }

            // sign_in_type.txt
            is = am.open(res.getString(R.string.sign_in_type));
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);

            fout = openFileOutput(res.getString(R.string.sign_in_type), Context.MODE_APPEND);
            while ((line = br.readLine()) != null) {
                fout.write((line + "\n").getBytes());
            }

            // market_info.txt
            is = am.open(res.getString(R.string.market_info));
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);

            fout = openFileOutput(res.getString(R.string.market_info), Context.MODE_APPEND);
            while ((line = br.readLine()) != null) {
                fout.write((line + "\n").getBytes());
            }

            // default_market.txt
            is = am.open(res.getString(R.string.default_market));
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);

            fout = openFileOutput(res.getString(R.string.default_market), Context.MODE_APPEND);
            while ((line = br.readLine()) != null) {
                fout.write((line + "\n").getBytes());
            }

            // vendor_info.txt
            is = am.open(res.getString(R.string.vendor_data));
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);

            fout = openFileOutput(res.getString(R.string.vendor_data), Context.MODE_APPEND);
            while ((line = br.readLine()) != null) {
                fout.write((line + "\n").getBytes());
            }

            // cart_info.txt
            is = am.open(res.getString(R.string.cart_info));
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);

            fout = openFileOutput(res.getString(R.string.cart_info), Context.MODE_APPEND);
            while ((line = br.readLine()) != null) {
                fout.write((line + "\n").getBytes());
            }

        } catch(IOException e) {
            System.out.println("Something went wrong! Assets could not be copied to android");
        }
    }

}