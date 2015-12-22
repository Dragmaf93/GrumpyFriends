 
function deletePanelMenu() {
	$("#panelForMenu").toggle();
}
function showDailyMenu() {
	$("#panelForMenu").toggle();

var posLeft = 5;
$.ajax({
	method : "POST",
	url : 'GetMenuDaily',
	dataType: 'json',
	success : function(data) {
		var responseJson = eval(data);
		if(responseJson != null) {
            $.each(responseJson, function(key,value) {
            	var category = eval(value.category);
            	if (category == "1")
            		$('#panelForMenu').append('<div><p id="categoryDish" style="left:'+(posLeft+10)+'%">Primo</p> </div>');
            	else if (category == "2")
            		$('#panelForMenu').append('<div><p id="categoryDish" style="left:'+(posLeft+10)+'%">Secondo</p> </div>');
            	else if (category == "3")
            		$('#panelForMenu').append('<div><p id="categoryDish" style="left:'+(posLeft+10)+'%">Contorno</p> </div>');
            	
            	$('#panelForMenu').append('<div><p id="nameDish" style="left:'+(posLeft+5)+'%">' + value.name + '</p> </div>');
            	$('#panelForMenu').append('<div><a id="dish""><img style="left:'+posLeft+'%" src="' + value.image_url + '"/></a> </div>');
            	
            	if(value.description != null)
                	$('#panelForMenu').append('<div><p id="descriptionDish" style="left:'+(posLeft)+'%">' + value.description + '</p> </div>');
            	else
                	$('#panelForMenu').append('<div><p id="descriptionDish" style="left:'+(posLeft)+'%">Descrizione non presente</p> </div>');
                	
                	posLeft += 30;
                });
            }
		}
	});
}