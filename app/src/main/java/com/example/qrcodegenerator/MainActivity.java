package com.example.qrcodegenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.client.android.Intents;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    EditText etName,etAddress,etContact,etCourse;
    String gName,gAddress,gContact,gCourse;
    JSONObject jsonObject;
    JSONArray jsonArray;
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etName=findViewById(R.id.etName);
        etAddress=findViewById(R.id.etAddress);
        etContact=findViewById(R.id.etContact);
        etCourse = findViewById(R.id.etcourse);
    }
    public void generate(View view){
        gName=etName.getText().toString().toLowerCase();
        gAddress=etAddress.getText().toString().toLowerCase();
        gContact=etContact.getText().toString().toLowerCase();
        gCourse = etCourse.getText().toString().toLowerCase();
        if (gName.isEmpty()&&gAddress.isEmpty()&&gContact.isEmpty()&&gCourse.isEmpty()){
            Toast.makeText(this, "fill up all", Toast.LENGTH_SHORT).show();
        }else {
            //generate qr code
            jsonObject = new JSONObject();
            try {
                jsonObject.put("name",gName);
                jsonObject.put("address",gAddress);
                jsonObject.put("contact",gContact);
                jsonObject.put("course",gCourse);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArray=new JSONArray();
            jsonArray.put(jsonObject);

            //convert json to string for reading
            result=jsonObject.toString();
            startActivity(new Intent(this,QRcode.class).putExtra("result",result));
        }
    }
    public void scan(View view){
        startActivity(new Intent(this, Scanner.class));
    }
}