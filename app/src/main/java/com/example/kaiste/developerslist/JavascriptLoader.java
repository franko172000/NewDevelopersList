package com.example.kaiste.developerslist;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

/**
 * Created by Franklin on 7/30/2017.
 */

public class JavascriptLoader extends AsyncTaskLoader<List<DeveloperObj>> {
    //url to fetch GitHub Lagos developers
    private static String mUrl=null;

    public JavascriptLoader(Context context, String Url){
        super(context);
        mUrl = Url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<DeveloperObj> loadInBackground() {
        if(mUrl == null){
            return null;
        }

        List<DeveloperObj> developerObjs = DevelopersUtil.DevelopersList(mUrl);

        return developerObjs;
    }
}
