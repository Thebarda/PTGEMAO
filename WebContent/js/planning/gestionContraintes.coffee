tabJSON = []
$(document).ready( ->
  
  
  ajouterDansTableauJSON = ->
    idJour = $("#jourContrainte").val()
    idSalle = $("#salleContrainte").val()
    heureDebContrainte = $("#heureDebContrainte").val()
    minuteDebContrainte = $("#minuteDebContrainte").val()
    heureFinContrainte = $("#heureFinContrainte").val()
    minuteFinContrainte = $("#minuteFinContrainte").val()
    messageContrainte = $("#messageContrainte").val()
    isGlobale = $("#isGlobale").val()

    bool = $("#isGlobale").val()
    console.log(bool)
    
    contrainteJSON =
      idJour: idJour
      idSalle: idSalle
      heureDebContrainte: heureDebContrainte
      minuteDebContrainte: minuteDebContrainte
      heureFinContrainte: heureFinContrainte
      minuteFinContrainte: minuteFinContrainte
      messageContrainte: messageContrainte
      isGlobale: isGlobale
        
    tabJSON.push(contrainteJSON)

    $("#heureDebContrainte").val("")
    $("#minuteDebContrainte").val("")
    $("#heureFinContrainte").val("")
    $("#minuteFinContrainte").val("")
    $("#messageContrainte").val("")
    #$("#isGlobale").val("")
    
  remplirTextArea = ->
    $("#jsonContraintes").val(JSON.stringify(tabJSON))
    
  mettreAJourPhrases = ->
    phrase = "<p class=\"text-align\">Le créneau ne pourra pas être placé "
    
    if $("#salleContrainte").val() != null
      phrase += "en " + $("#salleContrainte option:selected").text()
    
    jour = $("#jourContrainte")
    hDebC = $("#heureDebContrainte")
    mDebC = $("#minuteDebContrainte")
    hFinC = $("#heureFinContrainte")
    mFinC = $("#minuteFinContrainte")
    message = $("#messageContrainte").val()
    isGlobale = $("#isGlobale")
    
    toastr.options =
      "closeButton": true
      "debug": false
      "newestOnTop": false
      "progressBar": true
      "positionClass": "toast-top-right"
      "preventDuplicates": false
      "onclick": null
      "showDuration": "500"
      "hideDuration": "1000"
      "timeOut": "5000"
      "extendedTimeOut": "1000"
      "showEasing": "swing"
      "hideEasing": "linear"
      "showMethod": "fadeIn"
      "hideMethod": "fadeOut"
    
    if jour.val() != null
      if $("#jourContrainte option:selected").text() == "Tous les jours"
        phrase += " pour tous les jours "
      else
        phrase += " le " + $("#jourContrainte option:selected").text()
        
    # Tests de l'incohérence des champs vides    
        
    if mDebC.val() != "" && hDebC.val() == ""
      throw "Indiquez une heure de début"
    if mFinC.val() != "" && hFinC.val() == ""
      throw "Indiquez une heure de fin"    
    if hDebC.val() != "" && hFinC.val() == ""
      throw "Indiquez une heure de fin"
    if hFinC.val() != "" && hDebC.val() == ""
      throw "Indiquez une heure de début"
      
    # Tests de l'incohérence des heures
    
    
    console.log(hDebC.val())
    console.log(hFinC.val())
    console.log(hDebC.val() < hFinC.val())
    
    if hDebC.val() != "" && hFinC.val() != "" && parseInt(hDebC.val()) > parseInt(hFinC.val())
      throw "L'heure de début ne peut pas être postérieure à l'heure de fin"
    if hDebC.val() == hFinC.val() && mDebC.val() > mFinC.val()
      throw "L'horaire de fin ne peut pas être antérieur à l'heure de début"
    
    if hDebC.val() != ""
      phrase += " de " + hDebC.val() + "h"
    if mDebC.val() != ""
      phrase += mDebC.val()
    if hFinC.val() != ""
      phrase += " à " + hFinC.val() + "h"
    if mFinC.val() != ""
      phrase += mFinC.val()
    if message != ""
      phrase += " pour la raison : \"" + message + "\""
      
    phrase += "</p>"
    $("#restrictionsPhrases").append(phrase)
    
  $("#ajouterContrainte").click ->
    try
      mettreAJourPhrases()
      ajouterDansTableauJSON()
    catch error
      toastr.error(error, "Erreur de contrainte")
    
  $(".submitJSON").click ->
    remplirTextArea()
)
