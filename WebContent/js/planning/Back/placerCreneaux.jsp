<%@ page import="fr.gemao.view.Pattern"%>
<%@ page import="fr.gemao.entity.planning.Creneau"%>
<%@ page import="fr.gemao.entity.planning.Planning"%>
<%@ page import="fr.gemao.entity.planning.Contrainte"%>
<%@ page import="fr.gemao.entity.cours.Salle"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%
	Planning planning = (Planning)(request.getAttribute("planning"));
	List<Creneau> creneaux = planning.getListeCreneau();
%>
<script>
	salles = new Array();
	creneauxJS = new Array();
  	enModif = false;
	<%List<Salle> salles = (List<Salle>) request.getAttribute("salles");
	for (final Salle s : salles) {
		%>
		salles.push('<%= s.getNomSalle() %>');
	<% }
	%>
	<% Boolean enModif = (Boolean) request.getAttribute("enModif");
	if (enModif != null) { %>
		enModif = <%= enModif%>;

	<% }
	else { %>
		enModif = false;
 	<% }
	%>
	var manager = new window.CreneauManager();
	
	<%
	for ( final Creneau c : creneaux) {
		%>
		var idCreneau = <%= c.getIdCreneau() %>
		var heureDebString = null;
		var jourString = null;
		var salleString = null;
		var contraintes = new Array();
		<% if (c.getHeureDeb() != null) {
		%>
		heureDebString = getChaineHeure(<%=c.getHeureDeb().getHeure()%>, <%=c.getHeureDeb().getMinute()%>);
		<% }%>
		<% if (c.getJour() != null) {
			%>
			
			jourString = '<%= c.getJour().getNomJour() %>';

		<% }%>
		<% if (c.getSalle() != null) { %>
			salleString = '<%= c.getSalle().getNomSalle() %>';
		<% }%>
		if (enModif) {
			
		<%
		List<Contrainte> contraintes = c.getContraintes();
		for (Contrainte cont : contraintes) {
			%>
			jourStringCont = null;
			<%if (cont.getJour() != null) { %>
				jourStringCont = '<%= cont.getJour().getNomJour() %>';
			<% } %>
			heureDebStringCont = null;
			<%if (cont.getHeureDebut() != null) { %>
				heureDebStringCont = getChaineHeure(<%=cont.getHeureDebut().getHeure()%>, <%=cont.getHeureDebut().getMinute()%>);
			<% } %>
			heureFinStringCont = null;
			<%if (cont.getHeureFin() != null) { %>
				heureFinStringCont = getChaineHeure(<%=cont.getHeureFin().getHeure()%>, <%=cont.getHeureFin().getMinute()%>);
			<% } %>
			salleStringCont = null;
			<%if (cont.getSalle() != null) { %>
				salleStringCont = '<%= cont.getSalle().getNomSalle() %>';
			<% } %>
            message = null;
			<%if (cont.getMessage() != null) { %>
				message = encodeURI('<%= cont.getMessage() %>');
			<% } %>
			contraintes.push(new window.Contrainte(idCreneau,jourStringCont,heureDebStringCont,heureFinStringCont,salleStringCont, message));
			
		<% } %>
		}
		var cours = null;
		<%if (c.getCours() != null) {%>
			cours = new window.Cours('<%=c.getCours().getDiscipline().getMatiere().getNomMatiere() %>', '<%= c.getCours().getProf().getIdentiteProf() %>');
		<%} %>
		creneauxJS.push(new window.Creneau(<%= c.getIdCreneau() %>,
				'<%= c.getLibelle() %>',
				<%= c.getNbQuartDHeure() %>,
				heureDebString,
				jourString,
				salleString,
				'<%= c.getCouleur()%>',cours,false,false,contraintes));
<% 	}
	%>
	
</script>