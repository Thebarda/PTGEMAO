<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>

<c:set var="titre" value="Lister les locations en cours" scope="request" />

<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />

<h1>Lister les location en cours</h1>
<table>
<tr>
<th>Nom</th><th>Instrument</th><th>Type Location</th><th>Date d'emprunt</th><th>Date d'échéance</th><th>Date de retour</th><th>Caution</th><th>Montant</th><th>Etat début</th>
</tr>
</table>

<c:import url="/inc/footer.inc.jsp" />