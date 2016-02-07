<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="java.util.List" %>

<%@ page import="java.util.Collections" %>

<%@ page import="guestbook.Greeting" %>

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

<p>Hello, ${fn:escapeXml(user.nickname)}! (You can

<a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">sign out</a>.)</p>
    
   	<div style="text-align:center">	
		<a href="/blogEntry.jsp">Make a Post!</a>
	</div>    

<%

    } else {

%>

<p>Hello!

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

        for (Greeting greeting : greetings) {

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

                <p><b>${fn:escapeXml(greeting_user.nickname)}</b> wrote:</p>

                <%

            }

            %>

            <blockquote>${fn:escapeXml(greeting_content)}</blockquote>

            <%

        }

    }

%>

 

    

 

  </body>

</html>