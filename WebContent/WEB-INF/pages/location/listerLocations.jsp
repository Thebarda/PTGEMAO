<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>

<c:set var="titre" value="Liste des locations" scope="request" />

<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />


<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<h1>Liste des locations</h1>
	<br>
	<form class="offset" method="post" action="#" id="form1">
		Locations en cours : <input type="radio" name="setLocation" value="currentLocs" onclick="document.getElementById('form1').submit()"><br>
		Toutes les locations : <input type="radio" name="setLocation" value="allLocs" onclick="document.getElementById('form1').submit()"><br>
		Année courante : <input type="radio"  name="setLocation" value="currentYear" onclick="document.getElementById('form1').submit()"><br>
	</form>
	<form method="post" action="#" id="form2" class="offset">	
		Choisir l'année : 
		<select name="year" onchange="document.getElementById('form2').submit()">
			<option></option>
			<c:forEach items="${date }" var="year">
				<option value="${year }"><c:out value="${year }"></c:out></option>
			</c:forEach>
		</select>
	</form>
	<br>
	<c:choose >
		<c:when test="${empty vide }">
		<form method="post" action="#">
		<table class='tablesorter-blue  pure-table' id="tableNom">
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
					<th>Options</th>
				</tr>
			</thead>
		<c:forEach items="${typeLocations }" var="loc">
		<tr>
			<td><c:out value="${loc.getLocation().getPersonne().getNom() }"></c:out></td>
			<td><c:out value="${loc.getLocation().getPersonne().getPrenom() }"></c:out></td>
			<td><c:out value="${loc.getLocation().getMateriel().getTypeMat() }"></c:out></td>
			<td><c:out value="${loc.getTypeLocation() }"></c:out></td>
			<td><c:out value="${loc.getLocation().getDateEmprunt() }"></c:out></td>
			<td><c:out value="${loc.getLocation().getDateEcheance() }"></c:out></td>
			<td><c:out value="${loc.getLocation().getDateRetour() }"></c:out></td>
			<td><c:out value="${loc.getLocation().getCaution() }"></c:out></td>
			<td><c:out value="${loc.getLocation().getMontant() }"></c:out></td>
			<td><c:out value="${loc.getLocation().getEtatDebut().getLibelleEtat() }"></c:out></td>
			<td><a href="<c:url value="<%= Pattern.LOCATION_CHEQUE_AJOUTER %>"/>?id=<c:out value="${loc.getLocation().getId()}" />"
					title='Enregistrer un chèque pour cette location'><img src="<c:url value="/ressources/images/cheque.jpg"/>" alt="Enregistrer un chèque"></a>
					<a href="<c:url value="<%= Pattern.LOCATION_RETOUR %>"/>?id=<c:out value="${loc.getLocation().getId()}" />"
					title='Enregistrer un retour'><img src="<c:url value="/ressources/images/retour.png"/>" alt="Enregistrer un retour"></a>
					<a href="<c:url value="<%= Pattern.LOCATION_IMPRIMER %>"/>?id=<c:out value="${loc.getLocation().getId()}" />"
					title='Imprimer le contrat de location'><img src="<c:url value="/ressources/images/print.jpg"/>" alt="Imprimer le contrat de location"></a></td>
		</tr>
		</c:forEach>
		</table>
		</form>
	</c:when>
	<c:otherwise>
		<h2 class="align-center text-danger"><%= request.getAttribute("vide") %></h2>
	</c:otherwise>
</c:choose>
<script src="<c:url value="/js/ListerLocations.js"/>"></script>
<c:import url="/inc/footer.inc.jsp" />