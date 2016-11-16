<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.gemao.view.Pattern"%>

<div class="container">
	<!-- Push Wrapper -->
	<div class="mp-pusher" id="mp-pusher">

		<!-- mp-menu -->
		<nav id="mp-menu" class="mp-menu">
			<div class="mp-level">
				<h2>GEMAO</h2>
				<ul>
					<li><a href="<c:url value="<%= Pattern.ACCUEIL %>"/>">Accueil</a></li>
					<c:if test="${sessionScope.sessionObjectPersonnel.profil.recupererTypeDroit('Adhérent') != 'Aucun' }">
						<li class="icon icon-arrow-left"><a href="#">Adhérent</a>
							<div class="mp-level">
								<h2>Adhérent</h2>
								<a class="mp-back" href="#">Retour</a>
								<ul>
									<c:if test="${sessionScope.sessionObjectPersonnel.profil.recupererTypeDroit('Adhérent') == 'Lecture/écriture' }">
									<li><a href="<c:url value="<%= Pattern.ADHERENT_AJOUT %>"/>">Créer</a></li>
									</c:if>
									<li><a href="<c:url value="<%= Pattern.ADHERENT_LISTER %>"/>">Lister</a></li>
									<li><a href="<c:url value="<%= Pattern.ADHERENT_SIMPLE_CALCUL_QF %>"/>">Calculer Quotient Familial</a></li>
									<c:if test="${sessionScope.sessionObjectPersonnel.profil.recupererTypeDroit('Adhérent') == 'Lecture/écriture' }">
									<li><a href="<c:url value="<%= Pattern.ADHERENT_PARAMETRE %>"/>">Paramètres</a></li>
									
									</c:if>
								</ul>
							</div>
						</li>
					</c:if>
					<c:if test="${sessionScope.sessionObjectPersonnel.profil.recupererTypeDroit('Personnel') != 'Aucun' }">
					<li class="icon icon-arrow-left"><a href="#">Personnel</a>
						<div class="mp-level">
							<h2>Personnel</h2>
							<a class="mp-back" href="#">Retour</a>
							<ul>
								<c:if test="${sessionScope.sessionObjectPersonnel.profil.recupererTypeDroit('Personnel') == 'Lecture/écriture' }">
								<li><a href="<c:url value="<%= Pattern.PERSONNEL_AJOUT %>"/>">Créer</a></li>
								</c:if>
								<li><a href="<c:url value="<%= Pattern.PERSONNEL_LISTER %>"/>">Lister</a></li>
							</ul>
						</div></li>
					</c:if>
					<c:if test="${sessionScope.sessionObjectPersonnel.profil.recupererTypeDroit('Matériel') != 'Aucun' }">
					<li class="icon icon-arrow-left"><a href="#">Matériel</a>
						<div class="mp-level">
							<h2>Matériel</h2>
							<a class="mp-back" href="#">Retour</a>
							<ul>
								<c:if test="${sessionScope.sessionObjectPersonnel.profil.recupererTypeDroit('Matériel') == 'Lecture/écriture' }">
								<li><a href="<c:url value="<%= Pattern.MATERIEL_AJOUT %>"/>">Créer</a></li>
								</c:if>
								<li><a href="<c:url value="<%= Pattern.MATERIEL_LISTER %>"/>">Lister</a></li>
								<c:if test="${sessionScope.sessionObjectPersonnel.profil.recupererTypeDroit('Matériel') == 'Lecture/écriture' }">
								<li class="icon icon-arrow-left"><a href="#">Autres</a>
									<div class="mp-level">
										<h2>Autres</h2>
										<a class="mp-back" href="#">Retour</a>
										<ul>
											<li><a href="<c:url value="<%= Pattern.MATERIEL_LISTE_CATEGORIE %>"/>">Catégories</a></li>
											<li><a href="<c:url value="<%= Pattern.MATERIEL_LISTE_ETAT %>"/>">Etats</a></li>
											<li><a href="<c:url value="<%= Pattern.MATERIEL_LISTE_FOURNISSEUR %>"/>">Fournisseurs</a></li>
											<li><a href="<c:url value="<%= Pattern.MATERIEL_LISTE_MARQUE %>"/>">Marques</a></li>
											<li><a href="<c:url value="<%= Pattern.MATERIEL_LISTE_DESIGNATION %>"/>">Désignations</a></li>
										</ul>
									</div>
								</li>
								</c:if>
							</ul>
						</div></li>
					</c:if>
					<c:if test="${sessionScope.sessionObjectPersonnel.profil.recupererTypeDroit('Location') != 'Aucun' }">
					<li class="icon icon-arrow-left"><a href="#">Location</a>
						<div class="mp-level">
							<h2>Location</h2>
							<a class="mp-back" href="#">Retour</a>
							<ul>
								<c:if test="${sessionScope.sessionObjectPersonnel.profil.recupererTypeDroit('Location') == 'Lecture/écriture' }">
								<li><a href="<c:url value="<%= Pattern.LOCATION_LOCATION_INTERNE %>"/>">Location interne</a></li>
								<li><a href="<c:url value="<%= Pattern.LOCATION_LOCATION_EXTERNE %>"/>">Location externe</a></li>
								<li><a href="<c:url value="<%= Pattern.LOCATION_RETOUR %>"/>">Enregistrer un Retour</a></li>
								</c:if>
								<li><a href="<c:url value="<%= Pattern.LOCATION_LISTER %>"/>">Lister</a></li>
							</ul>
						</div>
					</li>
					</c:if>
					<c:if test="${sessionScope.sessionObjectPersonnel.profil.recupererTypeDroit('Cours') != 'Aucun' }">
					<li class="icon icon-arrow-left"><a href="#">Cours</a>
						<div class="mp-level">
							<h2>Cours</h2>
							<a class="mp-back" href="#">Retour</a>
							<ul>
								<c:if test="${sessionScope.sessionObjectPersonnel.profil.recupererTypeDroit('Cours') == 'Lecture/écriture' }">
								<li><a href="<c:url value="<%= Pattern.COURS_AJOUTEDT %>"/>">Ajout</a></li>
								</c:if>
							</ul>
						</div>
					</li>
					</c:if>
					 <li class="icon icon-arrow-left"><a href="#">Emploi du temps</a>
						<div class="mp-level">
							<h2>Emploi du temps</h2>
							<a class="mp-back" href="#">Retour</a>
							<ul>
								<li class="icon icon-arrow-left"><a href="#">Plannings</a>
									<div class="mp-level">
										<h2>Plannings</h2>
										<a class="mp-back" href="#">Retour</a>
										<ul>
											<li><a href="<c:url value="<%= Pattern.PLANNING_LISTER %>"/>">Lister</a></li>
											<li><a href="<c:url value="<%= Pattern.PLANNING_LISTER_ARCHIVE %>"/>">Archives</a></li>
											<li><a href="<c:url value="<%= Pattern.PLANNING_CREER %>"/>">Créer</a></li>
											<li><a href="<c:url value="<%= Pattern.CONTRAINTE_ADD_GLOBALE %>"/>">Contraintes Globales</a></li>
											
										</ul>
									</div>
								</li>
								<li class="icon icon-arrow-left"><a href="<c:url value="<%= Pattern.COURS_LISTER %>"/>">Cours</a></li>
								<li class="icon icon-arrow-left"><a href="<c:url value="<%= Pattern.ADHERENT_LISTE_DISCIPLINES %>"/>">Disciplines</a></li>
								<li class="icon icon-arrow-left"><a href="<c:url value="<%= Pattern.SALLE_LISTER %>"/>">Salles</a>
								</li>
							</ul>
						</div></li>
					<c:if test="${sessionScope.sessionObjectPersonnel.profil.recupererTypeDroit('Administration') != 'Aucun' }">
					<li class="icon icon-arrow-left"><a href="#">Administration</a>
						<div class="mp-level">
							<h2>Administration</h2>
							<a class="mp-back" href="#">Retour</a>
							<ul>
								<c:if test="${sessionScope.sessionObjectPersonnel.profil.recupererTypeDroit('Administration') == 'Lecture/écriture' }">
								<li><a href="<c:url value="<%= Pattern.ADMINISTRATION_RESET_PASSWORD %>"/>">Réinit. mot de passe</a></li>
								</c:if>
								<li class="icon icon-arrow-left"><a href="#">Profils</a>
									<div class="mp-level">
										<h2>Profils</h2>
										<a class="mp-back" href="#">Retour</a>
										<ul>
											<c:if test="${sessionScope.sessionObjectPersonnel.profil.recupererTypeDroit('Administration') == 'Lecture/écriture' }">
											<li><a href="<c:url value="<%= Pattern.ADMINISTRATION_AJOUT_PROFIL %>"/>">Créer</a></li>
											</c:if>
											<li><a href="<c:url value="<%= Pattern.ADMINISTRATION_LISTER_PROFIL %>"/>">Lister</a></li>
										</ul>
									</div>
								</li>
								<li><a href="<c:url value="<%= Pattern.ADMINISTRATION_LISTER_MODIFICATION %>"/>">Historique des modifications</a></li>
							</ul>
						</div>
					</li>
					</c:if>
				</ul>
			</div>
		</nav>
		<!-- /mp-menu -->

		<!-- Top Navigation -->
		<div class="block block-40 clearfix" id="boutonMenu">
			<p id="trigger" >
                <i class="fa fa-bars fa-6éé"></i>Menu
			</p>
		</div>
		<div style="clear: both;"></div>
		<article>