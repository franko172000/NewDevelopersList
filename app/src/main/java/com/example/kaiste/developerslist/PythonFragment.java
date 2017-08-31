package com.example.kaiste.developerslist;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PythonFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<DeveloperObj>>{

    private DevelopersAdapter mAdapter;
    private View rootView;
    private RelativeLayout relativeLayout;
    private int loaderID = 4;
    private int trackLoaderId=0;
    public PythonFragment() {
        // Required empty public constructor
    }

    /**
     * Create a log name for debugging purpose
     */
    private final static String LOG_TAG  = PythonFragment.class.getSimpleName();

    @Override
    public Loader<List<DeveloperObj>> onCreateLoader(int id, Bundle args) {
        trackLoaderId = id;
        //set language to fetch its developers
        //DevelopersUtil.setmLanguage("python");

        String GITHUB_URL = DevelopersUtil.getJsonUrl("python",2);

        relativeLayout = (RelativeLayout) rootView.findViewById(R.id.statusParent);

        relativeLayout.setVisibility(View.VISIBLE);

        DevelopersUtil.ControlProgressBar(rootView,"show");

        return new JavaDevelopersLoader(getContext(),GITHUB_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<DeveloperObj>> loader, List<DeveloperObj> data) {
        if(trackLoaderId == loaderID){
            mAdapter.clear();
            if(data != null && !data.isEmpty()){
                mAdapter.addAll(data);

                relativeLayout.setVisibility(View.GONE);
            }
            // DevelopersUtil.ControlProgressBar(rootView,"hide");
            //Log.v(LOG_TAG,""+data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<DeveloperObj>> loader) {
        mAdapter.clear();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_python,container,false);
        //get the list of states from the text file
        //ArrayList<DeveloperObj> developerList = DevelopersUtil.getStates(getContext());
        //populate the adapter
        // DevelopersAdapter developersAdapter = new DevelopersAdapter(getActivity(),developerList);
        //remove the preloader
        // DevelopersUtil.ControlProgressBar(rootView,"hide");

        LoaderManager loaderManager = getLoaderManager();

        loaderManager.initLoader(loaderID,null,this);

        mAdapter = new DevelopersAdapter(getContext(),new ArrayList<DeveloperObj>());

        ListView devView = (ListView) rootView.findViewById(R.id.pythonListItem);

        devView.setAdapter(mAdapter);

        return rootView;
    }

}
