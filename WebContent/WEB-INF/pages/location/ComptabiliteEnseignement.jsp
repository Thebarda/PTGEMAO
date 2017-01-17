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
		<select class="offset" name="famille">
			<c:forEach items="${familles }" var="famille">
				<option value="${famille.getIdFamille() }"><c:out value="${famille.getNomFamille() }"></c:out></option>
			</c:forEach>
		</select>
		<br>
		<input type="submit" value="Valider" class="offset">
	</fieldset>
	</form>
</c:when>
<c:otherwise>
	<%= request.getAttribute("tfc") %>
</c:otherwise>
</c:choose>
<script src="<c:url value="/js/ScriptFicheComptable.js"/>" ></script>
<c:import url="/inc/footer.inc.jsp" />