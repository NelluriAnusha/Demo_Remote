//Getting GitHub User details from webserver using XMLHttpRequest
function getGithubInfo(user) {

    //Instantiating a new instance of an XMLHttpRequest
    const xHttpReq = new XMLHttpRequest()
    //dynamically passing specified username to GitHub
    const user_url = `https://api.github.com/users/${user}`

    //opening a new XMLHttpRequest connection, using a GET request with user_url
    xHttpReq.open('GET',user_url,true);

    // Once the request has been received, we need to execute the following steps
    xHttpReq.onload = () => {
        if(xHttpReq.status == 200) {
            //parsing API user data into JSON
            const user_data = JSON.parse(xHttpReq.response)
            showUser(user_data)
        } else{
            noSuchUser(username)
        }
    }

    // Sending our request to GitHub(server) using xHttpReq.send()
    xHttpReq.send();

}

//When the Information found on the server, the following function will be called.
function showUser(user) {
    //2. set the contents of the h2 and the two div elements in the div '#profile' with the user content

    document.getElementById('nameOfTheUser').innerText = user.login;
    document.getElementById('userBiodata').innerText = user.bio;
    document.getElementById('IDofUser').innerText = user.id;
    document.getElementById('githubimage').src = user.avatar_url;
    document.getElementById('userLink').href = user.url;
    document.getElementById('userLink').innerText = user.url;
    document.getElementById('userFollowers').innerText = user.followers;
    document.getElementById('userFollowing').innerText = user.following;
    document.getElementById('userPublicRepos').innerText = user.public_repos;
    document.getElementById('userLocation').innerText = user.location;
}

//When the user info is not available on the server, the following function is called.
function noSuchUser(username) {
    //3. set the elements such that a suitable message is displayed
    window.alert("No user was found with name "+username);
    window.location.reload();
}

$(document).ready(function () {
    $(document).on('keypress', '#username', function (e) {
        //check if the enter(i.e return) key is pressed
        if (e.which == 13) {
            //get what the user enters
            username = $(this).val();
            //reset the text typed in the input
            $(this).val("");
            //get the user's information and store the response
            response = getGithubInfo(username);
            //if the response is successful show the user's details
            if (response.status == 200) {
                showUser(JSON.parse(response.responseText));
                //else display suitable message
            } else {
                noSuchUser(username);
            }
        }
    })
});
