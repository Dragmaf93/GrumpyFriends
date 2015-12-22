var socialUser = false;

function insertPaneLogin() {
	
	$.ajax({
		  url: "login.html"
		}).done(function(data){
		$('body').append(data);
	});
}

function deletePanelLogin() {
	$('#paneBottomLogin').remove();
	$('#paneLogin').remove();
}

function deletePanelRegister() {
	$('#register').remove();
}

function insertPaneForRegister() {
	$.ajax({
		  url: "register.html"
		}).done(function(data){
		$('body').append(data);
	});
}

function executeLogin() {
	$.ajax({
		type : "POST",
		url : 'Login',
		data : {
			'username' : $("#user").val(),
			'password' : $("#pwd").val()
		},
		success : function(data) {
			localStorage["user"] = data;
			var jsonResp = eval("("+data+")");
			if (jsonResp["user"] != "null") {
				deleteLoginAndInsertNameUser(jsonResp["username"]);
			}
			else {
				userNotPresent();
			}
		},
		 error: function (data) {
              alert("ERRORE");
        }
	});
}


function deleteLoginAndInsertNameUser(username) {
	$('#paneLogin').remove();
	$('#paneBottomLogin').remove();
	$('#menu li').eq(3).remove();
	$('ul#menu').last().append('<li><a id="exitKey" onclick="executeLogOut()">Exit</a></li>');
	$('body').append('<div id="nameUser"><p>'+username+'</p></div>');
}

function userNotPresent() {
	$('body').append('<div id="userNotPresent"><p>Utente non presente, Registrati.</p>'+
			'<input onclick="ok()" type="button" value="Ok"/></div>');
}

function ok() {
	$('#userNotPresent').remove();
}

function executeLogOut() {
	$('#menu li').eq(3).remove();
	$('ul#menu').last().append('<li><a id="loginKey" onclick="insertPaneLogin()">Login</a></li>');
	$('#nameUser').remove();
	
	if (socialUser)
		Logout();
	else
		localStorage["user"] = null;
}

window.fbAsyncInit = function() {
	FB.init({
		appId : '187466334935416',
		xfbml : true,
		version : 'v2.5'
	});
};
(function(d, s, id) {
	var js, fjs = d.getElementsByTagName(s)[0];
	if (d.getElementById(id)) {
		return;
	}
	js = d.createElement(s);
	js.id = id;
	js.src = "//connect.facebook.net/en_US/sdk.js";
	fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));

// login Facebook
function Login() {
    FB.login(function(response) {
       if (response.authResponse) {
            getUserInfo(); // Get User Information.
        } else {
         console.log('Authorization failed.');
        }
     },{scope: 'email'});
}

function getUserInfo() {
	FB.api('/me', {fields : [ 'email', 'name', 'picture.type(normal)']}, function(response) {

     //response.name       - User Full name
     //response.link       - User Facebook URL
     //response.username   - User name
     //response.id         - id
     //response.email      - User email
    	$.ajax({
    		type : "POST",
    		url : 'SignUp',
    		data : {
    			'name' : response.name,
    			'username' : response.username,
    			'email' : response.email,
    			'imageUrl' : response.picture.data.url,
    			'social' : true
    		},
    		success : function(data) {
    			console.log(localStorage);
    			localStorage["user"] = data;
    			var jsonResp = eval("("+data+")");
//    			if (localStorage["user"] == jsonResp["username"])
//    				console.log("YOOOOOOO");
    			if (jsonResp["user"] != "null") {
    				deleteLoginAndInsertNameUser(jsonResp["username"]);
    				socialUser = true;
    			}
    			else {
    				userNotPresent();
    			}
    		},
    		 error: function (data) {
                  console.log("ERRORE");
            }
    	});
    	
     });
}

function getPhoto()
{
  FB.api('/me/picture?type=normal', function(response) {

//      var str="<br/><b>Pic</b> : <img src='"+response.data.url+"'/>";
//      document.getElementById("status").innerHTML+=str;
	  console.log(response.data.url);
	  return response.data.url;
  }); 
}


function Logout()
{
	localStorage["user"] = null;
    FB.logout(function(){document.location.reload();});
}

