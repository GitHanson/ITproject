
package com.example.familyapp2;

        import androidx.appcompat.app.AppCompatActivity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.ImageButton;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);


        ImageButton goback = findViewById(R.id.goback);
        goback.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent i = new Intent(AboutUsActivity.this, SettingActivity.class);
                startActivity(i);
            }
        });



    }

}
