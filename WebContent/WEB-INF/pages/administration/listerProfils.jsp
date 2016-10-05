<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>

<c:set var="titre" value="Liste des profils" scope="request" />

<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />
<h1>Liste des profils</h1>
<table class='tablesorter-blue pure-table'>
<thead>
	<tr>
		<th>Nom</th>
		<th>Actions</th>
	</tr>
</thead>
<tbody>
	<c:forEach items="${requestScope.listeProfils}" var="profil">
		<tr>
			<td><c:out value="${profil.nomProfil}" /></td>
			<td>
				<a href="<c:url value="<%= Pattern.ADMINISTRATION_CONSULTER_PROFIL %>"/>?id=<c:out value="${profil.idProfil}"/>" title='Afficher les détails'><i class="fa fa-search"></i>
				</a>
				<c:if test="${profil.nomProfil != 'Admin'  && profil.nomProfil != 'Professeur'}">
					<a href="<c:url value="<%= Pattern.ADMINISTRATION_MODIFIER_PROFIL %>"/>?id=<c:out value="${profil.idProfil}" />" title='Modifier les informations'><i class="fa fa-pencil"></i></a>
					<a href="<c:url value="<%= Pattern.ADMINISTRATION_SUPPRIMER_PROFIL %>"/>?id=<c:out value="${profil.idProfil}" />" title='Supprimer le profil'><i class="fa fa-trash-o"></i></a>
				</c:if>
			</td>
		</tr>
	</c:forEach>
</tbody>
</table>
<div class='align-center'><a href="<c:url value='<%= Pattern.ADMINISTRATION_AJOUT_PROFIL %>'/>" class="btn" title="Créer un nouveau profil">Nouveau</a></div>
<c:import url="/inc/footer.inc.jsp" />