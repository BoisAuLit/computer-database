function validate() {

	console.log("cousin")

	// const id = $("input#id").val()
	const name = $("input#computerName").val().trim()
	const introduced = $("input#introduced").val().trim()
	const discontinued = $("input#discontinued").val().trim()
	// const companyId = $("option:selected").val().trim()

	// Name cannot be empyt string
	if (!name.length) {
		alert("Name cannot be filled with empty characters!")
	}

	let introducedDate;
	let discontinuedDate;
	const introducedExists = introduced.length > 0;
	const discontinuedExists = discontinued.length > 0;
	const today = Date.now();

	// Introduced date cannot be in the future !
	if (introducedExists && (introducedDate = new Date(introduced)) > today) {
		alert("Introduced Date cannot be in the future!")
	}

	// Discontinued date can be in the future, but cannot be earlier
	// than today
	if (discontinuedExists) {
		if ((discontinuedDate = new Date(discontinued)) > today) {
			alert("Discontinued Date cannot be in the future!")
		}

		if (introducedExists && introducedDate > discontinuedDate) {
			alert("Discontinued Date cannot be later than the introduced date!")
		}

	}

	console.log("pass")

}