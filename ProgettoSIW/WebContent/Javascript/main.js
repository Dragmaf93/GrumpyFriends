
$.ajax({
	  url: "index.html"
	}).done(function(data){
	$('body').append(data);
	var json = eval("("+localStorage["user"]+")");
	
	if (json != null) {
			simpleLogin(json);
		}
});

	
function simpleLogin(json) {
	$.ajax({
		type : "POST",
		url : 'Login',
		data : {
			'username' : json["username"],
			'password' : json["password"]
		},
		success : function(data) {
			var jsonResp = eval("("+data+")");
			if (jsonResp["user"] != "null") {
				deleteLoginAndInsertNameUser(jsonResp["username"]);
			}
//			else {
//				userNotPresent();
//			}
		},
		 error: function (data) {
              alert("ERRORE");
        }
	});
}

