window.makeDroppableOnglets = (elem) ->
  $(elem).hover(
    (event) ->
        if window.enTrainDeDrag == true
            console.log "JE DIS OUI"
            console.log "##{elem.children("a").eq(0).attr("href")}"
            $("#{elem.children().eq(0).attr("href")}").show()
            
  )
  
$(document).ready( ->
  #window.makeDroppableOnglets($("#jours li"))
    
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
      
    
  $("#jours li").mouseenter( 
      ->
        if window.enTrainDeDrag == true
            console.log this
            index = $("#jours li").index(this)
            console.log index
            $("#edt").tabs(active: index)
            $("#edt").tabs("refresh")
            
        
      
  )
)