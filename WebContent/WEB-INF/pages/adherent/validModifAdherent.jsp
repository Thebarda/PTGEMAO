<%@page import="fr.gemao.view.Pattern"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="titre" value="Validation" scope="request" />

<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />

<h1>Validation</h1>

<form action="#" method="post">
	<fieldset>
		<legend>Informations personnelles</legend>
		<div>
			<span class='text-label'>Civilité :</span>
			<c:out value="${adherent['civilite']}"></c:out>
		</div>
		<div>
			<span class='text-label'>Nom : </span>
			<c:out value="${adherent['nom']}" />
		</div>
		<div>
			<span class='text-label'><label for="prenom">Prénom : </label></span>
			<c:out value="${adherent['prenom']}" />
		</div>
		<div>
			<span class='text-label'><label for="famille">Famille : </label></span>
			<c:out value="${adherent.getFamille().getNomFamille()}" />
		</div>
		<div>
			<span class='text-label'>Date de naissance : </span>
			<c:out value="${dateNaissance}" />
		</div>
		<div>
			<span class='text-label'>Téléphone fixe : </span>
			<c:out value="${adherent['telFixe']}" />
		</div>
		<div>
			<span class='text-label'>Téléphone portable : </span>
			<c:out value="${adherent['telPort']}" />
		</div>
		<div>
			<span class='text-label'>E-mail : </span>
			<c:out value="${adherent['email']}" />
		</div>		
	</fieldset>
	<fieldset>
		<legend>Adresse</legend>
		<div>
			<span class='text-label'>Numéro : </span>
			<c:out value="${adherent.adresse['numRue']}" />
		</div>
		<div>
			<span class='text-label'>Rue : </span>
			<c:out value="${adherent.adresse['nomRue']}" />
		</div>
		<c:if test="${adherent.adresse['infoCompl'] != null}">
			<div>
				<span class='text-label'>Complément d'adresse : </span>
				<c:out value="${adherent.adresse['infoCompl']}" />
			</div>
		</c:if>
		<div>
			<span class='text-label'>Commune : </span>
			<c:out value="${adherent.adresse.commune['nomCommune']}" />
		</div>
		<div>
			<span class='text-label'>Code postal : </span>
			<c:out value="${adherent.adresse.commune['codePostal']}" />
		</div>
	</fieldset>
	<fieldset>
		<legend>Disciplines</legend>
			<c:forEach var="discipline" items="${sessionScope.modif_adh_adherent.getDisciplines()}">
				<div class='align-center'>${ discipline.getMatiere().getNomMatiere()} - ${discipline.getNiveau().getNomNiveau() }</div>
			</c:forEach>		
	</fieldset>
	<fieldset>
		<legend>Informations complémentaires</legend>
		<div>
			<span class='text-label'>Date d'inscription : </span>
			<c:out value="${dateInscription}" />
		</div>
		<div>
			<span class='text-label'>Montant de la cotisation : </span>
			<c:out value="${adherent['cotisation']}" />
			<span class="euro"></span>
		</div>
		<div>
			<span class='text-label'>Droit à l'image : </span>
			<c:choose>
				<c:when test="${adherent['droitImage']==true}">Oui</c:when>
				<c:otherwise>Non</c:otherwise>
			</c:choose>
		</div>
		<div>
			<span class='text-label'>Membre CA : </span>
			<c:choose>
				<c:when test="${adherent['membreCA']==true}">Oui</c:when>
				<c:otherwise>Non</c:otherwise>
			</c:choose>
			
		</div>
	</fieldset>
<c:if test="${adherent.responsable!=null}">
	<fieldset>
		<legend>Responsable</legend>
		<div>
			<span class='text-label'>Nom : </span>
			<c:out value="${adherent.responsable['nom']}" />
		</div>
		<div>
			<span class='text-label'>Prénom : </span>
			<c:out value="${adherent.responsable['prenom']}" />
		</div>
		<div>
			<span class='text-label'>Téléphone : </span>
			<c:out value="${adherent.responsable['telephone']}" />
		</div>
		<div>
			<span class='text-label'>E-mail : </span>
			<c:out value="${adherent.responsable['email']}" />
		</div>
	</fieldset>
</c:if>
<fieldset class='align-center no-border'>
	<a href="<c:url value="<%=Pattern.ADHERENT_LISTER %>"/>"><input type="button" class="btn" value="Annuler" /></a>
	<input type="submit" class="btn" value="Valider" />
</fieldset>
</form>


<c:import url="/inc/footer.inc.jsp" />