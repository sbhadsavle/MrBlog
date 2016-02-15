
package guestbook;



import com.google.appengine.api.users.User;

import com.google.appengine.api.users.UserService;

import com.google.appengine.api.users.UserServiceFactory;

import com.googlecode.objectify.ObjectifyService;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
 

public class SendEmail extends HttpServlet {
	
	public void doGet(HttpServletRequest request,
            HttpServletResponse response)
    throws ServletException, IOException {
		// Recipient's email ID needs to be mentioned.
		String to = "sarang.bhadsavle@gmail.com";
		
		// Sender's email ID needs to be mentioned
		String from = "sarang.bhadsavle@gmail.com";
		
		// Assuming you are sending email from localhost
		String host = "localhost";
		
		// Get system properties
		Properties properties = System.getProperties();
		
		// Setup mail server
		properties.setProperty("mail.smtp.host", host);
		
		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties);
		
		// Set response content type
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		try{
		 // Create a default MimeMessage object.
		 MimeMessage message = new MimeMessage(session);
		 // Set From: header field of the header.
		 message.setFrom(new InternetAddress(from));
		 // Set To: header field of the header.
		 message.addRecipient(Message.RecipientType.TO,
		                          new InternetAddress(to));
		 // Set Subject: header field
		 message.setSubject("This is the Subject Line!");
		 // Now set the actual message
		 message.setText("This is actual message");
		 // Send message
		 Transport.send(message);
		 System.out.println("Works");
		 String title = "Send Email";
		 String res = "Sent message successfully....";
		 String docType =
		 "<!doctype html public \"-//w3c//dtd html 4.0 " +
		 "transitional//en\">\n";
		 out.println(docType +
		 "<html>\n" +
		 "<head><title>" + title + "</title></head>\n" +
		 "<body bgcolor=\"#f0f0f0\">\n" +
		 "<h1 align=\"center\">" + title + "</h1>\n" +
		 "<p align=\"center\">" + res + "</p>\n" +
		 "</body></html>");
		}catch (MessagingException mex) {
		 mex.printStackTrace();
		}
	}

	
} 