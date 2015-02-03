package com.kmd.remoterdp.view;

import android.widget.Toast;
import com.kmd.remoterdp.R;
import com.kmd.remoterdp.adapter.MainAdapter;
import com.kmd.remoterdp.network.ServiceResponse;
import com.kmd.remoterdp.utils.FatchContactList;

import android.os.Bundle;
import android.widget.ListView;
import android.app.Activity;


public class MainActivity extends BaseActivity {

	private ListView mList;
	private MainAdapter adapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //FetchContactList.ContactList(MainActivity.this);

        mList = (ListView) findViewById(R.id.list);
        adapter = new MainAdapter(MainActivity.this);
        mList.setAdapter(adapter);


    }

    @Override
    public void updateUi(ServiceResponse response) {

      if(response.getErrorCode() == SUCCESS){
          //ToDo Go to parse data
          switch (response.getAction()){
              case 2:
                  break;
          }
      }else{
          Toast.makeText(this,response.getErrorMsg(),Toast.LENGTH_SHORT).show();
      }
    }
}
