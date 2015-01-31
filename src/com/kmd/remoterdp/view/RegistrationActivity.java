package com.kmd.remoterdp.view;

import android.provider.Settings;
import android.widget.Toast;
import com.kmd.remoterdp.R;
import com.kmd.remoterdp.network.ServiceResponse;
import com.kmd.remoterdp.utils.Validation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class RegistrationActivity extends BaseActivity implements OnClickListener{

	private EditText mName, mEmailId, mMobile;
	private Button mRegister;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		
		mEmailId = (EditText) findViewById(R.id.edt_registration_email_id);
		mMobile = (EditText) findViewById(R.id.edt_registration_mobile_numbe);
		mName = (EditText) findViewById(R.id.edt_registration_name);
		mRegister = (Button) findViewById(R.id.btn_register);
		
		mRegister.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_register:
            showProgressDialog();
			fetchData("vdsfds",3,"jfh");
			//if(checkValidation())
			//{
				Toast.makeText(this,getDeviceId(),Toast.LENGTH_LONG).show();
//				Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
//				startActivity(intent);
			//}
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
			//ToDo Go to poarse data
			switch (response.getAction()){
				case 1:
					break;
			}
		}else{
			Toast.makeText(this,response.getErrorMsg(),Toast.LENGTH_SHORT).show();
		}
	}
}
