package com.a1.apiscraper.logic;

import lombok.Getter;
import lombok.Setter;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Getter
@Setter
public class Mailer {
    final String username = "apiscrapetool@gmail.com";
    final String password = "apiscrapetoola1";

    private String message = null;
    private String receiver = null;
    private String subject = null;

    public boolean sendMail() {
        if (this.checkInfo()) {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });
            try {

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(username));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(receiver));
                message.setSubject(subject);
                message.setText(this.message);

                Transport.send(message);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }

    private boolean checkInfo() {
        return message != null && receiver != null && subject != null;
    }
}
