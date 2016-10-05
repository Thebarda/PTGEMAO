<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>

<c:set var="titre" value="Changement de mot de passe - Accès refusé" scope="request" />

<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />

<h1>Accès non autorisé</h1>

<p class='offset text-danger'>Vous ne possédez pas les droits pour accéder à cette page.</p>

<div class="align-center">
	<a class="btn" href='<c:url value="<%= Pattern.ACCUEIL %>"/>'>Accueil</a>
</div>
	
<c:import url="/inc/footer.inc.jsp" />