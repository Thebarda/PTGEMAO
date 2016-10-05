<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>

<%@ page buffer="32kb" %>
<%@ page isErrorPage="true" %>

<c:set var="titre" value="Modification d'un matériel" scope="request" />

<c:import url="/inc/head.inc.jsp" />
<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />

<script src="<c:url value="/js/modifierMateriel.js"/>" ></script>

<h1>Modification d'un matériel</h1>
<p>${form.getErreurs().get("Modification")}</p>

<form id="modifierMateriel" action="#" method="post">
	<input type="text" name="nomMarque" id="nomMarque" class="hidden"/>
	<input type="text" name="nomEtat" id="nomEtat" class="hidden"/>
	<input type="text" name="nomDes" id="nomDes" class="hidden"/>
	<input type="text" name="nomFour" id="nomFour" class="hidden"/>
	<input type="text" name="nomCat" id="nomCat" class="hidden"/>
</form>

<form name="modifierMaterielInstrument" action="#" method="post">
	<fieldset>
		<legend>Informations générales</legend>
		<div>
			<label for="categorie">Catégorie </label> <select name="categorie"
				id="categorie">
				<option
					value="${sessionScope.sessionObjectMateriel.getCategorie().getIdCategorie()}">
					${sessionScope.sessionObjectMateriel.getCategorie().getLibelleCat()}</option>
				<c:forEach items="${listeCat}" var="cat">
					<option value="${cat.getIdCategorie()}">${cat.getLibelleCat()}</option>
				</c:forEach>
			</select> <input type="button" name="ajoutCategorie" id="ajoutCat"
				value="Créer..." />
				<p>${form.getErreurs().get("categorie")}</p>
		</div>

		<div>
			<label for="valeurAch" class='required'>Valeur d'achat </label> <input type="text"
				id="valeurAch" name="valeurAch" required autocomplete="off"
				required autocomplete="off" value="${sessionScope.sessionObjectMateriel.getValeurAchat()}" />
				<span class="euro"></span>
				<p>${form.getErreurs().get("valeurAch")}</p>
		</div>
		<div>
			<label for="dateAch">Date d'acquisition </label> <input type="text"
				id="dateAch" name="dateAch"
				value="${dateAchat}" class="datepicker" />
				<p>${form.getErreurs().get("dateAch")}</p>
		</div>


		<div>
			<label for="valRea" class='required'>Valeur de réapprovisionnement </label> <input
				type="text" name="valRea" required autocomplete="off"
				required autocomplete="off" value="${sessionScope.sessionObjectMateriel.getValeurReap()}" /> <span
				class="euro"></span>
				<p>${form.getErreurs().get("valRea")}</p>
		</div>
		

		<div>
			<label for="fournisseurResult">Fournisseur </label> <select
				name="fournisseur" id="fournisseur">
				<option
					value="${sessionScope.sessionObjectMateriel.getFournisseur().getIdFournisseur()}">
					${sessionScope.sessionObjectMateriel.getFournisseur().getNomFournisseur()}</option>
				<c:forEach items="${listeFourn}" var="fou">
					<option value="${fou.getIdFournisseur()}">${fou.getNomFournisseur()}</option>
				</c:forEach>
			</select> <input type="button" name="ajoutFournisseur" id="ajoutFour"
				value="Créer..." />
				<p>${form.getErreurs().get("fournisseur")}</p>
		</div>

	</fieldset>

	<fieldset>
		<legend>Informations détaillées</legend>
		<div>
			<label for="designation">Désignation </label> <select
				name="designation" id="designation">
				<option
					value="${sessionScope.sessionObjectMateriel.getDesignation().getIdDesignation()}">
					${sessionScope.sessionObjectMateriel.getDesignation().getLibelleDesignation()}</option>
				<c:forEach items="${listeDes}" var="des">
					<option value="${des.getIdDesignation()}">${des.getLibelleDesignation()}</option>
				</c:forEach>
			</select> <input type="button" name="ajoutDesignation" id="ajoutDes"
				value="Créer..." />
				<p>${form.getErreurs().get("designation")}</p>
		</div>

		<div>
			<label for="type" class='required'>Type </label> <input type="text" id="type"
				name="type" required autocomplete="off"
				value="${sessionScope.sessionObjectMateriel.getTypeMat()}" />
				<p>${form.getErreurs().get("type")}</p>
		</div>

		<!--% Etat à empêcher de s'améliorer -->
		<div>
			<label for="etat">Etat </label> <select name="etat" id="etat">
				<option
					value="${sessionScope.sessionObjectMateriel.getEtat().getIdEtat()}">
					${sessionScope.sessionObjectMateriel.getEtat().getLibelleEtat()}</option>
				<c:forEach items="${listeEtats}" var="etat">
					<option value="${etat.getIdEtat()}">${etat.getLibelleEtat()}</option>
				</c:forEach>
			</select> <input type="button" name="ajoutEtat" id="ajoutEtat" value="Créer..." />
			<p>${form.getErreurs().get("etat")}</p>
		</div>

		<div>
			<label for="marque">Marque </label> <select name="marque"
				id="marque">
				<option
					value="${sessionScope.sessionObjectMateriel.getMarque().getIdMarque()}">${sessionScope.sessionObjectMateriel.getMarque().getNomMarque()}</option>
				<c:forEach items="${listeMarque}" var="marque">
					<option value="${marque.getIdMarque()}">${marque.getNomMarque()}</option>
				</c:forEach>
			</select> <input type="button" name="ajoutMarque" id="ajoutMarque" value="Créer..." />
			<p>${form.getErreurs().get("marque")}</p>
		</div>
		

		<div>
			<label for="numSerieResult">Numéro de série </label> <input
				name="numSerie" type="text" autocomplete="off"
				value="${sessionScope.sessionObjectMateriel.getNumSerie()}" />
				<p>${form.getErreurs().get("numSerie")}</p>
		</div>



		<div>
			<label for="deplacable">Déplaçable : </label> <span> <c:choose>
					<c:when
						test="${sessionScope.sessionObjectMateriel.isDeplacable()==true}">
						<label for="deplacableOui">Oui</label>
						<input type="radio" name="deplacable" id="deplacable" value="oui"
							checked="checked" />
						<label for="deplacableNon">Non</label>
						<input type="radio" name="deplacable" id="deplacable" value="non" />
					</c:when>
					<c:otherwise>
						<label for="deplacableOui">Oui</label>
						<input type="radio" name="deplacable" id="deplacable" value="oui" />
						<label for="deplacableNon">Non</label>
						<input type="radio" name="deplacable" id="deplacable" value="non" checked="checked" />
					</c:otherwise>
				</c:choose>
			</span>
		</div>
		<div>
			<label for="louable">Ouvert à la location : </label> 
			<span> 
				<c:choose>
					<c:when
						test="${sessionScope.sessionObjectMateriel.isLouable()==true}">
						<label for="louableeOui">Oui</label>
						<input type="radio" name="louable" id="louable" value="oui" checked="checked" />
						<label for="louableNon">Non</label>
						<input type="radio" name="louable" id="louable" value="non" />
					</c:when>
					<c:otherwise>
						<label for="louableOui">Oui</label>
						<input type="radio" name="louable" id="louable" value="oui" />
						<label for="louableNon">Non</label>
						<input type="radio" name="louable" id="louable" value="non" checked="checked" />
					</c:otherwise>
				</c:choose>
			</span>
		</div>
	</fieldset>
	<fieldset>
		<legend>Observations</legend>

		<div class='align-center'>
			<textarea name="observation" id="observation" rows="5"
				placeholder="Ajoutez ici toute information que vous jugez nécessaire de mentionner."
				cols="50">${sessionScope.sessionObjectMateriel.getObservation()}</textarea>
		</div>
	</fieldset>
	<fieldset class='align-center no-border'>
		<p class="oblig">* Champs obligatoires</p>
		
		<input type="button" name="precedent" value="Précédent"
			onClick="javascript:window.history.go(-1)" /> <input type="submit"
			name="valider" value="Valider" />
	</fieldset>
</form>
<c:import url="/inc/footer.inc.jsp" />
