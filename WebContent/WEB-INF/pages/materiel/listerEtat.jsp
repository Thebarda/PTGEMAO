<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>

<c:set var="titre" value="Liste des états" scope="request" />

<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />
<script src="<c:url value="/js/listerEtat.js"/>"></script>


<h1>Liste des états</h1>
<table class='pure-table'>
	<c:forEach var="etat" items="${listEtat}">
		<tr>
			<form action="#" method="post" class="offset">
				<td><label for="etat">${etat.libelleEtat}</label></td>
				<input type="text" name="id" value="${etat.idEtat}" style="display: none">
				<td><input type="submit" class="btSuppr" value="Supprimer"></td>
			</form>
		</tr>	
	</c:forEach>
	<form action="#" method="post" class="offset">
		<tr>
			<td><input type="text" required name="libelle" value="" ></td>
			<input type="text" name="id" value="0" style="display: none">
			<td><input type="submit" value="Ajouter"></td>
		</tr>
	</form>
</table>
<fieldset class='align-center no-border'>
	<div>
		<p>Nous ne pouvons pas supprimer les etats utilisés.</p>
		<c:if test="${!empty modifKO}">
			<p style="color: red">Erreur : <c:out value="${err}" /></p>
		</c:if>
	</div>
</fieldset>

<c:import url="/inc/footer.inc.jsp" />