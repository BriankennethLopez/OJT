package com.example.qrcodegenerator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Scanner extends AppCompatActivity {
    CodeScanner codeScanner;
    CodeScannerView codeScannerView;
    TextView tvResult;
    String gResult,gName,gAddress,gContact,gCourse;
    JSONObject jsonObject;
    JSONArray jsonArray;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        codeScannerView=findViewById(R.id.scanner);
        codeScanner=new CodeScanner(this,codeScannerView);
        tvResult=findViewById(R.id.tvResult);
        
        tvResult.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                gResult=tvResult.getText().toString();
                try {
                    jsonObject=new JSONObject(gResult);
                    gName=jsonObject.getString("name");
                    gAddress=jsonObject.getString("address");
                    gContact=jsonObject.getString("contact");
                    gCourse=jsonObject.getString("gCourse");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                AlertDialog.Builder alert = new AlertDialog.Builder(Scanner.this);
                alert.setTitle("Confirm");
                alert.setCancelable(false);
                alert.setMessage("Name: "+gName +"\n"+"Address: "+gAddress +"\n"+"Contact: "+gContact+"\n"+"Course:"+gCourse);

                alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StringRequest request = new StringRequest(Request.Method.POST, "https://brianenzio.000webhostapp.com/insert.php", new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.equalsIgnoreCase("inserted")){
                                    Toast.makeText(Scanner.this, "inserted", Toast.LENGTH_SHORT).show();

                                }else {
                                    Toast.makeText(Scanner.this, response, Toast.LENGTH_SHORT).show();

                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Scanner.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                        ){
                            @Nullable
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String,String> param = new HashMap<String,String>();
                                param.put("name",gName);
                                param.put("address",gAddress);
                                param.put("contact",gContact);
                                param.put("course",gCourse);
                                return param;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(Scanner.this);
                        requestQueue.add(request);


                    }
                });
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(Scanner.this, "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                alert.show();

            }


            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        requestforcamera();
        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvResult.setText(result.getText());
                    }
                });
            }
        });
    }
    private void requestforcamera() {
        Dexter.withActivity(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                codeScanner.startPreview();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
                Toast.makeText(Scanner.this, "Camera Required", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                token.continuePermissionRequest();

            }
        }).check();
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestforcamera();
    }
}