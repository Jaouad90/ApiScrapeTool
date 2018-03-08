package com.a1.apiscraper.logic;

import com.a1.apiscraper.domain.Endpoint;

import java.util.HashMap;

public class EmailScrape extends APIScraperDecorator {
    APIScraper apiScraper;

    public EmailScrape(APIScraper apiScraper) {
        super(apiScraper.getApi());
        this.apiScraper = apiScraper;
    }

    @Override
    public HashMap<Endpoint, String> scrape() {
        super.scrape();
        Mailer mailer = new Mailer();
        mailer.setMessage("xx");
        mailer.setReceiver("xx");
        mailer.sendMail();
        return super.scrape();
    }
}
