function validate() {

	// const id = $("input#id").val()
	const name = $("input#computerName").val().trim()
	const introduced = $("input#introduced").val().trim()
	const discontinued = $("input#discontinued").val().trim()
	// const companyId = $("option:selected").val().trim()

	// Name cannot be empyt string
	if (!name.length) {
		alert("Name cannot be filled with empty characters!")
		return false
	}

	let introducedDate;
	let discontinuedDate;
	const introducedExists = introduced.length > 0;
	const discontinuedExists = discontinued.length > 0;
	const today = Date.now();

	// Introduced date cannot be in the future !
	if (introducedExists && (introducedDate = new Date(introduced)) > today) {
		alert("Introduced Date cannot be in the future!")
		return false
	}

	// Discontinued date can be in the future, but cannot be earlier
	// than today
	if (discontinuedExists) {
		if ((discontinuedDate = new Date(discontinued)) > today) {
			alert("Discontinued Date cannot be in the future!")
			return false
		}

		if (introducedExists && introducedDate > discontinuedDate) {
			alert("Discontinued Date cannot be later than the introduced date!")
			return false
		}

	}

	console.log("Form validation pass")
	
	return true
	
}

// Attach a submit handler to the form
$("#edit-computer-form").submit( event => {

	if (!validate()) {
		console.log("Form validation failed")
		return
	}
	
    // Stop form from submitting normally
    event.preventDefault();

    // Get some values from elements on the page:
    const $form = $( this );

    // We want to customize what we post, therefore we format our data
// var data = "login="+ $('#login').val() +"&passwordHash=" +
// CryptoJS.MD5($('#password').val());

    
    const name = $("input#computerName").val().trim()
	const introduced = $("input#introduced").val().trim()
	const discontinued = $("input#discontinued").val().trim()
    
	const data = {name, introduced, discontinued}
	
    
    // For debugging purposes... see your console:
    // Prints out for example:
	// login=myLoginName&passwordHash=a011a78a0c8d9e4f0038a5032d7668ab
    console.log(data);

    $.redirect('edit-computer', {'arg1': 'value1', 'arg2': 'value2'});
    
//    $.ajax({
//    	type: "POST",
//    	url: "edit-computer",
//    	data
//    })
    
    
//    $.post("edit-computer", data)
    
// // The actual from POST method
// $.ajax({
// type: $form.attr('method'),
// url: $form.attr('action'),
// data: data,
// success: data => {
// console.log("Hey, we got reply form java side, with following data: ");
// }
// });

});   
