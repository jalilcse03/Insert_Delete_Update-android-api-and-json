package com.example.murtuza.login_template;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewProductActivity extends AppCompatActivity {
    EditText inputName,inputPrice,inputDesc;
    Button bntCreateProduct;

    String name="",price="",description="";
    // progress Dialog
    private ProgressDialog pDialog;
    JSONParser jsonParser=new JSONParser();

    private static String url_create_product="http://10.0.3.2/login/create_product.php";

    //jshon node names
    private static final String TAG_SUCCESS="success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);
        inputName=(EditText)findViewById(R.id.inputName);
        inputPrice=(EditText)findViewById(R.id.inputPrice);
        inputDesc=(EditText)findViewById(R.id.inputDesc);

        bntCreateProduct=(Button)findViewById(R.id.bntCrated);

        bntCreateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=inputName.getText().toString();
                price=inputPrice.getText().toString();
                description=inputDesc.getText().toString();
                new CreateNewProduct().execute();
            }
        });

    }

    /**
    * Background async task to create new product
    * */
    class CreateNewProduct extends AsyncTask<String, String,String>{

        /*
        * before starting backgroudn thread show progress dialog
        * */

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog=new ProgressDialog(NewProductActivity.this);
            pDialog.setMessage("Createing Product....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {

            // building parameters
            List<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("name", name));
            params.add(new BasicNameValuePair("price",price));
            params.add(new BasicNameValuePair("description", description));

            // getting JSON Object
            JSONObject json=jsonParser.makeHttpRequest(url_create_product,"POST",params);

            try{
                int success=json.getInt(TAG_SUCCESS);
                if(success==1)
                {
                    Intent i=new Intent(NewProductActivity.this,AllProductsActivity.class);
                    startActivity(i);
                    finish();
                }
                else{
                        handler.sendEmptyMessage(0);
                }
            }
            catch(Exception e){

            }
            return null;
        }

        @Override
        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
        }
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==0)
                Toast.makeText(NewProductActivity.this,"Error",Toast.LENGTH_SHORT).show();
        }
    };
}
