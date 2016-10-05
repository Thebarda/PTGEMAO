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
			<legend>Instrument</legend>
			<div>
				<label for="categorie">Catégorie :</label>
				<span>** A remplir automatiquement **</span>
			</div>
			<div>
				<label for="designation">Désignation :</label>
				<span>** A remplir automatiquement **</span>
			</div>
			<div>
				<label for="etat">Etat :</label>
				<span>** A remplir automatiquement **</span>
			</div>
		</fieldset>
		<fieldset>
			<legend>Adhérent</legend>
			<div>
				<label for="adherentNom">Nom :</label>
				<span>** A remplir automatiquement **</span>
			</div>
			<div>
				<label for="adherentPrenom">Prenom :</label>
				<span>** A remplir automatiquement **</span>
			</div>
			<div>
				<label for="reparation">Réparation : </label>
				<span>
					<label for="reparationOui">Oui</label>
					<input type="radio" checked="checked" name="reparation" value="oui"/>
					<label for="reparationNon">Non</label>
					<input type="radio" checked="checked" name="reparation" value="non"/>
				</span>
			</div>
			Nom réparateur,date réparation?
		</fieldset>
		<fieldset>
			<legend>Dates</legend>
			<div>
				<label for="datedeb">Date d'emprunt :</label>
				<span>** A remplir automatiquement **</span>
			</div>
			<div>
				<label for="datefin">Date limite de retour :</label>
				<span>** A remplir automatiquement **</span>
			</div>
			<div>
				<label for="dateRetourEffectif">Date de retour :</label>
				<span>** A remplir automatiquement (input avec date du jour par défaut) **</span>
			</div>
		</fieldset>
		<fieldset class='align-center no-border'>
			<input type="reset" value="Annuler"/>
			<input type="submit" value="Valider"/>
		</fieldset>
	</form>

<c:import url="/inc/footer.inc.jsp" />