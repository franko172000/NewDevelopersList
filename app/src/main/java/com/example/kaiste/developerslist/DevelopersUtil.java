package com.example.kaiste.developerslist;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anyaso Chibugo Franklin
 * Utility class for general functions on the project
 */

public class DevelopersUtil {
    /**
     * Create a log name for debugging purpose
     */
    private final static String LOG_TAG  = DevelopersUtil.class.getSimpleName();

    private static int mPage= 1;

    private static int mNoOfRecordPerPage = 15;

    private static String mLanguage = "java";

    /**
     * Private constructor, class will not be instantiated
     */
    private DevelopersUtil(){
    }

    /**
     * Method to build the API url to fetch different content
     * @return String
     */
    public static String getJsonUrl(String language, int page){
        /** GitHub API url */
        String URL = "https://api.github.com/search/users?page="+mPage+"&per_page="+mNoOfRecordPerPage;

        String suffix="", finalUrl;
        switch (language){
            case "java":
                suffix = "java";
                break;
            case "php":
                suffix = "php";
                break;
            case "javascript":
                suffix = "javascript";
                break;
            case "python":
                suffix = "python";
                break;
        }
        finalUrl = URL+"&q=location:lagos+language:"+suffix;
        Log.v(LOG_TAG,finalUrl);
       // Log.v(LOG_TAG,""+data);
        return finalUrl;
    }

    public static void setmPage(int page){
        mPage = page;
    }



    public static void setmNoOfRecordPerPage(int NoOfRecordPerPage){
        mNoOfRecordPerPage = NoOfRecordPerPage;
    }

    public static void setmLanguage(String Language){
        mLanguage = Language;
    }

    /**
     * Method to hide the progress bar
     * @param view the progress bar view
     */
    public static void ControlProgressBar(View view,String status){
        ProgressBar progress = (ProgressBar) view.findViewById(R.id.progress);
        //progress.setVisibility(View.GONE);
        if(status == "hide"){
            progress.setVisibility(View.GONE);
        }else if(status == "show"){
            progress.setVisibility(View.VISIBLE);
        }
    }
    /**
     * Method to control when the no internet connectivity message shows
     * @param view the linear layout container
     */
    public static void ControlNoInternetMsg(View view, String status){
        LinearLayout internetLayout = (LinearLayout) view.findViewById(R.id.internetContainer);
        if(status == "hide"){
            internetLayout.setVisibility(View.GONE);
        }else if(status == "show"){
            internetLayout.setVisibility(View.VISIBLE);
        }
    }

    public static ArrayList<DeveloperObj> getStates(Context context){
        ArrayList<DeveloperObj> developersInfo = new ArrayList<DeveloperObj>();
        String line;
        InputStreamReader stream = null;
        BufferedReader textReader;
        try{
            stream = new InputStreamReader(context.getAssets().open("states.txt"));
            textReader = new BufferedReader(stream);
            while((line = textReader.readLine()) != null){
               // developersInfo.add(new DeveloperObj(line,line,R.drawable.b));
            }
        }catch(IOException e){
            Log.e(LOG_TAG,"Unable to open file "+e);
        }finally {
            if(stream != null){
                try {
                    stream.close();
                } catch (IOException e) {
                    Log.e(LOG_TAG,"Error closing stream "+e);
                }
            }
        }
        return developersInfo;
    }

    public static List<DeveloperObj> DevelopersList(String url){
        //create a url object from the string
        URL httpUrl = createUrl(url);
        //create variable to hold the json response
        String jsonResponse = null;
        try{
            jsonResponse = makeHttpRequest(httpUrl);
        }catch(IOException e){
            Log.e(LOG_TAG,"Http request error",e);
        }

        List<DeveloperObj> developerObjs = buildDevelopersObj(jsonResponse);
        return developerObjs;
    }

    public static DeveloperObj DevelopersProfile(String url){
        //create a url object from the string
        URL httpUrl = createUrl(url);
        //create variable to hold the json response
        String jsonResponse = null;
        try{
            jsonResponse = makeHttpRequest(httpUrl);
        }catch(IOException e){
            Log.e(LOG_TAG,"Http request error",e);
        }

        DeveloperObj developerProfile = buildDevelopersProfile(jsonResponse);
        return developerProfile;
    }

    /**
     * Create a new url object from the url string. will throw an error if there is an issue with the url
     * @param GitUrl url to be created
     * @return url Object
     */
    private static URL createUrl(String GitUrl){
        URL url = null;
        try{
            url = new URL(GitUrl);
        }catch(MalformedURLException e){
            Log.e(LOG_TAG,": Url error",e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException{
        String jsonResponse = "";
        //exit further execution if the url is not set
        if(url == null){
            return null;
        }

        HttpURLConnection httpConnection = null;
        InputStream inputStream = null;
        try{
            //open http connection
            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setReadTimeout(10000);
            httpConnection.setConnectTimeout(15000);
            httpConnection.setRequestMethod("GET");
            httpConnection.connect();
            //check http connection status
            if(httpConnection.getResponseCode() == 200){//connection was successful, proceed with further execution
                inputStream = httpConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }else{
                Log.e(LOG_TAG,"Http connection error. Returned response code:"+httpConnection.getResponseCode());
            }
        }catch (IOException e){
            Log.e(LOG_TAG,"Http connection error",e);
        }finally {
            //close opened connections
            if(httpConnection != null){
                httpConnection.disconnect();
            }
            if(inputStream != null){
                inputStream.close();;
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<DeveloperObj> buildDevelopersObj (String jsonResponse){
        //exit further execution if the json response is not set
        if(TextUtils.isEmpty(jsonResponse)){
            return null;
        }
        List<DeveloperObj> DevList = new ArrayList<>();
        try{
            JSONObject baseObject = new JSONObject(jsonResponse);
            JSONArray baseArray = baseObject.getJSONArray("items");
            //looP through the json array
            for(int i = 0; i < baseArray.length(); i++){
                //get json objects in the array
                JSONObject innerObject = baseArray.getJSONObject(i);
                //extract the relevant developers information
                String username = innerObject.getString("login");
                String pageUrl = innerObject.getString("url");
                String avatarUrl = innerObject.getString("avatar_url");
                //set the values
                DevList.add(new DeveloperObj(username,pageUrl,avatarUrl,""));
            }

        }catch(JSONException e){
            Log.e(LOG_TAG,"JSON Error",e);
        }
        return DevList;
    }

    private static DeveloperObj buildDevelopersProfile (String jsonResponse){
        //exit further execution if the json response is not set
        if(TextUtils.isEmpty(jsonResponse)){
            return null;
        }
        try{
            JSONObject baseObject = new JSONObject(jsonResponse);

                //extract the relevant developers information
                String username = baseObject.getString("login");
                String pageUrl = baseObject.getString("html_url");
                String avatarUrl = baseObject.getString("avatar_url");
                String fullName = baseObject.getString("name");
            //return new developers object
            return new DeveloperObj(username,pageUrl,avatarUrl,fullName);

        }catch(JSONException e){
            Log.e(LOG_TAG,"JSON Error",e);
        }
        return null;
    }

}
