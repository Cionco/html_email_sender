package io;

import java.nio.charset.Charset;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Mailer {

	private static class SMTPAuthenticator extends Authenticator
    {
        private PasswordAuthentication authentication;

        public SMTPAuthenticator(String login, String password)
        {
            authentication = new PasswordAuthentication(login, password);
        }

        @Override
        protected PasswordAuthentication getPasswordAuthentication()
        {
            return authentication;
        }
    }
	
	public static void main(String[] args) {
		String from = "jan@janriks.com";
        String to = "nicolas@divirad.com";
        String subject = "test";
        String message = "testmessage";
        String login = "jan@janriks.com";
        String password = "***********";
        
        Properties props = new Properties();
        props.setProperty("mail.host", "smtp.strato.de");
        props.setProperty("mail.smtp.port", "587");
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.starttls.enable", "true");
        
        Authenticator auth = new SMTPAuthenticator(login, password);

        Session session = Session.getInstance(props, auth);

        MimeMessage msg = new MimeMessage(session);

        try{
        	byte[] encoded = html.getBytes();//Files.readAllBytes(Paths.get(servletContext.getRealPath("/text/mailtemplate.html")));
            String html = new String(encoded, Charset.forName("UTF-8"));
            
            MimeMultipart multipart = new MimeMultipart("related");

            BodyPart messageBodyPart = new MimeBodyPart();
            /*html = html.replace("FULLTOKENCODE", token);
            html = html.replace("TOKENPARTONECODE", token.substring(0, 4));
            html = html.replace("TOKENPARTTWOCODE", token.substring(4, 8));
            html = html.replace("TOKENPARTTHREECODE", token.substring(8, 12));*/
            messageBodyPart.setContent(html, "text/html");
            multipart.addBodyPart(messageBodyPart);

            msg.setContent(multipart);
            msg.setSubject(subject);
            msg.setFrom(new InternetAddress(from));
            msg.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));
            Transport.send(msg);
            System.out.println("Sent");
        }
        catch (Exception ex){
            System.out.print(ex.toString());
        }
	}
	
	static String html = "<html><head><meta charset=\"UTF-8\"><style>body {background: #dddddd;min-width: 750px;}.emailcontent a {outline: none;text-decoration: none;padding: 2px 1px 0;}.emailcontent a:link {color: #720000;}.emailcontent a:visited {color: #4C0000;}.emailcontent a:focus {border-bottom: 1px solid;background: #270000;}.emailcontent a:hover {border-bottom: 1px solid;background: #BB3434;}.emailcontent a:active {background: #270000;color: #BB3434;}.border {border-color: #720000;border-width: 1px;border-radius: 10px;border-style: solid;padding: 5px;}.container {width: 98%;margin: 1%;background: #cccccc;padding: 1% 0px;min-width: 710px;}.header {margin: auto;background: #720000;border-radius: 20px;height: 100px;width: 690px;}.headerline {color: white;text-align: center;font-size: 80px;}.content {margin: auto;margin-top: 20px;width: 690px;min-height: 200px;text-align: center;}</style></head><body><div class=\"container\"><div class=\"header border\"><div class=\"headerline\">JanRiks</div></div><div class=\"content border\"><div class=\"emailcontent\"></div><div class=\"signature\" align=center><table style=\"width: 400px; font-size: 9pt; font-family: Arial, sans-serif; line-height:normal;\" cellpadding=\"0\" cellspacing=\"0\"><tbody><tr><td style=\" font-size:12pt; font-family:Arial,sans-serif; color:#1793ce; padding-bottom:6px;  width:259px\"><span style=\"font-family: Arial, sans-serif; color:#a20000; font-weight: bold\">Jan Riks</span><span style=\"font-family: Arial, sans-serif; color:#878787; font-size: 10pt;\"><span style=\"font-family: Arial, sans-serif; color:#878787\"><br>Experte in E-Commerce und Dropshipping</span></span></td><td style=\" width:141px;  padding-bottom:6px; text-align:right;\">&nbsp;</td></tr><tr><td style=\"width:400px; border-top:1px solid; border-top-color:#a20000;\" colspan=\"2\" width=\"400\">&nbsp;</td></tr><tr><td style=\" font-size: 10pt; font-family: Arial, sans-serif; font-weight:bold; color: #2c2c2c; width:400px;\" colspan=\"2\" width=\"400\"><span style=\"font-family: Arial, sans-serif; color:#2c2c2c\">AJ Digital Media Consulting UG (haftungsbeschränkt)</span></td></tr><tr><td style=\" font-size: 10pt; font-family: Arial, sans-serif; color: #2c2c2c; width:400px;\" colspan=\"2\" width=\"400\"><span style=\"font-family: Arial, sans-serif; color:#2c2c2c\">Mörfelder Landstraße 6-8</span></td></tr><tr><td style=\" font-size: 10pt; font-family: Arial, sans-serif; color: #2c2c2c; width:400px;\" colspan=\"2\" width=\"400\"><span style=\"font-family: Arial, sans-serif; color:#2c2c2c\">60598 Frankfurt am Main</span></td></tr><tr><td style=\" font-size: 10pt; font-family: Arial, sans-serif; color: #2c2c2c; width:400px;\" colspan=\"2\" width=\"400\"><span style=\"font-family: Arial, sans-serif; color:#2c2c2c\">e: jan@janriks.com</span></td></tr><tr><td style=\"width:400px;\" colspan=\"2\" width=\"400\">&nbsp;</td></tr><tr><td style=\" font-size: 10pt; font-family: Arial, sans-serif; font-weight:bold; color: #1793ce; width:200px; height:15px;\" width=\"200\"><span style=\"display:inline-block; height:15px; font-family: Arial, sans-serif; font-size: 9pt;\"><a href=\"http://coaching.janriks.com\" target=\"_blank\" style=\"text-decoration:none;\"><span style=\"font-family:Arial, sans-serif; color:#a20000\">coaching.janriks.com</span></a></span></td><td style=\"width:200px; text-align:right;\" width=\"200\"><span style=\"display:inline-block; height:15px;\"><span><a href=\"https://www.facebook.com/jan.riks.37\" target=\"_blank\"><img alt=\"Facebook icon\" border=\"0\" width=\"15\" height=\"15\" style=\"border:0; height:15px; width:15px\" src=\"https://codetwocdn.azureedge.net/images/mail-signatures/generator/sky/fb.png\"></a>&nbsp;&nbsp;&nbsp;</span><span><a href=\"https://www.youtube.com/channel/UCyN8wiueqKudBhzi-xoRoAg\" target=\"_blank\"><img alt=\"Youtbue icon\" border=\"0\" width=\"15\" height=\"15\" style=\"border:0; height:15px; width:15px\" src=\"https://codetwocdn.azureedge.net/images/mail-signatures/generator/sky/yt.png\"></a>&nbsp;&nbsp;&nbsp;</span><span><a href=\"http://instagram.com/jan.riks\" target=\"_blank\"><img alt=\"Instagram icon\" border=\"0\" width=\"15\" height=\"15\" style=\"border:0; height:15px; width:15px\" src=\"https://codetwocdn.azureedge.net/images/mail-signatures/generator/sky/it.png\"></a></span></span></td></tr><tr><td style=\"width:400px;\" colspan=\"2\" width=\"400\">&nbsp;</td></tr><tr><td style=\" width:400px; padding-top:4px;\" colspan=\"2\" width=\"400\"><a href=\"https://coaching.janriks.com\" target=\"_blank\" rel=\"noopener\"><img border=\"0\" alt=\"Banner\" width=\"400\" style=\"width:400px; height:auto; border:0;\" src=\"https://d2saw6je89goi1.cloudfront.net/uploads/digital_asset/file/564864/banner_kleiner.jpg\"></a></td></tr><tr><td style=\"width:400px;\" colspan=\"2\" width=\"400\">&nbsp;</td></tr><tr><td style=\" font-size:8pt; font-family:Arial,sans-serif; color:#878787; width:400px; text-align:justify;\" colspan=\"2\" width=\"400\"><span style=\"font-family: Arial, sans-serif; color:#878787\">The content of this email is confidential and intended for the recipient specified in message only. It is strictly forbidden to share any part of this message with any third party, without a written consent of the sender. If you received this message by mistake, please reply to this message and follow with its deletion, so that we can ensure such a mistake does not occur in the future.</span></td></tr></tbody></table></div></div></div></body></html>";
}
