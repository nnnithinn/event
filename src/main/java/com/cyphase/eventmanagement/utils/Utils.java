package com.cyphase.eventmanagement.utils;

import org.apache.commons.mail.HtmlEmail;

public class Utils {

    public static boolean isWhite(String testString) {
        return testString == null || testString.isEmpty() || testString.isBlank();
    }


    public static HtmlEmail getEmail() {
        HtmlEmail email = new HtmlEmail();
        email.setHostName("smtp.gmail.com");
        email.setSmtpPort(587);
        email.setSSLOnConnect(true);
        email.setDebug(true);
        email.setAuthentication("nnithin040@gmail.com", "Httyiin1993$");
        return email;
    }
}
