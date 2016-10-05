<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>

<c:set var="titre" value="Réinscription d'un adhérent" scope="request" />

<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />

<script src="<c:url value="/js/modifierAdherent.js"/>"></script>


<h1>Réinscription d'un adhérent</h1>
<form id="modifAdherent" action="#" method="post">
	<fieldset>
		<legend>Informations personnelles</legend>
		<div>
			<span class='text-label'>Civilité </span> <span><input type="radio"
				name="civilite" value="F"
				<c:if test="${adherent.civilite['nameCourt'] == 'Mme'}"> checked="checked"</c:if> /><label
				for="civilite"> Mme </label> <input type="radio" name="civilite"
				value="M"
				<c:if test="${adherent.civilite['nameCourt'] == 'M.'}"> checked="checked" </c:if> /><label
				for="civilite"> M. </label> </span>
		</div>
		<div>
			<label for="nom" class="required">Nom </label><input type="text"
				name="nom" required="required"
				value="<c:out value="${adherent['nom']}" />" />
		</div>
		<div>
			<label for="prenom" class="required">Prénom </label><input
				type="text" name="prenom" required="required"
				value="<c:out value="${adherent['prenom']}" />" />
		</div>
		<div>
			<label for="famille" class="required">Famille </label><input
				type="text" name="famille" required="required" value="${adherent.getFamille().getNomFamille()}" />
		</div>
		<div>
			<label for="dateNaiss" class="required">Date de naissance </label><input
				type="text" name="dateNaiss" required="required" class="datepicker"
				value="<c:out value="${dateNaissance}" />" />
		</div>
		<div>
			<label for="telFixe" class="required">Téléphone fixe </label><input
				type="text" name="telFixe" pattern="[0][1-9][0-9]{8}"
				required="required" maxlength="10" autocomplete="on"
				value="<c:out value="${adherent['telFixe']}"/>" />
		</div>
		<div>
			<label for="telPort">Téléphone portable </label><input type="text"
				name="telPort" pattern="[0][1-9][0-9]{8}" maxlength="10"
				autocomplete="on" value="<c:out value="${adherent['telPort']}"/>" />
		</div>
		<div>
			<label for="email">E-mail </label><input type="email" name="email"
				autocomplete="on" value="<c:out value="${adherent['email']}"/>" />
		</div>
	</fieldset>
	<fieldset>
		<legend>Adresse</legend>
		<div>
			<label for="num">N° </label> <input type="text" name="num"
				autocomplete="off"
				value="<c:out value="${adherent.adresse.numRue}"/>" />
		</div>
		<div>
			<label for="rue" class="required">Rue </label> <input type="text"
				name="rue" required="required" autocomplete="off"
				value="<c:out value="${adherent.adresse.nomRue}"/>" />
		</div>
		<div>
			<label for="compl">Complément d'adresse </label> <input type="text"
				name="compl" autocomplete="off"
				value="<c:out value="${adherent.adresse.infoCompl}"/>" />
		</div>
		<div>
			<label for="commune" class="required">Commune </label> <input
				type="text" name="commune" required="required" autocomplete="off"
				value="<c:out value="${adherent.adresse.commune.nomCommune}"/>" />
		</div>
		<div>
			<label for="codePostal" class="required">Code postal </label> <input
				type="text" name="codePostal" required="required" maxlength="5"
				autocomplete="off"
				value="<c:out value="${adherent.adresse.commune.codePostal}"/>" />
		</div>
	</fieldset>
	<fieldset>
		<legend>Disciplines</legend>
		<div id="disciplines">
			<c:set var="i" value="1"></c:set>
			<c:forEach var="disciplines" items="${adherent.getDisciplines() }">
				<div>
					<label>Cours </label> <input
						name='<c:out value="disciplinesAnciennes${ i }"></c:out>'
						value="${ disciplines.getMatiere().getNomMatiere()} - ${disciplines.getNiveau().getNomNiveau() }"
						readonly> <input type="button" value="Supprimer"
						class="supprimerDiscipline">
				</div>
				<c:set var="i" value="${ i + 1}"></c:set>
			</c:forEach>
			<div id="select1">
				<label>Cours </label> <select size="1" name="disciplines1"
					id="disciplines1">
					<c:forEach var="discipline" items="${sessionScope.listDiscipline }">
						<option value="${ discipline.getIdDiscipline() }">${ discipline.getMatiere().getNomMatiere()}
							- ${discipline.getNiveau().getNomNiveau() }</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class='align-center'>
			<input type="button" value="-" id="retireDiscipline" title="Retirer" />
			<input type="button" value="+" id="ajoutDiscipline" title="Ajouter" />
		</div>
	</fieldset>
	<c:if test="${ ! empty adherent.responsable}">
		<fieldset>
			<legend>Responsable</legend>
			<div>
				<label for="nomResp" class="required">Nom </label> <input
					type="text" name="nomResp"
					value="<c:out value="${adherent.responsable['nom']}" />" />
			</div>
			<div>
				<label for="prenomResp" class="required">Prénom </label> <input
					type="text" name="prenomResp"
					value="<c:out value="${adherent.responsable['prenom']}" />" />
			</div>
			<div>
				<label for="telResp" class="required">Téléphone </label> <input
					type="text" name="telResp" maxlength="10"
					value="<c:out value="${adherent.responsable['telephone']}" />" />
			</div>
			<div>
				<label for="emailResp">E-mail </label> <input type="text"
					name="emailResp"
					value="<c:out value="${adherent.responsable['email']}" />" />
			</div>
		</fieldset>
	</c:if>
	<fieldset>
		<legend>Informations complémentaires</legend>
		<div>
			<label for="dateInscri" class="required">Date d'inscription </label><input
				type="text" name="dateInscri" id="dateInscri" required="required" class="datepicker" />
		</div>
		<div>
			<label class="required">Droit à l'image </label> <span> <input type="radio"
				name="droitImage" value="true"
				<c:if test="${adherent['droitImage'] == true}"> checked="checked" 
						</c:if> />
				<label for="droitImage">Oui</label> <input type="radio"
				name="droitImage" value="false"
				<c:if test="${adherent['droitImage'] == false}"> checked="checked" </c:if> />
				<label for="droitImage">Non</label>
			</span>
		</div>
		<div>
			<label class="required">Membre CA </label> <span> <input type="radio"
				name="membreCA" value="true"
				<c:if test="${adherent['membreCA'] == true}"> checked="checked" 
						</c:if> />
				<label for="membreCA">Oui</label> <input type="radio"
				name="membreCA" value="false"
				<c:if test="${adherent['membreCA'] == false}"> checked="checked" </c:if> />
				<label for="membreCA">Non</label>
			</span>
		</div>
	</fieldset>
	<fieldset class='align-center no-border'>
		<p class="oblig">* Champs obligatoires</p>
		<a class="btn" href="<c:url value="<%=Pattern.ADHERENT_LISTER%>"/>">Retour</a>
		<input type="submit" class="btn" value="Suivant" />
	</fieldset>
</form>
<c:import url="/inc/footer.inc.jsp" />

<script type="text/javascript">
var champ_date_inscription = document.getElementById("dateInscri");
champ_date_inscription.value = getDateActuelle();
</script>
