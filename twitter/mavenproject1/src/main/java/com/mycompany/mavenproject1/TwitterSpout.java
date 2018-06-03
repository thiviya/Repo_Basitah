package com.mycompany.mavenproject1;

import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;
import twitter4j.DirectMessage;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.UserList;
import twitter4j.UserStreamListener;
import twitter4j.conf.ConfigurationBuilder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hp
 */

public abstract class TwitterSpout implements IRichSpout {
 
    private SpoutOutputCollector collector;
    private LinkedBlockingQueue<Status> queue;
    private TwitterStream twitterStream;    
 
    /**
     * The initialization method. 
     */
   
    @Override
    public void open(Map conf, TopologyContext context,
            SpoutOutputCollector collector) {
        this.collector = collector;
        queue = new LinkedBlockingQueue<Status>(100);
        /** 
         * The UserStreamListener is a twitter4j API, which can be added to a Twitter stream, 
         * and will execute methods every time a message comes in through the stream.
         */
        UserStreamListener  listener = new UserStreamListener() {
        
            @Override
            public void onException(Exception arg0) {
                // TODO Auto-generated method stub
 
            }
 
        
            @Override
            public void onTrackLimitationNotice(int arg0) {
                // TODO Auto-generated method stub
 
            }
            // This method executes every time a new tweet comes in.
         
            @Override
            public void onStatus(Status status) {
                queue.offer(status);
            }
 
        
            @Override
            public void onStallWarning(StallWarning arg0) {
                // TODO Auto-generated method stub
 
            }
 
        
            @Override
            public void onScrubGeo(long arg0, long arg1) {
                // TODO Auto-generated method stub
 
            }
 
       
            @Override
            public void onDeletionNotice(StatusDeletionNotice arg0) {
                // TODO Auto-generated method stub
 
            }
 
          
            @Override
            public void onUserProfileUpdate(User arg0) {
                // TODO Auto-generated method stub
 
            }
 
            @Override
            public void onUserListUpdate(User arg0, UserList arg1) {
                // TODO Auto-generated method stub
 
            }
 
        
            @Override
            public void onUserListUnsubscription(User arg0, User arg1, UserList arg2) {
                // TODO Auto-generated method stub
 
            }
 
          
            @Override
            public void onUserListSubscription(User arg0, User arg1, UserList arg2) {
                // TODO Auto-generated method stub
 
            }
 
        
            @Override
            public void onUserListMemberDeletion(User arg0, User arg1, UserList arg2) {
                // TODO Auto-generated method stub
 
            }
 
         
            @Override
            public void onUserListMemberAddition(User arg0, User arg1, UserList arg2) {
                // TODO Auto-generated method stub
 
            }
 
            
            @Override
            public void onUserListDeletion(User arg0, UserList arg1) {
                // TODO Auto-generated method stub
 
            }
 
        
            @Override
            public void onUserListCreation(User arg0, UserList arg1) {
                // TODO Auto-generated method stub
 
            }
 
       
            @Override
            public void onUnfollow(User arg0, User arg1) {
                // TODO Auto-generated method stub
 
            }
 
    
            @Override
            public void onUnfavorite(User arg0, User arg1, Status arg2) {
                // TODO Auto-generated method stub
 
            }
 
     
            @Override
            public void onUnblock(User arg0, User arg1) {
                // TODO Auto-generated method stub
 
            }
 
            @Override
            public void onFriendList(long[] arg0) {
                // TODO Auto-generated method stub
 
            }
 
            @Override
            public void onFollow(User arg0, User arg1) {
                // TODO Auto-generated method stub
 
            }
 
            @Override
            public void onFavorite(User arg0, User arg1, Status arg2) {
                // TODO Auto-generated method stub
 
            }
 
            @Override
            public void onDirectMessage(DirectMessage arg0) {
                // TODO Auto-generated method stub
 
            }
 
            @Override
            public void onDeletionNotice(long arg0, long arg1) {
                // TODO Auto-generated method stub
 
            }
 
            @Override
            public void onBlock(User arg0, User arg1) {
                // TODO Auto-generated method stub
 
            }

            @Override
            public void onUserSuspension(long suspendedUser) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onUserDeletion(long deletedUser) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onRetweetedRetweet(User source, User target, Status retweetedStatus) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onFavoritedRetweet(User source, User target, Status favoritedRetweeet) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onQuotedTweet(User source, User target, Status quotingTweet) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        // ConfigurationBuilder object.
        ConfigurationBuilder cb = new ConfigurationBuilder();        
        cb.setOAuthConsumerKey(ApplicationConstants.CONSUMER_KEY_KEY);
        cb.setOAuthConsumerSecret(ApplicationConstants.CONSUMER_SECRET_KEY);
        cb.setOAuthAccessToken(ApplicationConstants.ACCESS_TOKEN_KEY);
        cb.setOAuthAccessTokenSecret(ApplicationConstants.ACCESS_TOKEN_SECRET_KEY);
 
        twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
        twitterStream.addListener(listener);
        twitterStream.user();
 
    }
 
    @Override
    public void close() {
        twitterStream.shutdown();
    }
 
    @Override
    public void activate() {
 
    }
 
    @Override
    public void deactivate() {
 
    }
 
    // Spout emits tuples to the output collector.
    @Override
    public void nextTuple() {
        // Status object from the queue.
        Status status = queue.poll();     
        if(status == null) {
            Utils.sleep(50);
        } else {
            collector.emit(new Values(status));
        }
    }
 
    @Override
    public void ack(Object msgId) {
 
    }
 
    @Override
    public void fail(Object msgId) {
 
    }
    // Emits single stream of tuple containing a single field status.

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("status"));
    }
 
    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
 
}