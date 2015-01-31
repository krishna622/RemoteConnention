package com.kmd.remoterdp.network;

import android.util.Log;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.kmd.remoterdp.utils.StringUtil;

/**
 * Created by FARHAN on 2/1/2015.
 */
public class VolleyRequest extends Request{

    private int action;

    public VolleyRequest(String url, int action,String postData,Response.ErrorListener listener) {
        super(StringUtil.isNullOrEmpty(postData)==true?Method.GET:Method.POST, url, listener);
        this.action = action;
    }

    @Override
    protected Response parseNetworkResponse(NetworkResponse networkResponse) {
        try {

            String jsonString = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
            Log.d("VolleyResponse", jsonString);

            ServiceResponse response = new ServiceResponse();
            response.setAction(action);
            response.setJsonString(jsonString);
            response.setErrorCode(1);

            return Response.success(jsonString, HttpHeaderParser.parseCacheHeaders(networkResponse));

        } catch (Exception e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(Object o) {

    }


}
