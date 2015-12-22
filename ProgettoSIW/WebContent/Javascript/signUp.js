
function createAccount() {
		$.ajax({
			type : "POST",
			url : 'SignUp',
			data : {
				'username' : $('#username').val(),
				'password' : $('#password').val(),
				'email' : $("#email").val(),
				'social' : false
//				'profileImage' : $('#fileChoosed').val()
			},
			success : function(data) {
				// FARE PANNELLINO
				localStorage["user"] = data;
				$('#register').toggle();
				$('#paneLogin').toggle();
				$('#paneBottomLogin').toggle();
				$('body').append('<div id="nameUser"><p>'+$('#username').val()+'</p></div>');
			},
			 error: function (data) {
	              alert("ERRORE");
            }
		});
} 


function chooseProfileImage() {
	$.ajax({
		  url: "fileChooser.html"
		}).done(function(data){
		$('body').append(data);
	});
}
