package com.ivashyn.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;

/**
 * Created by ickis on 5/4/15.
 */
public class MailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailService.class);

    private JavaMailSender mailSender;

    public void sendEmail(String from, String subject, String[] to, String text) {

        MimeMessage msg = null;
        try {
            msg = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(msg, true);

            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
            mailSender.send(msg);
        } catch (Exception e) {
            LOGGER.warn("Exception occurred  while trying to  send Email ");
        }
    }

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
}
