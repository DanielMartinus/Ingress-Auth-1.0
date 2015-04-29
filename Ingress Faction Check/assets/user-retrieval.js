if (window.location == "https://www.ingress.com/intel/?vp=f") {

	//alert('a');
	var elements = document.getElementsByClassName("button_link");
	 
	 for (var i = 0; i < elements.length; i++) {
	 	var text = elements[i].innerHTML;
		//alert('in loop');
	 	if(text == 'Sign in') {
		loadUrl(elements[i].href);
	 }
	}
	var x = document.getElementById("header_email");
	var email = (x.innerHTML);

	if (document.getElementsByTagName('html')[0].getAttribute('itemscope') != null)
		throw ('Ingress Intel Website is down, not a userscript issue.');

	window.onload = function() {
	};
	document.body.onload = function() {
	};
	
	var scr = document.getElementsByTagName('script');
	for ( var x in scr) {
		var s = scr[x];
		if (s.src)
			continue;
		if (s.type !== 'text/javascript')
			continue;
		var d = s.innerHTML.split('\n');
		break;
	}

	if (!d) {
		if (document.getElementById('header_email')) {
			alert("Page doesn't have player data, but you are logged in.");
		}
		alert("Couldn't retrieve player data. Are you logged in?");
	}

	for (var i = 0; i < d.length; i++) {
		if (!d[i].match('var PLAYER = '))
			continue;
		eval(d[i].match(/^var /, 'window.'));
		break;
	}

	var agent = window.PLAYER.nickname;
	var team = window.PLAYER.team;
	var email = document.getElementById('header_email').innerHTML;
	var ap = window.PLAYER.ap;
	var energy = window.PLAYER.energy;
	var invites = window.PLAYER.available_invites;
	androidResponse(agent, team, email, ap, energy, invites);void(0);
}

function loadUrl(url) {
	window.JSInterface.loadUrl(url);
}

function androidResponse(agent, faction, email, ap, energy, invites) {
	window.JSInterface.sendToAndroid(agent, faction, email, ap, energy, invites);
}
