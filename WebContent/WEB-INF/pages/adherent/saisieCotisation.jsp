<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="titre" value="Saisie de la cotisation" scope="request" />

<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />

<h1>Saisie de la cotisation</h1>

<c:choose>
	<c:when test="${adherent.getQf()==null}">
		<p class="offset">L'adhérent ne bénéficie pas du quotient familial</p>
	</c:when>
	<c:when test="${params.getQf_min() > adherent.getQf()}">
		<p class="offset">L'adhérent bénéficie du Quotient 3</p>
	</c:when>
	<c:when test="${params.getQf_max() > adherent.getQf()}">
		<p class="offset">L'adhérent bénéficie du Quotient 2</p>
	</c:when>
	<c:otherwise>
		<p class="offset">L'adhérent bénéficie du Quotient 1</p>
	</c:otherwise>
</c:choose>

<form action="#" method="post">
	<fieldset>
		<div>
			<label for="cotisation" class='required'>Montant de la cotisation </label>
			<input type="number" name="cotisation" required="required" min="0" value="0" autocomplete="off" />
			<span class="euro"></span>
		</div>
	</fieldset>
	<fieldset class='align-center no-border'>
		<p class="oblig">* Champs obligatoires</p>
		<input type="submit" class="btn" value="Valider" />
	</fieldset>
</form>


<c:import url="/inc/footer.inc.jsp" />