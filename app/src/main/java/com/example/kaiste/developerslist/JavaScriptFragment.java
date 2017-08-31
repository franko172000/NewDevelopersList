package com.example.kaiste.developerslist;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class JavaScriptFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<DeveloperObj>> {

    private DevelopersAdapter mAdapter;
    private View rootView;
    private RelativeLayout relativeLayout;

    public JavaScriptFragment() {
        // Required empty public constructor
    }

    /**
     * Create a log name for debugging purpose
     */
    private final static String LOG_TAG  = JavaScriptFragment.class.getSimpleName();

    @Override
    public Loader<List<DeveloperObj>> onCreateLoader(int id, Bundle args) {

        //set language to fetch its developers
        //DevelopersUtil.setmLanguage("javascript");
        //get url
        String GITHUB_URL = DevelopersUtil.getJsonUrl("javascript",1);

        relativeLayout = (RelativeLayout) rootView.findViewById(R.id.statusParent);

        relativeLayout.setVisibility(View.VISIBLE);

        DevelopersUtil.ControlProgressBar(rootView,"show");

        return new JavascriptLoader(getContext(),GITHUB_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<DeveloperObj>> loader, List<DeveloperObj> data) {
        mAdapter.clear();
        if(data != null && !data.isEmpty()){
            mAdapter.addAll(data);

            relativeLayout.setVisibility(View.GONE);
        }
        // DevelopersUtil.ControlProgressBar(rootView,"hide");
        Log.v(LOG_TAG,""+data);
    }

    @Override
    public void onLoaderReset(Loader<List<DeveloperObj>> loader) {
        //mAdapter.clear();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_javascript,container,false);
        //get the list of states from the text file
        //ArrayList<DeveloperObj> developerList = DevelopersUtil.getStates(getContext());
        //populate the adapter
        // DevelopersAdapter developersAdapter = new DevelopersAdapter(getActivity(),developerList);
        //remove the preloader
        // DevelopersUtil.ControlProgressBar(rootView,"hide");

        LoaderManager loaderManager = getLoaderManager();

        loaderManager.initLoader(3,null,this);

        mAdapter = new DevelopersAdapter(getContext(),new ArrayList<DeveloperObj>());

        ListView devView = (ListView) rootView.findViewById(R.id.javascriptListItem);

        devView.setAdapter(mAdapter);

        //set onclick listener for the clicked list item
        devView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //find the current dveloper information that was clicked
                DeveloperObj developer = mAdapter.getItem(position);
                //get the developer's page api url
                String pageUrl = developer.getmPageUrl();
                //create a new intent
                Intent intent = new Intent(getContext(),ProfileActivity.class);
                //pass the selected developer's url to the profile page
                intent.putExtra("pageUrl",pageUrl);
                //start the profile activity
                startActivity(intent);
            }
        });

        return rootView;
    }

}
