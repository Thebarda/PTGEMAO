
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


<script src="<c:url value="/js/planning/listerPlanning.js"/>"></script>

<h1>Liste des plannings</h1>

	<table class='tablesorter-blue pure-table'>
		<thead>
			<tr>
				<th>Nom</th>
				<th>Date début</th>
				<th>Date fin</th>
				<th>Actions</th>
				<th>Contraintes globales</th>
				<th>Créneaux</th>
				<th>Validé</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${plannings}" var="planning">
				<tr id="<c:out value="${planning.getIdPlanning()}"/>">
					<td><c:out value="${planning.getNomPlanning()}" /></td>
					<td><c:out value="${DateUtil.toFrenchDate(planning.getDateDeb())}" /></td>
					<td><c:out value="${DateUtil.toFrenchDate(planning.getDateFin())}" /></td>
					<td>
						<a title="Afficher"href="<c:url value="${Pattern.PLANNING_AFFICHER }?idPlanning=${planning.getIdPlanning()}"/>"><i class="fa fa-search"></i></a>
						<a title="Dupliquer" href="<c:url value="${Pattern.PLANNING_LISTER }?idPlanningDuplique=${planning.getIdPlanning()}"/>"><i class="fa fa-files-o"></i></a>
						<c:if test="${!planning.isArchive() }">
							<a title="Archiver" href="<c:url value="${Pattern.PLANNING_LISTER }?idPlanningArchive=${planning.getIdPlanning()}"/>"><i class="fa fa-lock"></i></a>
							
							<c:if test="${!planning.getValide() }">
								<a title="Ajouter des créneaux" href="<c:url value="${Pattern.PLANNING_MODIFIERCRENEAUX }?idPlanning=${planning.getIdPlanning()}"/>"><i class="fa fa-pencil-square-o"></i></a>
							</c:if>
						</c:if>
						<c:if test="${!planning.getValide() }">
							<a  title="Modifier caractéristiques" href="<c:url value="${Pattern.PLANNING_LISTER }?idPlanningModif=${planning.getIdPlanning()}"/>"><i class="fa fa-pencil"></i></a>
							<a title="Supprimer" class="supprimer" ><i class="fa fa-trash-o"></i></a>
						</c:if>
					</td>
					<c:if test="${!planning.getValide() }">
						<td>
							<a title="Ajouter une contrainte globale" href="<c:url value="${Pattern.CONTRAINTE_ADD_GLOBALE }?idPlanning=${planning.getIdPlanning()}"/>"><i class="fa fa-plus-circle"></i></a>
							<a title="Lister les contraintes globales" href="<c:url value="${Pattern.CONTRAINTE_LISTE_GLOBALE }?idPlanning=${planning.getIdPlanning()}"/>"><i class="fa fa-list-ol"></i></a>
						</td>
						<td>
							<a title="Ajouter un creneau" href="<c:url value="${Pattern.PLANNING_LISTER }?quickAdd=${planning.getIdPlanning()}"/>"><i class="fa fa-plus-circle"></i></a>
							<a title="Lister les créneaux" href="<c:url value="${Pattern.CRENEAUX_LISTER }?idPlanning=${planning.getIdPlanning()}"/>"><i class="fa fa-list-ol"></i></a>
						</td>
					</c:if>
					<c:if test="${planning.getValide() }">
						<td></td>
					</c:if>
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

