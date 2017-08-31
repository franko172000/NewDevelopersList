package com.example.kaiste.developerslist;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ProfileActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<DeveloperObj> {
    private ProgressBar progressBar;
    private RelativeLayout relativeLayout;
    private String customUrl;
    private String URL;
    private String getHubUsername;
    @Override
    public Loader<DeveloperObj> onCreateLoader(int id, Bundle args) {

        relativeLayout.setVisibility(View.VISIBLE);

        progressBar.setVisibility(View.VISIBLE);
        
        return new DeveloperProfileLoader(this,URL);
    }

    @Override
    public void onLoadFinished(Loader<DeveloperObj> loader, DeveloperObj data) {
        //get the image view
        ImageView displayPic = (ImageView) findViewById(R.id.profile_pic);
        //download the profile image from the url ad set it on the view
        Picasso.with(this).load(data.getmAvatarUrl()).into(displayPic);
        //get the text view and set the developer's name on it
        TextView nameTxtView = (TextView) findViewById(R.id.name);
        getHubUsername = data.getmUsername();
        nameTxtView.setText(getHubUsername);
        //get the username text view and set the developer's username on it
        TextView usernameTextView = (TextView) findViewById(R.id.username);
        usernameTextView.setText(data.getmUsername());
        //set the url text view and set the developer's page url on it.
        TextView pageUrl = (TextView) findViewById(R.id.pageUrl);
        customUrl = data.getmPageUrl();
        pageUrl.setText(customUrl);

        //remove the loading indicator
        relativeLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<DeveloperObj> loader){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //get the url from the previous activity
        URL = getIntent().getExtras().getString("pageUrl");
        //get the preloader container
        relativeLayout = (RelativeLayout) findViewById(R.id.statusParent);
        //get the progress bar
        progressBar = (ProgressBar) findViewById(R.id.progress);

        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.initLoader(1,null,this);
        //get the container housing the page url
        RelativeLayout urlContainer = (RelativeLayout) findViewById(R.id.urlContainer);
        //set an onclick listener to listen for click event on the container
        urlContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //convert the custom url to a url object
                Uri pageUri = Uri.parse(customUrl);
                //launch the user's browser
                Intent intent = new Intent(Intent.ACTION_VIEW, pageUri);
                startActivity(intent);
            }
        });
        //get the loading button
        FloatingActionButton shareFAB  = (FloatingActionButton) findViewById(R.id.fab);
        //track when the fab button is clicked
        shareFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Checkout this awesome developer! @"+getHubUsername+" "+customUrl);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent,"Share via"));
            }
        });
    }
}
