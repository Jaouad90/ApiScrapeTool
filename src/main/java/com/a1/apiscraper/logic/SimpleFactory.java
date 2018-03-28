package com.a1.apiscraper.logic;

public class SimpleFactory {

    public APIScraper getDecorator(String decoratorType, APIScraper scraper){
        if(decoratorType.equals("TweetDecorator")){
            return new TweetDecorator(scraper);
        }
        else if(decoratorType.equals("MailDecorator")){
            return new EmailDecorator(scraper);
        }
        return null;
    }

    public ScrapeBehavior getScrapeBehavior(String scrapeBehaviorString){

        if(scrapeBehaviorString.equals("NormalScrapeBehavior")){
            return new NormalScrapeBehavior();
        }
        else if(scrapeBehaviorString.equals("DeepScrapeBehavior")){
            return new DeepScrapeBehavior();
        }
        return null;
    }
}