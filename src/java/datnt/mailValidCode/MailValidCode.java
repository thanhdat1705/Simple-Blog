package datnt.mailValidCode;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import datnt.mailConfig.MailConfig;
import javax.mail.Authenticator;

/**
 *
 * @author SE130204
 */
public class MailValidCode {

    public static void sendEmail(String code, String email) {
        Properties props = new Properties();
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.host", MailConfig.HOST_NAME);
//        props.put("mail.smtp.socketFactory.port", MailConfig.SSL_PORT);
//        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//        props.put("mail.smtp.port", MailConfig.SSL_PORT);

        props.put("mail.smtp.host", MailConfig.HOST_NAME);
        props.put("mail.smtp.port", MailConfig.SSL_PORT);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.socketFactory.port", MailConfig.SSL_PORT);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(MailConfig.APP_EMAIL, MailConfig.APP_PASSWORD);
            }
        });

        try {
            
            System.out.println(email);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(MailConfig.APP_EMAIL));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject("User Email Verification");
            message.setText("Your code to verify: " + code);

            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

}
