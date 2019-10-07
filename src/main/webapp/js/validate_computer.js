
const today = Date.now()
// Birth day of the first programmable computer
const earliest = new Date(1938, 1, 1)
const date_range_err = "Date cannot be later than today or earlier than 1938-01-01"

function check_date(introducedStr, discontinuedStr){
  
  let introduced, discontinued
  
  if (introducedStr) {
    introduced = new Date(introducedStr)
    if (introduced > today || introduced < earliest) {
      alert(date_range_err)
      return false
    }
  }
  
  if (discontinuedStr) {
    discontinued = new Date(discontinuedStr)
    if (discontinued > today || discontinued < earliest) {
      alert(date_range_err)
      return false
    }
    if (introduced && introduced > discontinued) {
      alert("Introduced date cannot be later than discontinued date")
      return false
    }
  }
  return true
}

function validate() {

	// const id = $("input#id").val()
	const name = $("input#computerName").val().trim()
	const introduced = $("input#introduced").val().trim()
	const discontinued = $("input#discontinued").val().trim()
	// const companyId = $("option:selected").val().trim()

	// Name cannot be empyt string
	if (!name) {
		alert("Name cannot be filled with empty characters!")
		return false
	}
	
	return check_date(introduced, discontinued)
}

// Attach a submit handler to the form
$("#edit-computer-form").submit(event => {

	// Validate the form
	if (!validate()) {
		console.log("Form validation failed")
		return
	} else {
	  console.log("Form validation passed")
	}

	// Stop form from submitting normally
	event.preventDefault();

	const id = $("input#id").val().trim();
	const name = $("input#computerName").val().trim()
	const introduced = $("input#introduced").val().trim()
	const discontinued = $("input#discontinued").val().trim()
	const companyId = $("option:selected").val().trim()

	const dataToPost = {
		id,
		name,
		introduced,
		discontinued,
	 	companyId
	}
	
	console.log(JSON.stringify(dataToPost))
	console.log("************************")
	console.log(dataToPost)

	$.post("edit-computer", JSON.stringify(dataToPost)).done(data => {
	  alert("post success", data)
	}).fail(error => {
	  alert("post failure", error)
	})
});