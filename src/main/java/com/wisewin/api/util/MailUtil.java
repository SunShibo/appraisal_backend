package com.wisewin.api.util;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Properties;

/**
 * Created by sunshibo on 2016/3/22.
 */
public class MailUtil {

    public static final String ENCODING = "utf-8"  ;

    public void sendTextMail(String to , String subject , String text)  {
        JavaMailSenderImpl senderImpl = this.getJavaMailSender() ;
        // 建立邮件消息
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        // 设置收件人，寄件人 用数组发送多个邮件
        // String[] array = new String[] {"sun111@163.com","sun222@sohu.com"};
        // mailMessage.setTo(array);
        mailMessage.setTo(to);
        mailMessage.setFrom("395831708@qq.com");
        mailMessage.setSubject(subject);
        mailMessage.setText(text);

        // 发送邮件
        senderImpl.send(mailMessage);
        System.out.println(" 邮件发送成功.. ");
    }

    public void sendHTMLMail (String subject , String html , String... to) throws MessagingException {
        JavaMailSenderImpl senderImpl = this.getJavaMailSender() ;

        // 建立邮件消息,发送简单邮件和html邮件的区别
        MimeMessage mailMessage = senderImpl.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true , ENCODING);

        // 设置收件人，寄件人
        messageHelper.setTo(to);
        messageHelper.setFrom("service@sollandtech.com");
        messageHelper.setSubject(subject);
        // true 表示启动HTML格式的邮件
        messageHelper.setText(html, true);
        // 发送邮件
        senderImpl.send(mailMessage);

        System.out.println("邮件发送成功..");
    }

    /**
     * 本类测试邮件中嵌套图片
     */
    public void attachedImageMail (String to , String subject , String html) throws MessagingException {
        JavaMailSenderImpl senderImpl = this.getJavaMailSender() ;

        // 建立邮件消息,发送简单邮件和html邮件的区别
        MimeMessage mailMessage = senderImpl.createMimeMessage();
        // 注意这里的boolean,等于真的时候才能嵌套图片，在构建MimeMessageHelper时候，所给定的值是true表示启用，
        // multipart模式
        MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage,
                true);

        // 设置收件人，寄件人
        messageHelper.setTo("to");
        messageHelper.setFrom("username@163.com");
        messageHelper.setSubject(subject);
        // true 表示启动HTML格式的邮件
        messageHelper.setText(
                "<html><head></head><body><h1>hello!!spring image html mail</h1>"
                        + "<img src=\"cid:aaa\"/></body></html>", true);

        FileSystemResource img = new FileSystemResource(new File("g:/123.jpg"));

        messageHelper.addInline("aaa", img);

        // 发送邮件
        senderImpl.send(mailMessage);

        System.out.println("邮件发送成功..");
    }

    /**
     * 本类测试的是关于邮件中带有附件的例子
     */
    public void attachedFileMail (String subject , String html , String... to)  throws MessagingException {
        JavaMailSenderImpl senderImpl = this.getJavaMailSender() ;

        // 建立邮件消息,发送简单邮件和html邮件的区别
        MimeMessage mailMessage = senderImpl.createMimeMessage();
        // 注意这里的boolean,等于真的时候才能嵌套图片，在构建MimeMessageHelper时候，所给定的值是true表示启用，
        // multipart模式 为true时发送附件 可以设置html格式
        MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage,
                true, "utf-8");

        // 设置收件人，寄件人
        messageHelper.setTo(to);
        messageHelper.setFrom("username@163.com");
        messageHelper.setSubject(subject);
        // true 表示启动HTML格式的邮件
        messageHelper.setText(html, true);

        FileSystemResource file = new FileSystemResource(
                new File("g:/test.rar"));
        // 这里的方法调用和插入图片是不同的。
        messageHelper.addAttachment("test.rar", file);
        // 发送邮件
        senderImpl.send(mailMessage);

        System.out.println("邮件发送成功..");
    }

    public JavaMailSenderImpl getJavaMailSender () {
        JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
        // 设定mail server
        senderImpl.setHost("smtp.qq.com");
        senderImpl.setPort(465);
        senderImpl.setUsername("service@sollandtech.com"); // 根据自己的情况,设置username
        senderImpl.setPassword("Pai123456"); // 根据自己的情况, 设置password
//        senderImpl.setDefaultEncoding(ENCODING);
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true"); // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
        prop.put("mail.smtp.timeout", "25000");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        senderImpl.setJavaMailProperties(prop);
        return senderImpl ;
    }
//
//    public static void main(String[] args) throws IOException {
//        String[] array = new String []{"395831708@qq.com", "hr@sollandtech.com", "daisypei@sollandtech.com"} ;
//        String html = new Env().getProperty("mail.sendCode") ;
//        System.out.println(html);
//        try {
//            new MailUtil().sendHTMLMail("系统群发测试邮件" , html , array);
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//    }
}
