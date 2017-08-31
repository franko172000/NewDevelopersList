package com.example.kaiste.developerslist;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

/**
 * Created by Franklin on 7/23/2017.
 */

public class DeveloperProfileLoader extends AsyncTaskLoader<DeveloperObj> {
    //url to fetch GitHub Lagos developers
    private static String mUrl=null;

    public DeveloperProfileLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public DeveloperObj loadInBackground() {
        if(mUrl == null){
            return null;
        }

        DeveloperObj developersProfile = DevelopersUtil.DevelopersProfile(mUrl);

        return developersProfile;
    }
}
