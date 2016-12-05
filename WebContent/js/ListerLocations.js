document.getElementById("tableDate").style.visibility = "collapse";
document.getElementById("changeTable").addEventListener("click", function(){
	if(document.getElementById("tableDate").style.visibility == "collapse"){
		document.getElementById("tableDate").style.visibility = "visible";
		document.getElementById("tableNom").style.visibility = "collapse";
	}else{
		document.getElementById("tableDate").style.visibility = "collapse";
		document.getElementById("tableNom").style.visibility = "visible";
	}
});