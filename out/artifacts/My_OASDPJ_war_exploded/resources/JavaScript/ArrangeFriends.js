let application = $("#application").val();
$(".reject").click(function () {
    let id = $(this).attr("alt");
    let xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange=function()
    {
        if (xmlhttp.readyState===4 && xmlhttp.status===200)
        {
            alert("You have rejected this user!");
            location.replace(location.href);
        }
    };
    xmlhttp.open("POST",application+"/myFriends",true);
    xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xmlhttp.send("method=reject&uid="+id);
});
$(".accept").click(function () {
    let id = $(this).attr("alt");
    let xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange=function()
    {
        if (xmlhttp.readyState===4 && xmlhttp.status===200)
        {
            alert("You are friends now!");
            location.replace(location.href);
        }
    };
    xmlhttp.open("POST",application+"/myFriends",true);
    xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xmlhttp.send("method=accept&uid="+id);
});
$(".send").click(function () {
    let id = $(this).attr("alt");
    let xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange=function()
    {
        if (xmlhttp.readyState===4 && xmlhttp.status===200)
        {
            alert("Request sent!");
            location.replace(location.href);
        }
    };
    xmlhttp.open("POST",application+"/myFriends",true);
    xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xmlhttp.send("method=send&uid="+id);
});
$(".cantSee").click(function (){
    alert("TA has set his or her favorites private!")
});
