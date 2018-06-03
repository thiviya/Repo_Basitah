package com.mycompany.mavenproject1;

public class FileDetails {
    private String screenName;
    private String userName;
    private String statusID;
    private String url;
    private String actualURL;
    private String title;
    private String publishedDate;
    private String isRetweet ;
    private String retweetScreenName;
    private String retweetUserName;
    private boolean isDetailsBoltExec = false;
    private boolean isRetweetBoltExec = false;
 
    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setStatusID(String statusID) {
        this.statusID = statusID;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public void setActualURL(String actualURL) {
        this.actualURL = actualURL;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }
    public void setIsRetweet(String isRetweet) {
        this.isRetweet = isRetweet;
    }
    public void setRetweetScreenName(String retweetScreenName) {
        this.retweetScreenName = retweetScreenName;
    }
    public void setRetweetUserName(String retweetUserName) {
        this.retweetUserName = retweetUserName;
    }
 
    public void isDetailsBoltExec(boolean isDetailsBoltExec) {
        this.isDetailsBoltExec = isDetailsBoltExec;
    }
 
    public void isRetweetBoltExec(boolean isRetweetBoltExec) {
        this.isRetweetBoltExec = isRetweetBoltExec;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();        
        sb.append("\""+this.screenName+"\",");
        sb.append("\""+this.userName+"\",");
        sb.append("\""+this.statusID+"\",");
        sb.append("\""+this.url+"\",");
        sb.append("\""+this.actualURL+"\",");
        sb.append("\""+this.title+"\",");
        sb.append("\""+this.publishedDate+"\",");
        sb.append("\""+this.isRetweet +"\",");
        sb.append("\""+this.retweetScreenName+"\",");
        sb.append("\""+this.retweetUserName+"\"");
        return sb.toString();
    }
    public boolean isWritable() {
        if(this.isDetailsBoltExec && this.isRetweetBoltExec) {
            return true;
        }
        return false;
    }
}
