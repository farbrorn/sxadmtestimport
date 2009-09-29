<%-- 
    Document   : sokserviceombud
    Created on : 2009-jul-29, 07:26:00
    Author     : ulf
--%>

<%@ page import="se.saljex.sxserver.*" %>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="se.saljex.sxserver.tables.*" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>

<%
Integer rows = null;
String sokstr;
sokstr = "%"+request.getParameter("sokstr")+"%";
try { rows = Integer.parseInt(request.getParameter("rows")); } catch (NumberFormatException e) {}
if (rows==null || rows < 1) rows=10;
Connection con = (Connection)request.getAttribute("con");
PreparedStatement stm = con.prepareStatement("select nummer, namn from kund " +
		  " where ucase(nummer) like ucase(?)" +
		  " or ucase(namn) like ucase(?)" +
		  " or ucase(adr3) like ucase(?)" +
		  " or ucase(tel) like ucase(?)" +
		  " or ucase(biltel) like ucase(?)" +
		  " order by nummer" +
		  " limit " + rows);
stm.setString(1, sokstr);
stm.setString(2, sokstr);
stm.setString(3, sokstr);
stm.setString(4, sokstr);
stm.setString(5, sokstr);
ResultSet rs =stm.executeQuery();
%>
<table>
	<%
	while (rs.next()) {
	%>
	<tr>
		<td><a href="javascript:setSomb('<%= rs.getString(1) %>', '<%= rs.getString(2) %>');"><%= rs.getString(2) %></a></td>
	</tr>
	<%
	}
	%>
</table>