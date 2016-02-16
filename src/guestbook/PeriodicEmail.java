package guestbook;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

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

	for(Stringey str: OfySignGuestbookServlet.emails){
		if(!str.string.contains("@gmail.com") && !str.string.contains("@utexas.edu")){
			str.string = str.string + "@gmail.com";
		}
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        String msgBody = "...";

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("sarang.bhadsavle@gmail.com", "Example.com Admin"));
            msg.addRecipient(Message.RecipientType.TO,
                             new InternetAddress(str.string, "Mr. User"));
            msg.setSubject("Your Example.com account has been activated");
            msg.setText(msgBody);
            Transport.send(msg);

        } catch (AddressException e) {
            // ...
        } catch (MessagingException e) {
            // ...
        }
	}
    
    }
	

	public void doPost(HttpServletRequest req, HttpServletResponse resp)

            throws ServletException, IOException {
		doGet(req, resp);
	}

}
