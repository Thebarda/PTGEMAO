<%@page import="java.util.ArrayList"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="titre" value="Ajout d'un adhérent" scope="request" />

<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />

<c:import url="/js/autocompleteCommune.jsp"/>
<c:import url="/js/autocompleteAdresse.jsp"/>
<c:import url="/js/autocompleteFamille.jsp"/>

<script type="text/javascript">
autocompletionCommuneCodePostal("#commune", "#codePostal");
autocompletionAdresse("#rue", "#compl");
autocompletionFamille("#famille");
</script>

<script src="<c:url value="/js/AjouterAdherent.js"/>"></script>


<h1>Ajout d'un adhérent</h1>

<c:if test="${errDate}">
	<p class="offset text-danger">La date d'inscription doit être antérieure à aujourd'hui et postérieure à la date de naissance.</p>
</c:if>
<form id="ajoutAdherent" action="#" method="post">
	<fieldset>
		<legend>Informations personnelles</legend>

		<div>
			<label>Civilité</label> <span> <input type="radio"
				name="civilite" value="F" id="civiliteF"
				checked="checked" />
				<label for="civiliteF"> Mme </label> <input type="radio"
				name="civilite" id="civiliteM" value="M"
				<c:if test="${ajout_adh_adherent.civilite['nameCourt'] == 'M.'}"> checked="checked" </c:if> /><label
				for="civiliteM"> M. </label>
			</span>
		</div>

		<div>
			<label for="nom" class='required'>Nom </label> <input type="text"
				name="nom" value="${ajout_adh_adherent.getNom()}" required="required" />
		</div>

		<div>
			<label for="prenom" class='required'>Prénom </label> <input
				type="text" name="prenom" value="${ajout_adh_adherent.getPrenom()}" required="required" />
		</div>
		
		<div>
			<label for="famille" class='required'>Famille </label> <input
				type="text" name="famille" id="famille" value="${ajout_adh_adherent.getFamille().getNomFamille()}" required="required" />
		</div>

		<div>
			<label for="dateNaiss" class='required'>Date de naissance </label> <input
				type="text" name="dateNaiss"
				value="<fmt:formatDate
						value="${ajout_adh_adherent.getDateEntree()}" pattern="dd/MM/yyyy" />"
				class="datepicker" required="required" />
		</div>

		<div>
			<label for="telFixe" class='required'>Téléphone fixe </label> <input
				type="text" name="telFixe" pattern="[0][1-9][0-9]{8}" value="${ajout_adh_adherent.getTelFixe()}"
				required="required" maxlength="10" autocomplete="on" />
		</div>

		<div>
			<label for="telPort">Téléphone portable </label> <input type="text"
				name="telPort" pattern="[0][1-9][0-9]{8}" value="${ajout_adh_adherent.getTelPort()}"
				maxlength="10" autocomplete="on" />
		</div>

		<div>
			<label for="email">E-mail </label> <input type="email" name="email"
				value="${ajout_adh_adherent.getEmail()}" autocomplete="on" />
		</div>
	</fieldset>
	<fieldset>
		<legend>Adresse</legend>
		<div>
			<label for="num">N° </label> <input type="text" name="num" value="${ajout_adh_adresse.getNumRue()}"/>
		</div>

		<div>
			<label for="rue" class='required'>Rue </label> <input type="text"
				name="rue" id="rue" required="required" value="${ajout_adh_adresse.getNomRue()}"/>
		</div>

		<div>
			<label for="compl">Complément d'adresse </label> <input type="text"
				name="compl" id="compl" value="${ajout_adh_adresse.getInfoCompl()}"/>
		</div>

		<div>
			<label for="commune" class='required'>Commune </label> <input
				type="text" name="commune" required="required" id="commune" value="${ajout_adh_commune.getNomCommune()}"/>
		</div>

		<div>
			<label for="codePostal" class='required'>Code postal </label> <input
				type="text" name="codePostal" id="codePostal" required="required" maxlength="5"
				pattern="\d*" value="${ajout_adh_commune.getCodePostal()}"/>
		</div>
	</fieldset>
	<fieldset>
		<legend>Disciplines</legend>
		<div id='disciplines'>
			<div>
				<label>Cours</label> <select size="1" name="disciplines1">
					<c:forEach var="discipline" items="${sessionScope.listDiscipline }">
						<option value="${ discipline.getIdDiscipline() }">${ discipline.getMatiere().getNomMatiere()}
							- ${discipline.getNiveau().getNomNiveau() }</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class='align-center'>
			<input class="btn" type="button" value="-" id="retireDiscipline" title="Retirer" />
			<input class="btn" type="button" value="+" id="ajoutDiscipline" title="Ajouter" />
		</div>
	</fieldset>
	<fieldset>
		<legend>Informations complémentaires</legend>
		<div>
			<label for="dateInscri" class='required'>Date d'inscription </label>
			<input type="text" name="dateInscri" id="dateInscri"
				value="${ajout_adh_adherent.getDateEntree()}"
				class="datepicker" required="required" />
		</div>
		<div>
			<label class='required'>Droit à l'image </label> <span> <input
				type="radio" name="droitImage" value="true"
				checked="checked" />
				<label for="droitImage">Oui</label> <input type="radio"
				name="droitImage" value="false"
				<c:if test="${ajout_adh_adherent.isDroitImage() == false}"> checked="checked" 
						</c:if> />
				<label for="droitImage">Non</label>
			</span>
		</div>
		<div>
			<label class='required'>Membre CA </label> <span> <input
				type="radio" name="membreCA" value="true"/> <label
				for="membreCA">Oui</label> <input type="radio" name="membreCA"
				value="false" checked="checked" /> <label for="membreCA">Non</label>
			</span>
		</div>
	</fieldset>
	<fieldset class='align-center no-border'>
		<p class="oblig">* Champs obligatoires</p>
		<input type="submit" class="btn" value="Suivant" />
	</fieldset>
</form>
<c:import url="/inc/footer.inc.jsp" />
