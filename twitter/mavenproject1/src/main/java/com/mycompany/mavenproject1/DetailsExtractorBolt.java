package com.mycompany.mavenproject1;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import twitter4j.Status;

public abstract class DetailsExtractorBolt implements IRichBolt{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private OutputCollector collector;
 
    @Override
    public void cleanup() {
        // TODO Auto-generated method stub
 
    }
 
    /* (non-Javadoc)
     * @see backtype.storm.task.IBolt#execute(backtype.storm.tuple.Tuple)
     * This emits the tweet information.
     */
    @Override
    public void execute(Tuple tuple) {
        Status status = (Status)tuple.getValueByField("status");
        String url = ApplicationConstants.EMPTY;
        String actual_url = ApplicationConstants.EMPTY;
 
        if (status.getURLEntities() != null
                && status.getURLEntities().length > 0) {
            url = status.getURLEntities()[0].getURL().trim();
            actual_url = getRedirectedurl(url);
        } else {            
            url = ApplicationConstants.NOT_AVAILABLE;
            actual_url = ApplicationConstants.NOT_AVAILABLE;
        }
        collector.emit(new Values(status.getUser().getScreenName(), status.getUser().getName(),
            status.getId(), url, actual_url, status.getText(), status.getCreatedAt()));
    }
    /*
     * This saves the OutputCollector as an instance variable to be used to emit tuples.
     */
    public void prepare(Map arg0, TopologyContext arg1, OutputCollector collector) {
        this.collector = collector;        
    }
 
    /* (non-Javadoc)
     * @see backtype.storm.topology.IComponent#declareOutputFields(backtype.storm.topology.OutputFieldsDeclarer)
     * 
     * Declares the output fields.
     */
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("screenName", "userName", "statusID", 
                "url", "actualURl", "title", "publishedDate"));
    }
 
    @Override
    public Map<String, Object> getComponentConfiguration() {
        // TODO Auto-generated method stub
        return null;
    }
 
    /**
     * @param url
     */
    private String getRedirectedurl(String url) {
        HttpURLConnection con;
        try {
            if (url != null) {
                con = (HttpURLConnection) new URL(url).openConnection();
                con.connect();
                con.setInstanceFollowRedirects(false);
 
                int responseCode = con.getResponseCode();
                if ((responseCode / 100) == 3) {
                    url = con.getHeaderField("");
                    responseCode = con.getResponseCode();
 
                    url = getRedirectedurl(url);
                }
            }
        } catch (MalformedURLException e) {
 
        } catch (IOException e) {
 
        }
        return url;
    }
}