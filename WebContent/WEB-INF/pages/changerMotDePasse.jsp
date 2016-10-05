<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>

<c:set var="titre" value="Changement de mot de passe" scope="request" />

<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />

<h1>Changer son mot de passe</h1>

<form method="post" action="<c:url value="<%= Pattern.CHANGER_MOT_DE_PASSE %>" />">
	<fieldset>
		<legend>Informations</legend>
		
		<div>
			<label for="ancien" class='required'>Saisir l'ancien mot de passe </label>
			<input type="password" name="ancien" required/>
		</div>
		
		<div class='align-center text-danger'><c:out value="${requestScope.form.erreurs['ancien'] }"></c:out> </div>
		
		<div>
			<label for="nouveau1" class='required'>Saisir le nouveau mot de passe </label>
			<input type="password" name="nouveau1" required pattern="[a-zA-Z0-9,?;.:/!&é'(-è)_çà]{6,}" title="Le mot de passe doit contenir au moins 6 caractères (lettres ou chiffres). Les caractères spéciaux autorisés sont les suivants : , ? ; . : / ! & é ' ( ) - è _ ç à"/>
		</div>
		
		<div>
			<label for="nouveau2" class='required'>Re-saisir le nouveau mot de passe </label>
			<input type="password" name="nouveau2" required pattern="[a-zA-Z0-9,?;.:/!&é'(-è)_çà]{6,}" title="Le mot de passe doit contenir au moins 6 caractères (lettres ou chiffres). Les caractères spéciaux autorisés sont les suivants : , ? ; . : / ! & é ' ( ) - è _ ç à"/>
		</div>		
		
		<div class='align-center text-danger'><c:out value="${requestScope.form.erreurs['nouveau'] }"></c:out> </div>
	</fieldset>
	
	<fieldset class='align-center no-border'>
		<p class="oblig">* Champs obligatoires</p>
		<input type="submit" value="Valider"/>
	</fieldset>
</form>
	
<c:import url="/inc/footer.inc.jsp" />