<%@ page import="se.saljex.sxserver.*" %>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="se.saljex.sxserver.websupport.*" %>
<%@ page import="se.saljex.sxlibrary.SXSession" %>
<%@ page import="se.saljex.sxlibrary.WebSupport" %>

<% 
SXSession sxSession = WebSupport.getSXSession(session);
String anv = "";
if (sxSession.getKundLoginNamn() != null) {
	anv = sxSession.getKundKontaktNamn() + ", " + sxSession.getKundnamn();
} else if (sxSession.getLevnr() != null) {
	anv = sxSession.getLevnamn();
} else if (sxSession.getIntraAnvandare() != null) {
	anv = sxSession.getIntraAnvandare();
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
               "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=ISO-8859-1" />
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
				<ul>
				 <li><a href="/">Säljex AB</a></li>
				 <li>
					<%
					if (sxSession.getInloggad()) {
					 %> <a href="login?action=logout">Logga ut</a>&nbsp;<span id="siteanvandare">Inloggad som: <%= anv %></span> <%
					}
					%>

				 </li>
				 <li><a href="kund">Kund</a></li>
				 <li>Leverantör</li>
				 <li><a href="intra">Internt</a></li>
			  </ul>
				
			 </div>
