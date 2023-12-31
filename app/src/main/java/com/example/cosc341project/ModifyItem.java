package com.example.cosc341project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class ModifyItem extends AppCompatActivity {

    // Views
    ImageButton backButton;
    Button saveButton;

    TextView nameInput, priceInput;

    // Var
    int index;
    String name, price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_item);

        Bundle b = getIntent().getExtras();
        index = b.getInt("index");
        name = b.getString("name");
        price = b.getString("price");

        backButton = findViewById(R.id.backButton);
        saveButton = findViewById(R.id.saveButton);
        nameInput = findViewById(R.id.nameTextInput);
        priceInput = findViewById(R.id.priceTextInput);

        nameInput.setText(name);
        priceInput.setText(price);

        backButton.setOnClickListener(v -> { finish(); });
        saveButton.setOnClickListener(this::onSaveClick);

    }

    public void onSaveClick(View v) {

        // Simply overwrite the arraylist and Cardview data
        String new_name, new_price;

        new_name = nameInput.getText().toString();
        new_price = priceInput.getText().toString();

        ListOfVendorItems.items.set(index, new_name);
        ListOfVendorItems.prices.set(index, new_price);

        CardView cardView = ListOfVendorItems.modifyCardView;
        TextView nameView = cardView.findViewById(R.id.item_name);
        TextView priceView = cardView.findViewById(R.id.item_price);

        nameView.setText(new_name);
        priceView.setText(new_price);

        ListOfVendorItems.saveVendorData(this);

        finish();

    }
}