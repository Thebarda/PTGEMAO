<%@page import="fr.gemao.sql.DAOFactory"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>
<%@ page import="fr.gemao.entity.planning.Creneau"%>
<%@ page import="fr.gemao.entity.planning.Planning"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>

<c:set var="titre" value="Afficher les creneaux d'un planning"
	scope="request" />


<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% Planning planning = (Planning) request.getAttribute("planning"); %>

<h1>Détail des créneaux du planning "<%= planning.getNomPlanning() %>"</h1>

<table class="tablesorter-blue tablesorter pure-table">
	<thead>
		<tr>
			<th>Libellé</th>
			<th>Heure début</th>
			<th>Durée</th>
			<th>Salle</th>
			<th>Jour</th>
			<th>Discipline</th>
			<th>Prof</th>
			<th>Actions</th>
	
		</tr>
	</thead>

	<tbody>
	<c:forEach items="${creneaux}" var="creneaux">
		<tr>
			<td><c:out value="${creneaux.getLibelle()}" /></td>
			<td><c:out value="${creneaux.getHeureDeb()}" /></td>
			<td><c:out value="${creneaux.getDuree()}" /></td>
			<td><c:out value="${creneaux.getSalle().getNomSalle()}" /></td>
			<td><c:out value="${creneaux.getJour().getNomJour()}" /></td>
			<td><c:out
					value="${creneaux.getCours() != null ? creneaux.getCours().getDiscipline().getNomDiscipline() : '-'}" />
			</td>
			<td><c:out
					value="${creneaux.getCours() != null ? creneaux.getCours().getProf().getIdentiteProf() : '-'}" />
			</td>
			<td><a title="Modifier"
				href="<c:url value="${Pattern.CRENEAU_MODIFIER }?idPlanning=${creneaux.getIdPlanning()}&idCreneau=${creneaux.getIdCreneau()}"/>"><i
					class="fa fa-pencil"></i></a> <a title="Supprimer"
				href="<c:url value="${Pattern.CRENEAU_SUPPRIMER }?idCreneau=${creneaux.getIdCreneau()}"/>"><i
					class="fa fa-trash-o"></i></a></td>
		</tr>
	</c:forEach>
	</tbody>


</table>

<c:import url="/inc/footer.inc.jsp" />
