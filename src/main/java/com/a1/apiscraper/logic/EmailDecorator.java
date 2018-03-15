package com.a1.apiscraper.logic;

import com.a1.apiscraper.domain.Endpoint;

import java.time.Instant;
import java.util.HashMap;


public class EmailDecorator extends DecoratedScraper {

    public EmailDecorator(APIScraper apiScraper) {
        super(apiScraper.getApi());
    }

    public void communicate(){
        Mailer mailer = new Mailer();
        mailer.setSubject(getApi().getName() + " Has successfully been scraped");
        mailer.setMessage("Successfully scraped API: " + getApi().getName() + " on " + Instant.now().toString() + "."
                +"\n\nAPIScrapeTool");
        mailer.setReceivers("tbergh1@student.avans.nl, apiscrapetool@gmail.com");
        mailer.sendMail();
    }
}
