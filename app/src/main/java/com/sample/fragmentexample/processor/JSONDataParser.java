package com.sample.fragmentexample.processor;

import android.content.Context;
import android.util.Log;

import com.sample.fragmentexample.bo.Facts;
import com.sample.fragmentexample.bo.FactsDataContainer;
import com.sample.fragmentexample.util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONDataParser {

    private Context mContext;
    private FactsDataContainer mFactsDataContainer = new FactsDataContainer();

    public JSONDataParser(Context context){
        this.mContext = context;
    }

    public void parseJSONData(String responseData){
        try {
             JSONObject jsonObject = new JSONObject(responseData);
            mFactsDataContainer.header = jsonObject.getString(Constants.HEADER_TITLE);
            Log.d("TAG","---Json Data--"+mFactsDataContainer.header);
            JSONArray mJsonArray = (JSONArray) jsonObject.get(Constants.ROWS);
            fetchJSONArrayItems(mJsonArray);
        }catch(JSONException jsonException){
            jsonException.toString();
        }

    }

    private void fetchJSONArrayItems(JSONArray mJsonArray) throws JSONException {
        for(int i=0;i<mJsonArray.length();i++){
            JSONObject mJsonObject = mJsonArray.getJSONObject(i);
            Facts mFacts = new Facts();
            mFacts.setTitle(mJsonObject.get(Constants.TITLE).toString());
            mFacts.setDescription(mJsonObject.get(Constants.DESCRIPTION).toString());
            mFacts.setImageURL(mJsonObject.get(Constants.IMAGE_URL).toString());
            validateDataToAppend(mFacts);
        }
    }

    public FactsDataContainer getmFactsDataContainer(){
        return this.mFactsDataContainer;
    }

    private void validateDataToAppend(Facts mFacts){
        if(!mFacts.getTitle().equals("null") && !mFacts.getDescription().equals("null") && !mFacts.getImageURL().equals("")) {
            mFactsDataContainer.mListOfFacts.add(mFacts);
        }
    }
}
