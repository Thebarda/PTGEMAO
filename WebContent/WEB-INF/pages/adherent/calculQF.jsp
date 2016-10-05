<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="titre" value="Calcul du Quotient Familial" scope="request" />

<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />
<h1>Calcul du quotient familial</h1>
<form action="#" method="post">
	<fieldset>
		<div>
			<label for="nbPers" class='required'>Nombre de personnes dans le foyer </label>
			<input type="number" name="nbPers" required="required" min="1" value="1" autocomplete="off" />
		</div>
		<div>
			<label for="nbEnf" class='required'>dont enfant(s) </label>
			<input type="number" name="nbEnf" required="required" min="0" value="0" autocomplete="off" />
		</div>
		<div>
			<label for="revenues" class='required'>Revenus annuels (bruts) </label>
			<input class='align-right' type="text" pattern="[0-9]*[0-9,.][0-9]*" name="revenues" required="required" autocomplete="off" />
			<span class='euro'></span>
		</div>
	</fieldset>
	<fieldset class='align-center no-border'>
		<p class="oblig">* Champs obligatoires</p>
		<input type="button" class="btn" value="Annuler" /> 
		<input type="submit" class="btn" value="Valider" />
	</fieldset>
</form>
<c:if test="${ erreurPers == true }">
	<p class="align-center text-danger">Le nombre d'enfants ne peut être supérieur au nombre de personnes.</p>
</c:if>
<c:if test="${ ! empty simpleQuotient }">
	<p class="align-center">Montant du quotient familial : <c:out value="${simpleQuotient}" /><span class="euro"></span>,  <c:out value="${QF}" /></p>
</c:if>
<c:import url="/inc/footer.inc.jsp" />
