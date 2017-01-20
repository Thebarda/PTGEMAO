<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>

<c:set var="titre" value="Liste des chèques de location" scope="request" />

<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />


<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<h1>Liste des chèques</h1>

		<form method="post" action="#" id="formDate" class="offset">	
			Choisir le mois et l'année : 
			<select name="month" required='required'>
				<option></option>
				<c:forEach items="${lesMois}" var="mois">
					<option value="${mois}"><c:out value="${mois}"></c:out></option>
				</c:forEach>
			</select>
			<select name="year" required='required'>
				<option></option>
				<c:forEach items="${lesAnnees}" var="annee">
					<option value="${annee}"><c:out value="${annee}"></c:out></option>
				</c:forEach>
			</select>
			<input type="submit" class="btn" value="Trier" />
		</form>

<br>

<table class='tablesorter-blue  pure-table' id="tableNom">
	<thead>
		<tr>
			<th>N° Contrat</th>
			<th>Type de location</th>
			<th>Nom locataire</th>
			<th>Référence instrument</th>
			<th>Date paiement</th>
			<th>Montant chèque</th>
			<th>N° du chèque</th>
			<th>Date encaissement</th>
			<th>Options</th>
		</tr>
	</thead>
	<c:choose>
		<c:when test="${empty chequesParMoisAnnee}">
			<c:forEach items="${cheques}" var="cheque">
				<tr>
					<td><c:out value="${cheque.getLocation().getId() }"></c:out></td>
					<td><c:out value="${cheque.getTypeLocation() }"></c:out></td>
					<td><c:out value="${cheque.getLocation().getPersonne().getNom()}"></c:out></td>
					<td><c:out value="${cheque.getLocation().getMateriel().getNumSerie() }"></c:out></td>
					<td><c:out value="${cheque.getDatePaiement() }"></c:out></td>
					<td><c:out value="${cheque.getMontantCheque() }"></c:out></td>
					<td><c:out value="${cheque.getNumCheque() }"></c:out></td>
					<td><c:out value="${cheque.getDateEncaissement() }"></c:out></td>
					<td><a href="<c:url value="<%= Pattern.LOCATION_CHEQUE_SUPPRIMER %>"/>?numCheque=<c:out value="${cheque.getNumCheque()}" />"
							title='Supprimer cet enregistrement de cheque'><img src="<c:url value="/ressources/images/supprimer.jpg"/>" alt="Supprimer enregistrement cheque"></a></td>
				</tr>
			</c:forEach>
		</c:when>
		<c:otherwise>
			<a class="offset btn" href="<c:url value="<%= Pattern.LOCATION_CHEQUE_LISTER %>"/>"/>Lister tous les chèques</a>
			<br>
			<br>
			<c:forEach items="${chequesParMoisAnnee}" var="cheque">
				<tr>
					<td><c:out value="${cheque.getLocation().getId() }"></c:out></td>
					<td><c:out value="${cheque.getTypeLocation() }"></c:out></td>
					<td><c:out value="${cheque.getLocation().getPersonne().getNom()}"></c:out></td>
					<td><c:out value="${cheque.getLocation().getMateriel().getNumSerie() }"></c:out></td>
					<td><c:out value="${cheque.getDatePaiement() }"></c:out></td>
					<td><c:out value="${cheque.getMontantCheque() }"></c:out></td>
					<td><c:out value="${cheque.getNumCheque() }"></c:out></td>
					<td><c:out value="${cheque.getDateEncaissement() }"></c:out></td>
					<td><a href="<c:url value="<%= Pattern.LOCATION_CHEQUE_SUPPRIMER %>"/>?numCheque=<c:out value="${cheque.getNumCheque()}" />"
							title='Supprimer cet enregistrement de cheque'><img src="<c:url value="/ressources/images/supprimer.jpg"/>" alt="Supprimer enregistrement cheque"></a></td>
				</tr>
			</c:forEach>
		</c:otherwise>
	</c:choose>
</table>

<c:import url="/inc/footer.inc.jsp" />
