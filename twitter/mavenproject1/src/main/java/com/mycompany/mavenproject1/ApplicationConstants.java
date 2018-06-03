/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

/**
 *
 * @author hp
 */
public class ApplicationConstants {
 
    // Twitter application specific secret constants.   
    public static final String CONSUMER_KEY_KEY = "G1vsi5YJdGqxAeeatvoQ9X3UB";
    public static final String CONSUMER_SECRET_KEY = "6S4FN4D4mkbdB5bLjeIBVwowRPT8JuIooSBM9iYvUVqLCZ7onW";
    public static final String ACCESS_TOKEN_KEY = "******-w46iJSxP8Pwo6xYiYANu49lJJnhz5i";
    public static final String ACCESS_TOKEN_SECRET_KEY = "FzEjaKg1tkJ9vpm2mGTLFUnG6yT36ZpciBvTLSuORZqtG";
 
    // constants
    public static final String NOT_AVAILABLE = "Not Available";
    public static final String EMPTY = "";
 
    // Topology Constants 
    public static final String TOPOLOGY_NAME = "twitter-topology";
    public static final String TWITTER_SPOUT_ID = "twitterSpout";
    public static final String DETAILS_BOLT_ID = "detailsExtractorBolt";
    public static final String RETWEET_DETAILS_BOLT_ID = "retweetDetailsExtractorBolt";
    public static final String FILE_WRITER_BOLT_ID = "fileWriterBolt";
}