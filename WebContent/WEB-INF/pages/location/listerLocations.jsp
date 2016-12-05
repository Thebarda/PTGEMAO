<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>

<c:set var="titre" value="Liste des locations" scope="request" />

<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />


<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<h1>Lister les location en cours</h1>
<br>
<div class="offset"><span>Trier par date : </span><input type="checkbox" id="changeTable"></div>
<br>
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
</tr>
</c:forEach>
</table>

<table class='tablesorter-blue  pure-table' id="tableDate">
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
	<tbody>
<c:forEach items="${date }" var="loc">
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
</tr>
</c:forEach>
</tbody>
</table>
<script src="<c:url value="/js/ListerLocations.js"/>"></script>
<c:import url="/inc/footer.inc.jsp" />