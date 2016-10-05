<%@page import="fr.gemao.sql.DAOFactory"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>
<%@ page import="fr.gemao.entity.planning.Creneau"%>
<%@ page import="fr.gemao.entity.planning.Planning"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>


<c:set var="titre" value="Afficher un planning" scope="request" />


<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />

<script src="<c:url value="/js/planning/classes.js"/>"></script>
<script src="<c:url value="/js/planning/fonctions.js"/>"></script>
<script src="<c:url value="/js/planning/modification.js"/>"></script>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% Planning planning = (Planning) request.getAttribute("planning"); %>

	<h1><%= planning.definitionPlanning() %></h1>

       <div id="edt">
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
       <c:if test="${!planning.getValide()}">
       		<a href="<c:url value="${Pattern.PLANNING_AFFICHER }?idPlanning=${planning.getIdPlanning()}&valider=1"/>"><input type="button" id="boutonExporter" class="btn" name="valider" value="Valider le planning"></a>
       </c:if>
       <form action="#" method="post" target="_blank">
       		<input type="submit" value="Exporter en PDF" class="pure-button pure-button-primary btn" id="boutonValider">
       </form>
       
       
<c:import url="/js/planning/placerCreneaux.jsp" />
<script src="<c:url value="/js/planning/init.js"/>"></script>
<c:import url="/inc/footer.inc.jsp" />
