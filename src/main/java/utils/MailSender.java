package utils;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.MessagingException;
import javax.mail.internet.*;
import java.util.Properties;

public class MailSender {
    private static final String smtpDebug = "false"; //todo fill in the date about stmp server below
    private static final String smtpEmail = "";    // stmp owner email adress
    private static final String smtpUsername = ""; // stmp username
    private static final String smtpPassword = "";  // stmp pass
    private static final String smtpHost = "";      // stmp server adress
    private static final String smtpPort = "465"; // port "465" for ssl
    private static final String smtpAuth = "true"; //ssl authentication
    private static final String smtpSecurity = "ssl";
    String recipientEmail;
    String emailSubject;
    String emailBody;


    public MailSender(String recipientEmail, String emailSubject, String emailBody) {
        this.emailBody = emailBody;
        this.emailSubject = emailSubject;
        this.recipientEmail = recipientEmail;
    }

    public void sendEmail() throws MessagingException {
        Properties smtpProperties = new Properties();

        smtpProperties.put("mail.debug", smtpDebug);
        smtpProperties.put("mail.smtp.auth", smtpAuth);
        smtpProperties.put("mail.smtp." + smtpSecurity + ".enable", true);
        smtpProperties.put("mail.smtp.host", smtpHost);
        smtpProperties.put("mail.smtp.port", smtpPort);

        Authenticator authenticator = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(smtpUsername, smtpPassword); //connect to stmp
            }
        };

        Session session = Session.getInstance(smtpProperties, authenticator); //create session

        InternetAddress[] replyAddresses = InternetAddress.parse(smtpEmail);
        InternetAddress[] recipientAddresses = InternetAddress.parse(recipientEmail);//parse emails

        Message message = new MimeMessage(session);

        message.addFrom(replyAddresses); //email constructor
        message.setRecipients(Message.RecipientType.TO, recipientAddresses);
        message.setReplyTo(replyAddresses);
        message.setSubject(emailSubject);

        Multipart multipart = new MimeMultipart();
        MimeBodyPart part = new MimeBodyPart();

        part.addHeader("Content-Type", "text/plain; charset=UTF-8");
        part.setText(emailBody);

        multipart.addBodyPart(part);
        message.setContent(multipart);

        Transport.send(message);
    }
}
