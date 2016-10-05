#config
toastr.options =
  "closeButton": true
  "debug": false
  "newestOnTop": false
  "progressBar": true
  "positionClass": "toast-top-right"
  "preventDuplicates": false
  "onclick": null
  "showDuration": "300"
  "hideDuration": "2000"
  "timeOut": "10000"
  "extendedTimeOut": "10000"
  "showEasing": "swing"
  "hideEasing": "linear"
  "showMethod": "fadeIn"
  "hideMethod": "fadeOut"
SEPARATEUR = " : "
###
  fonctions utilisées par les classes (et exterieur)
###
window.parseHeure = (chaine) =>
  if chaine == null
    return null
  chaine.split(SEPARATEUR)
###
  Fonction retournant la chaine représentant une heure
###
window.getChaineHeure = (heure, minutes) ->
  if heure < 10 then chaine = "0" + heure else chaine = heure

  chaine = chaine + (SEPARATEUR + minutes)
  if minutes == 0
    chaine = chaine +  "0"
  return chaine

###
Classe en charge de la création du html pour le planning
###
class window.PlanningHtmlCreator
  constructor: (@salles,@heureDebut = 8, @heureFin = 22) ->
    @nbLignes = (@heureFin - @heureDebut) * 4 - 1
  ###
    Fonction retournant la ligne représentant les salles du planning
  ###
  creerLigneSalle: =>
    ligne = $(document.createElement("tr"))
    ligne.append $(document.createElement("th"))
    for salle in @salles
      td = $(document.createElement("th"))
      td.text salle
      ligne.append td
    ligne
  ###
    creer une ligne avec en en-tete, l'heure passée en parametre
  ###
  creerLigneDroppable: (heure, minutes) =>
    ligne = $(document.createElement "tr")
    td = $(document.createElement "th")
    td.text(window.getChaineHeure(heure, minutes))
    if minutes == 0
      td.attr("rowspan",4)
    else
      td.css("display", "none")

    ligne.append td
    for i in [0..@salles.length-1]
      td = $(document.createElement("td"))
      if minutes == 0
        td.addClass "bordure-debut-heure"
      else if minutes == 45
        td.addClass "bordure-fin-heure"

      ligne.append td
    ligne
  ###
    Fonction retournant un tableau crée avec les parametres du planning
    en ligne, les heures
    en colonne, les salles
  ###
  creerTableauJour: =>
    tableau = $(document.createElement("table"))
    tableau.append @creerLigneSalle
    for i in [0..@nbLignes]
      heure = @heureDebut + (i // 4)
      minutes = (i % 4) * 15

      tableau.append @creerLigneDroppable heure, minutes
    tableau
class window.Cours
  constructor: (@matiere, @professeur) ->
  
class window.Contrainte
    constructor: (@idCreneau, @jour, @heureDeb, @heureFin, @salle, @message=null) ->
    
    getEmplacementsConcernes : ->
        chaineSalle = "td"
        chaineJour = "#edt table"
        indexDeb = 1
        indexFin = $("#edt table:first-child").find("tr:last-child").index() + 1
        if @salle != null
            salle = @salle
            indexSalle = $("#edt table").find("tr:first-child").find("th").filter( ->
                return $(this).text() == salle
            ).index() - 1
            chaineSalle = "td:eq(#{indexSalle})"

        if @jour != null
            chaineJour = "#edt ##{@jour}"
        if @heureDeb != null && @heureFin != null
            deb = @heureDeb
            fin = @heureFin
            indexDeb = $("table").children().find("th").filter( ->
                return $(this).text() == deb
            ).closest("tr").index()
            indexFin = $("table").children().find("th").filter( ->
                return $(this).text() == fin
            ).closest("tr").index()

        jours = $(chaineJour)
        elems = new Array()
        for jour in jours
            heures = $(jour).find("tr").slice(indexDeb,indexFin)
            for heure in heures
                elems.push($(heure).find(chaineSalle))
        return elems
        
    
###
  Classe représentant un créneau destiné a etre placé sur un planning
###
class window.Creneau
  constructor: (@id,
   @intitule = "Creneau",
    @nbQuartDHeure = 2,
     @heureDeb = null,
      @jour = null,
       @salle = null,
        @backgroundColor = "ffffff",
         @cours = null,
          @modifie = false,
           @cree = false,
           @contraintes = new Array()) ->

  ###
    Retourne vrai si le créneau est à placer.
  ###
  doitEtrePlace: =>
    return @heureDeb != null && @jour != null && @salle != null && @heureDeb != undefined && @jour != undefined && @salle != undefined
  getHeureFin: =>
    tab = window.parseHeure(@heureDeb)
    tab[0] = parseInt(tab[0])
    tab[1] = parseInt(tab[1])
    tab[1] = tab[1] / 15 + @nbQuartDHeure
    while tab[1] >= 4
      tab[0] += 1
      tab[1] -= 4
    tab[1] *= 15
    return getChaineHeure(tab[0], tab[1])
  ###
    Methode mettant a jour le creneau selon l'element où il a été placé
    elem : l'element, un td ou li appartenant a la liste de créneaux a placer
  ###
  mettreAJour : (elem) ->
    aJour = @jour
    aHeureDeb = @heureDeb
    aSalle = @salle
    if $(elem).prop("tagName") == "LI"
        @salle = null
        @jour = null
        @heureDeb = null
    else
        indexTr = $(elem).closest("tr").index()
        indexElem = $(elem).index()

        @jour = $(elem).closest("div").attr("id")
        @heureDeb = $(elem).closest("table").find("tr:nth-child(#{indexTr + 1})").children().eq(0).text()
        @salle = $(elem).closest("table").find("tr:first-child").find("th:nth-child(#{indexElem + 1})").text()
        
    if aJour != @jour || aHeureDeb != @heureDeb || aSalle != @salle
        @modifie = true
  highlightContraintes: ->
    console.log "highlight"
  hideContraintes: ->
    console.log "hide"
  getChaineInvalide: ->
    return "invalide#{@id}"
    



###
  Classe responsable de placer les classes html sur divers éléments
###
class window.ClassPlacer
  constructor: ->

  placerClassesSalles: (salles, tableau) =>
    for i in [0..salles-1]
      	$(tableau).find("tr td:nth-child("+ i +")").addClass salles[i]
  placerDropZones: (tableau) =>
    $(tableau).find("tr td").addClass "dropZone slotCreneau"

  placerClassesPureCss: (tableau) =>
    $(tableau).addClass("pure-table pure-table-bordred")
  placerClassePlanning:  =>
	   $("table").addClass("pure-planning")

###
  Manager du créneau : responsable de la gestion des créneaux
###
class window.CreneauManager
  constructor: ->

  creerCreneauHtml: (creneau) =>
    if !creneau.doitEtrePlace()
      elem = $(document.createElement("li"))
    else
      elem = $(document.createElement("span"))    
    $(elem).addClass "creneau"
    $(elem).text(creneau.intitule)
    $(elem).css("background-color", "##{creneau.backgroundColor}")
    
    elem.data("creneau",creneau)
    
    elem
    
  ajouterTooltipCreneau: (element, creneau) =>
    texte = @texteCreneauTooltip(creneau)
    $(element).attr("title",texte)
    $(element).tooltip(
      content: texte
      position:
         my: "left top"
         at: "right bottom"
      show:
        effect: "drop"
        delay: 100
      hide:
        effect: "drop"
        delay: 150
    )
    true
  texteCreneauTooltip : (creneau) =>
    if creneau.cours == null
      chaineCours = "Cours: aucun"
    else
      chaineCours = "Cours : <br>&nbsp;&nbsp;Matière : #{creneau.cours.matiere} <br>&nbsp;&nbsp;Professeur : #{creneau.cours.professeur}"
    "Intitule : #{creneau.intitule} <br>
    Heure debut : #{creneau.heureDeb}<br>
    Heure fin : " + creneau.getHeureFin() + "<br>" + chaineCours 
  ###
    Fonction placant un créneau sur le planning
  ###
  placerCreneau : (creneau, enModification = false) =>
    crenHtml = @creerCreneauHtml(creneau)
    if creneau.doitEtrePlace()
      td = @trouverEmplacementCreneau creneau
      #td.css("background-color","##{creneau.backgroundColor}")
      #@ajouterTooltipCreneau(td, creneau)
      window.placerCoursSurDroppable td, creneau.nbQuartDHeure, crenHtml, this, false
    else
      ##Mode affichage du planning
      if enModification == false
        @signalerCreneauNonPlace(creneau)
      else
        @placerCreneauDansListe(crenHtml)

  signalerCreneauNonPlace : (creneau) ->
    toastr.warning('Le créneau ' + creneau.intitule + ' n\'a pas été placé', 'Placement en attente')
  ###
    Place le créneau dans la liste des créneaux "a placer"
  ###
  placerCreneauDansListe : (li) ->
    liste = $("#creneauxAPlacer")
    creneau = li.data("creneau")
    #$(li).css("height", 50 * creneau.nbQuartDHeure)
    liste.append(li)
    window.makeDraggable(li)
    creneau.mettreAJour(li)

    

  ###
    Fonction placant l'ensemble des créneaux sur le planning
  ###
  placerCreneaux: (creneaux, enModif) ->
    for creneau in creneaux
      @placerContraintesCreneau creneau
      @placerCreneau creneau, enModif
    true
      
  placerContraintesCreneau: (creneau) ->
    chaineInvalide = creneau.getChaineInvalide()

    for contrainte in creneau.contraintes
      elems = contrainte.getEmplacementsConcernes()
      for elem in elems
        $(elem).data chaineInvalide,contrainte.message
        console.log ($(elem).data(chaineInvalide))
      elems = []
     
    true


  ###
      Fonction trouvant l'emplacement correspondant un creneau
      Pré-conditions :
        jour positionné
        heureDébut valide
        salle valide
  ###
  trouverEmplacementCreneau: (creneau) =>
    table = $("#" + creneau.jour + " > table")
    #elemHeure = $(table).children().find("th:contains(" + creneau.heureDeb + ")")
    elemHeure = $(table).children().find("th").filter( ->
      return $(this).text() == creneau.heureDeb
    )
    indexColonne = $(table).children().find("th").filter( ->
      return $(this).text() == creneau.salle
    ).index()
    return $(elemHeure).closest("tr").children().eq(indexColonne)