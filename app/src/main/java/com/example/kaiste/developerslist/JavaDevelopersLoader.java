package com.example.kaiste.developerslist;



import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Franklin on 7/11/2017.
 */

public class JavaDevelopersLoader extends AsyncTaskLoader<List<DeveloperObj>> {
    //url to fetch GitHub Lagos developers
    private static String mUrl=null;

    public JavaDevelopersLoader(Context context, String Url){
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
