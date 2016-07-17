package com.sample.fragmentexample;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class BaseActivity extends AppCompatActivity  implements ListPageFragment.OnUpdateToolBarListener,DetailPageFragment.OnDetailContentListener {
    private boolean isLandscape;
    private Toolbar toolbar;
    private static final String TAG="BaseActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity);
        isLandscape = getResources().getBoolean(R.bool.landscape);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(" Sample App ");
        setSupportActionBar(toolbar);
        initialize(savedInstanceState);

    }

    private void initialize(Bundle savedInstanceState) {
        if(savedInstanceState==null)
        {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            ListPageFragment fragment1 = new ListPageFragment();
            fragmentTransaction.add(R.id.listpage_fragment,fragment1, ListPageFragment.TAG);
           // fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
        if(isLandscape){
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            DetailPageFragment fragment2 = new DetailPageFragment();
            fragmentTransaction.add(R.id.detailpage_fragment,fragment2, DetailPageFragment.TAG);
           // fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_base, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.refresh) {
            ListPageFragment fragment1 = new ListPageFragment();
            if(fragment1.isInLayout())
                 fragment1.downloadData();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDetailContentClickEvent(String data) {

    }

    @Override
    public void onUpdateToolBarTitle(String data) {
        toolbar.setTitle(data);
    }
}
