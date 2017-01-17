<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>

<c:set var="titre" value="Liste des contrats de location" scope="request" />

<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />


<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:choose>
	<c:when test="${empty cheque}">
		
		<h1>Ajouter un chèque</h1>
		
		<form id="ajoutCheque" action="#" method="post">
			<fieldset>
				<legend>Informations du chèque</legend>
				
				<div>
					<label for="datePaiement" class='required'>Date de paiement </label> <input type="text"
						name="datePaiement" class="datepicker" value="${cheque.getDatePaiement()}" required="required" />
				</div>
				
				<div>
					<label for="montantCheque" class='required'>Montant du chèque </label> <input type="text"
						name="montantCheque" value="${cheque.getMontantCheque()}" required="required" />
				</div>
				
				<div>
					<label for="numeroCheque" class='required'>Numéro du chèque </label> <input type="text"
						name="numeroCheque" value="${cheque.getNumCheque()}" required="required" />
				</div>
				
				<div>
					<label for="dateEncaissement" class='required'>Date d'encaissement </label> <input type="text"
						name="dateEncaissement" class="datepicker" value="${cheque.getDateEncaissement()}" required="required" />
				</div>
				
				
				
			
			</fieldset>
			
			<fieldset class='align-center no-border'>
				<p class="oblig">* Champs obligatoires</p>
				<a href="<c:url value="<%= Pattern.ACCUEIL %>"/>">Annuler</a>
				<input type="submit" class="btn" value="Enregistrer" />
			</fieldset>
		</form>
	</c:when>
	<c:otherwise>
		<c:choose>
			<c:when test="${empty validation}">
				<h1>Ajouter un chèque - Récapitulatif</h1>
				
				<form method="post" action="#">
					<fieldset>
						<legend>Validation des informations</legend>
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
					</fieldset>
					<fieldset class='align-center no-border'>
						<a href="<c:url value="<%= Pattern.ACCUEIL %>"/>">Annuler</a>
						<input type="submit" value="Valider" />
					</fieldset>
				</form>
			</c:when>
			<c:otherwise>
				<h1>Validation</h1>
				<p class="offset text-success">Le chèque a bien été enregistré</p>
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>



<c:import url="/inc/footer.inc.jsp" />
