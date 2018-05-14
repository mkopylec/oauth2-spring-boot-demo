var accessToken = null;
var authorizationData = null;

fetch('/client/implicit/authorization-data')
    .then(function (response) {
        return response.json();
    })
    .then(function (json) {
        console.log(json);
        authorizationData = json;
    });

function authorize() {
    window.location.replace('')
}