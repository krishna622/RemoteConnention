package com.kmd.remoterdp.view;

import com.kmd.remoterdp.R;
import com.kmd.remoterdp.adapter.MainAdapter;
import com.kmd.remoterdp.utils.FatchContactList;

import android.os.Bundle;
import android.widget.ListView;
import android.app.Activity;


public class MainActivity extends Activity {

	private ListView mlist;
	private MainAdapter adapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        FatchContactList.ContactList(MainActivity.this);
        
        mlist = (ListView) findViewById(R.id.list);
        adapter = new MainAdapter(MainActivity.this);
        mlist.setAdapter(adapter);
    }
    
}
