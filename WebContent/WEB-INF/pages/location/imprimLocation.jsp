<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>

<c:set var="titre" value="imprimer contrat" scope="request" />

<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />
<h1>Imprimer contrat de location</h1>
<c:choose>
	<c:when test="${empty printerError }">
		<p class="offset">Le contrat de location est en cours d'impression</p>
	</c:when>
	<c:otherwise>
		<p class="offset text-danger"><%= request.getAttribute("printerError") %></p>
	</c:otherwise>
</c:choose>
<a class="offset btn" href="<c:url value="<%= Pattern.LOCATION_LISTER %>"/>">Retour</a>
 <c:import url="/inc/footer.inc.jsp" />