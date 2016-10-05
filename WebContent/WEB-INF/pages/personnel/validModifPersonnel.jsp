<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>

<c:set var="titre" value="Récapitulatif des informations d'un membre du personnel" scope="request" />

<c:import url="/inc/head.inc.jsp" />
<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />

<h1>Récapitulatif des informations d'un membre du personnel</h1>

<form action="#" method="post">
	<table class='table-col-2'>
		<caption>Informations personnelles</caption>
		<tr>
			<td>
				<span>Civilité</span>
			</td>
			<td>
				<c:out value="${personnel['civilite']}"></c:out>
			</td>
		</tr>
		<tr>
			<td>
				<span>Nom</span>
			</td>
			<td>
				<c:out value="${personnel['nom']}"/>
			</td>
		</tr>
		<tr>
			<td>
				<span>Prénom</span>
			</td>
			<td>
				<c:out value="${personnel['prenom']}"/>
			</td>
		</tr>
		<tr>
			<td>
				<span>Téléphone fixe</span>
			</td>
			<td>
				<c:out value="${personnel['telFixe']}"/>
			</td>
		</tr>
		<tr>
			<td>
				<span>Téléphone portable</span>
			</td>
			<td>
				<c:out value="${personnel['telPort']}"/>
			</td>
		</tr>
		<tr>
			<td>
				<span>E-mail</span>
			</td>
			<td>
				<c:out value="${personnel['email']}"/>
			</td>
		</tr>
		<tr>
			<td>
				<span>Numéro de sécurité sociale</span>
			</td>
			<td>
				<c:out value="${personnel['numeroSS']}"/>
			</td>
		</tr>
	</table>
	<table class='table-col-2'>
		<caption>Adresse</caption>
		<tr>
			<td>
				<span>N°</span>
			</td>
			<td>
				<c:out value="${adresse['numRue']}"/>
			</td>
		</tr>
		<tr>
			<td>
				<span>Rue</span>
			</td>
			<td>
				<c:out value="${adresse['nomRue']}"/>
			</td>
		</tr>
		<tr>
			<td>
				<span>Complément d'adresse</span>
			</td>
			<td>
				<c:out value="${adresse['infoCompl']}"/>
			</td>
		</tr>
		<tr>
			<td>
				<span>Code postal</span>
			</td>
			<td>
				<c:out value="${commune['codePostal']}"/>
			</td>
		</tr>
		<tr>
			<td>
				<span>Commune</span>
			</td>
			<td>
				<c:out value="${commune['nomCommune']}"/>
			</td>
		</tr>
	</table>
	<table class='table-col-2'>
		<caption>Informations professionnelles</caption>
		<tr>
			<td>
				<span>Contrat : </span>
			</td>
			<td>
				<c:out value="${contrat['typeContrat'].libelle}"/>
			</td>
		</tr>
		<tr>
			<td>
				<span>Date de début : </span>
			</td>
			<td>
				<c:out value="${dateDebutContrat}"/>
			</td>
		</tr>
		<c:if test="${contrat['typeContrat'].libelle == 'CDD' }">
			<tr>
				<td>
					<span>Date de fin : </span>
				</td>
				<td>
					<c:out value="${dateFinContrat}"/>
				</td>
			</tr>
		</c:if>
		<tr>
			<td>
				<span>Diplôme :</span>
			</td>
			<td>
				<c:forEach items="${listeDiplome}" var="dipl">
					<c:out value="${dipl['nomDiplome']}"/>
				</c:forEach>
			</td>
		</tr>
		<tr>
			<td>
				<span>Responsabilité : </span>
			</td>
			<td>
				<c:forEach items="${listeResponsabilite}" var="resp">
					<c:out value="${resp['libelle']}"/>
				</c:forEach>
			</td>
		</tr>
	</table>
	<table class='table-col-2'>
		<caption>Informations liées à l'application</caption>
		<tr>
			<td>
				<span>Login : </span>
			</td>
			<td>
				<c:out value="${personnel.login}"/>
			</td>
		</tr>
		<tr>
			<td>
				<span>Profil : </span>
			</td>
			<td>
				<c:out value="${personnel.profil.nomProfil}"/>
			</td>
		</tr>
	</table>
	<p class='align-center no-border'>
		<a class="btn" href="<c:url value="<%= Pattern.PERSONNEL_LISTER %>"/>">Retour</a>
		<input type="submit" value="Valider"/>
	</p>
</form>
<c:import url="/inc/footer.inc.jsp"/>