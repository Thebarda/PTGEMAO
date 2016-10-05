<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>

<c:set var="titre" value="Ajout d'un profil" scope="request" />

<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />

<h1>Ajout d'un profil</h1>

<form method="post" action="<c:url value="<%= Pattern.ADMINISTRATION_AJOUT_PROFIL %>" />">
	<fieldset>
		<legend>Informations</legend>
		
		<div>
			<label for="nom" class='required'>Nom </label>
			<input type="text" name="nom" required autocomplete="off"/>
		</div>
		<div class='text-danger align-center'><c:out value="${erreur }"/></div>
	</fieldset>
		
	<fieldset>
		<legend>Droits</legend>
		<ul>
			<c:forEach items="${listeModules}" var="module">
				<li>
					<div>Module <c:out value="${module.nomModule }"/></div>
					<span>
						<input type="radio" checked="checked" value=""
							name="module<c:out value="${module.idModule }"/>"/>
						<label>Aucun</label>
					</span>
					<c:forEach items="${listeTypes}" var="type">
						<span>
							<input type="radio" 
								name="module<c:out value="${module.idModule }"/>"
								value="<c:out value="${type.idType}"/>"/>
							<label><c:out value="${type.nomType}"/></label>
						</span>
					</c:forEach>
				</li>
			</c:forEach>
		</ul>
		
	</fieldset>
	
	<fieldset class='align-center no-border'>
		<p class="oblig">* Champs obligatoires</p>
		<a href="<c:url value="<%= Pattern.ADMINISTRATION_LISTER_PROFIL %>" />"><input type="button" value="Retour" /></a>
		<input type="submit" value="Valider"/>
	</fieldset>
</form>
	
<c:import url="/inc/footer.inc.jsp" />