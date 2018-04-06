package com.example.dell.qltc;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class DoPostMoneyLog extends AsyncTask<String,Void,String> {
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected String doInBackground(String... strings) {
        String urlString = strings[0];
        URL url=null;
        HttpURLConnection httpURLConnection =null;
        InputStream inputStream =null;
        OutputStream outputStream=null;

        try{
            url = new URL(urlString);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);


            outputStream= new BufferedOutputStream( httpURLConnection.getOutputStream());

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));


            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Owner",strings[1]);
            jsonObject.put("Amount",strings[2]);
            jsonObject.put("Comes",strings[3]);
            jsonObject.put("UseContent",strings[4]);
            jsonObject.put("Note",strings[5]);
            jsonObject.put("TimeUse",strings[6]);

            Log.d("test",jsonObject.toString());
            writer.write(getPostDataString(jsonObject));
            writer.flush();
            writer.close();
            outputStream.close();
            int responseCode=httpURLConnection.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader in=new BufferedReader(
                        new InputStreamReader(
                                httpURLConnection.getInputStream()));
                StringBuffer sb = new StringBuffer("");
                String line="";

                while((line = in.readLine()) != null) {

                    sb.append(line);
                    break;
                }

                in.close();
                return sb.toString();

            }
            else {
                return new String("false : "+responseCode);
            }
        }
        catch(Exception e){
            return new String("Exception: " + e.getMessage());
        }
    }
    private String getPostDataString(JSONObject params){
        StringBuilder result = new StringBuilder();
        boolean first = true;
        Iterator<String> itr = params.keys();

        while(itr.hasNext()){
            String key= itr.next();
            try {
                Object value = params.get(key);
                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(key, "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(value.toString(), "UTF-8"));
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return result.toString();
    }
}
