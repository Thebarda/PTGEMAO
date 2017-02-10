<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>

<c:set var="titre" value="Ajouter cheques partenaires" scope="request" />

<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />


<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<h1>Ajouter un partenaire</h1>
<c:choose>
<c:when test="${empty validation }">
<form method="post" action="#" class="offset">
	<fieldset>
		<legend>Cheque</legend>
		<label>Date de paiement *: </label><input type="text" name="datePaiement" required="required" class="datepicker"><br>
		<label>Numéro de cheque (11 caractères) *: </label><input type="text" name="numero" required="required"><br>
		<label>Montant de chèque *: </label><input type="text" name="montant" required="required"><br>
		<label>Date d'encaissement prévu*: </label><input type="text" name="dateEncaissement" required="required" class="datepicker"><br>
		<label>Partenaire *: </label><input type="text" name="partenaire" required="required"><br>
		<input type="submit" value="Valider" class="btn">
	</fieldset>
</form>
</c:when>
<c:otherwise>
	<c:choose>
	<c:when test="${validation==false }">
		<form action="#" method="post" class="offset">
			<fieldset>
				<legend>Cheque</legend>
				<p><b> Date de paiement : </b><c:out value="${cheque.getDatePaiement() }"></c:out></p>
				<p><b> Numéro de chèque : </b><c:out value="${cheque.getNumero() }"></c:out></p>
				<p><b> Montant de chèque : </b><c:out value="${cheque.getMontant() }"></c:out></p>
				<p><b> Date d'encaissement : </b><c:out value="${cheque.getDateEncaissement() }"></c:out></p>
				<p><b> Partenaire : </b><c:out value="${cheque.getPartenaire() }"></c:out></p>
				<input type="submit" value="Valider" class="btn">
			</fieldset>
		</form>
	</c:when>
	<c:otherwise>
		<h3 class="offset text-success">Partenaire ajouté !</h3>
		<a class="offset btn" href="<c:url value="<%= Pattern.COMPTABILITE_LISTER_CHEQUES %>"/>"/>Lister les chèques</a>
	</c:otherwise>
	</c:choose>
</c:otherwise>
</c:choose>

<c:import url="/inc/footer.inc.jsp" />