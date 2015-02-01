package com.kmd.remoterdp.model.request;

/**
 * Created by kmd on 2/2/2015.
 */
public class ContactListRequest extends BaseRequest {

    private String userName;
    private String name;
    private String phoneNo;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
