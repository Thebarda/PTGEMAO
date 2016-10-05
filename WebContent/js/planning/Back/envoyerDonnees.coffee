window.asJSON = (creneaux) ->
    JSON.stringify(creneaux)
    
  
window.envoyer = (string) ->
    console.log "envoi " + string
    $.ajax(
        url: window.location.href
        type: 'post'
        data: 'creneaux=' + string
        dataType: 'json'
        success: (data)->
            if data.state == "succes"
                toastr.success("Les créneaux ont été modifiés", "Succès")
        error: (data) ->
            if data.state == "echec"
                toastr.error("Echec de la modification", "Echec")
            

    )
window.envoyerCreneaux    
$(document).ready( ->
  $("#boutonValider").click(->
    json = window.asJSON(window.creneauxJS)
    window.envoyer(json)
    for c in creneauxJS
        c.cree = false
        c.modifie = false
  )
)
    