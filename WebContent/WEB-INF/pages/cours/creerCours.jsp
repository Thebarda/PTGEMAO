<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>
<%@ page import="fr.gemao.entity.planning.Creneau"%>

<c:set var="titre" value="Créer un cours" scope="request" />

<c:import url="/inc/head.inc.jsp" />
<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />

<script src="<c:url value="/js/planning/gestionContraintes.js"/>"></script>

	<h1>Créer un cours</h1>
	
	<form class="pure-form pure-form-aligned" id="creerCours" method="post" action="<c:url value="#" />">
		<fieldset>
			
			<div class="pure-control-group">
				<label for="idDiscipline" class="required">Discipline*</label>
				<select name="idDiscipline" id="idDiscipline">
				<c:forEach var="discipline" items="${requestScope['LISTE_DISCIPLINE']}">	
					<option value="${discipline.getIdDiscipline()}">${discipline.getNomDiscipline()}</option>
				</c:forEach>
				</select>
			</div>
			
			<div class="pure-control-group" id="displayOnlyIfDisciplineSet">
				<label for="idProf" class="required">Professeur associé*</label>
				<select name="idProf" id="idProf">
				<c:forEach var="prof" items="${requestScope['LISTE_PROF']}">	
					<option value="${prof.getIdPersonne()}">${prof.getIdentiteProf()}</option>
				</c:forEach>
				</select>
			</div>
		</fieldset>
		
		<fieldset class='align-center no-border'>
			<div>
				<p class="oblig">* Champs obligatoires</p>
				<input type="submit" name="valider" value="Valider" />
			</div>
		</fieldset>
	</form>
	
<c:import url="/inc/footer.inc.jsp" />