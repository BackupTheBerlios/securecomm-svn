function set_disable (myCB) {
	var pTable=myCB.parentNode.parentNode.parentNode.getElementsByTagName("input");
	
	for (var i=0; i<pTable.length; i++){
		if (pTable[i].getAttribute("type") != "checkbox"){
			if (myCB.checked) {
				pTable[i].disabled=false;
			}
			else {
				pTable[i].disabled=true;
			}
		}
	}
}