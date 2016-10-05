<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>
<%@ page import="fr.gemao.entity.planning.Creneau"%>

<c:set var="titre" value="Créer des contraintes" scope="request" />

<c:import url="/inc/head.inc.jsp" />
<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />

<script src="<c:url value="/js/planning/gestionContraintes.js"/>"></script>

	<h1>Ajouter des contraintes globales à un planning.</h1>
	
	<form class="pure-form pure-form-aligned" id="creerContrainteGlobale" method="post" action="#">

		<fieldset>
			<legend>Restrictions</legend>
			
			<div class="">
				<label for="idPlanning">Planning sur lequel appliquer la contrainte</label>
				<select name="idPlanning" id="idPlanning">
				<c:forEach var="planning" items="${requestScope['LISTE_PLANNINGS']}">	
					<option value="${planning.getIdPlanning()}" <c:if test="${NUM_PLANNING != null && planning.getIdPlanning() == NUM_PLANNING}">selected</c:if> >${planning.getNomPlanning()}</option>
				</c:forEach>
				</select>
			</div>
			
			<div class="">
				<label for="jourContrainte">Jour</label>
				<select name="jourContrainte" id="jourContrainte">
				<c:forEach var="jour" items="${requestScope['LISTE_JOUR']}">	
					<option value="${jour.getIdJour()}">${jour.getNomJour()}</option>
				</c:forEach>
				</select>
			</div>
			
			<div class="pure-control-group">
				<label for="salleContrainte">Salle</label>
				<select name="salleContrainte" id="salleContrainte">
				<c:forEach var="salle" items="${requestScope['LISTE_SALLE']}">	
					<option value="${salle.getIdSalle()}">${salle.getNomSalle()}</option>
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