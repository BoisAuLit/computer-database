function changeSize(size) {
 window.location.replace(`dashboard?limit=${ parseInt(size) }&offset=0`);
}


function changePage(limit, offset) {
  const nameToFind = $('input[type="search"]').val().trim()
  window.location.replace(`dashboard?nameToFind=${nameToFind}&limit=${limit}&offset=${offset}`)
}

$("#searchForm").submit(event => {

  // Stop form from submitting normally
  event.preventDefault();

  const size = $("button.active").text()
  const nameToFind = $('input[type="search"]').val().trim()
  
  window.location.replace(`dashboard?nameToFind=${nameToFind}&limit=${size}&offset=0`)
});
