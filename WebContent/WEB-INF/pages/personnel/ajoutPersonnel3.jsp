<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>

<c:set var="titre" value="Ajout d'un membre du personnel - Résultat" scope="request" />

<c:import url="/inc/head.inc.jsp" />
<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />

<h1>Ajout d'un membre du personnel</h1>

<p class='offset'>La personne suivante a été ajoutée avec succès à la liste du personnel.</p>

<table>
	<tr>
		<td>Nom</td>
		<td><c:out value="${personnel['nom']}" /></td>
	</tr>
	<tr>
		<td>Prénom</td>
		<td><c:out value="${personnel['prenom']}" /></td>
	</tr>
	<tr>
		<td>Login</td>
		<td class="text-danger"><c:out value="${personnel['login']}" /></td>
	</tr>
	<tr>
		<td>Mot de passe</td>
		<td class="text-danger"><c:out value="${personnel['password']}" /></td>
	</tr>
</table>

<p class="offset text-danger">Notez bien le mot de passe et le login</p>
<a class="offset btn" href="<c:url value="<%= Pattern.ACCUEIL %>"/>" >Accueil</a>

<c:import url="/inc/footer.inc.jsp" />