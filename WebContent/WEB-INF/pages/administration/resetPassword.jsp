<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>

<c:set var="titre" value="Réinitialisation d'un mot de passe" scope="request" />

<c:import url="/inc/head.inc.jsp" />
<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />

	<h1>Réinitialisation d'un mot de passe</h1>
	
	<form id='form' action='#' method='POST'>
		
		<c:choose>
			<%-- 1er écran : juste une liste déroulante --%>
			<c:when test="${empty requestScope.idPersonne && empty requestScope.personne}">
				<fieldset>
					<legend>Choix de la personne</legend>
					<div>
						<label>Login</label>
						<select name="idPersonne" id="idPersonne">
								<option selected="selected">Sélectionner l'identifiant</option>
							<c:forEach items="${requestScope.listePersonnel}" var="personnel">
								<option value='<c:out value="${personnel.idPersonne }"/>'><c:out value="${personnel.login }"/></option>
							</c:forEach>
						</select>
					</div>
				</fieldset>
			</c:when>

			<%-- 2eme écran : la liste déroulante + les infos de la personne choisie--%>
			<c:when test="${ (!empty requestScope.idPersonne && !empty requestScope.personne) || (!empty requestScope.erreur)}">
				<fieldset>
					<legend>Choix de la personne</legend>
					<div>
						<label>Login</label>
						<select name="idPersonne" id="idPersonne">
							<c:forEach items="${requestScope.listePersonnel}" var="personnel">
								<option value='<c:out value="${personnel.idPersonne }"/>'
								<c:if test="${personnel.idPersonne==requestScope.idPersonne}">selected</c:if> >
									<c:out value="${personnel.login }"/>
								</option>
							</c:forEach>
						</select>
					</div>
					<%-- Champ caché contenant la valeur de l'id de la personne sélectionnée --%>
					<input type='text' class='hidden' value='<c:out value="${requestScope.personne.idPersonne }"/>' name='id' id='id'/>
				</fieldset>
				
				<fieldset>
					<legend>Informations sur la personne</legend>
					<div>
						<span class='text-label'>Nom</span>
						<span><c:out value="${requestScope.personne.nom }"/></span>
					</div>
					<div>
						<span class='text-label'>Prénom</span>
						<span><c:out value="${requestScope.personne.prenom }"/></span>
					</div>
					<div>
						<span class='text-label'>Identifiant</span>
						<span><c:out value="${requestScope.personne.login }"/></span>
					</div>
				</fieldset>
				
				<div class='align-center text-danger'><c:out value="${requestScope.erreur }"></c:out></div>
				
				<div id="dialog-form" title="Confirmer la réinitialisation">
					<label for="password">Votre mot de passe</label>		
					<input type='password' name='password' id='password' />
				</div>
				
				<fieldset class='align-center no-border'>
					<input type="button" value="Valider" id='Valider'/>
				</fieldset>
		
				
			</c:when>
			
			
			<%-- Dernier écran : Message de résultat --%>
			<c:otherwise>
				<div class='offset'>Modification effectuée, le nouveau mot de passe est <span class='text-danger'><c:out value="${requestScope.personne.password}"/></span>.</div>
			</c:otherwise>
		</c:choose>
		
		
		
	</form>
	
	
	

<c:import url="/inc/footer.inc.jsp" />
<script type="text/javascript" src="<c:url value="/js/resetPassword.js"/>"></script>