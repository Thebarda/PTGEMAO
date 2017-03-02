<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>

<c:set var="titre" value="Lister les répertoires et les fichiers" scope="request" />
<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />
<p class="text-danger offset"><c:out value="${diffDate }"></c:out></p>
<h1>Lister les dossiers et les fichiers</h1>
<div class="offset">

<c:choose>
	<c:when test="${empty ajout }">
			<p class="text-success"><c:out value="${ajout }"></c:out></p>
			<a href="<c:out value="${apz }"></c:out>" download="Documents" class="offset btn" id="sauvegarde">Télécharger la sauvegarde</a>
			<c:if test="${noReturn == false}">
				<br>
				<a id="retour" class="btn" href="<c:url value="<%= Pattern.ARCHIVAGE_LISTER %>"/>?path=<%= session.getAttribute("retour") %>">Retour</a><br><br><br>
			</c:if>
			<p><b>Chemin : <c:out value="${pathAffiche }"></c:out></b></p>
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
	</c:when>
	<c:otherwise>
		
		<c:if test="${not noReturn}">
			<p class="text-success"><c:out value="${ajout }"></c:out></p>
			
			<a id="retour" class="btn" href="<c:url value="<%= Pattern.ARCHIVAGE_LISTER %>"/>?path=<%= session.getAttribute("path") %>">Retour</a><br><br><br>
		</c:if>
	</c:otherwise>
</c:choose>
<script type="text/javascript" src="<c:url value="/js/sauvegarde.js"></c:url>"></script>
<c:import url="/inc/footer.inc.jsp" />