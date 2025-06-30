package service;

import configs.ConfigUtil;


import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailService {

    private final String username;
    private final String password;
    private final Session session;

    public EmailService() {
        this.username = ConfigUtil.getEmailUsuario();
        
        this.password = ConfigUtil.getEmailSenha();
        
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); 
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
       

        this.session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    public void enviarEmail(String para, String assunto, String texto) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(para));
        message.setSubject(assunto);
        message.setText(texto);

        Transport.send(message);
        System.out.println("Email enviado para " + para);
    }
}
