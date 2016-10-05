<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>
<%@ page import="fr.gemao.entity.cours.Salle"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>

<c:set var="titre" value="Afficher la liste des salles" scope="request" />

<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<h1>Liste des salles</h1>

<table class="tablesorter-blue tablesorter pure-table">
	<thead>
		<tr>
			<th>Nom</th>
			<th>Action</th>
		</tr>
	</thead>

	<tbody>
		<c:forEach items="${salles}" var="salle">
		<tr>
			<td><c:out value="${salle.getNomSalle()}" /></td>
			<td>
				<a title="Modifier" href="<c:url value="${Pattern.SALLE_MODIFIER }?idSalleUpdate=${salle.getIdSalle()}"/>"><i class="fa fa-pencil"></i></a>
				<a title="Supprimer" href="<c:url value="${Pattern.SALLE_LISTER }?idSalleDelete=${salle.getIdSalle()}"/>"><i class="fa fa-trash-o"></i></a>
			</td>
		</tr>
		</c:forEach>
	</tbody>

</table>


<p class="align-center">
	<a class="waves-effect waves-light btn" title="Creer" href="<c:url value="<%= Pattern.SALLE_CREER %>"/>">Créer une salle</a>
</p>


<c:import url="/inc/footer.inc.jsp" />
