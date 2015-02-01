package com.kmd.remoterdp.model.request;

/**
 * Created by kmd on 2/1/2015.
 */
public class RegistrationRequest extends BaseRequest{

    private String userName;
    private String email;
    private String device_id;
    private String phone_no;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
