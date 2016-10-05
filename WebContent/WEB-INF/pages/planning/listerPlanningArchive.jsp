<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page import="fr.gemao.sql.util.DateUtil"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="fr.gemao.entity.planning.Planning"%>
<%@ page import="fr.gemao.view.Pattern"%>
<%@ page import="fr.gemao.entity.planning.Planning"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.text.SimpleDateFormat"%>

<c:set var="titre" value="Lister les plannings" scope="request" />

<c:import url="/inc/head.inc.jsp" />
<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />

	<h1>Liste des plannings archivés</h1>

	<table class='tablesorter-blue pure-table'>
		<thead>
			<tr>
				<th>Nom</th>
				<th>Date début</th>
				<th>Date fin</th>
				<th>Actions</th>
				<th>Validé</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${plannings}" var="planning">
				<tr>
					<td><c:out value="${planning.getNomPlanning()}" /></td>
					<td><c:out value="${DateUtil.toFrenchDate(planning.getDateDeb())}" /></td>
					<td><c:out value="${DateUtil.toFrenchDate(planning.getDateFin())}" /></td>
					<td>
						<a title="Afficher" href="<c:url value="${Pattern.PLANNING_AFFICHER }?idPlanning=${planning.getIdPlanning()}"/>"><i class="fa fa-search"></i></a>
						<a title="Désarchiver" href="<c:url value="${Pattern.PLANNING_LISTER_ARCHIVE}?idPlanningDesarchive=${planning.getIdPlanning()}"/>"><i class="fa fa-unlock"></i></a>
					</td>
					<c:if test="${planning.getValide()}">
						<td>Oui</td>
					</c:if>
					<c:if test="${!planning.getValide()}">
						<td>Non</td>
					</c:if>
				</tr>
			</c:forEach>
		</tbody>
	</table>
<c:import url="/inc/footer.inc.jsp" />

