<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>
<%@ page import="fr.gemao.entity.planning.Creneau"%>

<c:set var="titre" value="Modifier un créneau" scope="request" />

<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />


	<h1>Modifier un cours</h1>
	
	<form class="pure-form pure-form-aligned" id="modifierCours" method="post" action="<c:url value="#" />">
		<fieldset>
			<div class="pure-control-group">
				<label for="idDiscipline" class="required">Discipline</label>
				<select name="idDiscipline" id="idDiscipline">
				<c:forEach var="discipline" items="${requestScope['LISTE_DISCIPLINE']}">	
					<option value="${discipline.getIdDiscipline()}" <c:if test="${cours != null && cours.getDiscipline().getIdDiscipline()==discipline.getIdDiscipline()}">selected</c:if> >${discipline.getNomDiscipline()}</option>
				</c:forEach>
				</select>
			</div>
			
			<div class="pure-control-group">
				<label for="idProf" class="required">Professeur associé</label>
				<select name="idProf" id="idProf">
				<c:forEach var="prof" items="${requestScope['LISTE_PROF']}">	
					<option value="${prof.getIdPersonne()}" <c:if test="${cours != null && cours.getProf().getIdPersonne()==prof.getIdPersonne()}">selected</c:if>>${prof.getIdentiteProf()}</option>
				</c:forEach>
				</select>
			</div>
			
			<input type="hidden" name="idCours" value="${cours.getIdCours()}"/>
		</fieldset>
		
		<fieldset class='align-center no-border'>
			<div>
				<p class="oblig">* Champs obligatoires</p>
				<input class="btn" type="submit" name="valider" value="Valider" />
				<a class="btn" href="<c:url value="${Pattern.COURS_LISTER }"/>">Retour</a>
			</div>
		</fieldset>
	</form>

<c:import url="/inc/footer.inc.jsp" />