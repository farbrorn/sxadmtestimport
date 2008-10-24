<%-- 
    Document   : printkundinfo
    Created on : 2008-jun-16, 19:42:28
    Author     : ulf
--%>
<%@ page import="se.saljex.sxserver.*" %>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="java.util.List" %>

<% 
SXSession sxSession = WebUtil.getSXSession(session);

String divInfo = (String)request.getAttribute("divinfo");
if (divInfo == null) divInfo = "";
%>
<div <%= divInfo %>>

<h1>Logga in</h1>
<form action="?id=2" method="get">
<table>
<tr>
	<td>Beställningsnummer<br/>Purchase order number</td><td><input type="text" name="bnr"></td>
</tr>
<tr>
	<td>Säkerhetskod<br/>Safety code</td><td><input type="text" name="skd"></td>
</tr>
<tr>
	<td colspan="2"><input type="submit" value="OK" name="ok"></td>
</tr>
</table>
</form>
</div>
