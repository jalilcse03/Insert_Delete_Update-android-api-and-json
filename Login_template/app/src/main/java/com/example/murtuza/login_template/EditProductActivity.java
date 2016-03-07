package com.example.murtuza.login_template;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EditProductActivity extends AppCompatActivity {
    EditText inputName,inputPrice,inputDesc;
    Button deleteBt,updateBt;

    String pid="";
    static String s="";

    String name="",price="",des="";

    // progress dialog
    private ProgressDialog pDialog;

    // json parser class
    JSONParser jsonParser=new JSONParser();

    // single product url
    private static final String url_product_detials = "http://10.0.3.2/login/get_product_details.php";
    private static String url_update_product = "http://10.0.3.2/login/update_product.php";
    private static String url_delete_product = "http://10.0.3.2/login/delete_product.php";

    // json node names
    private static final  String TAG_SUCCESS="success";
    private static final  String TAG_PRODUCT="product";
    private static final  String TAG_PID="pid";
    private static final  String TAG_NAME="name";
    private static final  String TAG_PRICE="price";
    private static final  String TAG_DESCRIPTION="description";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //android.os.NetworkOnMainThreadException for solve

        final StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        setContentView(R.layout.activity_edit_product);
        inputName = (EditText) findViewById(R.id.inputName);
        inputPrice = (EditText) findViewById(R.id.inputPrice);
        inputDesc = (EditText) findViewById(R.id.inputDesc);

//        inputName=(EditText)findViewById(R.id.inputName);
//        inputPrice=(EditText)findViewById(R.id.inputPrice);
//        inputDesc=(EditText)findViewById(R.id.inputPrice);

        deleteBt=(Button)findViewById(R.id.delete);
        updateBt=(Button)findViewById(R.id.update);

        // getting complete product details in background thread
        Intent i=getIntent();
        pid=i.getStringExtra(TAG_PID);
       // Toast.makeText(EditProductActivity.this,pid,Toast.LENGTH_SHORT).show();
        new GetProductDetails().execute();

        updateBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = inputName.getText().toString();
                price = inputPrice.getText().toString();
                des = inputDesc.getText().toString();
                new updateProduct().execute();

            }
        });

        deleteBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DeleteProduct().execute();
            }
        });


    }

    /**
     * Background Async Task to Get complete product details
     * */
    class GetProductDetails extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditProductActivity.this);
            pDialog.setMessage("Loading product details. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Getting product details in background thread
         * */
        protected String doInBackground(String... params) {

            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    // Check for success tag
                    int success;
                    try {
                        // Building Parameters
                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("pid", pid));

                        // getting product details by making HTTP request
                        // Note that product details url will use GET request
                        JSONObject json = jsonParser.makeHttpRequest(
                                url_product_detials, "GET", params);

                        // check your log for json response
                     //   Log.d("Single Product Details", json.toString());
                       s =json.toString();
                        // json success tag
                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            // successfully received product details
                            JSONArray productObj = json
                                    .getJSONArray(TAG_PRODUCT); // JSON Array

                            // get first product object from JSON Array
                            JSONObject product = productObj.getJSONObject(0);

                            // product with this pid found
                            // Edit Text

                            // display product data in EditText
                            inputName.setText(product.getString(TAG_NAME));
                            inputPrice.setText(product.getString(TAG_PRICE));
                            inputDesc.setText(product.getString(TAG_DESCRIPTION));

                        }else{
                        //inputName.setText("ok1");

                        }
                    } catch (JSONException e) {
                     //  inputDesc.setText("ok \n"+s);
                        e.printStackTrace();
                    }
                }
            });

            return null;
        }


        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once got all details
            pDialog.dismiss();
        }
    }

    /**
     * background asynce task to save product details
     *
     */
    class updateProduct extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... params) {
            List<NameValuePair> param=new ArrayList<NameValuePair>();
            param.add(new BasicNameValuePair(TAG_PID,pid));
            param.add(new BasicNameValuePair(TAG_NAME,name));
            param.add(new BasicNameValuePair(TAG_PRICE,price));
            param.add(new BasicNameValuePair(TAG_DESCRIPTION,des));
            JSONObject json=jsonParser.makeHttpRequest(url_update_product,"POST",param);

                    try{
                        int success=json.getInt(TAG_SUCCESS);
                        if(success==1){
                            Intent i=getIntent();
                            setResult(100,i);
                            finish();
                        }
                        else{
                            // failed to update
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
            return null;
        }
    };

    /**
     *
     * background for deleteing operation
     *
     */
    class DeleteProduct extends AsyncTask<String,String,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog=new ProgressDialog(EditProductActivity.this);
            pDialog.setMessage("Deleting Product...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            int success;
            try{
                List<NameValuePair> param=new ArrayList<NameValuePair>();
                param.add(new BasicNameValuePair("pid",pid));

                JSONObject json=jsonParser.makeHttpRequest(url_delete_product,"POST",param);

                success=json.getInt(TAG_SUCCESS);
                if(success==1)
                {
                    Intent i=getIntent();
                    setResult(100,i);
                    finish();
                }
                else{

                }
            }
            catch(Exception e){
               e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            //super.onPostExecute(s);
            pDialog.dismiss();
        }
    }

}


