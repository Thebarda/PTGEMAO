<%@page import="fr.gemao.sql.DAOFactory"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>
<%@ page import="fr.gemao.entity.planning.Creneau"%>
<%@ page import="fr.gemao.entity.planning.Planning"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="fr.gemao.sql.util.DateUtil"%>

<c:set var="titre" value="Creer un planning" scope="request" />


<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />


<form class="pure-form pure-form-aligned" method="post">

	<%
		Planning p = (Planning) request.getSession().getAttribute("planning");
	%>
	<fieldset>

		<div class="pure-control-group">
			<label>Nom du planning</label> 
			<input type="text" id="nomPlanning"	name="nomPlanning" value="${sessionScope.planning['nomPlanning']}" />
		</div>
	
		<div class="pure-control-group">
			<label>Date début </label> <input type="date" value="<%= DateUtil.toFrenchDate(p.getDateDeb()) %>" placeholder="dd/mm/yyyy" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])/(0[1-9]|1[012])/[0-9]{4}"  id="dateD" name="dateD" />
		</div>
	
		<div class="pure-control-group">
			<label>Date fin</label> <input type="date" id="dateF" value="<%= DateUtil.toFrenchDate(p.getDateFin()) %>" name="dateF" placeholder="dd/mm/yyyy" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])/(0[1-9]|1[012])/[0-9]{4}"/>
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