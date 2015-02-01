package com.kmd.remoterdp.network;

import android.util.Log;
import com.android.volley.*;
import com.android.volley.toolbox.HttpHeaderParser;
import com.kmd.remoterdp.utils.StringUtil;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by FARHAN on 2/1/2015.
 */
public class VolleyRequest extends Request<ServiceResponse>{

    private int action;
    private String postData;
    private int contentType = ContentType.JSON;

    public interface ContentType {

        int PLAIN_TEXT = 0;
        int FORM_ENCODED_DATA = 1;
        int JSON = 2;
    }

    /**
     * Charset for request.
     */
    private static final String PROTOCOL_CHARSET = "utf-8";

    /**
     * Content type for request.
     */
    private static final String PROTOCOL_CONTENT_TYPE =
            String.format("application/json; charset=%s", PROTOCOL_CHARSET);

    private Response.ErrorListener errorListener;
    private Response.Listener successListener;

    public VolleyRequest(String url, int action,String postData,Response.ErrorListener errorListener,Response.Listener successListener) {
        super(StringUtil.isNullOrEmpty(postData)==true?Method.GET:Method.POST, url, errorListener);
        this.action = action;
        this.postData = postData;
        this.successListener = successListener;
        this.errorListener = errorListener;

    }

    public void setContentType(int contentType){
        this.contentType = contentType;
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
            successListener.onResponse(response);
            return null;

        } catch (Exception e) {
            Log.e("Response",e.getMessage());
            errorListener.onErrorResponse(new ParseError(e));
            return null;
        }
    }

    @Override
    protected void deliverResponse(ServiceResponse serviceResponse) {

    }


    /**
     * @deprecated Use {@link #getBodyContentType()}.
     */
    @Override
    public String getPostBodyContentType() {
        return getBodyContentType();
    }

    /**
     * @deprecated Use {@link #getBody()}.
     */
    @Override
    public byte[] getPostBody() throws AuthFailureError {
        return getBody();
    }

    @Override
    public String getBodyContentType() {
        switch (contentType) {
            case ContentType.JSON:
                return PROTOCOL_CONTENT_TYPE;
            default:
                return super.getBodyContentType();
        }

    }

    @Override
    public byte[] getBody() throws AuthFailureError {

        try {
            switch (contentType) {
                case ContentType.JSON: {
                    return null == postData ? null : postData.getBytes(PROTOCOL_CHARSET);
                }
                case ContentType.PLAIN_TEXT:
                    return null == postData ? null : postData.getBytes();
                case ContentType.FORM_ENCODED_DATA:
                    return super.getBody();
            }
        } catch (UnsupportedEncodingException uee) {
            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                    postData, PROTOCOL_CHARSET);

        }
        return null;
    }

}
