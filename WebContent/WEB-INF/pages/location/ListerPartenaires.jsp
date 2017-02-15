<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>

<c:set var="titre" value="Liste des contrats de location" scope="request" />

<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />

<h1>Liste des partenaires</h1>

<table class='tablesorter-blue  pure-table' id="tableNom">
	<thead>
		<tr>
			<th>Raison sociale</th>
			<th>Adresse</th>
			<th>Année de partenariat</th>
			<th>Taille de la page</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${partenaires}" var="partenaire">
			<tr>
				<td><c:out value="${partenaire.getRaisonSociale() }"></c:out></td>
				<td><c:out value="${partenaire.getAdresse().getNumRue() }"></c:out><br><c:out value="${partenaire.getAdresse().getNomRue() }"></c:out> <c:out value="${partenaire.getAdresse().getInfoCompl() }"></c:out> <c:out value="${partenaire.getAdresse().getCommune().getNomCommune() }"></c:out> <c:out value="${partenaire.getAdresse().getCommune().getCodePostal() }"></c:out></td>
				<td><c:out value="${partenaire.getAnnee() }"></c:out></td>
				<td><c:out value="${partenaire.getTaillePage() }"></c:out></td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<c:import url="/inc/footer.inc.jsp" />