<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>
<%@ page import="fr.gemao.entity.planning.Creneau"%>

<c:set var="titre" value="Créer un créneau" scope="request" />

<c:import url="/inc/head.inc.jsp" />
<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />

<script src="<c:url value="/js/planning/gestionContraintes.js"/>"></script>

	<h1>Créer un créneau</h1>
	
	<form class="pure-form pure-form-aligned" id="creerCreneau" method="post" action="<c:url value="<%= Pattern.CRENEAU_CREER %>" />">
		<fieldset>
			<legend>Créneau</legend>
			<div class="pure-control-group">
				<label for="libelle" class="required">Libellé</label>
				<input id="libelle" name="libelle" type="text" placeholder="Creneau" required="required" />
			</div>
			
			<div class="pure-control-group">
				<label for="heureDeb">Heure de début</label>
				<input id="heureDeb" name="heureDeb" type="number" max="23" min="8" step="1" />
				<label for="minuteDeb"> : </label>
				<input id="minuteDeb" name="minuteDeb" type="number" max="45" min="0" step="15" />
			</div>
			
			<div class="pure-control-group">
				<label for="heureDuree" class="required">Durée</label>
				<input id="heureDuree" name="heureDuree" type="number" max="23" min="0" step="1" required="required" />
				<label for="minuteDuree"> : </label>
				<input id="minuteDuree" name="minuteDuree" type="number" max="45" min="0" step="15" required="required" />
			</div>
				
			<div class="pure-control-group">
				<label for="idCours" class="required">Cours</label>
				<select name="idCours" id="idCours">
				<option value="-1">Aucun cours</option>
				<c:forEach var="cours" items="${requestScope['LISTE_COURS']}">	
					<option value="${cours.getIdCours()}">${cours.getIntituleCours()}</option>
				</c:forEach>
				</select>
			</div>			
<!-- 			<div class="pure-control-group"> -->
<!-- 				<label for="idDiscipline" class="required">Discipline associée</label> -->
<!-- 				<select name="idDiscipline" id="idDiscipline"> -->
<!-- 				<option value="-1">Aucune</option> -->
<%-- 				<c:forEach var="discipline" items="${requestScope['LISTE_DISCIPLINE']}">	 --%>
<%-- 					<option value="${discipline.getIdDiscipline()}">${discipline.getNomDiscipline()}</option> --%>
<%-- 				</c:forEach> --%>
<!-- 				</select> -->
<!-- 			</div> -->
			
<!-- 			<div class="pure-control-group" id="displayOnlyIfDisciplineSet"> -->
<!-- 				<label for="idProf" class="required">Professeur associé</label> -->
<!-- 				<select name="idProf" id="idProf"> -->
<!-- 				<option value="-1">Aucune</option> -->
<%-- 				<c:forEach var="prof" items="${requestScope['LISTE_PROF']}">	 --%>
<%-- 					<option value="${prof.getIdPersonne()}">${prof.getIdentiteProf()}</option> --%>
<%-- 				</c:forEach> --%>
				
<!-- 				</select> -->
<!-- 			</div> -->
			
			<div class="pure-control-group">
				<label for="salle">Salle</label>
				<select name="salle" id="salle">
				<c:forEach var="salle" items="${requestScope['LISTE_SALLE']}">	
					<option value="${salle.getIdSalle()}" <c:if test="${INFOS.getSalle().getIdSalle()==salle.getIdSalle()}">selected</c:if>>${salle.getNomSalle()}</option>
				</c:forEach>
					<option value="-1">Aucune</option>
				</select>
			</div>
			
			<div class="pure-control-group">
				<label for="jour">Jour</label>
				<select name="jour" id="jour">
				<c:forEach var="jour" items="${requestScope['LISTE_JOUR']}">	
					<option value="${jour.getIdJour()}" <c:if test="${INFOS.getIdJour()==jour.getIdJour()}">selected</c:if>>${jour.getNomJour()}</option>
				</c:forEach>
				</select>
			</div>
			
			<div class="pure-control-group">
				<label for="couleur">Couleur</label>
				<input id="couleur" value="#00E28D" name="couleur" type="color" />
			</div>
		</fieldset>
		
		<fieldset>
			<legend>Restrictions</legend>
			<div class="">
				<label for="jourContrainte">Jour</label>
				<select name="jourContrainte" id="jourContrainte">
				<c:forEach var="jour" items="${requestScope['LISTE_JOUR']}">	
					<option value="${jour.getIdJour()}" <c:if test="${INFOS.getIdJour()==jour.getIdJour()}">selected</c:if>>${jour.getNomJour()}</option>
				</c:forEach>
				</select>
			</div>
			
			<div class="pure-control-group">
				<label for="salleContrainte">Salle</label>
				<select name="salleContrainte" id="salleContrainte">
				<c:forEach var="salle" items="${requestScope['LISTE_SALLE']}">	
					<option value="${salle.getIdSalle()}" <c:if test="${INFOS.getSalle().getIdSalle()==salle.getIdSalle()}">selected</c:if>>${salle.getNomSalle()}</option>
				</c:forEach>
					<option value="-1">Aucune</option>
				</select>
			</div>
			
			<div class="pure-control-group">
				<label for="heureDebContrainte">Heure de début</label>
				<input id="heureDebContrainte" name="heureDebContrainte" type="number" max="23" min="8" step="1" />
				<label for="minuteDebContrainte"> : </label>
				<input id="minuteDebContrainte" name="minuteDebContrainte" type="number" max="45" min="0" step="15" />
			</div>
			
			<div class="pure-control-group">
				<label for="heureFinContrainte">Heure de fin</label>
				<input id="heureFinContrainte" name="heureFinContrainte" type="number" max="23" min="8" step="1" />
				<label for="minuteFinContrainte"> : </label>
				<input id="minuteFinContrainte" name="minuteFinContrainte" type="number" max="45" min="0" step="15" />
			</div>
			
			<div class="pure-control-group">
				<label for="messageContrainte">Message</label>
				<input id="messageContrainte" name="messageContrainte" type="text" />
			</div>
			
			
			
			<div>
				<button type="button" class="btn" id="ajouterContrainte"><i class="fa fa-plus"></i></button>
			</div>
		</fieldset>
		
		<fieldset class='align-center no-border'>
				<p class="oblig">* Champs obligatoires</p>
            <div id="restrictionsPhrases">

            </div>
			<div>
				<input class="submitJSON btn" type="submit" name="valider" value="Valider" />
			</div>
		</fieldset>
		<textarea id="jsonContraintes" name="jsonContraintes" style="display: none"></textarea>
	</form>
	
	

<c:import url="/inc/footer.inc.jsp" />