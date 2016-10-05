<%@page import="fr.gemao.sql.DAOFactory"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>
<%@ page import="fr.gemao.entity.planning.Creneau"%>
<%@ page import="fr.gemao.entity.planning.Planning"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>

<c:set var="titre" value="Modifier un planning" scope="request" />


<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />

<script src="<c:url value="/js/planning/classes.js"/>"></script>
<script src="<c:url value="/js/planning/modification.js"/>"></script>
<script src="<c:url value="/js/planning/fonctions.js"/>"></script>
<script src="<c:url value="/js/planning/init.js"/>"></script>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<% 
	Planning planning = (Planning)(request.getAttribute("planning"));
%>
<c:import url="/js/planning/placerCreneaux.jsp" />
<script src="<c:url value="/js/planning/envoyerDonnees.js"/>"></script>
<script src="<c:url value="/js/planning/formCreationRapide.js"/>"></script>

	<h1><%= planning.definitionPlanning() %></h1>
      <div id="champCreneau">
          <div class="pure-form pure-form-aligned" id="ajoutCreneau">
          <h3>Création de créneau</h3>
              <div class="pure-control-group">
                  <label for="libelle" class="required">Libellé</label>
                  <input id="libelle" name="libelle" type="text" placeholder="Creneau" required="required" />
              </div>

              <div class="pure-control-group">
                  <label for="heureDuree" class="required">Durée</label>
                  <input class="smallInput" id="heureDuree" name="heureDuree" type="number" max="23" min="0" step="1" required="required" />
                  <label for="minuteDuree"> : </label>
                  <input class="smallInput" id="minuteDuree" name="minuteDuree" type="number" max="45" min="0" step="15" required="required" />
              </div>

              <div class="pure-control-group">
                  <label for="couleur">Couleur</label>
                  <input id="couleur" name="couleur" value="#d66767" type="color" />
              </div>
              <div>
                  <button type="submit" class="btn" id="formCreerButton">Créer</button>
                  <button class="btn" id="viderChampsCreneau">Vider</button>
              </div>
        </div>
        <div id="creneauxPlacement">
            <h3>Créneaux à placer <a id="addCreneau"><i class="fa fa-plus-square"></i>
</a></h3>
            <ul id="creneauxAPlacer">
            </ul>
        </div>
      </div>
       <div id="edt" class="edt-modifier">
           <ul id="jours">
               <li><a href="#Lundi">Lundi</a></li>
               <li><a href="#Mardi">Mardi</a></li>
               <li><a href="#Mercredi">Mercredi</a></li>
               <li><a href="#Jeudi">Jeudi</a></li>
               <li><a href="#Vendredi">Vendredi</a></li>
               <li><a href="#Samedi">Samedi</a></li>
               <li><a href="#Dimanche">Dimanche</a></li>
           </ul>
           
           <div id="Lundi">
           </div>
           
           <div id="Mardi">
           </div>
           
           <div id="Mercredi">
           </div>
           
           <div id="Jeudi">
           </div>
           
           <div id="Vendredi">
           </div>
           
           <div id="Samedi">
           </div>
           
           <div id="Dimanche">
           </div>
       </div>
       <input type="submit" value="Enregistrer" class="btn" id="boutonValider">
       
<c:import url="/inc/footer.inc.jsp" />
