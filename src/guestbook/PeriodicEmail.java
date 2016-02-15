package guestbook;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;


public class PeriodicEmail extends HttpServlet{
	
	int count = 0;
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)

            throws IOException {

    UserService userService = UserServiceFactory.getUserService();

    User user = userService.getCurrentUser();
    
    count += 1;

    System.out.println("Yo " + count);
    
	resp.sendRedirect("/ofyguestbookConcise.jsp");
    }

}
