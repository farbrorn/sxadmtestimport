<jsp:useBean id="fak" scope="request" class="se.saljex.sxserver.web.FakturaBean"/>
bönan
<%
//se.saljex.sxserver.web.FakturaBean f = (se.saljex.sxserver.web.FakturaBean)request.getAttribute("fak");
//if (f == null) { out.println("Hittar inte faktbean"); }
fak.setFirstRow();
	out.println("firstrow");	

while (fak.next()) {
	%>
	 <%= fak.getArtnr() %>
//	 ${ fak.artnr } 
//	 <jsp:getProperty name="fak" property="namn"/>
<% }	 


		

			
			%>
