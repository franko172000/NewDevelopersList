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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhpFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<DeveloperObj>> {
    private DevelopersAdapter mAdapter;
    private View rootView;
    private RelativeLayout relativeLayout;
    private LinearLayout internetConStatusContainer;
    private View footer = null;
    private LoaderManager  loaderManager;
    private ListView devView;
    private int mPage = 1;
    private String conMsg ;

    public PhpFragment() {
        // Required empty public constructor
    }

    /**
     * Create a log name for debugging purpose
     */
    private final static String LOG_TAG  = PhpFragment.class.getSimpleName();

    @Override
    public Loader<List<DeveloperObj>> onCreateLoader(int id, Bundle args) {
        //set language to fetch its developers
        //DevelopersUtil.setmLanguage("php");
        //get url
        String GITHUB_URL = DevelopersUtil.getJsonUrl("php",1);

        if(AppStatus.getInstance(getContext()).isOnline()){
            DevelopersUtil.ControlNoInternetMsg(rootView,"hide");
            if(mPage == 1){

                relativeLayout.setVisibility(View.VISIBLE);

                DevelopersUtil.ControlProgressBar(rootView,"show");
            }
        }else{
            Toast.makeText(getContext(),conMsg,Toast.LENGTH_SHORT).show();
            relativeLayout.setVisibility(View.VISIBLE);
            DevelopersUtil.ControlNoInternetMsg(rootView,"show");
            DevelopersUtil.ControlProgressBar(rootView,"hide");
        }

        return new PhpLoader(getContext(),GITHUB_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<DeveloperObj>> loader, List<DeveloperObj> data) {
        //mAdapter.clear();
        if(data != null && !data.isEmpty()){
            mAdapter.addAll(data);
            if(footer != null){
                //devView.removeFooterView(footer);
                footer.setVisibility(View.GONE);
                mAdapter.notifyDataSetChanged();
            }
            relativeLayout.setVisibility(View.GONE);
        }
        DevelopersUtil.ControlProgressBar(rootView,"hide");
        Log.v(LOG_TAG,""+data);
    }

    @Override
    public void onLoaderReset(Loader<List<DeveloperObj>> loader) {
        //mAdapter.clear();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_php,container,false);
        conMsg = getResources().getString(R.string.no_connection_msg);
        //create loader manager
        loaderManager = getLoaderManager();
        //layout for the app status indicator
        relativeLayout = (RelativeLayout) rootView.findViewById(R.id.statusParent);
        //internet connection layout
        internetConStatusContainer = (LinearLayout) rootView.findViewById(R.id.internetContainer);
        //check if internet is active
        if(AppStatus.getInstance(getContext()).isOnline()){
            DevelopersUtil.ControlNoInternetMsg(rootView,"hide");
            loaderManager.initLoader(1,null,this);
        }else{
            Toast.makeText(getContext(),conMsg,Toast.LENGTH_SHORT).show();
            relativeLayout.setVisibility(View.VISIBLE);
            DevelopersUtil.ControlNoInternetMsg(rootView,"show");
            DevelopersUtil.ControlProgressBar(rootView,"hide");
        }

        mAdapter = new DevelopersAdapter(getContext(),new ArrayList<DeveloperObj>());

        final ListView devView = (ListView) rootView.findViewById(R.id.phpListItem);

        devView.setAdapter(mAdapter);

        footer = LayoutInflater.from(getContext()).inflate(R.layout.footer_loader,null);

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
                mPage = 1;
                //start the profile activity
                startActivity(intent);
            }
        });

        devView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState ==AbsListView.OnScrollListener.SCROLL_STATE_IDLE && (devView.getLastVisiblePosition() - devView.getHeaderViewsCount()) >= mAdapter.getCount() - 1){
                    footer.setVisibility(View.VISIBLE);
                    devView.addFooterView(footer,null,false);
                    mPage++;
                    DevelopersUtil.setmPage(mPage);
                    //loaderManager.getLoader(1).onContentChanged();
                    loaderManager.restartLoader(1,null,PhpFragment.this);
                }
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        //retry loading again
        internetConStatusContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loaderManager.restartLoader(1,null,PhpFragment.this);
            }
        });

        return rootView;
    }
}
