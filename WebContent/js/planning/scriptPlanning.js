function ajouterClasses() {
	$("table").addClass("pure-planning");
}

function makeDraggable(elem){
	$(elem).draggable({
		snap: ".slotCours",
		snapMode: "inner",
		snapTolerance: 10,
		helper: "clone",

		start: function(event,ui) {
            $(ui.helper).css("background-color", "rgba(80, 80, 80, 0.80)");
            $(ui.helper).css("border", "none");
		}
	});
}

function makeDroppable(elem) {
	$(elem).droppable({
		drop: function(event, ui) {
			var spanLignes = $(ui.draggable).data("taille").span;
			if (!(coursPeutEtrePlaceSurDroppable(this,spanLignes))) {
				alert("Controle TODO ASAP");
			}
			else{
				placerCoursSurDroppable(this,spanLignes,ui.draggable);
			}
		}
	});
}

function placerCoursSurDroppable(elem, spanLignes,draggable) {
	var ligne = $(elem).parent("tr");
	var indexTr = $(ligne).index("tr");
	var indexTd = $(elem).index();
	var nbTdBase = $(ligne).find("th, td").length;
	var chaineElem = ":nth-child(" + (indexTd+1) + ")";
	var table = $(ligne).closest("table");
	var nbTr = $(table).find("tr").length;
	var i = 1;
	while (i < spanLignes && i < nbTr) {
		ligne = $(ligne).next();
		var td = $(ligne).find(chaineElem);
		console.log (td.index());

		td.css("display", "none");
		++i;
	}
	makeDraggable(elem);
	$(elem).addClass("draggable");
	$(elem).attr("rowspan", spanLignes);
	$(elem).find("span").remove();
	$(elem).data( $(draggable).data() );
	$("<span></span>").text($(draggable).text()).appendTo(elem);
	if ($(draggable).prop("tagName") == "TD") {
		enleverCoursDeDroppable(draggable);
	}
}

function enleverCoursDeDroppable(elem) {

	var spanLignes = $(elem).data("taille").span;
	console.log("c'est bon");
	console.log("span " + spanLignes);
	$(elem).removeData();
	$(elem).find("span").remove();

	$(elem).attr("rowspan", 1);
	var ligne = $(elem).parent("tr");
	var indexTd = $(elem).index();
	var chaineElem = ":nth-child(" + (indexTd+1) + ")";
	var table = $(ligne).closest("table");
	var i = 1;
	while (i < spanLignes) {
		ligne = $(ligne).next();
		var td = $(ligne).find(chaineElem);
		td.removeAttr("style");
		++i;
	}
}

function coursPeutEtrePlaceSurDroppable(droppable, tailleNouvElem){
	var ligne = $(droppable).parent();
	var indexTd = $(droppable).index();
	var indexTr = $(ligne).index("tr");
	var nbTr = $(ligne).parent().find("tr").length;
	if (indexTr + tailleNouvElem > nbTr ) {
		return false;
	}
	var chaineElem = ":eq(" + indexTd + ")";
	for (var i = 0; i < tailleNouvElem; i++) {
		var tds = $(ligne).children();
		var td = tds.eq(indexTd);
		if (td.has("span").length){
			return false;
		}
		ligne = ligne.next();
	}
	return true;
}
