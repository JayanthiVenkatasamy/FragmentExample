package com.sample.fragmentexample.processor;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.gson.Gson;
import com.sample.fragmentexample.util.CommonFunctions;
import com.sample.fragmentexample.util.Constants;


public class DataDownloaderTask extends AsyncTask<Void,Void,String>{
    private Context mContext;
    private String response = "";
    private JSONDataParser jsonDataParser = new JSONDataParser(mContext);

    public DataDownloaderTask(Context context){
       this.mContext = context;
    }
    private ProgressDialog mProgressDialog;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setTitle("Download Sample Data");
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.show();

    }

    @Override
    protected String doInBackground(Void... voids) {
        if(CommonFunctions.getInstance().isConnected(mContext)){
            response = HttpRequestManager.getResponseFromServer();

        }
        else{
            response = Constants.NO_CONNECTIVITY;
        }
        return response;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        mProgressDialog.dismiss();
        if(result.equals(Constants.NO_CONNECTIVITY))
        {
            Intent sendData = new Intent(Constants.ACTION_UPDATE_LIST);
            sendData.putExtra("JSONParser","");
            LocalBroadcastManager.getInstance(mContext).sendBroadcast(sendData);

        }
        else{
        jsonDataParser.parseJSONData(result);
        Gson gson = new Gson();
        String jsonParser = gson.toJson(jsonDataParser);
        Intent sendData = new Intent(Constants.ACTION_UPDATE_LIST);
        sendData.putExtra("JSONParser",jsonParser);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(sendData);
        }
    }

    private JSONDataParser getJsonDataParser(){
       return this.jsonDataParser;
    }
}
