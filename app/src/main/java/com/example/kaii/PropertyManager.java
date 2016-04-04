package com.example.kaii;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
/**
 * Created by RND on 2016-03-30.
 */
public class PropertyManager {
    private static PropertyManager instance;
    public static PropertyManager getInstance() {
        if (instance == null) {
            instance = new PropertyManager();
        }
        return instance;
    }

    SharedPreferences mPrefs;
    SharedPreferences.Editor mEditor;

    private PropertyManager() {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
        mEditor = mPrefs.edit();
    }

    private static final String REG_KEY = "key";
    public void setKey(String key){
        mEditor.putString(REG_KEY,key);
        mEditor.commit();
    }
    public String getKey(){
        return mPrefs.getString(REG_KEY,"");
    }
}
