<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="titre" value="Erreur de connexion à la base de donnes" scope="request" />

<c:import url="/inc/head.inc.jsp" />
<c:import url="/inc/header.inc.jsp" />

<p class="offset text-danger">Il semble y avoir une erreur de la connexion avec la base de données.</p>
<p class="offset text-danger">${ requestScope.error}</p>
	

<c:import url="/inc/footer.inc.jsp" />