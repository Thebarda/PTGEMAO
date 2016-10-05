<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>

<c:set var="titre" value="Liste du personnel" scope="request" />

<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />
<script src="<c:url value="/js/ListerPersonnel.js"/>"></script>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<h1>Liste du personnel</h1>

<form class="offset">
	<p>
		<span id="gras">Afficher : </span>
		<span class="choix">
			<label for="naissance">Date de naissance</label>
			<input type="checkbox" name="naissance" id="naissance" onchange="affNaissance()" />
		</span>
		<span class="choix">
			<label for="email">Email</label>
			<input type="checkbox" name="email" id="email" onchange="affEmail()" />
		</span>
		<span class="choix">
			<label for="commune">Commune</label>
			<input	type="checkbox" name="commune" id="commune" onchange="affCommune()" />
		</span>
		<span class="choix">
			<label for="entree">Date de début d'enseignement</label>
			<input type="checkbox"	name="entree" id="entree" onchange="affEntree()" />
		</span>
		<span class="choix">
			<label for="CA">Membre CA</label>
			<input type="checkbox" name="CA" id="CA" onchange="affCA()" />
		</span>
	</p>
</form>

<table class='tablesorter-blue pure-table'>
	<thead>
		<tr>
			<th>Nom</th>
			<th>Prénom</th>
			<th class="listNaiss">Date de naissance</th>
			<th class="listEmail">Email</th>
			<th class="listCom">Commune</th>
			<th class="listCom">Code Postal</th>
			<th class="listEntree">Date de début d'enseignement</th>
			<th class="listCA">Membre CA</th>
			<th>Actions</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${listePersonnels}" var="pers">
			<tr>
				<td><c:out value="${pers['nom']}" /></td>
				<td><c:out value="${pers['prenom']}" /></td>
				<td class="listNaiss"><fmt:formatDate
							value="${pers.getDateNaissance()}" pattern="dd/MM/yyyy" /></td>
				<td class="listEmail"><c:out value="${pers.getEmail()}" /></td>
				<td class="listCom"><c:out
							value="${pers.getAdresse().getCommune().getNomCommune()}" /></td>
				<td class="listCom"><c:out
							value="${pers.getAdresse().getCommune().getCodePostal()}" /></td>
				
				<td class="listEntree"><fmt:formatDate
							value="${pers.getDateEntree()}" pattern="dd/MM/yyyy" /></td>
				<c:choose>
					<c:when test="${pers.isMembreCA()}">
						<td class="listCA">Oui</td>
					</c:when>
					<c:otherwise>
						<td class="listCA">Non</td>
					</c:otherwise>
				</c:choose>	
				<td>
					<a href="<c:url value="<%= Pattern.PERSONNEL_CONSULTER %>" />?id=<c:out value="${pers['idPersonne']}" />" title='Afficher les détails'><i class="fa fa-search"></i>
					</a>
					<a href="<c:url value="<%= Pattern.PERSONNEL_MODIFIER %>" />?id=<c:out value="${pers['idPersonne']}" />" title='Modifier les informations'><i class="fa fa-pencil"></i></a>
					<a href="<c:url value="<%= Pattern.ADMINISTRATION_CHANGER_PROFIL %>" />?id=<c:out value="${pers['idPersonne']}" />" title="Modifier le profil dans l'application"><i class="fa fa-user"></i></a>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<div class='align-center'>
	<a href="<c:url value="<%= Pattern.PERSONNEL_EXPORTER %>"/>" class="btn">Exporter les données</a>
</div>
<c:import url="/inc/footer.inc.jsp" />
