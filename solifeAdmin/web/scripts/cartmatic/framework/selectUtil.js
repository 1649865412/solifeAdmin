
/**
  * 将一个select的options移动到另一个select。 
  */
function fnMoveAllOptions(objSource, objDes) {
	for (var i = 0; i < objSource.options.length; i++) {
		var optOption = new Option(objSource.options[i].text, objSource.options[i].value);
		objDes.options[objDes.options.length] = optOption;
	}
	fnRemoveAllOptions(objSource);
}

//remove all select item
function fnRemoveAllOptions(obj) {
	obj.options.length = 0;
}

//add selected item
function addSelect(objSource, objDest) {
	var sourceLen = objSource.options.length;
	for (var i = 0; i < sourceLen; i++) {
		if (objSource.options[i].selected) {
			var searchKey = objSource.options[i].value;
			if (!isKeyExist(searchKey, objDest)) {
				var optOption = new Option(objSource.options[i].text, objSource.options[i].value);
				objDest.options[objDest.options.length] = optOption;
			}
		}
	}
	removeSelectOptions(objSource);
}
//is key is exist in select
function isKeyExist(searchKey, obj) {
	for (var i = 0; i < obj.options.length; i++) {
		if (searchKey == obj.options[i].value) {
			return true;
		}
	}
	return false;
}
function removeSelectOptions(obj) {
	for (var i = obj.length - 1; i >= 0; i--) {
		if (obj.options[i].selected) {
			obj.options[i] = null;
		}
	}
}
function moveOptionUp(obj, count) {
	var index = obj.selectedIndex;
	if (index == -1) {
		return;
	}
	var step = index - parseInt(count);
	if (step >= 0) {
		obj.options(index).swapNode(obj.options(step));
	} else {
		obj.options(index).swapNode(obj.options(0));
	}
}
function moveOptionDown(obj, count) {
	var index = obj.selectedIndex;
	if (index == -1) {
		return;
	}
	var step = index + parseInt(count);
	if (step < obj.length) {
		obj.options(index).swapNode(obj.options(step));
	} else {
		obj.options(index).swapNode(obj.options(obj.length - 1));
	}
}

