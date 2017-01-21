<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>

<c:set var="titre" value="Liste des chèques de location" scope="request" />

<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />


<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<h1>Enregistrement de la date d'encaissement effective</h1>

<c:choose>
	<c:when test="${empty resultat }">
		<form method="post" action="#" class="offset">
			<fieldset>
				<legend>Chèque</legend>
				<p>Date de paiement : <c:out value="${cheque.getDatePaiement() }"></c:out></p>
				<p>Montant : <c:out value="${cheque.getMontantCheque() }"></c:out></p>
				<p>Numéro : <c:out value="${cheque.getNumCheque() }"></c:out></p>
				<p>Date d'encaissement prévue : <c:out value="${cheque.getDateEncaissement() }"></c:out></p>
				<br>
				Date d'encaissement effective : <input type="text" name="DEE" class="datepicker" required="required">
				
			</fieldset>
			<fieldset class='align-center no-border'>
				<a href="<c:url value="<%= Pattern.ACCUEIL %>"/>">Annuler</a>
				<input id="valider" type="submit" value="Valider"/>
			</fieldset>
		</form>
	</c:when>
	<c:otherwise>
		<p class="offset text-success">Date d'encaissement effective enregistrée !</p>
		<a class="offset btn" href="<c:url value="<%= Pattern.LOCATION_CHEQUE_LISTER %>"/>">Retour</a>
	</c:otherwise>
</c:choose>

<c:import url="/inc/footer.inc.jsp" />