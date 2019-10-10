// Attach a submit handler to the form
$("#add-computer-form").submit(event => {
  
  // Stop form from submitting normally
  event.preventDefault();
  
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
    name,
    introduced,
    discontinued,
    companyId
  }
  
  console.log('here we got it ! ', dataToPost)
  
  $.post("add-computer", JSON.stringify(dataToPost)).done(data => {
    alert("Success", data)
    window.location.replace(`dashboard`)
  }).fail(error => {
    alert("Failure", error)
    window.location.replace(`dashboard`)
  })
});