// a - Represents an alpha character (A-Z,a-z)
// 9 - Represents a numeric character (0-9)
// * - Represents an alphanumeric character (A-Z,a-z,0-9)
// ? - Anything listed after '?' within the mask is considered optional user input

jQuery(function($) {
    $.mask.definitions['~']='[+-]';
    $('#phone').mask('(999) 999-9999', {completed:function(){alert("You typed the following: "+this.val());}});
    $('#phoneext').mask("(999) 999-9999? x99999");
    $("#date").mask("99/99/9999",{placeholder:" ",completed:function(){alert("You typed the following: "+this.val());}});
    $("#money").mask("$~9,999.99", {placeholder:" "});
    $('#listbox1').jec({maxLength: 10});
    
});


function showDetails( name, idx ) 
{
	for (var i=1; i<=idx; i++)
    {
		id = name + i;
        var ltext = $('div#'+id+'box').text();
		$('#'+id).text(ltext);

    }
}

function hideDetails( name, idx ) 
{
	for (var i=1; i<=idx; i++)
    {
		id = name + i;
        var ltext = $('div#'+id+'box').text();
    	var stext = ltext.substring(0,49) + "...";
		$('#'+id).text(stext);

    }
}

function popupBox( name, box )
{
	$('div#'+name).hover(function() {
        $('div#'+box).show();
	}, function() {
	    $('div#'+box).hide();
	});

}

