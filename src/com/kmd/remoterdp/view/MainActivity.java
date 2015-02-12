package com.kmd.remoterdp.view;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import com.kmd.remoterdp.R;
import com.kmd.remoterdp.adapter.MainAdapter;
import com.kmd.remoterdp.constants.ActionType;
import com.kmd.remoterdp.constants.RDPConstants;
import com.kmd.remoterdp.model.ServerIpResponse;
import com.kmd.remoterdp.network.ServiceResponse;
import com.kmd.remoterdp.utils.FatchContactList;

import android.os.Bundle;
import android.widget.ListView;
import android.app.Activity;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends BaseActivity {

	private ListView mList;
	private MainAdapter adapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mList = (ListView) findViewById(R.id.list);
        /*adapter = new MainAdapter(MainActivity.this);
        mList.setAdapter(adapter);*/
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this,"UnderDevelopment",Toast.LENGTH_SHORT).show();
            }
        });
        showProgressDialog();
        try{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action","serverlist");
            fetchData(RDPConstants.url,ActionType.SERVER,jsonObject.toString());

        }catch (Exception e){}


    }

    @Override
    public void updateUi(ServiceResponse response) {
        removeProgressDialog();
      if(response.getErrorCode() == SUCCESS){
          //ToDo Go to parse data
          switch (response.getAction()){
              case 2:
                  try {
                      JSONArray jsonArray = new JSONArray(response.getJsonString());
                      ArrayList<ServerIpResponse> serverList = new ArrayList<ServerIpResponse>();
                      if(jsonArray.length()>0)
                      {
                            for (int i=0;i<jsonArray.length();i++)
                            {
                                ServerIpResponse ipResponse = new ServerIpResponse();
                                JSONObject object = jsonArray.getJSONObject(i);
                                ipResponse.setServerid(object.getString("serverid"));
                                ipResponse.setServername(object.getString("servername"));
                                ipResponse.setHost(object.getString("host"));
                                serverList.add(i,ipResponse);
                            }
                          Log.d("server", jsonArray.toString());
                          adapter = new MainAdapter(MainActivity.this,serverList);
                          mList.setAdapter(adapter);
                      }else{
                          //Toast.makeText(MainActivity.this,"Database Empty",Toast.LENGTH_SHORT).show();
                          ServerIpResponse ipResponse = new ServerIpResponse();
                          ipResponse.setServerid("3389");
                          ipResponse.setServername("networkwindow.us");
                          ipResponse.setHost("networkwindow.us");
                          serverList.add(ipResponse);
                          adapter = new MainAdapter(MainActivity.this,serverList);
                          mList.setAdapter(adapter);
                      }
                  }catch(Exception e){}
                  break;
          }
      }else{
          Toast.makeText(this,response.getErrorMsg(),Toast.LENGTH_SHORT).show();
      }
    }
}
