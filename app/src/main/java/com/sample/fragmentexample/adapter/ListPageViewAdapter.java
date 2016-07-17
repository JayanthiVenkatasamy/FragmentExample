package com.sample.fragmentexample.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sample.fragmentexample.R;
import com.sample.fragmentexample.processor.ImageDownLoader;
import com.sample.fragmentexample.processor.JSONDataParser;

public class ListPageViewAdapter extends RecyclerView.Adapter<ListPageViewAdapter.ListPageRecyclerViewHolder> {
    private JSONDataParser mJsonDataParser;
    private ListPageRecyclerViewHolder mViewHolder;
    private ImageDownLoader imageDownLoader;
    private Context mContext;

    public ListPageViewAdapter( JSONDataParser mJsonDataParser,Context mContext){
        imageDownLoader = new ImageDownLoader(mContext);
        this.mJsonDataParser = mJsonDataParser;
        this.mContext = mContext;
    }

    @Override
    public ListPageRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_page_recyclerview_content,null);
        mViewHolder = new ListPageRecyclerViewHolder(view);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(ListPageRecyclerViewHolder holder, int position) {
        Log.d("TAG","---onBindHolder--");
        if(mJsonDataParser !=null) {
            Log.d("TAG","---onBindHolder-- IF");
            String title = mJsonDataParser.getmFactsDataContainer().mListOfFacts.get(position).getTitle();
            String content = mJsonDataParser.getmFactsDataContainer().mListOfFacts.get(position).getDescription();
            String imageURL = mJsonDataParser.getmFactsDataContainer().mListOfFacts.get(position).getImageURL();
            holder.mHeader.setText(title);
            holder.mContent.setText(content);
            imageDownLoader.DisplayImage(imageURL, holder.imageIcon);

        }
        else{
            holder.mHeader.setText("");
            holder.mContent.setText("Check connectivity");
            holder.imageIcon.setImageResource(R.mipmap.ic_launcher);
        }

    }

    @Override
    public int getItemCount() {
        if(mJsonDataParser !=null)
             return mJsonDataParser.getmFactsDataContainer().mListOfFacts.size();
        else
            return  1;
    }

    public class ListPageRecyclerViewHolder extends RecyclerView.ViewHolder{
        public TextView mHeader;
        public TextView mContent;
        public ImageView imageIcon;

        public ListPageRecyclerViewHolder(View itemView) {
            super(itemView);
            this.mHeader = (TextView)itemView.findViewById(R.id.txt_heading);
            this.mContent = (TextView)itemView.findViewById(R.id.txt_content);
            this.imageIcon = (ImageView)itemView.findViewById(R.id.image_photo);
        }
    }

    public void setJsonDataParser(JSONDataParser mJSOJsonDataParser){
        this.mJsonDataParser = mJSOJsonDataParser;

    }

    public  void setImage(Bitmap bitmap, String tag){
        if(mViewHolder!=null){
           if(mViewHolder.imageIcon.getTag().equals(tag)){
               mViewHolder.imageIcon.setImageBitmap(bitmap);
           }
        }
    }
}
