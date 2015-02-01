package com.kmd.remoterdp.view;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import com.android.volley.*;
import com.android.volley.toolbox.HttpHeaderParser;
import com.kmd.remoterdp.model.BaseModel;
import com.kmd.remoterdp.network.ServiceResponse;
import com.kmd.remoterdp.network.VolleyHelper;
import com.kmd.remoterdp.network.VolleyRequest;
import com.kmd.remoterdp.utils.StringUtil;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Objects;

/**
 * Created by FARHAN on 2/1/2015.
 */
public class BaseActivity extends Activity implements Response.Listener, Response.ErrorListener{

    public static int SUCCESS = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void fetchData(String url,int action,String postData){
        VolleyRequest request = new VolleyRequest(url,action,postData,this,this);
        VolleyHelper.getInstance(this).addRequestInQueue(request);
    }

    @Override
    public void onResponse(Object o) {
        updateUi((ServiceResponse)o);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        String errorMsg = "Some thing went wrong.";
        if( error instanceof NetworkError) {
            errorMsg = "Network Connection is not available.";
        } else if( error instanceof ServerError) {
            errorMsg = "Server Error";

        } else if( error instanceof AuthFailureError) {
            errorMsg = "Auth Error";
        } else if( error instanceof ParseError) {
            errorMsg = "Parsing Error";
        } else if( error instanceof NoConnectionError) {

            errorMsg = "No connection could be established.";

        } else if( error instanceof TimeoutError) {
            errorMsg = "Network Connection Time out";
        }

        ServiceResponse response = new ServiceResponse();
        response.setErrorCode(0);
        response.setErrorMsg(errorMsg);

        updateUi(response);
    }



    public void updateUi(ServiceResponse response){

    }


    private ProgressDialog mProgressDialog;

    /**
     * Shows a simple native progress dialog<br/>
     * Subclass can override below two methods for custom dialogs- <br/>
     * 1. showProgressDialog <br/>
     * 2. removeProgressDialog
     *
     * @param bodyText
     */
    public void showProgressDialog(String bodyText) {
        if (isFinishing()) {
            return;
        }
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(BaseActivity.this);
            mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setOnKeyListener(new Dialog.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_CAMERA || keyCode == KeyEvent.KEYCODE_SEARCH) {
                        return true; //
                    }
                    return false;
                }
            });
        }

        mProgressDialog.setMessage(bodyText);

        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    /**
     * Removes the simple native progress dialog shown via showProgressDialog <br/>
     * Subclass can override below two methods for custom dialogs- <br/>
     * 1. showProgressDialog <br/>
     * 2. removeProgressDialog
     */
    public void removeProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * Utility function for displaying progress dialog
     *
     */

    public void showProgressDialog() {

        showProgressDialog("Loading...");
    }


}
