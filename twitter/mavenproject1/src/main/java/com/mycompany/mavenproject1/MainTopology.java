/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;



 
public class MainTopology {
 
    public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException, AuthorizationException {
       
        TopologyBuilder topologyBuilder = new TopologyBuilder();    
       
        topologyBuilder.setSpout(ApplicationConstants.TWITTER_SPOUT_ID, new TwitterSpout() {});    
      
        topologyBuilder.setBolt(ApplicationConstants.DETAILS_BOLT_ID, new DetailsExtractorBolt() {}, 2)
        .shuffleGrouping(ApplicationConstants.TWITTER_SPOUT_ID);    
               
        topologyBuilder.setBolt(ApplicationConstants.RETWEET_DETAILS_BOLT_ID, new RetweetDetailsExtractorBolt() {}, 2)
        .shuffleGrouping(ApplicationConstants.TWITTER_SPOUT_ID);    
     
        topologyBuilder.setBolt(ApplicationConstants.FILE_WRITER_BOLT_ID, new FileWriterBolt())
        .fieldsGrouping(ApplicationConstants.DETAILS_BOLT_ID, new Fields("title"))
        .fieldsGrouping(ApplicationConstants.RETWEET_DETAILS_BOLT_ID, new Fields("title"));    
      
        Config config = new Config();    
       
 
        if (args != null && args.length > 0) {
              config.setNumWorkers(1);
              StormSubmitter.submitTopology(args[0], config, topologyBuilder.createTopology());
            }
            else {
                final LocalCluster cluster = new LocalCluster();
          
                cluster.submitTopology(ApplicationConstants.TOPOLOGY_NAME, config, topologyBuilder.createTopology());
             
                Runtime.getRuntime().addShutdownHook(new Thread()    {
                    @Override
                    public void run()    {
                    cluster.killTopology(ApplicationConstants.TOPOLOGY_NAME);
                    cluster.shutdown();
                }
                });
            }
    }
 
}
