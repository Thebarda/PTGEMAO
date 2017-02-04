<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>

<c:set var="titre" value="Comptabilité enseignement" scope="request" />

<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />

<h1>Comptabilité enseignement</h1>

<c:choose>
<c:when test="${empty idFamille }">
	<form method="post" action="#">
	<fieldset>
		<legend>Familles</legend>
		<label>Veuillez choisir une famille</label>
		<select name="famille">
			<c:forEach items="${familles }" var="famille">
				<option value="${famille.getIdFamille() }"><c:out value="${famille.getNomFamille() }"></c:out></option>
			</c:forEach>
		</select>
		<label>Choisir l'année : </label>
		<select name="annee">
			<option></option>
			<c:forEach items="${date }" var="year">
				<option value="${year }"><c:out value="${year }"></c:out></option>
			</c:forEach>
		</select>
		<input type="submit" value="Valider" class="btn">
	</fieldset>
	</form>
</c:when>
<c:otherwise>
	<c:choose>
	<c:when test="${empty vide }">
	<form method="post" action="#">
		<fieldset>
			<legend>Famille</legend>
			Veuillez choisir une famille :
			<select name="famille" id="petit">
				<c:forEach items="${familles }" var="famille">
					<option value="${famille.getIdFamille() }"><c:out value="${famille.getNomFamille() }"></c:out></option>
				</c:forEach>
			</select>
			<label>Choisir l'année : </label>
			<select name="annee">
				<option></option>
				<c:forEach items="${date }" var="year">
					<option value="${year }"><c:out value="${year }"></c:out></option>
				</c:forEach>
			</select>
			<input type="submit" value="Valider" class="btn align-right">
		</fieldset>
	</form>
	<span><h2 class="offset">Famille : <%= request.getAttribute("nomFamille") %></h2></span>
	<h2 class="offset">Année : <%= request.getAttribute("annee") %> - <%= request.getAttribute("anneeFin") %></h2>
	<h3 id="validation" class="offset text-success"></h3>
	<h2 class="offset">Tableau Fiche Comptable</h2>
	<div id="tfc">
		<%= request.getAttribute("tfc") %>
	</div>
	<br><br>
	<h2 class="offset">Tableau Récapitulatif</h2>
	<div id="recap">
		<%= request.getAttribute("tr") %>
	</div>
	<button id="enregistrer" class="offset btn" style="margin-top: 10px;">Enregistrer les tableaux</button>
	</c:when>
	<c:otherwise>
		<p class="offset"><c:out value="${vide }"></c:out></p>
	</c:otherwise>
	</c:choose>
</c:otherwise>
</c:choose>
<script src="<c:url value="/js/ScriptFicheComptable.js"/>" ></script>
<script src="<c:url value="/js/ScriptTableauRecapitulatif.js"/>" ></script>
<script src="<c:url value="/js/enregistrerTableaux.js"/>" ></script>
<c:import url="/inc/footer.inc.jsp" />