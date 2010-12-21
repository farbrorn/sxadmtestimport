<%-- 
    Document   : newdocument
    Created on : 2009-feb-23, 09:23:51
    Author     : ulf
--%>
<%@ page import="se.saljex.sxlibrary.*" %>
<%@ page import="se.saljex.sxlibrary.WebSupport" %>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="se.saljex.sxserver.websupport.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="se.saljex.sxlibrary.SXSession" %>

Inlägg<br/>
<%
	Connection con = (java.sql.Connection)request.getAttribute("con");
	SXSession sXSession = WebSupport.getSXSession(session);
	InlaggHandler ih = new InlaggHandler(con);
	ih.setupFromRequest(request);
	// Kolla om vi har en DO-request och utför den. Efter utförande har action-åtgärden ändrats
	// till antingen DONE eller originalet för att försöka igen
	ih.processDoAction(sXSession.getIntraAnvandareKort());
	ResultSet rs;

	final String JSP = "inlagg";


	if (ih.ACTION_NEW.equals(ih.getAction())) {
%>
	<h1>Spara nytt dokument</h1>
	<% if (!ih.getFormErrorString().isEmpty()) { %> <div id="errortext"> <%= ih.getFormErrorString() %> </div> <% } %>
	<form action="?id=inlagg&<%= ih.K_ACTION %>=<%= ih.ACTION_DONEW %>" method="post" enctype="multipart/form-data">
		<table>
			<tr><td>
			<input type="hidden" name="<%= ih.K_INLAGGID %>" value=""/>
			</td></tr>

			<tr><td>Kanal:</td><td><select name="<%= ih.K_KANALID %>"> <%= ih.getKanalerOptionList() %></select></td></tr>
			<tr><td>Rubrik:</td><td><input type="text" name="<%= ih.K_RUBRIK %>" value="<%= ih.getFormStringRubrik() %>"/></td></tr>
			<tr><td>Ingress:</td><td><input type="text" name="<%= ih.K_INGRESS %>" value="<%= ih.getFormStringIngress() %>"/></td></tr>
			<tr><td>Brödtext:</td><td><input type="text" name="<%= ih.K_BRODTEXT %>" value="<%= ih.getFormStringBrodtext() %>"/></td></tr>
			<tr><td>Visa tom:</td><td><input type="text" name="<%= ih.K_VISATILL %>" value="<%= ih.getFormStringVisaTill() %>"/></td></tr>
			<tr><td>Bifoga fil:</td><td><input type="file" name="<%= ih.K_ORIGINALFILENAME %>" value=""/></td></tr>
			<tr><td>
				<input type="submit" value="OK"/>
			</td></tr>
		</table>
	</form>
<% } else if (ih.ACTION_DONEW_DONE.equals(ih.getAction()))  { %>
Nytt inlägg sparat!<p/>
<a href="?id=<%= JSP %>&action=<%= ih.ACTION_LIST %>">Visa lista</a><br/>
<% } else if (ih.ACTION_UPDATE.equals(ih.getAction()))  { %>

<% } else if (ih.ACTION_DELETE.equals(ih.getAction()))  { %>

<% } else if (ih.ACTION_LIST.equals(ih.getAction()))  {
ArrayList<se.saljex.sxserver.web.InlaggHandler.IntraInlagg> iharr = ih.getInlaggListByKanalId(null);

%>			<table>	
				<tr><th></th><th></th><th>Inläggid</th><th>Kanal</th><th>Rubrik</th><th>Ingress</th><th>Filnamn</th><th>Originalfilnamn</th><th>Skapad av</th><th>Skapad datum</th>
<%
		for (se.saljex.sxserver.web.InlaggHandler.IntraInlagg inl : iharr ) {
%>
				<tr>
					<td><a href="?id=<%= JSP %>&action=<%= ih.ACTION_UPDATE %>&<%= ih.K_INLAGGID %>=<%= inl.inlaggId %>">Ändra</a></td>
					<td><a href="?id=<%= JSP %>&action=<%= ih.ACTION_DELETE %>&<%= ih.K_INLAGGID %>=<%= inl.inlaggId %>">Radera</a></td>
					<td><%= inl.inlaggId %></td>
					<td><%= SXUtil.toHtml(inl.kanal_rubrik) %></td>
					<td><%= SXUtil.toHtml(inl.rubrik) %></td>
					<td><%= SXUtil.toHtml(inl.ingress) %></td>
					<td><%= SXUtil.toHtml(inl.fileName) %></td>
					<td><%= SXUtil.toHtml(inl.originalFileName) %></td>
					<td><%= SXUtil.toHtml(inl.anvandareKort) %></td>
					<td><%= SXUtil.toHtml(inl.crTime.toString()) %></td>
				</tr>
<%		}	%>
		</table>
		<a href="?id=<%= JSP %>&action=<%= ih.ACTION_NEW %>">Nytt</a><br/>

<%	} else  { %>
	Ogiltig åtgärd (Action)<p/>
<% } %>
