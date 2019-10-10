// Attach a submit handler to the form
$("#edit-computer-form").submit(event => {
  
  // Stop form from submitting normally
  event.preventDefault();
  
  const id = $("input#id").val().trim();
  const name = $("input#computerName").val().trim()
  const introduced = $("input#introduced").val().trim()
  const discontinued = $("input#discontinued").val().trim()
  const companyId = $("option:selected").val().trim()
  
	// Validate the form
	if (!validate(name, introduced, discontinued)) {
		console.log("Form validation failed")
		return
	} else {
	  console.log("Form validation passed")
	}

	const dataToPost = {
		id,
		name,
		introduced,
		discontinued,
	 	companyId
	}
	
	$.post("edit-computer", JSON.stringify(dataToPost)).done(data => {
	  alert("Success", data)
	  window.location.replace(`dashboard`)
	}).fail(error => {
	  alert("Failure", error)
	  window.location.replace(`dashboard`)
	})
});