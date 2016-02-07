// URL for working objectified app: http://guestbookssb2.appspot.com/

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

 

public class OfySignGuestbookServlet extends HttpServlet {
	
	HashSet<User> users = new HashSet<User>();
	static HashSet<String> emails = new HashSet<String>();
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)

                throws IOException {

        UserService userService = UserServiceFactory.getUserService();

        User user = userService.getCurrentUser();

        // We have one entity group per Guestbook with all Greetings residing

        // in the same entity group as the Guestbook to which they belong.

        // This lets us run a transactional ancestor query to retrieve all

        // Greetings for a given Guestbook.  However, the write rate to each

        // Guestbook should be limited to ~1/second.

        if (req.getParameter("Subscribe/Unsubscribe") != null){
        	this.toggleSubscription();
        }
        else {
	        String content = req.getParameter("content");
	        
	        String title = req.getParameter("title");
	
	        Date date = new Date();
	
	        Greeting greeting = new Greeting(user, content, title);
	
	        ofy().save().entity(greeting).now();
        }
        resp.sendRedirect("/ofyguestbook.jsp");

    }
	
	public void toggleSubscription(){
//		UserService userService = UserServiceFactory.getUserService();
//		User user = userService.getCurrentUser();
//		if (users.contains(user)){
//			users.remove(user);
//		}
//		else {
//			users.add(user);
//		}
//		for (User us : users){
//			System.out.println(user.getNickname());
//		}
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		String email = user.getNickname();
		if(emails.contains(email)){
			emails.remove(email);
		}
		else{
			emails.add(email);
		}
		for(String em: emails){
			System.out.println(em);
		}
		
	}

}