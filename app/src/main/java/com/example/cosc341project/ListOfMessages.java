package com.example.cosc341project;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ListOfMessages {
    static String[] messageData; // Old message data.
    static ArrayList<Integer> messageIds;
    static ArrayList<String> users; // Current message data. To be loaded into file when user exits activity.
    static ArrayList<String> messages;

    public static void populateMessages(Context context, LinearLayout itemsContainer) {
        LayoutInflater inflater = LayoutInflater.from(context);

        Resources res = context.getResources();

        messageIds = new ArrayList<>();
        users = new ArrayList<>();
        messages = new ArrayList<>();

        // find Conversation. It there isn't one, make one
        String line, userCheck, vendorCheck, convo = "";
        Inbox.convoFound = false;
        try {
            FileInputStream fis = context.openFileInput(res.getString(R.string.inbox_data));
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            while ((line = br.readLine()) != null) {
                if (!line.isEmpty() && line.charAt(0) == '@') {
                    vendorCheck = line.substring(1).split("/")[0];
                    userCheck = line.substring(1).split("/")[1];
                    if (vendorCheck.equals(Inbox.vendorName) && userCheck.equals(Inbox.username)) { // conversation found
                        Inbox.convoFound = true;
                        convo = br.readLine();
                        break;
                    }
                }
            }
            System.out.println("CONVO:" + convo);

        } catch(IOException e) {
            e.printStackTrace();
        }

        if (!Inbox.convoFound)
            return;

        // Parse message data
        messageData = convo.split("/");

        for (int i = 0; i < messageData.length; i++) {
            users.add(messageData[i].split(":")[0]);
            messages.add(messageData[i].split(":")[1]);
        }

        for (int i = 0; i < users.size(); i++) {
            // Inflate or create your custom card view layout
            CardView cardView = (CardView) inflater.inflate(R.layout.message_card, itemsContainer, false);

            // Customize card view
            TextView itemUser = cardView.findViewById(R.id.vendor_name);
            TextView itemMessage = cardView.findViewById(R.id.message_text);

            itemUser.setText(users.get(i));
            itemMessage.setText(messages.get(i)); // Just for example, setting a rating
            messageIds.add(cardView.getId());

            itemsContainer.addView(cardView);
        }

        // TODO: Figure out if we can start from the bottom up?
    }

    public static void updateMessages(Context context, LinearLayout itemsContainer, String message) {

        LayoutInflater inflater = LayoutInflater.from(context);
        CardView cardView = (CardView) inflater.inflate(R.layout.message_card, itemsContainer, false);

        // Customize card view
        TextView itemUser = cardView.findViewById(R.id.vendor_name);
        TextView itemMessage = cardView.findViewById(R.id.message_text);

        if (Inbox.accountType.equals("customer")) {
            itemUser.setText(Inbox.username);
            users.add(Inbox.username);
        } else {
            itemUser.setText(Inbox.vendorName);
            users.add(Inbox.vendorName);
        }
        itemMessage.setText(message);
        messages.add(message);

        // Probably redundant, but I'll keep here for now
        messageIds.add(cardView.getId());

        itemsContainer.addView(cardView);

    }
}
