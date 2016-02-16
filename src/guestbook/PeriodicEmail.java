package guestbook;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.ObjectifyService;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class PeriodicEmail extends HttpServlet{
		
	public void doGet(HttpServletRequest req, HttpServletResponse resp)

            throws IOException {

		List<Greeting> greetings = ObjectifyService.ofy().load().type(Greeting.class).list();
		Collections.sort(greetings);
		ArrayList<String> titles = new ArrayList<String>(0);
		ArrayList<String> messages = new ArrayList<String>(0);
		if(greetings != null && greetings.size() > 0){
			long pT = 0;
			long cT = System.currentTimeMillis();
			long tD = 0;
			long m24 = 24*60*60*1000;
			for(Greeting grt : greetings){
				pT = grt.date.getTime();
				tD = cT - pT;
				if(tD <= m24){
					messages.add(grt.getContent());
					titles.add(grt.getTitle());
				}
			}
		    
		}
		else{
			return;
		}
		
		if(OfySignGuestbookServlet.emails == null){
			return;
		}
		
		if(OfySignGuestbookServlet.emails.size() <= 0){
			return;
		}
		
		for(Stringey str: OfySignGuestbookServlet.emails){
			if(!str.string.contains("@gmail.com") && !str.string.contains("@utexas.edu")){
				str.string = str.string + "@gmail.com";
			}
	        Properties props = new Properties();
	        Session session = Session.getDefaultInstance(props, null);
	
	        String msgBody = "";
	        
	        for(int i = 0; i < titles.size(); i++){
	        	msgBody += "Title: " + titles.get(i) + "\n" + "Message: " + messages.get(i) + "\n\n";
	        }
	        
	        try {
	            Message msg = new MimeMessage(session);
	            msg.setFrom(new InternetAddress("sarang.bhadsavle@gmail.com", "Mr.Blog"));
	            msg.addRecipient(Message.RecipientType.TO,
	                             new InternetAddress(str.string, "Mr. User"));
	            msg.setSubject("Mr.Blog updates!");
	            msg.setText(msgBody);
	            Transport.send(msg);
	
	        } catch (AddressException e) {
	        } catch (MessagingException e) {
	        }
		}
    
    }
	

	public void doPost(HttpServletRequest req, HttpServletResponse resp)

            throws ServletException, IOException {
		doGet(req, resp);
	}

}
