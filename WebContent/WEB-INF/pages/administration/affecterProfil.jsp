<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>

<c:set var="titre" value="Modifier un profil" scope="request" />

<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />


<h1>Modifier le profil d'un utilisateur</h1>

<form action="#" method="POST">
	<fieldset>
		<div>
			<label for="idProfil">Nom du profil</label>
			<select name="idProfil">
				<option value="" title="Aucun droit ne sera accordé">Aucun</option>
			<c:forEach items="${requestScope.listeProfils}" var="profil">
				<option value="<c:out value="${profil.idProfil }"/>" <c:if test="${profil.idProfil==personne.profil.idProfil }">selected</c:if>>
					<c:out value="${profil.nomProfil }"/>
				</option>		
			</c:forEach>
			</select>
		</div>
	</fieldset>
	
	<fieldset class='align-center no-border'>
		<a href="<c:url value="<%= Pattern.PERSONNEL_LISTER %>" />"><input type="button" value="Retour" /></a>
		<input type="submit" value="Valider"/>
	</fieldset>
</form>

<c:import url="/inc/footer.inc.jsp" />