function deleteComputer() {
  
  const ids = []
  // $("input:checkbox[name=cb]:checked").each(function() {
  $("input:checkbox[name=cb]:checked").each(function() {
    ids.push($(this).val());
  });
  
  if (!ids.length) {
    alert("At lease select something!")
    return
  }

  if (!confirm("Are you sure to delete these computers?")) {
    return
  }
  
  $.ajax({
    url: "delete-computer",
    type: "POST",
    data: JSON.stringify(ids),
    contentType:"application/json; charset=utf-8",
    success: () => {
      alert("Success")
      window.location.replace(`dashboard`)
    },
    error: () => {
      alert("Failure")
      window.location.replace(`dashboard`)
    }
  })
  
//  $.post("delete-computer", JSON.stringify(ids)).done(data => {
//    alert("Success", data)
////    window.location.replace(`dashboard`)
//  }).fail(error => {
//    alert("Failure", error)
////    window.location.replace(`dashboard`)
//  })
}