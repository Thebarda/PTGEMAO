<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="titre" value="Récapitulatif des informations d'un membre du personnel" scope="request" />

<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />

<h1>Récapitulatif des informations d'un membre du personnel</h1>

<form action="<c:url value="<%=Pattern.PERSONNEL_VALIDATION_AJOUT%>" />" method="post">
	<table class='table-col-2'>
		<caption>Informations personnelles</caption>
		<tr>
			<td><span>Civilité</span></td>
			<td><c:out value="${personnel['civilite']}"></c:out></td>
		</tr>
		<tr>
			<td><span>Nom</span></td>
			<td><c:out value="${personnel['nom']}" /></td>
		</tr>
		<tr>
			<td><span>Prénom</span></td>
			<td><c:out value="${personnel['prenom']}" /></td>
		</tr>
		<tr>
			<td><span>Téléphone fixe</span></td>
			<td><c:out value="${personnel['telFixe']}" /></td>
		</tr>
		<tr>
			<td><span>Téléphone portable</span></td>
			<td><c:out value="${personnel['telPort']}" /></td>
		</tr>
		<tr>
			<td><span>E-mail</span></td>
			<td><c:out value="${personnel['email']}" /></td>
		</tr>
		<tr>
			<td><span>Numéro de sécurité sociale</span></td>
			<td><c:out value="${personnel['numeroSS']}" /></td>
		</tr>
	</table>
	<table class='table-col-2'>
		<caption>Adresse</caption>
		<tr>
			<td><span>N°</span></td>
			<td><c:out value="${personnel.adresse['numRue']}" /></td>
		</tr>
		<tr>
			<td><span>Rue</span></td>
			<td><c:out value="${personnel.adresse['nomRue']}" /></td>
		</tr>
		<tr>
			<td><span>Complément d'adresse</span></td>
			<td><c:out value="${personnel.adresse['infoCompl']}" /></td>
		</tr>
		<tr>
			<td><span>Commune</span></td>
			<td><c:out value="${personnel.adresse.commune['nomCommune']}" />
			</td>
		</tr>
		<tr>
			<td><span>Code postal</span></td>
			<td><c:out value="${personnel.adresse.commune['codePostal']}" />
			</td>
		</tr>
	</table>
	<table class='table-col-2'>
		<caption>Informations professionnelles</caption>
		<tr>
			<td><span>Contrat </span></td>
			<td>Date de début</td>
			<td>Date de fin</td>
		</tr>
		<c:forEach items="${contrats}" var="cont">
			<tr>
				<td><c:out value="${cont['typeContrat'].libelle}" /></td>
				<td><fmt:formatDate value="${cont.dateDebut}" pattern="dd/MM/yyyy" /></td>
				<td><c:if test="${cont['typeContrat'].libelle == 'CDD' }">
						<fmt:formatDate value="${cont.dateFin}" pattern="dd/MM/yyyy" />
					</c:if></td>
			</tr>
		</c:forEach>
		<tr>
			<td></td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td><span>Diplôme :</span></td>
			<td><c:forEach items="${listeDiplome}" var="dipl">
					<c:out value="${dipl['nomDiplome']}" />
				</c:forEach></td>
			<td></td>
		</tr>
		<tr>
			<td><span>Responsabilité : </span></td>
			<td></td>
			<td></td>
		</tr>
		<c:forEach items="${listeResponsabilite}" var="resp">
			<tr>
				<td><c:out value="${resp.libelle}" /> <td>
					<c:if
						test="${resp.libelle == 'Professeur' }">
						<c:forEach items="${listeDiscipline}" var="disp">
								-<c:out value="${disp.matiere.nomMatiere}" />
							<c:out value="${disp.niveau.nomNiveau}" />
							<br>
						</c:forEach></c:if></td>
				</td>
				<td></td>
			</tr>
		</c:forEach>
	</table>
	<table class='table-col-2'>
		<caption>Informations liées à l'application</caption>
		<tr>
			<td><span>Login : </span></td>
			<td><c:out value="${personnel.login}" /></td>
		</tr>
		<tr>
			<td><span>Profil : </span></td>
			<td><c:out value="${personnel.profil.nomProfil}" /></td>
		</tr>
	</table>
	<p class='align-center no-border'>
		<a class="btn" href="<c:url value="<%=Pattern.PERSONNEL_LISTER%>"/>">Annuler</a>
		<input type="submit" value="Valider" />
	</p>
</form>
<c:import url="/inc/footer.inc.jsp" />