<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="titre" value="Erreur" scope="request" />

<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />

<h1><c:out value="${requestScope.titreH1 }"/></h1>

<p class='offset text-danger'><c:out value="${requestScope.resultat }"></c:out> </p>

<div class="align-center">
	<a class='btn' href='<c:url value="${requestScope.lienBouton }"/>'>
		<c:out value="${requestScope.nomBouton }"/>
	</a>
</div>
	
<c:import url="/inc/footer.inc.jsp" />