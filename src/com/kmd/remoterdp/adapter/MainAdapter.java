package com.kmd.remoterdp.adapter;

import android.widget.TextView;
import com.kmd.remoterdp.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.kmd.remoterdp.model.ServerIpResponse;

import java.util.ArrayList;

public class MainAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<ServerIpResponse> ipList;
	
	public MainAdapter(Context context,ArrayList<ServerIpResponse> ipList)
	{
		this.context = context;
		this.ipList = ipList;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(ipList!=null&&ipList.size()>0)
			return ipList.size();
		return 0;
	}

	@Override
	public ServerIpResponse getItem(int position) {
		// TODO Auto-generated method stub
		if(ipList!=null&&ipList.size()>0)
			return ipList.get(position);
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.adapter_main, parent, false);
		TextView txt_server_id =(TextView)convertView.findViewById(R.id.txt_server_id);
		TextView txt_server_name =(TextView)convertView.findViewById(R.id.txt_server_name);
		TextView txt_host =(TextView)convertView.findViewById(R.id.txt_host);

		ServerIpResponse ipResponse = getItem(position);
		txt_host.setText(ipResponse.getHost());
		txt_server_id.setText(ipResponse.getServerid());
		txt_server_name.setText("@"+ipResponse.getServername());

		return convertView;
	}

}
