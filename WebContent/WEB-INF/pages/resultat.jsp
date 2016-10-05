<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="titre" value="Confirmation" scope="request" />

<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />

<h1><c:out value="${requestScope.titreH1 }"/></h1>

<p class='offset text-success'><c:out value="${requestScope.resultat }"></c:out> </p>

<div class="align-center">
	<a href='<c:url value="${requestScope.lienBouton }"/>'><input class="btn" type='button' value='<c:out value="${requestScope.nomBouton }"/>'/></a>
	<c:if test="${requestScope.lienBouton2 != null}">
		<a class='btn' href='<c:url value="${requestScope.lienBouton2}"/>'
			<c:if test="${requestScope.downloadBouton2}">download='<c:url value="${requestScope.downloadBouton2}"/>'</c:if>>
			<c:out value="${requestScope.nomBouton2}"/>
		</a>
	</c:if>
</div>
	
<c:import url="/inc/footer.inc.jsp" />
