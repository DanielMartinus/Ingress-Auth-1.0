// JavaScript Document mobileauth.js
//alert("!!");

	
if(window.location=="https://www.ingress.com/intel/?vp=f"){

	var x = document.getElementById("header_email");
	var email = ( x.innerHTML );	
	// REPLACE ORIG SITE ///////////////////////////////////////////////////
	if(document.getElementsByTagName('html')[0].getAttribute('itemscope') != null)
	  throw('Ingress Intel Website is down, not a userscript issue.');

	// disable vanilla JS
	window.onload = function() {};
	document.body.onload = function() {};

	// rescue user data from original page
	var scr = document.getElementsByTagName('script');
	for(var x in scr) {
	  var s = scr[x];
	  if(s.src) continue;
	  if(s.type !== 'text/javascript') continue;
	  var d = s.innerHTML.split('\n');
	  break;
	}

	//alert("on intel!");


	if(!d) {
	  // page doesn’t have a script tag with player information.
	  if(document.getElementById('header_email')) {
		// however, we are logged in.
		// it used to be regularly common to get temporary 'account not enabled' messages from the intel site.
		// however, this is no longer common. more common is users getting account suspended/banned - and this
		// currently shows the 'not enabled' message. so it's safer to not repeatedly reload in this case
	//    setTimeout('location.reload();', 3*1000);
		alert("Page doesn't have player data, but you are logged in.");
	  }
	  // FIXME: handle nia takedown in progress
	  alert("Couldn't retrieve player data. Are you logged in?");
	}
	
	//CEREBROEMAIL = CEREBROEMAIL.innerHTML;
	
	
	
	for(var i = 0; i < d.length; i++) {
	  if(!d[i].match('var PLAYER = ')) continue;
	  eval(d[i].match(/^var /, 'window.'));
	  break;
	}
	
	
	document.getElementsByTagName('body')[0].innerHTML = '<div a href="http://cerebro.botnyx.com/r/closeInAppBrowser.html"> done </div>';
	window.tt5 = function(){
            console.log("T10 timer function (1 minute)");
			window.location = "https://cerebro.botnyx.com/r/closeInAppBrowser.html";
        }
	window.setInterval(window.tt5,  5000);	
	
	
	// player information is now available in a hash like this:
	// window.PLAYER = {"ap": "123", "energy": 123, "available_invites": 123, "nickname": "somenick", "team": "ENLIGHTENED||RESISTANCE"};
	
	window.PLAYER.email = email;
	/*alert(window.PLAYER.ap);
	alert(window.PLAYER.energy);
	alert(window.PLAYER.available_invites);
	alert(window.PLAYER.nickname);
	alert(window.PLAYER.team);
	alert(window.PLAYER.email);
	*/
	
  
}