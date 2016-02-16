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
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;



 

public class OfySignGuestbookServlet extends HttpServlet {
	
	private boolean isConcise = true;
	
	static List<Stringey> emails;
	
	//static HashSet<String> emails = new HashSet<String>();
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)

                throws IOException {

        UserService userService = UserServiceFactory.getUserService();

        User user = userService.getCurrentUser();
        

        if (req.getParameter("Subscribe") != null){
        	this.toggleSubscription(true);
        }
        else if (req.getParameter("Unsubscribe") != null){
        	this.toggleSubscription(false);
        }
        else if (req.getParameter("See All Posts") != null){
        	this.toggleView();
        }
        else {
	        String content = req.getParameter("content");
	        
	        String title = req.getParameter("title");
	
	        Date date = new Date();
	
	        Greeting greeting = new Greeting(user, content, title);
	
	        ofy().save().entity(greeting).now();
        }
        
        if(isConcise){
    		resp.sendRedirect("/ofyguestbookConcise.jsp");
    	}
    	else{
            resp.sendRedirect("/ofyguestbook.jsp");
    	}

        
    }
	
	public void toggleView(){
		isConcise = !isConcise;
	}
	
	public void toggleSubscription(boolean wantSubs){
		
		
		emails = ObjectifyService.ofy().load().type(Stringey.class).list();
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		String email = user.getNickname();
		Stringey emailStringey = new Stringey(email);
		
		boolean isThere = false;
		Stringey stry = null;
		for(Stringey s: emails){
			if(s.equals(emailStringey)){
				stry = s;
				isThere = true;
				break;
			}
		}
		
		if(!wantSubs && stry != null){
			emails.remove(stry);
			ObjectifyService.ofy().delete().type(Stringey.class).id(stry.id).now();
		}
		else{
			if(!isThere){
				emails.add(emailStringey);
				ObjectifyService.ofy().save().entity(emailStringey).now();
			}
		}
		
		
	}
} 