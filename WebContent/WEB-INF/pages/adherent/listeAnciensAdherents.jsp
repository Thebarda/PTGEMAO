<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>

<c:set var="titre" value="Liste des anciens adhérents" scope="request" />

<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />
<script src="<c:url value="/js/ListerAdherent.js"/>"></script>

<!-- VOIR COMMENT METTRE JSTL LIBRARY EN LOCAL -->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<h1>Liste des anciens adhérents</h1>

<form class="offset">
	<p>
		<span id="gras">Afficher : </span>
		<span class="choix">
			<label for="famille">Famille</label>
			<input type="checkbox" name="famille" id="famille" onchange="affFamille()"/>
		</span>
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
			<label for="entree">Date d'entrée</label>
			<input type="checkbox"	name="entree" id="entree" onchange="affEntree()" />
		</span>
		<span class="choix">
			<label for="droitImage">Droit à l'image</label>
			<input type="checkbox" name="droitImage" id="droitImage" onchange="affDroitImage()" />
		</span>
		<span class="choix">
			<label for="motifSortie">Motif de sortie</label>
			<input type="checkbox" name="motifSortie" id="motifSortie" onchange="affMotifSortie()" />
		</span>
	</p>
</form>


<table class='tablesorter-blue  pure-table'>
<thead>
	<tr>
		<th>Nom</th>
		<th>Prénom</th>
		<th class="listFamille">Famille</th>
		<th class="listNaiss">Date de naissance</th>
		<th class="listEmail">Email</th>
		<th class="listCom">Commune</th>
		<th class="listCom">Code Postal</th>
		<th class="listEntree">Date d'entrée</th>
		<th class="listDroitIm">Droit à l'image</th>
		<th class="listMotif">Motif de sortie</th>
		<th>Actions</th>
	</tr>
</thead>
<tbody>
	<c:forEach items="${listeAdherents}" var="adh">
		<tr>
			<td><c:out value="${adh.getNom()}" /></td>
			<td><c:out value="${adh.getPrenom()}" /></td>
			<td class="listFamille"><c:out value="${adh.getFamille().getNomFamille()}" /></td>
			<td class="listNaiss"><fmt:formatDate
					value="${adh.getDateNaissance()}" pattern="dd/MM/yyyy" /></td>
			<td class="listEmail"><c:out value="${adh.getEmail()}" /></td>
			<td class="listCom"><c:out
					value="${adh.getAdresse().getCommune().getNomCommune()}" /></td>
			<td class="listCom"><c:out
					value="${adh.getAdresse().getCommune().getCodePostal()}" /></td>
			<td class="listEntree"><fmt:formatDate
					value="${adh.getDateEntree()}" pattern="dd/MM/yyyy" /></td>
			<c:choose>
				<c:when test="${adh.isDroitImage()}"><td class="listDroitIm">Oui</td></c:when>
				<c:otherwise><td class="listDroitIm">Non</td></c:otherwise>
			</c:choose>
			<td class="listMotif"><c:out
					value="${adh.getMotif().getLibelle()}" /></td>

			<td><a
				href="<c:url value="<%= Pattern.ADHERENT_CONSULTER %>"/>?id=<c:out value="${adh['idPersonne']}" />"
				title='Afficher les détails'><i class="fa fa-search"></i>
				</a> <a
				href="<c:url value="<%= Pattern.ADHERENT_MODIFIER %>"/>?id=<c:out value="${adh['idPersonne']}" />"
				title='Modifier les informations'><i class="fa fa-pencil"></i></a></td>
		</tr>
	</c:forEach>
</tbody>
</table>
<div class='align-center'>
	<a href="<c:url value="<%= Pattern.ADHERENT_LISTER %>"/>" class="btn">Adhérents inscrits</a>
	<a href="<c:url value="<%= Pattern.ADHERENT_EXPORTER_ANCIENS %>"/>" class="btn">Exporter les données</a>
</div>
<c:import url="/inc/footer.inc.jsp" />