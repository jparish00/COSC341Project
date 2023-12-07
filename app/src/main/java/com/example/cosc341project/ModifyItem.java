package com.example.cosc341project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ModifyItem extends AppCompatActivity {

    // Views
    ImageButton backButton;
    Button saveButton;

    TextView nameInput, priceInput;

    // Var
    int index, id;
    String name, price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_item);

        Bundle b = getIntent().getExtras();
        id = b.getInt("id");
        index = b.getInt("index");
        name = b.getString("name");
        price = b.getString("price");

//        backButton = findViewById(R.id.backButton);
//        saveButton = findViewById(R.id.saveButton);
//        nameInput = findViewById(R.id.name_input);
//        priceInput = findViewById(R.id.priceInput);

        nameInput.setText(name);
        nameInput.setText(price);

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

        CardView cardView = findViewById(id);
//        TextView nameView = cardView.findViewById(R.id.name);
//        TextView priceView = cardView.findViewById(R.id.price);

//        nameView.setText(new_name);
//        priceView.setText(new_price);

    }
}