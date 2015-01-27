package com.kmd.remoterdp.persistence;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * This Class will be used to store/fetch
 * data to/from  shared preferences
 * @author krishna
 *
 */
public class CustomSharedPreference {
	
	private static final int PRIVATE_MODE=0; 
	private SharedPreferences sharedPrefernces; 
	private Editor editor;
	private static String PREFERENCENAME = "RDP";
	public CustomSharedPreference(Context context)
	{
		sharedPrefernces=context.getSharedPreferences(PREFERENCENAME, PRIVATE_MODE);
		editor=sharedPrefernces.edit();
	}
	
	/**
	 * remove data
	 * @param key
	 * @param t
	 * @return
	 */
	public void removeValue(String key)
	{
		editor.remove(key);
		editor.commit();
	}
	/**
	 * clear all data
	 * from preference
	 */
	public void clear()
	{
		editor.clear();
		editor.commit();
	}
	
	
	/**
	 * fetch data one by one
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public boolean getBoolean(String key,boolean defaultValue)
	{
		return sharedPrefernces.getBoolean(key, defaultValue);
	}
	
	public String getString(String key,String defaultValue)
	{
		return sharedPrefernces.getString(key, defaultValue);
	}
	public Long getLong(String key,Long defaultValue)
	{
		return sharedPrefernces.getLong(key, defaultValue);
	}
	public Float getFloat(String key,Float defaultValue)
	{
		return sharedPrefernces.getFloat(key, defaultValue);
	}
	/**
	 * save data one by one
	 * @param key
	 * @param value
	 */

	public void putBoolean(String key,boolean value)
	{
		editor.putBoolean(key, value);
		editor.commit();
	}
	public void putString(String key,String value)
	{
		editor.putString(key, value);
		editor.commit();
	}
	public void putLong(String key,Long value)
	{
		editor.putLong(key, value);
		editor.commit();
	}
	public void putFloat(String key,Float value)
	{
		editor.putFloat(key, value);
		editor.commit();
	}
}
