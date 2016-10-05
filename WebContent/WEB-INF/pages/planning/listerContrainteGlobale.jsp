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

<%
	Planning planning = (Planning) request.getAttribute("planning");
%>

<h1>
	Détail des contraintes globales du planning "<%=planning.getNomPlanning()%>"
</h1>
<table class='pure-table tablesorter-blue'>
	<thead>
		<tr>
			<th>Salle</th>
			<th>Jour</th>
			<th>Intervalle</th>
			<th>Message</th>
			<th>Actions</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${contraintes}" var="contrainte">
			<tr>
				<td><c:out
						value="${contrainte.getSalle() == null ? '-' : contrainte.getSalle().getNomSalle()}" /></td>
				<td><c:out
						value="${contrainte.getJour() == null ? '-' : contrainte.getJour().getNomJour()}" /></td>
				<td><c:out
						value="${contrainte.getHeureDebut()} - ${contrainte.getHeureFin()}" /></td>
				<td><c:out
						value="${contrainte.getMessage()}" /></td>
				<td><a title="Supprimer"
					href="<c:url value="${Pattern.CRENEAU_MODIFIER }?idCreneau=${creneau.getIdCreneau()}&idContrainteDelete=${contrainte.getIdContrainte()}"/>"><i
						class="fa fa-trash-o"></i></a></td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<c:import url="/inc/footer.inc.jsp" />
