<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="titre" value="Liste des modifications" scope="request" />

<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />
<h1>Liste des modifications</h1>
<table class='tablesorter-blue pure-table'>
	<thead>
		<tr>
			<th>Date</th>
			<th>Responsable</th>
			<th>Action</th>
		</tr>
	</thead>
	<tbody>
	<c:forEach items="${requestScope.listeModifs}" var="modif">
		<tr>
			<td><fmt:formatDate value="${modif.dateModif}" pattern="dd/MM/yyyy HH:mm:ss" /></td>
			<td><c:out value="${modif.personne.login}" /></td>
			<td><c:out value="${modif.libelle}" /></td>
		</tr>
	</c:forEach>
	</tbody>
</table>
<div class='align-center'>
	<a href="<c:url value="<%= Pattern.ADMINISTRATION_MODIF_EXPORTER %>"/>" class="btn">Exporter les données</a>
</div>
<c:import url="/inc/footer.inc.jsp" />