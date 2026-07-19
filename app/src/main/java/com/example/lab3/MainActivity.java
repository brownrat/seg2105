package com.example.lab3;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private ImageView logoImage;
    private String drawableName;
    private ActivityResultLauncher<Intent> logoLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        logoImage = findViewById(R.id.logoImage);
        logoLauncher = registerForActivityResult(new
                ActivityResultContracts.StartActivityForResult(), (result -> {
            ImageView logoImage = (ImageView) findViewById(R.id.logoImage);
            drawableName = "ic_logo_00";
            Intent data = result.getData();
            int imageID = data.getIntExtra("imageID", R.id.teamid00);
            if (imageID == R.id.teamid00) {
                drawableName = "ic_logo_00";
            } else if (imageID == R.id.teamid01) {
                drawableName = "ic_logo_01";
            } else if (imageID == R.id.teamid02) {
                drawableName = "ic_logo_02";
            } else if (imageID == R.id.teamid03) {
                drawableName = "ic_logo_03";
            } else if (imageID == R.id.teamid04) {
                drawableName = "ic_logo_04";
            } else if (imageID == R.id.teamid05) {
                drawableName = "ic_logo_05";
            } else {
                drawableName = "ic_logo_00";
            }
            int resID = getResources().getIdentifier(drawableName, "drawable",
                    getPackageName());
            logoImage.setImageResource(resID);
        }));
    }

    public void setLogo(View view) {

        Intent intent = new Intent(this, LogoSelectActivity.class);
        logoLauncher.launch(intent);
    }

    public void openInGoogleMaps (View view) {
        EditText teamPostalCode = (EditText) findViewById(R.id.postalCode);
        Uri gmmIntentUri = Uri.parse("http://maps.google.co.in/maps?q="+teamPostalCode.getText());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    public void submit (View view) {
        EditText teamNameView = (EditText) findViewById(R.id.teamName);
        EditText postalCodeView = (EditText) findViewById(R.id.postalCode);

        String teamName = teamNameView.getText().toString();
        String postalCode = postalCodeView.getText().toString();

        Team team = new Team(teamName, postalCode, drawableName);

        Intent intent = new Intent(MainActivity.this,
                ConfirmationActivity.class);
        intent.putExtra("teamInfo", team);
        startActivity(intent);
    }
}

