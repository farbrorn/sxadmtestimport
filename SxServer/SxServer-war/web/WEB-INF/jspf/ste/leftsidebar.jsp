<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="se.saljex.sxserver.websupport.*" %>
<%@ page import="se.saljex.sxlibrary.*" %>
<%@ page import="se.saljex.sxlibrary.SXSession" %>

<%
Connection con = (Connection)request.getAttribute("con");
SXSession sxSession = WebSupport.getSXSession(request.getSession());
%>
<b>Stiebel Eltron</b>
<p/>
<a href="?id=welcome">Startsida</a><p/>
<p/>
<b>Produkter</b><br/>
<a href="?id=produkt">Lista</a><br/>
<a href="?id=produkt&action=<%= FormHandlerSteprodukt.ACTION_FOLJUPPLIST %>">Lista för uppföljning</a><br/>
<a href="?id=produkt&action=new">Lägg till</a><br/>
<p/>
<b>Listor</b><br/>
<a href="?id=forsakringslista">Redovisning försäkring</a><br/>
<p/>
<b>STE Artikelnummer</b><br/>
<a href="?id=pumpartnr">Lista</a><br/>
<a href="?id=pumpartnr&action=new">Lägg till</a><br/>
<p/>
<b>Statistik</b><br/>
<a href="?id=ste-statistik">Försäljning</a><br/>
<a href="?id=ste-topplistaartikel&lagernr=alla&inputform=true">Artikel</a><br/>
