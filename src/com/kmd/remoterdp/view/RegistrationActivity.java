package com.kmd.remoterdp.view;

import android.content.Intent;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;
import com.kmd.remoterdp.R;
import com.kmd.remoterdp.constants.ActionType;
import com.kmd.remoterdp.constants.RDPConstants;
import com.kmd.remoterdp.constants.WebAction;
import com.kmd.remoterdp.model.Contact;
import com.kmd.remoterdp.model.request.RegistrationRequest;
import com.kmd.remoterdp.network.ServiceResponse;
import com.kmd.remoterdp.persistence.CustomSharedPreference;
import com.kmd.remoterdp.utils.FatchContactList;
import com.kmd.remoterdp.utils.Validation;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class RegistrationActivity extends BaseActivity implements OnClickListener{

	private EditText mName, mEmailId, mMobile;
	private Button mRegister;
	private RegistrationRequest registrationRequest;
	private CustomSharedPreference mPref;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		
		mEmailId = (EditText) findViewById(R.id.edt_registration_email_id);
		mMobile = (EditText) findViewById(R.id.edt_registration_mobile_numbe);
		mName = (EditText) findViewById(R.id.edt_registration_name);
		mRegister = (Button) findViewById(R.id.btn_register);

		mPref = new CustomSharedPreference(RegistrationActivity.this);
		if(mPref.getString(RDPConstants.registrationStatus,"null")!=null &&
				mPref.getString(RDPConstants.registrationStatus,"null").equals("1"))
		{
			if(mPref.getString(RDPConstants.contactStatus,"null")!=null &&
					mPref.getString(RDPConstants.contactStatus,"null").equals("2"))
			{
				Intent intent = new Intent(RegistrationActivity.this,MainActivity.class);
				startActivity(intent);
				finish();
			}else{
				showProgressDialog();
				ArrayList<Contact> mContacts = FatchContactList.ContactList(RegistrationActivity.this);
				try {
					JSONObject obj = new JSONObject();
					obj.put("action","contactlist");
					obj.put("username",mPref.getString(RDPConstants.userName,"null"));
					JSONArray arr = new JSONArray();
					for (int i=0; i<mContacts.size(); i++)
					{
						JSONObject data = new JSONObject();
						data.put("name",mContacts.get(i).getName());
						data.put("phoneno",mContacts.get(i).getNumber());
						arr.put(i,data);
					}
					obj.put("contactlist",arr);

					//removeProgressDialog();
					Log.d("ContactList",obj.toString());
					fetchData(RDPConstants.url, ActionType.CONTACT, obj.toString());
				}catch (Exception e){}
			}
		}

		mRegister.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_register:
			if(checkValidation())
			{
				registrationRequest = new RegistrationRequest();
				registrationRequest.setEmail(mEmailId.getText().toString().trim());
				registrationRequest.setPhone_no(mMobile.getText().toString().trim());
				registrationRequest.setUserName(mName.getText().toString().trim());
				String deviceId = getDeviceId();
				if(deviceId==null || deviceId.contains("??"))
				{
					registrationRequest.setDevice_id(mEmailId.getText().toString());
				}else{
					registrationRequest.setDevice_id(deviceId);
				}
				registrationRequest.setAction(WebAction.regAction);
            showProgressDialog();
			JSONObject regJson = new JSONObject();
			try {

				regJson.put("action", registrationRequest.getAction());
				regJson.put("username", registrationRequest.getUserName());
				regJson.put("email", registrationRequest.getEmail());
				regJson.put("deviceid", registrationRequest.getDevice_id());
				regJson.put("phoneno", registrationRequest.getPhone_no());
			}catch (Exception e)
			{
			}
			Log.d("regJson",regJson.toString());
			fetchData(RDPConstants.url, ActionType.REGISTRATION, regJson.toString());
			}
			break;

		default:
			break;
		}
	}

	/**
	 * check field validation
	 * @return
	 */
	private boolean checkValidation()
	{
		boolean valid = true;
		
		if (!Validation.hasText(mName))
			valid = false;
		if (!Validation.isPhoneNumber(mMobile, true))
			valid = false;
		if (!Validation.isEmailAddress(mEmailId, true))
			valid = false;
		
		return valid;
	}

	/**
	 * get device id
	 * @return
	 */
	private String getDeviceId()
	{
		String device_id = null;
			device_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
		return device_id;
	}

	@Override
	public void updateUi(ServiceResponse response) {
        removeProgressDialog();
		if(response.getErrorCode() == SUCCESS)
		{

				switch (response.getAction())
				{
					case 0:
						Log.d("KrishnaReg", response.getJsonString());
						try
						{
							JSONObject reader = new JSONObject(response.getJsonString());
							int isSaved = Integer.parseInt(reader.getString("isSaved"));
							switch (isSaved)
							{
								case 0:
									Toast.makeText(this, "data missing", Toast.LENGTH_SHORT).show();
								break;
								case 1:
										mPref.putString(RDPConstants.userName,registrationRequest.getUserName());
										mPref.putString(RDPConstants.registrationStatus,"1");
										showProgressDialog();
										ArrayList<Contact> mContacts = FatchContactList.ContactList(RegistrationActivity.this);
									try {
										JSONObject obj = new JSONObject();
										obj.put("action","contactlist");
										obj.put("username",registrationRequest.getUserName());
										JSONArray arr = new JSONArray();
										for (int i=0; i<mContacts.size(); i++)
										{
											JSONObject data = new JSONObject();
											data.put("name",mContacts.get(i).getName());
											data.put("phoneno",mContacts.get(i).getNumber());
											arr.put(i,data);
										}
										obj.put("contactlist",arr);

										//removeProgressDialog();
										Log.d("ContactList",obj.toString());
										fetchData(RDPConstants.url, ActionType.CONTACT, obj.toString());
									}catch (Exception e){}
								break;
								case 2:
									mPref.putString(RDPConstants.registrationStatus,"1");
									mPref.putString(RDPConstants.contactStatus,"2");
									//Toast.makeText(this, "Your data has been updated", Toast.LENGTH_SHORT).show();
									Intent intentHome = new Intent(RegistrationActivity.this,MainActivity.class);
									startActivity(intentHome);
									finish();
								break;
							}
						}
						catch (Exception e)
						{
						}
						break;
						case 1:
							Log.d("KrishnaContactList", response.getJsonString());
							try {
								mPref.putString(RDPConstants.contactStatus,"2");
								JSONObject resJson = new JSONObject(response.getJsonString());
								boolean isSaved = Boolean.parseBoolean(resJson.getString("isSaved"));
								if(isSaved)
								{
									Toast.makeText(this, "Contact saved successfully", Toast.LENGTH_SHORT).show();
									Intent intentHome = new Intent(RegistrationActivity.this,MainActivity.class);
									startActivity(intentHome);
									finish();
								}else{
									Toast.makeText(this, "Contact does not saved", Toast.LENGTH_SHORT).show();
								}
							}catch(Exception e){}
							break;
				}
			}
		else{
			Toast.makeText(this,response.getErrorMsg(),Toast.LENGTH_SHORT).show();
		}
	}
}
