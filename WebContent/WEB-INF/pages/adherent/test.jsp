<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="titre" value="Saisie de la cotisation" scope="request" />

<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />


<form action="#" method="post">
	<input type="text" name="test"/>
	
	<input type="submit" value="Valider"/>
</form>

<% for (int i=0; i<100; i++) { %>
	<%= i %> <br/>
<% } %>

<c:import url="/inc/footer.inc.jsp" />