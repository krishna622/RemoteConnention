package com.kmd.remoterdp.view;

import com.kmd.remoterdp.R;
import com.kmd.remoterdp.utils.Validation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class RegistrationActivity extends Activity implements OnClickListener{

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
			if(checkValidation())
			{
				Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
				startActivity(intent);
			}
			break;

		default:
			break;
		}
	}
	
	private boolean checkValidation()
	{
		boolean valid = true;
		
		if (!Validation.hasText(mName))
			valid = false;
		if (!Validation.hasText(mMobile))
		{
			valid = false;
		}else if (mMobile.getText().toString().length()>10) {
			mMobile.setError("Enter 10 digits number");
			valid = false;
		}
		if (!Validation.isEmailAddress(mEmailId, true))
			valid = false;
		
		return valid;
	}
}
