<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>

<c:set var="titre" value="Lister les répertoires et les fichiers" scope="request" />

<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />

<h1>Lister les dossiers et les fichiers</h1>
<div class="offset">
	<p class="text-success"><c:out value="${ajout }"></c:out></p>
	<span id="formDroit">
		<form method="post" action="<c:url value="<%= Pattern.ARCHIVAGE_LISTER %>"/>?path=<%=session.getAttribute("path") %>" enctype="multipart/form-data">
			<fieldset>
				<legend>Sauvegarder</legend>
				Dossier destination *: <input type="file" name="dossierDestination" directory webkitdirectory mozdirectory multiple>
				<br>
				<br>
				<input class="btn" type="submit" value="Sauvegarder">
			</fieldset>
		</form>
	</span>
	<c:if test="${not noReturn}">
		<a class="btn" href="<c:url value="<%= Pattern.ARCHIVAGE_LISTER %>"/>?path=<%= session.getAttribute("retour") %>">Retour</a><br><br><br>
	</c:if>
	<h2>Dossiers</h2>
	<table class="tablesorter-blue  pure-table">
	<c:forEach items="${reps }" var="rep">
		<tr>
			<td><a href="<c:url value="<%= Pattern.ARCHIVAGE_LISTER %>"/>?path=<%=session.getAttribute("path") %>--<c:out value="${rep }"/>"><c:out value="${rep }"></c:out></a></td>
			<td><a class="croix" href="<c:url value="<%= Pattern.ARCHIVAGE_LISTER %>"/>?delete=<%=session.getAttribute("path") %>--<c:out value="${rep }"/>">X</a></td>
		</tr>
		<br>
	</c:forEach>
	</table>
	<hr>
	<h2>Fichiers</h2>
	<table class="tablesorter-blue  pure-table">
	<c:forEach items="${files }" var="file">
		<tr>
			<td><span><c:out value="${file }"></c:out></span></td>
			<td><a class="croix" href="<c:url value="<%= Pattern.ARCHIVAGE_LISTER %>"/>?delete=<%=session.getAttribute("path") %>--<c:out value="${file }"/>">X</a></td>
		</tr>
		<br>
	</c:forEach>
	</table>
	<br><br>
	<span id="formGauche">
		<form id="formGauche" method="post" action="<c:url value="<%= Pattern.ARCHIVAGE_LISTER %>"/>?path=<%=session.getAttribute("path") %>" enctype="multipart/form-data">
			<fieldset>
				<legend>Ajouter un dossier</legend>
				Nom du dossier *: <input type="text" name="dossier">
				<br>
				<br>
				<input class="btn" type="submit" value="Ajouter le dossier">
			</fieldset>
		</form>
	</span>
	<span id="formDroit">
		<form id="formDroit" method="post"  action="<c:url value="<%= Pattern.ARCHIVAGE_LISTER %>"/>?path=<%=session.getAttribute("path") %>" enctype="multipart/form-data">
			<fieldset>
				<legend>Ajouter un fichier</legend>
				Fichier *: <input type="file" name="fichier">
				<br>
				<br>
				<input class="btn" type="submit" value="Ajouter le fichier">
			</fieldset>
		</form>
	</span>
</div>
<c:import url="/inc/footer.inc.jsp" />