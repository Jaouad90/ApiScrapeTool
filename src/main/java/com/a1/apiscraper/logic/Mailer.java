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

    private String username = "apiscrapetool@gmail.com";
    private String pword = "apiscrapetoola1";

    private String message = null;
    private String receivers = null;
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
                            return new PasswordAuthentication(username, pword);
                        }
                    });
            try {

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(username));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(receivers));
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
        return message != null && receivers != null && subject != null;
    }

}
