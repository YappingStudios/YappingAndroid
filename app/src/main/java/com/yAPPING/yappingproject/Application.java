package com.yAPPING.yappingproject;

import com.parse.Parse;

/**
 * Created by srikar on 6/9/15.
 */
public class Application extends android.app.Application {
    //ParseUser user;

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "VZzoxKVdjKdijSI6kMdbstFBeCdAwFLgG7OdUBws", "oMD4QFWaYn5sjZF5kYMb60F8vJ9uvgvkr9aGPhjV");



    }
}
