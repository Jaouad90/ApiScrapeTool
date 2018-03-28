package com.a1.apiscraper.logic;

import com.a1.apiscraper.domain.Endpoint;
import com.a1.apiscraper.domain.Result;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.time.Instant;
import java.util.HashMap;

public class TweetDecorator extends DecoratedScraper{

    public TweetDecorator(APIScraper apiScraper) {
        super(apiScraper.getApi(), apiScraper);
    }

    @Override
    public HashMap<Endpoint, Result> scrape() {
        communicate();
        return super.scrape();
    }

    public void communicate(){
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("gpJXTPM4hsGhZ3OtEcSG9CrDp")
                .setOAuthConsumerSecret("m7ocphlYC3seo77NanukWAxnQlFgbmbRM2AbKGuwC66d1Wud4n")
                .setOAuthAccessToken("971714885636841472-cSbAcxFOxWIgWxJonOednmANzv8TED2")
                .setOAuthAccessTokenSecret("YiXwj7sEWTq7jkmdDww5kYb9zq26Sd0SL5oZyF5iC7Tet");
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        try {
            twitter.updateStatus("Successfully scraped API: " + getApi().getName() + " on " + Instant.now().toString() + "."
                    +"\n\nAPIScrapeTool");
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }
}
