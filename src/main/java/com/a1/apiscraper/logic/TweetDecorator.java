package com.a1.apiscraper.logic;

import com.a1.apiscraper.domain.Endpoint;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.time.Instant;
import java.util.HashMap;

public class TweetDecorator extends APIScraper {
    private APIScraper apiScraper;

    public TweetDecorator(APIScraper apiScraper) {
        super(apiScraper.getApi());
        this.apiScraper = apiScraper;
    }

    @Override
    public HashMap<Endpoint, String> scrape() {
        HashMap<Endpoint, String> results = apiScraper.scrape();

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
				.setOAuthConsumerKey("gpJXTPM4hsGhZ3OtEcSG9CrDp")
				.setOAuthConsumerSecret("m7ocphlYC3seo77NanukWAxnQlFgbmbRM2AbKGuwC66d1Wud4n")
				.setOAuthAccessToken("971714885636841472-YBGfTYMJOE8Ok1B4X9LUoZSbavIa1ge")
				.setOAuthAccessTokenSecret("ioYiSI7cn9lxhwJ3N7j7FM1e3VfxUKbgHqGbVTv5aDse0");
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
        try {
            twitter.updateStatus("Successfully scraped API: " + getApi().getName() + " on " + Instant.now().toString() + "."
                    +"\n\nAPIScrapeTool");
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return results;
    }
}
