<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>

<c:set var="titre" value="Liste des contrats de location" scope="request" />

<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />


<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<h1>Supprimer un chèque</h1>

<c:choose>
	<c:when test="${empty validation}">
		<form method="post" action="#">
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
					</tr>
				</thead>
				<tr>
					<td><c:out value="${cheque.getLocation().getId() }"></c:out></td>
					<td><c:out value="${cheque.getTypeLocation() }"></c:out></td>
					<td><c:out value="${identiteLocataire}"></c:out></td>
					<td><c:out value="${cheque.getLocation().getMateriel().getNumSerie() }"></c:out></td>
					<td><c:out value="${cheque.getDatePaiement() }"></c:out></td>
					<td><c:out value="${cheque.getMontantCheque() }"></c:out></td>
					<td><c:out value="${cheque.getNumCheque() }"></c:out></td>
					<td><c:out value="${cheque.getDateEncaissement() }"></c:out></td>
				</tr>
			</table>
			<fieldset class='align-center no-border'>
				<p>Êtes vous sur de vouloir supprimer cet enregistrement de chèque ?</p>
				<a href="<c:url value="<%= Pattern.ACCUEIL %>"/>">Annuler</a>
				<input type="submit" value="Oui" />
			</fieldset>
		</form>
	</c:when>
	<c:otherwise>
		<p class="offset text-success">Le chèque a bien été supprimé</p>
	</c:otherwise>
</c:choose>

<c:import url="/inc/footer.inc.jsp" />