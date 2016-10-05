<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>

<c:set var="titre" value="Liste des catégories" scope="request" />

<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />
<script src="<c:url value="/js/listerCategorie.js"/>"></script>


<h1>Liste des catégories</h1>
<table class='pure-table'>
	<c:forEach var="cat" items="${listCat}">
		<tr>
			<form action="#" method="post" class="offset">
				<td><label for="cat">${cat.libelleCat}</label></td>
				<input type="text" name="id" value="${cat.idCategorie}" style="display: none">
				<td><input type="submit" class="btSuppr" value="Supprimer"></td>
			</form>
		</tr>	
	</c:forEach>
	<form action="#" method="post" class="offset">
		<tr>
			<td><input type="text" required name="lib" value="" ></td>
			<input type="text" name="id" value="0" style="display: none">
			<td><input type="submit" value="Ajouter"></td>
		</tr>
	</form>
</table>
<fieldset class='align-center no-border'>
	<div>
		<p>Nous ne pouvons pas supprimer les catégories utilisées.</p>
		<c:if test="${!empty modifKO}">
			<p style="color: red">Erreur : <c:out value="${err}" /></p>
		</c:if>
	</div>
</fieldset>

<c:import url="/inc/footer.inc.jsp" />