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
   <link type="text/css" rel="stylesheet" href="/stylesheets/landingPage.css" />
  </head>
 

  <body>
	<h1 style="letter-spacing:10px";>MR. BLOG</h1>
	
	<p>Please enter your blog post with a title.</p> 
    
    <form action="/ofysign" method="post">
    
      <div><textarea name="title" rows="1" cols="120"></textarea></div>
      
      <p> </p>
	
      <div><textarea name="content" rows="25" cols="120"></textarea></div>
      
	  <p> </p>

      <div><input type="submit" value="Post!" /></div>

    </form>  
	
  </body>

</html>