package com.example.lab4;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    DatabaseReference databaseProducts;
    private EditText editTextName;
    private EditText editTextPrice;
    private List<Product> products;
    private ListView listViewProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        databaseProducts = FirebaseDatabase.getInstance().getReference("products");
    }

    protected void onStart() {
        super.onStart();
        //attaching value event listener
        databaseProducts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //clear previous product list
                products.clear();
                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting product
                    Product product = postSnapshot.getValue(Product.class);
                    //adding product to the list
                    products.add(product);
                }

                //creating adapter
                ProductList productsAdapter = new ProductList(MainActivity.this, products);
                //attaching adapter to the listview
                listViewProducts.setAdapter(productsAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addProduct() {
        //getting the values to save
        String name = editTextName.getText().toString().trim();
        double price = Double.parseDouble(String.valueOf(editTextPrice.getText().toString()));

        //checking if the value is provided
        if (!TextUtils.isEmpty(name)) {

            //getting a unique id using push().getKey() method
            String id = databaseProducts.push().getKey();

            //creating a Product Object
            Product product = new Product(id, name, price);

            //saving the Product
            databaseProducts.child(id).setValue(product);

            //setting edittext to blank again
            editTextName.setText("");
            editTextPrice.setText("");

            //displaying a success toast
            Toast.makeText(this, "Product added", Toast.LENGTH_LONG).show();
        } else {
            //if the value is not given
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
        }
    }

    private void updateProduct(String id, String name, double price) {
        //getting the specified product reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("products").child(id);
        //updating product
        Product product = new Product(id, name, price);
        dR.setValue(product);

        Toast.makeText(getApplicationContext(), "Product Updated", Toast.LENGTH_LONG).show();
    }

    private boolean deleteProduct(String id) {
        //getting the specified product reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("products").child(id);

        //removing product
        dR.removeValue();
        Toast.makeText(getApplicationContext(), "Product Deleted", Toast.LENGTH_LONG).show();
        return true;
    }
}