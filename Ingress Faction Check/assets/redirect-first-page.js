var elements = document.getElementsByClassName("button_link");

for (var i = 0; i < elements.length; i++) {
	var text = elements[i].innerHTML;
	if(text == 'Sign in') {
		elements[i].click();
	}
}
