<%@ page import="se.saljex.sxserver.*" %>
<%@ page import="se.saljex.sxserver.web.*" %>

<% 
SXSession sxSession = WebUtil.getSXSession(session);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
               "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<title>Säljex AB</title>
		<link href="style.css" type="text/css" rel="stylesheet" />
		<meta content="Säljex AB" name="description" />
		<meta content="säljex,logga in, kund" name="keywords" />
		<meta http-equiv="Pragma" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>
<script type="text/javascript" src="jquery.js"></script>          
	</head>
	<body>
		<div id="container">
			 <div id="siteheader">
				  <ul><li>
				  <a href="/">Säljex AB</a>
				</li><li>
					<% 
					if (sxSession.getInloggad()) {
					 %> Inloggad som: <span id="siteanvandare"><%= sxSession.getAnvandare() %></span><span id="sitekundnamn"><%= sxSession.getKundnamn() %></span> <%
					} else {
					  %> <a href="login">Logga in</a> <%
					} 
					%>
				 </li><li>Kund</li><li>lev</li><li>internt</li>
			  </ul>
				  
			 </div>
