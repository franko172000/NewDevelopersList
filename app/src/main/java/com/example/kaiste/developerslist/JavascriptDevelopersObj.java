package com.example.kaiste.developerslist;

/**
 * Created by Franklin on 7/30/2017.
 */

public class JavascriptDevelopersObj {
    /**
     * Image resource from GitHub
     */
    int mAvatar;
    /**
     * GitHub username
     */
    private String mUsername;
    /**
     * GitHub page url
     */
    private String mPageUrl;
    /**
     * User's display image url on GitHub
     */
    private String mAvatarUrl;
    /**
     * GitHud Profile Full name
     */
    private String mFullName;

    /**
     * Constructor method for the class
     * @param username GitHub username to be gotten from the json response
     * @param pageUrl  GitHub page url to be gotten from the json response
     * @param AvatarUrl   url for GitHub display image
     */
    public JavascriptDevelopersObj(String username, String pageUrl, String AvatarUrl,String fullname){
        mAvatarUrl= AvatarUrl;
        mPageUrl = pageUrl;
        mUsername = username;
        mFullName = fullname;

    }

    /**
     * get the username
     * @return String
     */
    public String getmUsername(){
        return mUsername;
    }

    /**
     * get the page url
     * @return String
     */
    public String getmPageUrl(){
        return mPageUrl;
    }

    /**
     * Get the display image
     * @return int
     */
    public int getmAvatar(){
        return mAvatar;
    }

    /**
     * String to get the url for the display image
     * @return
     */
    public String getmAvatarUrl(){
        return mAvatarUrl;
    }

    /**
     * String to get the developer's full name
     * @return
     */
}
