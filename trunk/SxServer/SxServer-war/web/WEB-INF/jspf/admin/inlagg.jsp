<%-- 
    Document   : newdocument
    Created on : 2009-feb-23, 09:23:51
    Author     : ulf
--%>
<%@ page import="se.saljex.sxserver.SXUtil" %>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="java.sql.*" %>

<%
	Connection con = (java.sql.Connection)request.getAttribute("con");
	InlaggHandler ih = new InlaggHandler(con);
	ih.setupFromRequest(request);

	final String JSP = "dokument";



	if (ih.ACTION_NEW.equals(ih.getAction())) {
%>
	<h1>Spara nytt dokument</h1>
	<form action="?id=<%= JSP %>&action=<%= ih.ACTION_DONEW %>" method="post">
		<table>
			<tr><td>Kanal:</td><td><select name="<%= ih.K_KANALID %>"> <%= ih.getKanalerOptionList() %>"></select></td></tr>
			<tr><td>Rubrik:</td><td><input type="text" name="<%= ih.K_RUBRIK %>" value="<%= SXUtil.toStr(ih.getRubrik()) %>"></td></tr>
			<tr><td>Ingress:</td><td><input type="text" name="<%= ih.K_INGRESS %>" value="<%= SXUtil.toStr(ih.getIngress()) %>"></td></tr>
			<tr><td>Brödtext:</td><td><input type="text" name="<%= ih.K_BRODTEXT %>" value="<%= SXUtil.toStr(ih.getBrodtext()) %>"></td></tr>
			<tr><td>Visa tom:</td><td><input type="text" name="<%= ih.K_VISATILL %>" value="<%= SXUtil.getFormatDate(ih.getVisaTill()) %>"></td></tr>
			<tr><td>
				<input type="hidden" name="<%= ih.K_INLAGGID %>" value="">
			</td></tr>
		</table>
	</form>
<% } else if (ih.ACTION_UPDATE.equals(ih.getAction()))  { %>

<% } else if (ih.ACTION_DELETE.equals(ih.getAction()))  { %>

<% } else  { %>
	Ogiltig åtgärd (Action)<p/>
<% } %>

