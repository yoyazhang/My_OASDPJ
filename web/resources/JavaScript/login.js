let application = document.getElementById("info").value;
$(":submit").click(function() {
    let username = document.getElementsByName("usernameOrEmail")[0];
    let password = document.getElementsByName("password")[0];
    let verifyCode = document.getElementsByName("verifyCode")[0];
    let loginForm = document.getElementById("loginForm");
    if(password.value === ""){
        password.setCustomValidity("Password cannot be empty");
        return;
    }else{
        password.setCustomValidity("");
    }
    let salt;
    let xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange=function()
    {
        if (xmlhttp.readyState===4 && xmlhttp.status===200)
        {
            salt = xmlhttp.responseText;
            password.value = md5(password.value+salt);
        }
    };
    xmlhttp.open("POST",application+"/getSalt",false);
    xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xmlhttp.send("username="+username.value);
});

