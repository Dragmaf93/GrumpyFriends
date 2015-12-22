function initialize() {
        var mapCanvas = document.getElementById('map');
        
        var mapOptions = {
          center: new google.maps.LatLng(39.355520, 16.225190),
          zoom: 15,
          mapTypeId: google.maps.MapTypeId.HYBRID
        };
        var map = new google.maps.Map(mapCanvas, mapOptions);
        
        var positions = [
   		    new google.maps.LatLng(39.35552, 16.22518),
   			new google.maps.LatLng(39.36211, 16.22403),
   			new google.maps.LatLng(39.36786, 16.22516)
   		];
        
        var information = [
			'<div id="content"><div id="siteNotice"></div><h1 id="firstHeading" class="firstHeading">Mensa Centro Residenziale</h1>'+
			'<div id="bodyContent"><p>87036 Arcavacata di Rende, Cs</p><p>(+39) 0984 4911</p></div></div>',
		//	'<p>facebook: <a href="www.facebook.com/Mensa-Centro-Residenziale-Unical-798030096938423/?fref=ts"></a></p></div></div>',
          	
			'<div id="content"><div id="siteNotice"></div><h1 id="firstHeading" class="firstHeading">Mensa Martensson</h1>'+
			'<div id="bodyContent"><p>Via Costantino 1, 30, Arcavacata CS, Italia</p></div></div>',         
                      
			'<div id="content"><div id="siteNotice"></div><h1 id="firstHeading" class="firstHeading">Mensa del Teatro</h1>'+
			'<div id="bodyContent"><p>Ponte Pietro Bucci, 87036 CS, Italia</p><p>+39 0984 175 8007</p</div></div>'           
        ];
        
  		var iconBase = "images/restaurant.png";
	 	for	(index = 0; index < positions.length; index++) {
	      	var marker = new google.maps.Marker({
	      	  position: positions[index],
	   		  map: map,
	     	  icon: iconBase
	     	 });

	      	var infoWindow = new google.maps.InfoWindow(), marker, int;
			 
			 google.maps.event.addListener(marker, 'click', (function(marker, int, index) {
					return function() {

						var contentString = information[index];
						infoWindow.setContent(contentString);
						infoWindow.open(map, marker);
					}
				})(marker, int, index));
      	} 
		
		
      }



  google.maps.event.addDomListener(window, 'load', initialize);
      

