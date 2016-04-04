package com.example.kaii;

import com.google.gson.annotations.SerializedName;

/**
 * Created by RND on 2016-03-30.
 */
public class LoginResult {
    @SerializedName("code")
    String key;
    String site_name;
    int error;
    String error_message;
}
