package com.a1.apiscraper.logic;

public class DecoratorFactory {

    public APIScraper getDecorator(String decoratorType, APIScraper scraper){
        if(decoratorType.equals("TweetDecorator")){
            return scraper = new TweetDecorator(scraper);
        }
        else if(decoratorType.equals("MailDecorator")){
            return scraper = new EmailDecorator(scraper);
        }
        else {
            return null;
        }

    }
}