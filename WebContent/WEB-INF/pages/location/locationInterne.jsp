<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>

<c:set var="titre" value="Location interne d'un instrument" scope="request" />

<c:import url="/inc/head.inc.jsp" />

<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />

	<h1>Location interne d'un instrument</h1>

	<c:choose >
		<c:when test="${empty resultat}">
			<form id="location" method="post" action="#">
				<fieldset>
					<legend>Instrument</legend>
					<c:choose>
						<c:when test="${!empty requestScope.listeCategorie}">
							<div>
								<label for="categorie">Catégorie :</label>
								<select name="categorie">
									<c:forEach items="${requestScope.listeCategorie}" var="categorie">
										<option value="${categorie['idCategorie']}"><c:out
												value="${categorie['libelleCat']}" /></option>
									</c:forEach>
								</select>
							</div>
						</c:when>
						<c:otherwise>
							
								<div><span class='text-label'>Catégorie : </span><span><%= session.getAttribute("nomCategorie") %></span></div>
								</fieldset>
								<fieldset>
								<label for="instrument">Instrument : </label>
									<select name="nomDesignation">
										<c:forEach items="${listeMateriel}" var="instrument">
											<option value="${instrument['idMateriel']}"><c:out
													value="${instrument['designation']['libelleDesignation']}" /></option>
										</c:forEach>
									</select><br><br>
									<label for="adh">Adhérent : </label>
									<select name="adherent">
										<c:forEach items="${listeAdherent}" var="adh">
											<option value="${adh['idPersonne']}"><c:out
													value="${adh['nom']}" /></option>
										</c:forEach>
									</select><br><br>
									<label>Date de la location : </label><input type="text" class="datepicker" required='required' name="debutLocation">
									<br><br><label>Caution : </label><input type="text" required='required' name="caution">
									<br><br><label>Montant : </label><input type="text" required='required' name="montant">
									<br><br><label>Date encaissement : </label>
							</fieldset>
						</c:otherwise>	
						</c:choose>
				<fieldset class='align-center no-border'>
					<input type="submit" value="Valider" />
				</fieldset>
			</form>
		</c:when>
		<c:otherwise>
			<p></p>
		</c:otherwise>
	</c:choose>
	

<c:import url="/inc/footer.inc.jsp" />