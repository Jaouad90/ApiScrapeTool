package com.a1.apiscraper.logic;

import com.a1.apiscraper.domain.Endpoint;

import java.time.Instant;
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
        mailer.setSubject(getApi().getName() + " Has successfully been scraped");
        mailer.setMessage("Successfully scraped API: " + getApi().getName() + " on " + Instant.now().toString());
        mailer.setReceiver("tbergh1@student.avans.nl");
        mailer.sendMail();
        return super.scrape();
    }
}
