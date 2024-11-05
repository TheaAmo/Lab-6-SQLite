package com.example.lab6productmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private MyDBHandler dbHandler;
    private EditText productNameInput, productSkuInput;
    private TextView productInfoDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // Link to activity_main.xml layout

        // Initialize database handler
        dbHandler = new MyDBHandler(this);

        // Find views by their IDs
        productNameInput = findViewById(R.id.productNameInput);
        productSkuInput = findViewById(R.id.productSkuInput);
        productInfoDisplay = findViewById(R.id.productInfoDisplay);

        Button addProductButton = findViewById(R.id.addProductButton);
        Button findProductButton = findViewById(R.id.findProductButton);
        Button deleteProductButton = findViewById(R.id.deleteProductButton);

        // Set up the add product button
        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = productNameInput.getText().toString();
                int sku = Integer.parseInt(productSkuInput.getText().toString());
                dbHandler.addProduct(productName, sku);
                productInfoDisplay.setText("Product added: " + productName);
            }
        });

        // Set up the find product button
        findProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = productNameInput.getText().toString();
                String result = dbHandler.findProduct(productName);
                productInfoDisplay.setText(result);
            }
        });

        // Set up the delete product button
        deleteProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = productNameInput.getText().toString();
                boolean result = dbHandler.deleteProduct(productName);
                if (result) {
                    productInfoDisplay.setText("Product deleted: " + productName);
                } else {
                    productInfoDisplay.setText("Product not found");
                }
            }
        });
    }
}
