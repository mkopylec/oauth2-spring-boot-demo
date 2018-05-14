function showElement(id) {
    document.getElementById(id).style.display = 'block';
}

function hideElement(id) {
    document.getElementById(id).style.display = 'none';
}

function setElementHtml(id, html) {
    document.getElementById(id).innerHTML = html;
}

function getAccessToken() {
    var matches = location.hash.match(new RegExp('access_token=([^&]*)'));
    return matches ? matches[1] : null;
}

function authorize() {
    location.replace('http://localhost:8080/oauth/authorize?response_type=token&client_id=client-application&redirect_uri=http://127.0.0.1:8086/client/implicit')
}

var accessToken = getAccessToken();

if (!accessToken) {
    authorize();
}

function getAuthorizationHeader() {
    var headers = new Headers();
    headers.set('Authorization', 'Bearer ' + accessToken);
    return headers;
}

function requestPublicResource() {
    requestResource('http://localhost:8081/resource/public', new Headers());
}

function requestProtectedResource() {
    requestResource('http://localhost:8081/resource/protected', getAuthorizationHeader());
}

function requestProtectedResourceError() {
    requestResource('http://localhost:8081/resource/protected', new Headers());
}

function requestScopeProtectedResource() {
    requestResource('http://localhost:8081/resource/protected/scope', getAuthorizationHeader());
}

function requestInvalidScopeProtectedResource() {
    requestResource('http://localhost:8081/resource/protected/scope/invalid', getAuthorizationHeader());
}

function requestAuthorityScopeProtectedResource() {
    requestResource('http://localhost:8081/resource/protected/authority', getAuthorizationHeader());
}

function requestInvalidAuthorityProtectedResource() {
    requestResource('http://localhost:8081/resource/protected/authority/invalid', getAuthorizationHeader());
}

function requestResource(url, headers) {
    fetch(url, {headers: headers})
        .then(function (response) {
            if (response.status !== 200) {
                throw response;
            }
            return response.text();
        })
        .then(function (body) {
            setElementHtml('response', 'Requested resource: <b >' + body + '</b>.');
            hideElement('no-response');
            showElement('response');
            hideElement('error');
        })
        .catch(function (response) {
            response.text().then(function (body) {
                if (body.includes('invalid_token')) {
                    authorize();
                } else {
                    setElementHtml('error', 'Requesting resource failed: <b >' + body + '</b>.');
                    hideElement('no-response');
                    hideElement('response');
                    showElement('error');
                }
            });
        });
}