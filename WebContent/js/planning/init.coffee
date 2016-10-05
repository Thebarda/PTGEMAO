tableauJours = ["Lundi"
                "Mardi"
                "Mercredi"
                "Jeudi"
                "Vendredi"
                "Samedi"
                "Dimanche"]


window.enTrainDeDrag = false
window.dragged = null
console.log "init"
manager = new window.CreneauManager()
$(document).ready( ->
  createur = new window.PlanningHtmlCreator(salles, 8, 22)
  classPlacer = new window.ClassPlacer()
  tableau = createur.creerTableauJour
  classPlacer.placerDropZones(tableau)
  #insertion du tableau pour chaque jour
  for jour in tableauJours
    $("##{jour}").append(tableau)
  classPlacer.placerClassePlanning()
  #Crée les onglet
  $("#edt").tabs()
  #Nécessaire pour les tooltips sur les créneaux
  $(document).tooltip()

  if enModif == true
    console.log "je suis en modif"
    $("#addCreneau").click ->
    
      form = $("#ajoutCreneau")
      crPlaI = $("#creneauxPlacement i")

      if form.css("display") == "none"
        form.slideDown()
        crPlaI.removeClass('fa-plus-square')
        crPlaI.addClass('fa-minus-square')
      else
        form.slideUp()
        crPlaI.addClass('fa-plus-square')
        crPlaI.removeClass('fa-minus-square')

    window.makeDroppableOnglets($("#jours li"))
    window.creerZonesDroppables()
  manager.placerCreneaux(creneauxJS,enModif)
   
)