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
		<legend>Partenaire</legend>
		<label>Raison sociale *: </label><input type="text" name="raisonSociale" required="required"><br>
		<label for="num">N° *: </label> <input type="text" name="num"/><br>
		<label for="rue" class='required'>Rue *: </label> <input type="text" name="rue" id="rue" required="required"/><br>
		<label for="compl">Complément d'adresse : </label> <input type="text" name="compl" id="compl"/><br>
		<label for="commune" class='required'>Commune *: </label> <input type="text" name="commune" required="required" id="commune"/><br>
		<label for="codePostal" class='required'>Code postal *: </label> <input type="text" name="codePostal" id="codePostal" required="required" maxlength="5" pattern="\d*"/><br>
		<label>Année de partenariat *: </label><select name="annee">
			<c:forEach items="${annees}" var="annee">
				<option value="${annee }"><c:out value="${annee }"></c:out></option>
			</c:forEach>
		</select><br>
		<label>Taille de la page *: </label><input type="text" name="taillePage" required="required"><br>
		<input type="submit" value="Valider" class="btn">
		<a href="<c:url value="<%= Pattern.ACCUEIL %>"/>">Annuler</a>
	</fieldset>
</form>
</c:when>
<c:otherwise>
	<c:choose>
	<c:when test="${validation==false }">
		<form action="#" method="post" class="offset">
			<fieldset>
				<legend>Partenaire</legend>
				<p><b> Raison sociale : </b><c:out value="${partenaire.getRaisonSociale() }"></c:out></p>
				<p><b> N° : </b><c:out value="${partenaire.getAdresse().getNumRue() }"></c:out></p>
				<p><b>Rue : </b><c:out value="${partenaire.getAdresse().getNomRue() }"></c:out></p>
				<p><b>Complément d'adresse : </b><c:out value="${partenaire.getAdresse().getInfoCompl() }"></c:out></p>
				<p><b>Commune : </b> <c:out value="${partenaire.getAdresse().getCommune().getNomCommune() }"></c:out> </p>
				<p><b>Code postal : </b> <c:out value="${partenaire.getAdresse().getCommune().getCodePostal() }"></c:out> </p>
				<p><b> Annee de partenariat : </b><c:out value="${partenaire.getAnnee() }"></c:out></p>
				<p><b> Taille de la page : </b><c:out value="${partenaire.getTaillePage() }"></c:out></p>
				<input type="submit" value="Valider" class="btn">
				<a href="<c:url value="<%= Pattern.ACCUEIL %>"/>">Annuler</a>
			</fieldset>
		</form>
	</c:when>
	<c:otherwise>
		<h3 class="offset text-success">Partenaire ajouté !</h3>
		<a class="offset btn" href="<c:url value="<%= Pattern.ACCUEIL %>"/>"/>Accueil</a>
	</c:otherwise>
	</c:choose>
</c:otherwise>
</c:choose>

<c:import url="/inc/footer.inc.jsp" />