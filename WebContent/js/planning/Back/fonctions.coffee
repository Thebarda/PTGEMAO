#Si ce fichier est inclus, on est en modification
window.supprimerCreneauCase = (elem, creneau) ->
  indexTr = $(elem).closest("tr").index()
  indexTd = $(elem).index()
  table = $(elem).closest("table")
  $(elem).removeAttr("style")
  $(elem).removeAttr("rowSpan")
  $(elem).removeAttr("title")
  $(elem).children().remove()
  $(elem).text("")
  $(elem).draggable("destroy")
  $(elem).tooltip("destroy")
  $.removeData(elem,"creneau")
  
  ligne = $(elem).closest("tr")
  for i in [0..creneau.nbQuartDHeure - 1]
      td = $(ligne).children().eq(indexTd)
      td.removeAttr("style")
      ligne = $(ligne).next()
  true
window.supprimerCreneauListe = (elem) ->
  $(elem).remove()      
window.makeDroppableOnglets = (elem) ->
  for e in elem
    $(e).droppable(
        tolerance: "pointer"
        over: ->
            console.log $(this).index()
            $("#edt").tabs("option","active",$(this).index())
            console.log $(this).find("a").attr("href")
    )
    

window.ancienneTargetHover = null
###
    DROPPABLE
###
window.creerZonesDroppables = ->
  window.makeDroppable($("#edt td"))
  $("#creneauxAPlacer").droppable(
    drop: (event, ui) ->
      if ui.draggable.prop("tagName") != "LI"
        m = new window.CreneauManager()
        creneau = ui.draggable.data("creneau")
        l = $(document.createElement "li")
        $(l).addClass "creneau"
        $(l).text(creneau.intitule)
        $(l).css("background-color", "##{creneau.backgroundColor}")
        l.data("creneau",creneau)
        m.placerCreneauDansListe(l)
        supprimerCreneauCase(ui.draggable, creneau)
  )


window.makeDroppable = (elem) ->
  $(elem).droppable(
    drop: (event, ui) ->
      creneau = $(ui.draggable).data("creneau")
      nbLignes = creneau.nbQuartDHeure
      if window.ancienneTargetHover != null && window.ancienneTargetHover != ui.draggable
        $(window.ancienneTargetHover).removeAttr("style")
      unless  window.coursPeutEtrePlaceSurDroppable(this, nbLignes,creneau)
        toastr.warning("Cours implaçable", "Impossible") #TODO toaster
      else
        placerCoursSurDroppable(this, nbLignes, ui.draggable, new window.CreneauManager())
      window.ancienneTargetHover = null
      
      
        
    over: (event, ui) ->
      #TODO fix la couleur qui s'en va
      target = event.target
      style = $(target).attr("style")
      couleur = $(ui.draggable).data("creneau").backgroundColor
      if target == ui.draggable
        console.log "la fin des haricots"
      if window.ancienneTargetHover != null && window.ancienneTargetHover != ui.draggable
        $(window.ancienneTargetHover).removeAttr("style")
      if style != undefined && target != ui.draggable
        window.ancienneTargetHover = null
      else
        $(target).css("background-color", "##{couleur}")
        window.ancienneTargetHover = target

  )
###
    DRAGGABLE
###
window.makeDraggable = (elem) ->
  $(elem).draggable(
    #snap: ".slotCours"
    snapMode: "inner"
    snapTolerance: 10
    zIndex: 100
    helper: 
        ->
            c = $(elem).clone()
            c.addClass("creneauDrag")
            $(elem).css("cursor","grabbing")
            c.css("height", "30px")
            c.css("width","60px")
            return c
    appendTo: "#edt"
    cursorAt:
        right: 30
        bottom: 15
    start: (event, ui) ->
        window.enTrainDeDrag = true
        window.dragged = ui.draggable
        true
    stop: (event, ui) ->
        window.enTrainDeDrag = false
        window.dragged = null
        $(elem).css("cursor","grab")
        true
  )
  $(elem).css("cursor","grab")
  $(elem).addClass("unselectable")
  true
###
    GESTION COURS DROPPABLE
###
window.placerCoursSurDroppable = (elem, nbLignes, draggable, creneauManager, doitMettreAJour = true) =>
  ligne = $(elem).parent("tr")
  indexTr = $(ligne).index("tr")
  indexTd = $(elem).index()
  chaineElem = ":nth-child(" + (indexTd+1) + ")"
  table = $(ligne).closest("table")
  nbTr = $(table).find("tr").length
  texte = draggable.text()
  i = 1
  while i < nbLignes and i < nbTr
    ligne = $(ligne).next()
    td = $(ligne).find(chaineElem)
    td.css("display", "none")
    i = i + 1
  
  $(elem).attr("rowspan", nbLignes)
  $(elem).find("span").remove()
  creneau = $(draggable).data("creneau")
  if enModif == true
    if doitMettreAJour
        creneau.mettreAJour(elem)
    $(elem).data("creneau",creneau)
    if $(draggable).prop("tagName") == "TD"
        window.supprimerCreneauCase(draggable,creneau)
    if $(draggable).prop("tagName") == "LI"
        window.supprimerCreneauListe(draggable)
    window.makeDraggable(elem)
  creneauManager.ajouterTooltipCreneau(elem,creneau)
  $(elem).css("background-color", "##{creneau.backgroundColor}")
  $(elem).text(texte)


  #$("<span></span>").text(draggable.text()).appendTo(elem)


window.coursPeutEtrePlaceSurDroppable = (droppable, tailleElem, creneau, doitEmpecherSurClasseInvalide = false) ->
    ligne = $(droppable).parent("tr")

    indexTd = $(droppable).index()
    indexTr = $(ligne).index()
    
    nbTr = $(ligne).siblings().length
    
    warningInvalideLance = false
    if indexTr + tailleElem - 1> nbTr
        return false
    classeInvalide = creneau.getChaineInvalide()
    varchaineElem = ":eq(#{indexTd})"
    for i in [0..tailleElem - 1]
        tds = $(ligne).children()
        td = tds.eq(indexTd)
        if td.text().length > 0
            return false
        msg = td.data(classeInvalide)
        if msg != undefined
            if doitEmpecherSurClasseInvalide == true
                return false
            else
                if warningInvalideLance == false
                    if msg == null
                        msg = "Une contrainte a été définie pour ce créneau à cet emplacement"
                    toastr.warning(msg, "Attention")
                    
                    warningInvalideLance = true
        ligne = ligne.next()
        
    ligne = $(elem).closest("tr")
    for i in [0..creneau.nbQuartDHeure - 1]
        td = $(ligne).children().eq(indexTd)
        
        td.removeAttr("style")
        ligne= $(ligne).next()
    true
window.supprimerCreneauListe = (elem) ->
    $(elem).remove()
    
   
###
    INIT

$(document).ready( ->
  createur = new window.PlanningHtmlCreator(salles, 8, 22)
  classPlacer = new window.ClassPlacer()

  tableauJours = ["Lundi","Mardi","Mercredi","Jeudi","Vendredi","Samedi","Dimanche"]
  tableau = createur.creerTableauJour
  classPlacer.placerDropZones(tableau)
  
  for jour in tableauJours
    $("##{jour}").append(tableau)
  classPlacer.placerClassePlanning()
  $("#edt").tabs()
  $( document ).tooltip();

)
###
