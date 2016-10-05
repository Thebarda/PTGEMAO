<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>
<%@ page import="fr.gemao.entity.planning.Creneau"%>

<c:set var="titre" value="Modifier un créneau" scope="request" />

<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />

<script src="<c:url value="/js/planning/gestionContraintes.js"/>"></script>

	<h1>Modifier une restriction</h1>
	
	<form class="pure-form pure-form-aligned" id="modifierContrainte" method="post" action="<c:url value="#" />">
		<fieldset>
			<legend>Restrictions</legend>
			<div class="pure-control-group">
				<label for="jourContrainte">Jour</label>
				<select name="jourContrainte" id="jourContrainte">
				<c:forEach var="jour" items="${requestScope['LISTE_JOUR']}">	
					<option value="${jour.getIdJour()}" <c:if test="${contrainte.getJour().getIdJour()==jour.getIdJour()}">selected</c:if>>${jour.getNomJour()}</option>
				</c:forEach>
				</select>
			</div>
			
			<div class="pure-control-group">
				<label for="salleContrainte">Salle</label>
				<select name="salleContrainte" id="salleContrainte">
				<c:forEach var="salle" items="${requestScope['LISTE_SALLE']}">	
					<option value="${salle.getIdSalle()}" <c:if test="${contrainte.getSalle().getIdSalle()==salle.getIdSalle()}">selected</c:if>>${salle.getNomSalle()}</option>
				</c:forEach>
					<option value="-1">Aucune</option>
				</select>
			</div>
			
			<div class="pure-control-group">
				<label for="heureDebContrainte">Heure de début</label>
				<input id="heureDebContrainte" name="heureDebContrainte" type="number" value="${contrainte.getHeureDebut().getHeure()}" max="23" min="0" step="1" />
				<label for="minuteDebContrainte"> : </label>
				<input id="minuteDebContrainte" name="minuteDebContrainte" type="number" value="${contrainte.getHeureDebut().getMinute()}" max="45" min="0" step="15" />
			</div>
			
			<div class="pure-control-group">
				<label for="heureFinContrainte">Heure de fin</label>
				<input id="heureFinContrainte" name="heureFinContrainte" type="number" value="${contrainte.getHeureFin().getHeure()}" max="23" min="0" step="1" />
				<label for="minuteFinContrainte"> : </label>
				<input id="minuteFinContrainte" name="minuteFinContrainte" type="number" value="${contrainte.getHeureFin().getMinute()}" max="45" min="0" step="15" />
			</div>
			
			<div class="pure-control-group">
				<label for="messageContrainte">Message</label>
				<input id="messageContrainte" name="messageContrainte" value="${contrainte.getMessage()}" type="text" />
			</div>
		</fieldset>
		
		<fieldset class='align-center no-border'>
			<div>
				<p class="oblig">* Champs obligatoires</p>
				<input class="btn" type="submit" name="valider" value="Valider" />
				<a class="btn" href="<c:url value="${Pattern.CRENEAUX_LISTER }?idPlanning=${id}"/>">Retour</a>
			</div>
		</fieldset>
	</form>

<c:import url="/inc/footer.inc.jsp" />