<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>

<c:set var="titre" value="Liste des locations" scope="request" />

<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<h1>Lister les location en cours</h1>
<table>
	<thead>
		<tr>
			<th>Nom</th>
			<th>Prénom</th>
			<th>Instrument</th>
			<th>Type Location</th>
			<th>Date d'emprunt</th>
			<th>Date d'échéance</th>
			<th>Date de retour</th>
			<th>Caution</th>
			<th>Montant</th>
			<th>Etat début</th>
		</tr>
	</thead>
<c:forEach items="${locations }" var="loc">
<tr>
	<td><c:out value="${loc.getDateEmprunt() }"></c:out>
</tr>
</c:forEach>
</table>

<c:import url="/inc/footer.inc.jsp" />