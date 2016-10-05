<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>

<c:set var="titre" value="Consultation d'un adhérent" scope="request" />

<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />

<h1>Consultation d'un adhérent</h1>

<table class='table-col-2'>
	<caption>Informations personnelles</caption>
	<tr>
		<td>Civilité :</td>
		<td><c:out value="${adherent['civilite']}"></c:out></td>
	</tr>
	<tr>
		<td>Nom : </td>
		<td><c:out value="${adherent['nom']}" /></td>
	</tr>
	<tr>
		<td>Prénom : </td>
		<td><c:out value="${adherent['prenom']}" /></td>
	</tr>
	<tr>
		<td>Famille : </td>
		<td><c:out value="${adherent.getFamille().getNomFamille()}" /></td>
	</tr>
	<tr>
		<td>Date de naissance : </td>
		<td><c:out value="${dateNaissance}" /></td>
	</tr>
	<tr>
		<td>Téléphone fixe : </td>
		<td><c:out value="${adherent['telFixe']}" /></td>
	</tr>
	<c:if test="${adherent['telPort'] != null}">
		<tr>
			<td>Téléphone portable : </td>
			<td><c:out value="${adherent['telPort']}" /></td>
		</tr>
	</c:if>
	<c:if test="${adherent['email'] != null}">
		<tr>
			<td>E-mail : </td>
			<td><c:out value="${adherent['email']}" /></td>
		</tr>
	</c:if>
</table>
<table  class='table-col-2'>
	<caption>Adresse</caption>
	<c:if test="${adherent.adresse['numRue'] != null}">
		<tr>
			<td>N° : </td>
			<td><c:out value="${adherent.adresse['numRue']}" /></td>
		</tr>
	</c:if>
	<tr>
		<td>Rue : </td>
		<td><c:out value="${adherent.adresse['nomRue']}" /></td>
	</tr>
	
	<c:if test="${adherent.adresse['infoCompl'] != null}">
		<tr>
			<td>Complément d'adresse : </td>
			<td><c:out value="${adherent.adresse['infoCompl']}" /></td>
		</tr>
	</c:if>	
	<tr>
		<td>Commune : </td>
		<td><c:out value="${adherent.adresse.commune['nomCommune']}" /></td>
	</tr>
	<tr>
		<td>Code postal : </td>
		<td><c:out value="${adherent.adresse.commune['codePostal']}" /></td>
	</tr>
</table>
<table class='table-col-2'>
	<caption>Disciplines</caption>
	<tbody id="disciplines">
		<c:forEach var="discipline" items="${adherent.getDisciplines()}">
			<tr>
				<td class='align-center'>${ discipline.getMatiere().getNomMatiere()} - ${discipline.getNiveau().getNomNiveau() }</td>
			</tr>
		</c:forEach>	
	</tbody>
</table>
<table class='table-col-2'>
	<caption>Informations complémentaires</caption>
	<tr>
		<td>Date d'inscription : </td>
		<td><c:out value="${dateInscription}" /></td>
	</tr>
	<tr>
		<td>Montant de la cotisation : </td>
		<td><c:out value="${adherent['cotisation']}" /><span class="euro"></span></td>
	</tr>
	<tr>
		<td>Droit à l'image :</td>
		<td>
			<c:choose>
				<c:when test="${adherent['droitImage']==true}">Oui</c:when>
				<c:otherwise>Non</c:otherwise>
			</c:choose>
		</td>
	</tr>
	<tr>
		<td>Membre CA :</td>
		<td>
			<c:choose>
				<c:when test="${adherent['membreCA']==true}">Oui</c:when>
				<c:otherwise>Non</c:otherwise>
			</c:choose>
		</td>
	</tr>
	<tr>
		<td>A Payé :</td>
		<td>
			<c:choose>
				<c:when test="${adherent.isAPaye() }">Oui</c:when>
				<c:otherwise>Non</c:otherwise>
			</c:choose>
	</tr>
</table>
<c:if test="${adherent.responsable!=null}">
<table class='table-col-2'>
	<caption>Responsable</caption>
	<tr>
		<td>Nom : </td>
		<td><c:out value="${adherent.responsable['nom']}" /></td>
	</tr>
	<tr>
		<td>Prénom : </td>
		<td><c:out value="${adherent.responsable['prenom']}" /></td>
	</tr>
	<tr>
		<td>Téléphone : </td>
		<td><c:out value="${adherent.responsable['telephone']}" /></td>
	</tr>
	<c:if test="${adherent.responsable['email'] != null}">
		<tr>
			<td>E-mail : </td>
			<td><c:out value="${adherent.responsable['email']}" /></td>
		</tr>
	</c:if>
</table>
</c:if>


<p class='align-center'>
	<a class='btn' href="<c:url value="<%= Pattern.ADHERENT_LISTER %>"/>">Retour</a>
	<a class='btn' href="<c:url value="<%= Pattern.ADHERENT_MODIFIER %>"/><c:out value='?id=${adherent.idPersonne }'/>">Modifier</a>
	<c:if test="${adherent.getMotif() == null }">
		<a class='btn' href="<c:url value="<%= Pattern.ADHERENT_DESINSCRIRE %>"/><c:out value='?id=${adherent.idPersonne }'/>">Désinscrire</a>
	</c:if>
	<c:if test="${adherent.getMotif() != null }">
		<a class='btn' href="<c:url value="<%= Pattern.ADHERENT_REINSCRIRE %>"/><c:out value='?id=${adherent.idPersonne }'/>">Réinscrire</a>
	</c:if>
	<c:if test="${!adherent.isAPaye() && adherent.getMotif() == null }">
		<a class='btn' href="<c:url value="<%= Pattern.ADHERENT_APAYE %>"/><c:out value='?id=${adherent.idPersonne}'/>">A Payé</a> 
	</c:if>
	<c:if test="${adherent.isAPaye() && adherent.getMotif() == null }">
		<a class='btn' href="<c:url value="<%= Pattern.ADHERENT_REINIT_APAYE %>"/><c:out value='?id=${adherent.idPersonne}'/>">Annuler Paiement</a> 
	</c:if>
</p>

<c:import url="/inc/footer.inc.jsp" />
