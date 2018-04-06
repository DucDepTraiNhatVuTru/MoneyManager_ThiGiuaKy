package com.example.dell.qltc;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.dell.qltc.datamodel.UserAccount;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DoGet extends AsyncTask<String, Void, Integer> {

    public static int ketqua=0;
    public static int soLuongKetQuaTraVe=0;


    @Override
    protected void onPostExecute(Integer integer) {
        //super.onPostExecute(integer);

        if(integer==500){
            Log.d("test","false");
        }else if(integer==200){
            Log.d("test","true");
        }
    }

    @Override
    protected Integer doInBackground(String... strings) {
        String urlString = strings[0];
        URL url=null;
        HttpURLConnection httpURLConnection =null;
        InputStream inputStream =null;
        String result="" ;
        int c;
        try {
            url = new URL(urlString);
            httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoInput(true);

            httpURLConnection.connect();

            inputStream = httpURLConnection.getInputStream();

            InstanceGet.responseCode=httpURLConnection.getResponseCode();
            while ((c=inputStream.read())!=-1){
                result+=(char)c;
            }

            Log.d("test",result);

            JSONArray jsonArray = new JSONArray(result);
            JSONObject jsonObject ;
            List<UserAccount> accounts = new ArrayList<>();

            InstanceGet.soLuongKetQua = jsonArray.length();
            for (int i=0; i<jsonArray.length(); i++){
                jsonObject=jsonArray.getJSONObject(i);

                accounts.add(new UserAccount(jsonObject.getString("Email"),jsonObject.getString("Password"),jsonObject.getString("TimeCreate")));
            }

            for (UserAccount account: accounts
                 ) {
                Log.d("Test",account.toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
            return 400;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 200;
    }

}
