package com.sample.fragmentexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.sample.fragmentexample.adapter.DividerItemDecoration;
import com.sample.fragmentexample.adapter.ListPageViewAdapter;
import com.sample.fragmentexample.processor.DataDownloaderTask;
import com.sample.fragmentexample.processor.JSONDataParser;
import com.sample.fragmentexample.util.Constants;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnUpdateToolBarListener} interface
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class ListPageFragment extends Fragment   {

    public static final String TAG = "ListPageFragment";
    private OnUpdateToolBarListener onUpdateToolBarListener;
    private RecyclerView mMyRecyclerView;
    private ListPageViewAdapter mMyRecyclerViewAdapter;
    private JSONDataParser mJsonDataParser ;
    private Context mContext;
    private boolean isImageDownloaderStarted = false;
    private List<String> mImageUrlList;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        if (context instanceof OnUpdateToolBarListener) {
            onUpdateToolBarListener = (OnUpdateToolBarListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListPageItemClickListeners");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // addDataToList();
        registerReceiverToUpdateView();


    }

    private BroadcastReceiver mUpdateViewReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(Constants.ACTION_UPDATE_LIST)) {
                Bundle data = intent.getExtras();
                String jsonParserdata = data.getString("JSONParser");
                if(jsonParserdata.equals("")){
                    mMyRecyclerViewAdapter.setJsonDataParser(null);
                }
                else{
                    Gson gson = new Gson();
                    mJsonDataParser = gson.fromJson(jsonParserdata,JSONDataParser.class);
                    onUpdateToolBarListener.onUpdateToolBarTitle(mJsonDataParser.getmFactsDataContainer().header);
                }
                updateView();
            }
        }
    };


    private void registerReceiverToUpdateView() {
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mUpdateViewReceiver,new IntentFilter(Constants.ACTION_UPDATE_LIST));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.list_page, container, false);
        mMyRecyclerView = (RecyclerView)view.findViewById(R.id.my_recyclerview);
        mMyRecyclerViewAdapter = new ListPageViewAdapter(mJsonDataParser,getActivity());
        mMyRecyclerView.setAdapter(mMyRecyclerViewAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mMyRecyclerView.setLayoutManager(mLayoutManager);
        mMyRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));
        mMyRecyclerViewAdapter.notifyDataSetChanged();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("TAG","---onResume ListFragent---");
        downloadData();
    }

    public void downloadData() {
        DataDownloaderTask mDownloaderTask = new DataDownloaderTask(getActivity());
        mDownloaderTask.execute();
    }

    public void updateView() {
        mMyRecyclerViewAdapter.setJsonDataParser(mJsonDataParser);
        mMyRecyclerViewAdapter.notifyDataSetChanged();
    }

    public interface OnUpdateToolBarListener {
        // TODO: Update argument type and name
        void onUpdateToolBarTitle(String data);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver();
    }

    public void unregisterReceiver(){
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mUpdateViewReceiver);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onUpdateToolBarListener = null;
    }
}
