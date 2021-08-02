package com.zhuang.util.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.mail.internet.MimeMessage;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class EmailUtils {

    @Autowired
    private static EmailUtils _this;
    @Autowired
    private JavaMailSender javaMailSender;

    @PostConstruct
    private void init() {
        _this = this;
    }

    public static void sendSimpleMail(String from, String to, String subject, String content) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(content);
        _this.javaMailSender.send(simpleMailMessage);
    }

    public static void sendHtmlMail(String from, String to, String subject, String content) {
        MimeMessage message = _this.javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            _this.javaMailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getHtml(String path) {
        try {
            return new String(Files.readAllBytes(Paths.get(EmailUtils.class.getResource(path).toURI())));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
