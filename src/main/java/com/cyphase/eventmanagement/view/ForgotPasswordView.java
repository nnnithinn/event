package com.cyphase.eventmanagement.view;

import com.cyphase.eventmanagement.utils.Utils;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;


@Route("forgot-password")
@AnonymousAllowed
public class ForgotPasswordView extends VerticalLayout {
    
    TextField emailField = new TextField();
    TextField userNameField = new TextField();
            
    public ForgotPasswordView() {
        buildFields();
    }
    
    private void buildFields() {
        H1 title = new H1("Forgot Password");
        Paragraph paragraph = new Paragraph("Please enter the username and email id that is registered with your account. " +
                "If we find the email id and username exists in our records we will send the reset email link to your email");
        
        add(title, paragraph, userNameField, emailField);
        
        add(new Button("Send Reset Link", new Icon(VaadinIcon.PAPERPLANE), event -> sendEmail(emailField.getValue(), "Test", "Test")));
    }

    private void sendEmail() {
        HtmlEmail email = Utils.getEmail();
        try {
            email.setFrom("nnithin040@gmail.com");
            email.addTo(emailField.getValue());
            email.setSubject("Password Reset Email");
            String message = "<p> Please click on the following link to reset the password <br> <a href = 'localhost:8080/reset-password'> Reset Password</a></p>";
            email.setHtmlMsg(message);
            email.send();
        } catch (EmailException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendEmail(String recipient, String subject, String message) {
        // Use Spring Boot's JavaMailSender to send the email
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("nnithin040@gmail.com");
        mailSender.setPassword("Warlord007!'");

        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.debug", "true");

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(recipient);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);

        Notification.show("Email sent");
    }



}
