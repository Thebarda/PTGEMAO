<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>
<%@ page import="fr.gemao.entity.cours.Salle"%>

<c:set var="titre" value="Afficher la liste des salles" scope="request" />

<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<h1>Créer une salle</h1>
	
	<form class="pure-form pure-form-aligned" id="ajoutSalle" method="post" action="<c:url value="<%= Pattern.SALLE_CREER %>" />">
		<fieldset>
			<div class="pure-control-group">
				<label for="nomSalle" class="required">Nom</label>
				<input id="nomSalle" name="nomSalle" type="texte" placeholder="Salle" required="required" />
			</div>
		</fieldset>
		
		<fieldset class='align-center no-border'>
			<div>
				<p class="oblig">* Champs obligatoires</p>
				<input type="submit" name="valider" value="Valider" />
			</div>
		</fieldset>
	</form>

<c:import url="/inc/footer.inc.jsp" />
