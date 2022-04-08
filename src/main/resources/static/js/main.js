
var singleUploadForm = document.querySelector('#singleUploadForm');
var singleFileUploadInput = document.querySelector('#singleFileUploadInput');
var singleFileUploadError = document.querySelector('#singleFileUploadError');
var singleFileUploadSuccess = document.querySelector('#singleFileUploadSuccess');
const resultContainer = document.querySelector('.result-container');
const getPersonsBtn = document.querySelector('.get-persons');


function uploadSingleFile(file) {
    var formData = new FormData();
    formData.append("file", file);

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/csvreader/upload");

    xhr.onload = function() {
        console.log(xhr.responseText);
        var response = JSON.parse(xhr.responseText);
        if(xhr.status === 200) {
            singleFileUploadError.style.display = "none";
            singleFileUploadSuccess.innerHTML = "<p>Uspješno učitana daoteka!</p>";
            singleFileUploadSuccess.style.display = "block";
        } else {
            singleFileUploadSuccess.style.display = "none";
            singleFileUploadError.innerHTML = "<p>"+(response && response.message)+"<p>" || "Some Error Occurred";
            singleFileUploadSuccess.style.display = "block";

        }
    }

    xhr.send(formData);
}
function getPersons(){
    fetch('http://localhost:8080/csvreader/persons')
        .then(function (response){
            return response.json();
        })
        .then(function (persons){
            persons.forEach(function (person){
                const div = document.createElement('div');
                div.innerHTML = `${person.firstName} ${person.lastName}, ${person.dateOfBirth}`;
                resultContainer.appendChild(div);
            })

        });
}


getPersonsBtn.addEventListener('click', function (){
    resultContainer.innerHTML = '';
    getPersons();
});



singleUploadForm.addEventListener('submit', function(event){
    var files = singleFileUploadInput.files;
    if(files.length === 0) {
        singleFileUploadError.innerHTML = "Please select a file";
        singleFileUploadError.style.display = "block";
    }
    uploadSingleFile(files[0]);
    event.preventDefault();
}, true);