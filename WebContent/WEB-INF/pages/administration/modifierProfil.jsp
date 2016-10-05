<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>

<c:set var="titre" value="Modifier un profil" scope="request" />

<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />

<h1>Modification d'un profil</h1>

<c:choose>
	<c:when test="${profil.nomProfil == 'Admin'  }">
		<p class="offset text-danger">Ce profil n'est pas modifiable.</p>
	</c:when>
	<c:otherwise>
	
	
<form method="post" action="<c:url value="<%= Pattern.ADMINISTRATION_MODIFIER_PROFIL %>" />">
	<fieldset>
		<legend>Informations</legend>
		
		<div>
			<label for="nom" class='required'>Nom </label>
			<input type="text" name="nom" required autocomplete="off" value="<c:out  value="${profil.nomProfil }"/>"/>
		</div>
		
		<div class="text-danger align-center"><c:out  value="${erreur }"/></div>
	</fieldset>
	
	<fieldset>
		<legend>Droits</legend>
		<ul>
			<c:forEach items="${listeModules}" var="module">
			
				<c:set var="typeDroit" value="${profil.recupererTypeDroit(module.nomModule)}" scope="request" />
				<li>
					<div>Module <c:out value="${module.nomModule }"/></div>
					<span>
						<input type="radio" value=""
							name="module<c:out value="${module.idModule }"/>"
							<c:if test='${typeDroit == "Aucun" }'>checked</c:if>	
						/>
						<label>Aucun</label>
					</span>
					<c:forEach items="${listeTypeDroit}" var="type">
						<span>
							<input type="radio" 
								name="module<c:out value="${module.idModule }"/>"
								value="<c:out value="${type.idType}"/>"
								<c:if test='${typeDroit == type.nomType }'>checked</c:if>
							/>
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
	
	
	</c:otherwise>
</c:choose>

	
<c:import url="/inc/footer.inc.jsp" />