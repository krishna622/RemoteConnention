package com.kmd.remoterdp.view;

import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;
import com.kmd.remoterdp.R;
import com.kmd.remoterdp.constants.ActionType;
import com.kmd.remoterdp.constants.RDPConstants;
import com.kmd.remoterdp.constants.WebAction;
import com.kmd.remoterdp.model.request.RegistrationRequest;
import com.kmd.remoterdp.network.ServiceResponse;
import com.kmd.remoterdp.utils.Validation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import org.json.JSONObject;

public class RegistrationActivity extends BaseActivity implements OnClickListener{

	private EditText mName, mEmailId, mMobile;
	private Button mRegister;
	private RegistrationRequest registrationRequest;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		
		mEmailId = (EditText) findViewById(R.id.edt_registration_email_id);
		mMobile = (EditText) findViewById(R.id.edt_registration_mobile_numbe);
		mName = (EditText) findViewById(R.id.edt_registration_name);
		mRegister = (Button) findViewById(R.id.btn_register);

		registrationRequest = new RegistrationRequest();
		registrationRequest.setEmail(mEmailId.getText().toString());
		registrationRequest.setPhone_no(mMobile.getText().toString());
		registrationRequest.setUserName(mName.getText().toString());
		registrationRequest.setDevice_id(getDeviceId());
		registrationRequest.setAction(WebAction.regAction);

		
		mRegister.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_register:
			if(checkValidation())
			{
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
		if(response.getErrorCode() == SUCCESS){

			switch (response.getAction()){
				case 0:
					Log.d("KrishnaReg",response.getJsonString());
					break;
				case 1:
					Log.d("KrishnaContactList",response.getJsonString());
					break;
			}
		}else{
			Toast.makeText(this,response.getErrorMsg(),Toast.LENGTH_SHORT).show();
		}
	}
}
