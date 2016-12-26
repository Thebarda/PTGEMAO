<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>

<c:set var="titre" value="Matériel" scope="request" />

<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />
	<form id="retour" method="post" action="#">
		<h1>Enregistrer le retour d'un instrument</h1>
		<fieldset>
			<legend>Location</legend>
			<div>
				<label for="typeLocation">Type de location : </label>
				<span><c:out value="${typeLocation }"></c:out></span>
			</div>
		</fieldset>
		<fieldset>
			<legend>Instrument</legend>
			<div>
				<label for="categorie">Catégorie :</label>
				<span><c:out value="${categorie }"></c:out></span>
			</div>
			<div>
				<label for="designation">Désignation :</label>
				<span><c:out value="${designation }"></c:out></span>
			</div>
			<div>
				<label for="etat">Etat :</label>
				<span><c:out value="${etat }"></c:out></span>
			</div>
		</fieldset>
		<fieldset>
			<legend>Adhérent</legend>
			<div>
				<label for="adherentNom">Nom :</label>
				<span><c:out value="${nomAdh }"></c:out></span>
			</div>
			<div>
				<label for="adherentPrenom">Prenom :</label>
				<span><c:out value="${prenomAdh }"></c:out></span>
			</div>
		</fieldset>
		<fieldset>
			<legend>Dates</legend>
			<div>
				<label for="datedeb">Date d'emprunt :</label>
				<span><c:out value="${dateEmprunt }"></c:out></span>
			</div>
			<div>
				<label for="datefin">Date d'échéance :</label>
				<span><c:out value="${dateEcheance }"></c:out></span>
			</div>
			<div>
				<label for="dateRetourEffectif">Date de retour :</label>
				<span><c:out value="${dateRetour }"></c:out></span>
			</div>
		</fieldset>
		<fieldset>
			<legend>Etat de fin</legend>
			<label>Etat de fin : </label>
			<select name="etatFin">
				<c:forEach items="${etats }" var="etat">
					<option value="${etat.getIdEtat() }"><c:out value="${etat.getLibelleEtat() }"></c:out></option>
				</c:forEach>
			</select>
		</fieldset>
		<fieldset class='align-center no-border'>
			<input type="reset" value="Annuler"/>
			<input type="submit" value="Valider"/>
		</fieldset>
	</form>

<c:import url="/inc/footer.inc.jsp" />