package com.lrowy.service;

import com.lrowy.dao.EmailDao;
import com.lrowy.pojo.email.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

@Service
public class EmailService {
    @Value("${spring.mail.username}")
    private String workEmail;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private EmailDao emailDao;

    private int saveEmail(String content, String subject, String sendFrom, String sendTo) {
        Email email = new Email();
        email.setContent(content);
        email.setSubject(subject);
        email.setSendFrom(sendFrom);
        email.setSendTo(sendTo);
        email.setSendDate(new Date());
        emailDao.saveEmail(email);
        return email.getEmailId();
    }

    public int sendSimpleMail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(workEmail);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
        return saveEmail(text, subject, workEmail, to);
    }

    public int sendHTMLMail(String to, String subject, String text) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
        mimeMessageHelper.setFrom(workEmail);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(text, true);
        mailSender.send(message);
        return saveEmail(text, subject, workEmail, to);
    }

    public int sendTemplateMail(String to, String subject, String template, Context context) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
        mimeMessageHelper.setFrom(workEmail);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        String t = templateEngine.process(template, context);
        mimeMessageHelper.setText(t, true);
//        mailSender.send(message);
        return this.saveEmail(t, subject, workEmail, to);
    }
}
