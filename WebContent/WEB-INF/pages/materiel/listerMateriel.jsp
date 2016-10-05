<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>

<c:set var="titre" value="Liste des matériesl" scope="request" />

<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />

<script src="<c:url value="/js/ListerMateriel.js"/>"></script>

<h1>Liste des matériels</h1>
<p style="text-align: center;">${message}</p>

<form class="offset">
	<p>
		<span id="gras">Afficher : </span>
		<span class="choix">
			<label for="dateAchat">Date d'achat</label>
			<input type="checkbox" name="dateAchat" id="dateAchat" onchange="affDateAchat()" />
		</span>
		<span class="choix">
			<label for="four">Fournisseur</label>
			<input	type="checkbox" name="fournisseur" id="fournisseur" onchange="affFournisseur()" />
		</span>
		<span class="choix">
			<label for="marque">Marque</label>
			<input type="checkbox"	name="marque" id="marque" onchange="affMarque()" />
		</span>
		<span class="choix">
			<label for="etat">Etat</label>
			<input type="checkbox" name="etat" id="etat" onchange="affEtat()" />
		</span>
		<span class="choix">
			<label for="numSer">Numéro de série</label>
			<input type="checkbox" name="numSer" id="numSer" onchange="affNumSer()" />
		</span>
		<span class="choix">
			<label for="deplacable">Deplaçable</label>
			<input type="checkbox" name="deplacable" id="deplacable" onchange="affDeplacable()" />
		</span>
		<span class="choix">
			<label for="ouvLoc">Ouvert à la location</label>
			<input type="checkbox" name="ouvLoc" id="ouvLoc" onchange="affOuvLoc()"/>
		</span>
		<span class="choix">
			<label for="type">Type</label>
			<input type="checkbox" name="type" id="type" onchange="affType()"/>
		</span>
	</p>
</form>

<table class='tablesorter-blue pure-table'>
<thead>
	<tr>
		<th>Numéro IMMO</th>
		<th>Désignation</th>
		<th class="listCat">Catégorie</th>
		<th class="listDateAchat">Date d'achat</th>
		<th class="listFour">Fournisseur</th>
		<th class="listMarque">Marque</th>
		<th class="listEtat">Etat</th>
		<th class="listNumSer">Numéro de série</th>
		<th class="listDepl">Deplaçable</th>
		<th class="listOuvLoc">Ouvert à la location</th>
		<th class="listType">Type</th>
		<th>Actions</th>
	</tr>
</thead>
<tbody>
	<c:forEach items="${listeMateriels}" var="mat">
		<tr>
			<td>ANA-${mat.idMateriel}</td>
			<td><c:out value="${mat.designation.libelleDesignation}" /></td>
			<td class="listCat"><c:out value="${mat.categorie.libelleCat}" /></td>
			<td class="listDateAchat"><c:out value="${mat.dateAchat}" /></td>
			<td class="listFour"><c:out value="${mat.fournisseur.nomFournisseur}" /></td>
			<td class="listMarque"><c:out value="${mat.marque.nomMarque}" /></td>
			<td class="listEtat"><c:out value="${mat.etat.libelleEtat}" /></td>
			<td class="listNumSer"><c:out value="${mat.numSerie}" /></td>
			<td class="listDepl">
				<c:choose>
					<c:when test="${mat.deplacable==true}">
						oui
					</c:when>
					<c:otherwise>
						non
					</c:otherwise>
				</c:choose>
			</td>
			<td class="listOuvLoc">
				<c:choose>
					<c:when test="${mat.louable==true}">
							oui
					</c:when>
					<c:otherwise>
						non
					</c:otherwise>
				</c:choose>
			</td>
			
			<td class="listType"><c:out value="${mat.typeMat}" /></td>
			<td>
				<a href="<c:url value="<%= Pattern.MATERIEL_CONSULTER %>" />?idMateriel=<c:out value="${mat.idMateriel}" />" title='Afficher les détails'><i class="fa fa-search"></i>
				</a>
				<a href="<c:url value="<%= Pattern.MATERIEL_MODIFIER %>" />?idMateriel=<c:out value="${mat.idMateriel}" />" title='Modifier les informations'><i class="fa fa-pencil"></i></a>
			</td>
		</tr>
	</c:forEach>
</tbody>
</table>
<div class='align-center'>
	<a href="<c:url value="<%= Pattern.MATERIEL_EXPORTER %>"/>" class="btn">Exporter les données</a>
</div>
<c:import url="/inc/footer.inc.jsp" />