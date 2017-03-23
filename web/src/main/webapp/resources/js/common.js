var markerCenter;
var markerRadius;
var map;
var mlsRadius;

function initMap() {
	document.getElementById('contentForm:radius').innerHTML = ' ';
	document.getElementById('contentForm:center_lat').innerHTML = ' ';
	document.getElementById('contentForm:center_lon').innerHTML = ' ';
	markerCenter = null;
	markerRadius = null;
	document.getElementById('contentForm:ok_btn').style.display = 'none';
	
    // Create a map object and specify the DOM element for display.
    map = new google.maps.Map(document.getElementById('locMap'), {
      center: {lat: 0, lng: 0},
      zoom: 2
    });
    
    google.maps.event.addListener(map, 'click', function(event) {
	   placeCenter(event.latLng);
	});
    
    google.maps.event.addListener(map, 'rightclick', function(event) {
 	   placeCircle(event.latLng);
 	});
  }

function placeCenter(location) {
	if(markerCenter != null) {
		markerCenter.setMap(null);
		markerCenter = null;
		if(markerRadius != null) {
			markerRadius.setMap(null);
			markerRadius = null;
		}
		document.getElementById('contentForm:radius').innerHTML = '0';
		document.getElementById('contentForm:radius_hidden').value = '';
	} 
	PF('ok_loc_btn').disabled = true;
	markerCenter = new google.maps.Marker({
		position: location, 
		map: map,
		icon: 'https://maps.gstatic.com/intl/en_us/mapfiles/markers2/measle_blue.png'
	});
	document.getElementById('contentForm:center_lat').innerHTML = location.lat();
	document.getElementById('contentForm:center_lon').innerHTML = location.lng();
	
	document.getElementById('contentForm:center_lat_hidden').value = location.lat();
	document.getElementById('contentForm:center_lon_hidden').value = location.lng();
	
	document.getElementById('contentForm:ok_btn').style.display = 'none';
}

function placeCircle(location) {
	if(markerCenter != null) {
		if(markerRadius != null) {
			markerRadius.setMap(null);
			markerRadius = null;
		}
		var radius = measure(markerCenter.getPosition().lat(), markerCenter.getPosition().lng(), location.lat(), location.lng());
		radius = Math.floor(radius);
		markerRadius = new google.maps.Circle({
	        strokeColor: '#0000FF',
	        strokeOpacity: 0.8,
	        strokeWeight: 2,
	        fillColor: '#0000FF',
	        fillOpacity: 0.35,
	        clickable: false,
	        map: map,
	        center: markerCenter.getPosition(),
	        radius: radius
	      });
		
		google.maps.event.addListener(markerRadius, 'rightclick', function(){
		    google.maps.event.trigger(map, 'rightclick', null);
		});
		
		document.getElementById('contentForm:radius').innerHTML = radius;
		document.getElementById('contentForm:radius_hidden').value = radius;
		
		document.getElementById('contentForm:ok_btn').style.display = 'block';
	}
}

function moveMapCamera(){
	var map = PF('widgetMap').map;
	var lat =  document.getElementById("contentForm:lat").value;
	var lng =  document.getElementById("contentForm:lng").value;
	var latlng = new google.maps.LatLng(lat, lng);
	map.panTo(latlng);
}

function markerSelected(){
	var pfMap = PF('widgetMap').map;
	var lat =  document.getElementById("contentForm:lat").value;
	var lng =  document.getElementById("contentForm:lng").value;
	var radius =  document.getElementById("contentForm:mlsRadius").value;
	var latlng = new google.maps.LatLng(lat, lng);
	if(mlsRadius != null) {
		mlsRadius.setMap(null);
		mlsRadius = null;
	}
	if(radius > 0) {
		var tmp = parseFloat(radius);
		mlsRadius = new google.maps.Circle({
	        strokeColor: '#0000FF',
	        strokeOpacity: 0.8,
	        strokeWeight: 2,
	        fillColor: '#0000FF',
	        fillOpacity: 0.35,
	        clickable: false,
	        map: pfMap,
	        center: latlng,
	        radius: tmp
	      });
	}
}

function openInfoWindow() {
	var lat =  document.getElementById("contentForm:lat").value;
	var lng =  document.getElementById("contentForm:lng").value;
	var geocoder = new google.maps.Geocoder;
	var latlng = {lat: parseFloat(lat), lng: parseFloat(lng)};
    geocoder.geocode({'location': latlng}, function(results, status) {
      if (status === 'OK') {
        if (results[1]) {
          document.getElementById("contentForm:infoWindow_content").innerHTML = results[1].formatted_address;
          //alert(results[1].formatted_address);
        } else {
          //window.alert('No results found');
        }
      } else {
        //window.alert('Geocoder failed due to: ' + status);
      }
    });

}

function reverseGeocode(){
	markerSelected();
	var lat =  document.getElementById("contentForm:lat").value;
	var lng =  document.getElementById("contentForm:lng").value;
	if(lat != "" && lng != "") {
		var geocoder = new google.maps.Geocoder;
		var latlng = {lat: parseFloat(lat), lng: parseFloat(lng)};
	    geocoder.geocode({'location': latlng}, function(results, status) {
	      if (status === 'OK') {
	        if (results[1]) {
	        	setTimeout(function(){ 
	        		document.getElementById("contentForm:location").innerHTML = results[1].formatted_address;
	        	}, 500);
	        	
	          //alert(results[1].formatted_address);
	        } else {
	          //window.alert('No results found');
	        }
	      } else {
	        //window.alert('Geocoder failed due to: ' + status);
	      }
	    });
	}
}

function measure(lat1, lon1, lat2, lon2){  // generally used geo measurement function
    var R = 6378.137; // Radius of earth in KM
    var dLat = lat2 * Math.PI / 180 - lat1 * Math.PI / 180;
    var dLon = lon2 * Math.PI / 180 - lon1 * Math.PI / 180;
    var a = Math.sin(dLat/2) * Math.sin(dLat/2) +
    Math.cos(lat1 * Math.PI / 180) * Math.cos(lat2 * Math.PI / 180) *
    Math.sin(dLon/2) * Math.sin(dLon/2);
    var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
    var d = R * c;
    return d * 1000; // meters
}
