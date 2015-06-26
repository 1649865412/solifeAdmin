var hasChange = false;
if (document.forms[0])
{
	document.forms[0].setAttribute('autocomplete','off'); 
	document.forms[0].onkeypress = function (e) 
	{   
    	hasChange = true;
	}
	document.forms[0].onmouseup = function(e) 
	{
		var targ;
		if (!e)
		{
			var e = window.event;
		}
		if (e.target)
		{
			targ = e.target;
		}
		else if (e.srcElement)
		{
			targ = e.srcElement;
		}
		if (targ.nodeType == 3) // defeat Safari bug
		{
			targ = targ.parentNode;
		}
		if (targ.type == 'select-one' || targ.type =='checkbox')
		{
			hasChange = true;
		}
	}
}
function fnTabLink($url)
{
	if (hasChange)
	{
		if (confirm(__FMT.common_message_tabConfirmSave))
		{
			window.location=$url;
		}
	}
	else
	{
		window.location=$url;
	}
}