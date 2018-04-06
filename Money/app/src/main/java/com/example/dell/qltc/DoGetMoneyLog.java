package com.example.dell.qltc;

import android.icu.text.UnicodeSet;
import android.os.AsyncTask;
import android.util.Log;

import com.example.dell.qltc.datamodel.MoneyLog;
import com.example.dell.qltc.datamodel.UserAccount;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class DoGetMoneyLog extends AsyncTask<String, Void, Integer> {
    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
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

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));

            result = reader.readLine();


            Log.d("test",result);

            JSONArray jsonArray = new JSONArray(result);
            JSONObject jsonObject ;
            LinkedList<MoneyLog> moneyLogs = new LinkedList<>();

            for (int i=0; i<jsonArray.length(); i++){
                jsonObject=jsonArray.getJSONObject(i);
                String[] date = jsonObject.getString("TimeUse").split("T");
                moneyLogs.add(new MoneyLog(jsonObject.getInt("ID"),
                        jsonObject.getString("Owner"),
                        jsonObject.getInt("Amount"),
                        jsonObject.getBoolean("Comes"),
                        jsonObject.getString("UseContent"),
                        jsonObject.getString("Note"),
                        date[0]));
            }

            InstanceGet.moneyLogs.clear();
            for (MoneyLog money: moneyLogs
                 ) {
                InstanceGet.moneyLogs.add(money);
            }

           /* for (UserAccount account: accounts
                    ) {
                Log.d("Test",account.toString());
            }*/

        } catch (IOException e) {
            e.printStackTrace();
            return 400;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 200;
    }
}
