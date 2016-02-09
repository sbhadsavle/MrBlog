<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="java.util.List" %>

<%@ page import="java.util.Collections" %>

<%@ page import="guestbook.Greeting" %>

<%@ page import="guestbook.OfySignGuestbookServlet" %>

<%@ page import="com.googlecode.objectify.*" %>

<%@ page import="com.google.appengine.api.users.User" %>

<%@ page import="com.google.appengine.api.users.UserService" %>

<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>

<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>

<%@ page import="com.google.appengine.api.datastore.Query" %>

<%@ page import="com.google.appengine.api.datastore.Entity" %>

<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>

<%@ page import="com.google.appengine.api.datastore.Key" %>

<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

 

<html>

  <head>
   <link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
  </head>

 

  <body>


<%

    String guestbookName = request.getParameter("guestbookName");

    if (guestbookName == null) {

        guestbookName = "Mr. Blog";

    }

    pageContext.setAttribute("guestbookName", guestbookName);

    UserService userService = UserServiceFactory.getUserService();

    User user = userService.getCurrentUser();

    if (user != null) {

      pageContext.setAttribute("user", user);

%>

<p align="right">Hello, ${fn:escapeXml(user.nickname)}! (You can

<a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">sign out</a>.)</p>
	
	<form align = "right" action="/ofysign" method="post">
    	<input type="submit" name="Subscribe/Unsubscribe" value="Subscribe/Unsubscribe" onclick = "myFunction()" />
	</form>
	
	<form align = "right" action="/ofysign" method="post">
    	<input type="submit" name="See All Posts" value="See All Posts" />
	</form>
	
	<script>
		function myFunction(){
			alert("Thank you")
		}
	</script>
    
   	<div style="text-align:center">	
		<a href="/blogEntry.jsp">Make a Post!</a>
	</div>
	   

<%

    } else {

%>

<p align="right">Hello!

<a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a>

to create a blog post.</p>

<%

    }

%>

 

<%


	ObjectifyService.register(Greeting.class);

	List<Greeting> greetings = ObjectifyService.ofy().load().type(Greeting.class).list();   

	Collections.sort(greetings); 

    if (greetings.isEmpty()) {

        %>

        <p>${fn:escapeXml(guestbookName)} has no posts.</p>

        <%

    } else {

        %>

        <p>${fn:escapeXml(guestbookName)}:</p>

        <%

        int size = 1;
        
        for (Greeting greeting : greetings) {
        	
        	if(size <= 3){
        	
            pageContext.setAttribute("greeting_title",

                    greeting.getTitle());
        	
            pageContext.setAttribute("greeting_content",

                                     greeting.getContent());

            if (greeting.getUser() == null) {

                %>

                <p>An anonymous person wrote:</p>

                <%

            } else {

                pageContext.setAttribute("greeting_user",

                                         greeting.getUser());

                %>

                <p align= "left"><i>${fn:escapeXml(greeting_user.nickname)}</i> wrote:</p>

                <%

            }

            %>
          
			<blockquote><b><em>${fn:escapeXml(greeting_title)}</em></b>
			<p> </p>
            ${fn:escapeXml(greeting_content)}</blockquote>

            <%
        	}
        	size += 1;
        }

    }

%>

 

    

 

  </body>

</html>