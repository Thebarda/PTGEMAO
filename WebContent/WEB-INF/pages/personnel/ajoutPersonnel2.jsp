<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>

<c:set var="titre" value="Ajout d'un membre du personnel - Contrat"
	scope="request" />

<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />

<script src="<c:url value="/js/AjouterPersonnel2.js"/>"></script>

<h1>Ajout d'un membre du personnel</h1>

<!-- 2eme partie du formulaire -->
<form id="ajoutp2" method="post"
	action="<c:url value="<%=Pattern.PERSONNEL_AJOUT2%>" />">
	<fieldset>
		<div>
			<label>Nom : </label> <span><c:out value="${personnel['nom']}" /></span>
		</div>
		<div>
			<label>Prénom : </label> <span><c:out
					value="${personnel['prenom']}" /></span>
		</div>
		<div>
			<label for="datedebEns" class="required">Date de début
				d'enseignement : </label> <input type="text" name="datedebEns"
				class="datepicker" required />
		</div>
		<div id="contrats">
			<div id="divContrat1">
				<p>Contrat 1</p>
				<label for="type">Type de contrat : </label> 
				<select name="type1" id="type1" onChange="afficherDuree()">
					<option value="1">CDI</option>
					<option value="2">CDD</option>
					<option value="4">CTT</option>
					<option value="3">Bénévole</option>
				</select> 
				<label for="datedeb" class="required">Date de début : </label> 
				<input type="text" name="datedeb1" id="datedeb1" class="datepicker" required />
				<div id="duree1" hidden=hidden>
					<label for="duree">Durée : </label> 
					<input type="number" name="dureeContrat1" id="dureeContrat1" min="1" value="1" /> mois
				</div>
				<input type="button" style="margin-left:50%" value="+" id="ajoutContrat" /> 
				<input type="button" value="-" id="retireContrat" />
			</div>
		</div>
	</fieldset>
	<fieldset class='align-center no-border'>
		<a href="<c:url value="<%=Pattern.PERSONNEL_AJOUT%>" />"><input
			type="button" value="Retour" /></a> <input type="submit" value="Valider" />
	</fieldset>
</form>
<c:import url="/inc/footer.inc.jsp" />

