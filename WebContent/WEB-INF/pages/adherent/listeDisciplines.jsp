<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>

<c:set var="titre" value="Liste des disciplines" scope="request" />

<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />
<script src="<c:url value="/js/listerDisciplines.js"/>"></script>


<h1>Liste des disciplines</h1>
<h2 class="offset">Matière - Niveau</h2>

<c:if test="${modifKO}">
	<p class="align-center">Une erreur s'est produite durant la modification de la discipline. Cette dernière existe peut-être déjà.</p>
</c:if>

<c:if test="${ajoutKO}">
	<p class="align-center">Une erreur s'est produite durant l'ajout de la discipline. Cette dernière existe peut-être déjà.</p>
</c:if>

<c:if test="${ajoutKO}">
	<p class="align-center">La discipline a correctement été ajoutée.</p>
</c:if>

<c:if test="${modifKO}">
	<p class="align-center">La discipline a correctement été modifiée.</p>
</c:if>

<c:forEach var="discipline" items="${sessionScope.listDiscipline }">
	<form action="#" method="post" class="offset">
		<input type="text" name="matiere" value="${discipline.getMatiere().getNomMatiere()}" readonly>
		<input type="text" name="niveau" value="${discipline.getNiveau().getNomNiveau()}" readonly>
		<input type="text" name="id" value="${discipline.getIdDiscipline()}" style="display: none">
		<a class="icon icon-pen" title='Modifier la discipline'></a>
		<a href="<c:url value="<%= Pattern.ADHERENT_LISTE_DISCIPLINES %>"/>?delete=<c:out value="${discipline.getIdDiscipline()}" />" class="icon icon-trash" title='Supprimer la discipline'></a>
		
		<input type="submit" class="btModif hidden" value="Modifier">
	</form>
</c:forEach>
<form action="#" method="post" class="offset">
	<input type="text" name="matiere" value="" >
	<input type="text" name="niveau" value="" >
	<input type="text" name="id" value="0" style="display: none">
	<input class="btn" type="submit" value="Ajouter">
</form>

<c:import url="/inc/footer.inc.jsp" />