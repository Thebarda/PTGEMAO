<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>

<c:set var="titre" value="Ajout d'un matériel" scope="request" />

<c:import url="/inc/head.inc.jsp" />
<c:import url="/inc/header.inc.jsp" />
<c:import url="/inc/menu.inc.jsp" />

<h1>Ajout d'un matériel</h1>
<form id="ajouterMateriel" action="#" method="post">
	<fieldset>
		<legend>Informations générales</legend>
		<div>
			<label for="categorie">Catégorie</label>
			 <select name="categorie" id="categorie">
				<c:forEach var="cat" items="${requestScope['LISTE_CATEGORIE']}">	
					<option value="${cat.getIdCategorie()}" <c:if test="${INFOS.getCategorie().getIdCategorie()==cat.getIdCategorie()}">selected</c:if>>${cat.getLibelleCat()}</option>
				</c:forEach>
			</select>
	
			<input type="button" name="ajoutCat" id="ajoutCat" value="Créer...">
			<input type="text" name="nomCat" id="nomCat" class="hidden"/>
		</div>
		<c:if test="${!empty requestScope.form.erreurs['erreurCat'] }">
			<div class='align-center'><c:out value="${requestScope.form.erreurs['erreurCat']}"/></div>
		</c:if>
		
		<div>
			<label for="ValeurAch" class='required'>Valeur d'achat</label> 
			<input type="text" name="ValeurAch" required autocomplete="off" value="${sessionScope.INFOS['valeurAchat']}"/> 
			<span class="euro"></span>
		</div>
		
		<div>
			<label for="dateAch">Date d'acquisition</label> 
			<input type="text" name="dateAch" class="datepicker" value="${date}"/> 
		</div>
		
		<div>
			<label for="valRea" class='required'>Valeur de réapprovisionnement</label>
			<input type="text" name="valRea" required autocomplete="off" value="${sessionScope.INFOS['valeurReap']}"/> 
			<span class="euro"></span>
		</div>
		
		<div>
			<label for="fournisseur">Fournisseur</label> 
			<select name="fournisseur" id="fournisseur">
				<c:forEach var="fourn" items="${requestScope['LISTE_FOURNISSEUR']}">
					<option value="${fourn.getIdFournisseur()}" <c:if test="${INFOS.getFournisseur().getIdFournisseur()==fourn.getIdFournisseur()}">selected</c:if>>${fourn.getNomFournisseur()}</option>
				</c:forEach>
			</select>
			<input type="button" name="ajoutFour" id="ajoutFour" value="Créer..." />
			<input type="text" name="nomFour" id="nomFour" class="hidden"/>
		</div>
	</fieldset>

	<fieldset>
		<legend>Informations détaillées</legend>
		<div>
			<label for="designation">Désignation</label>
				
			<select name="designation" id="designation">
				<c:forEach var="des" items="${requestScope['LISTE_DESIGNATION']}">
					<option value="${des.getIdDesignation()}" <c:if test="${INFOS.getDesignation().getIdDesignation()==des.getIdDesignation()}">selected</c:if>>${des.getLibelleDesignation()}</option>
				</c:forEach>
			</select>
			<input type="button" name="ajoutDes" id="ajoutDes" value="Créer..." />
			<input type="text" name="nomDes" id="nomDes" class="hidden"/>
		</div>
		
		<div>
			<label for="type" class='required'>Type</label>
			<input type="text" name="type" required autocomplete="off" value="${sessionScope.INFOS['typeMat']}"/>
		</div>
		
		<div>
			<label for="etat">Etat</label>
			<select name="etat" id="etat">
				<c:forEach var="etat" items="${requestScope['LISTE_ETAT']}">
					<option value="${etat.getIdEtat()}" <c:if test="${INFOS.getEtat().getIdEtat()==etat.getIdEtat()}">selected</c:if>>${etat.getLibelleEtat()}</option>
				</c:forEach>
			</select>
			<input type="button" name="ajoutEtat" id="ajoutEtat" value="Créer..." />
			<input type="text" name="nomEtat" id="nomEtat" class="hidden"/>
		</div>
		
		<div>
			<label for="marque">Marque</label>
			<select name="marque" id="marque">
				<c:forEach var="marque" items="${requestScope['LISTE_MARQUE']}">
					<option value="${marque.getIdMarque()}" <c:if test="${INFOS.getMarque().getIdMarque()==marque.getIdMarque()}">selected</c:if>>${marque.getNomMarque()}</option>
				</c:forEach>
			</select>
			<input type="button" name="ajoutMarque" id="ajoutMarque" value="Créer..." />
			<input type="text" name="nomMarque" id="nomMarque" class="hidden"/>
		</div>
		
		<div>
			<label for="numSerie">Numéro de série</label>
			<input type="text" autocomplete="off" name="numSerie" value="${sessionScope.INFOS['numSerie']}"/>
		</div>
		
		<div>
			<label for="deplacable">Déplaçable</label>
			<input type="checkbox" name="deplacable" value="on" />
		</div>
	
		<div>
			<label>Ouvert à la location</label>
			<input type="checkbox" name="louable" value="on" />
		</div>
	</fieldset>
	
	<fieldset>
		<legend>Observations</legend>
		<div class='align-center'>
			<textarea name="observation" rows="5" cols="50" placeholder="Ajoutez ici toute information que vous jugez nécessaire de mentionner." >${sessionScope.INFOS['observation']}</textarea>
		</div>
	</fieldset>
	
	<fieldset class='align-center no-border'>
		<div>
			<p class="oblig">* Champs obligatoires</p>
			<input type="submit" name="valider" value="Valider" />
		</div>
	</fieldset>
</form>

<script src="<c:url value="/js/ajouterMateriel.js"/>" ></script>
<c:import url="/inc/footer.inc.jsp" />