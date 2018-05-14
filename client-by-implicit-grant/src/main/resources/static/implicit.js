var accessToken = null;

function authorize() {
    window.location.replace('http://localhost:8080/oauth/token?response_type=token&client_id=client-application&redirect_uri=http://127.0.0.1:8086/client/implicit')
}