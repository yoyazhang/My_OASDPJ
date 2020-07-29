let application = document.getElementById("info").value;
let lastURL = window.location.href;
$(".removeFavor").click(function () {
    let id = $(this).attr("alt");
    let xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange=function()
    {
        if (xmlhttp.readyState===4 && xmlhttp.status===200)
        {
            alert("取消收藏成功");
            location.replace(lastURL);
        }
    };
    xmlhttp.open("POST",application+"/arrangeFavor",true);
    xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xmlhttp.send("method=remove&id="+id);
});
$(".allowSee").click(function () {
    $(this).attr("src",application+"/resources/images/icons/view_off.png");
    $(this).attr("class","notAllowSee");
    let xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange=function()
    {
        if (xmlhttp.readyState===4 && xmlhttp.status===200)
        {
            alert("Change to private");
            location.replace(lastURL);
        }
    };
    xmlhttp.open("POST",application+"/arrangeFavor",true);
    xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xmlhttp.send("method=change&view=off");
});
$(".notAllowSee").click(function () {
    $(this).attr("src",application+"/resources/images/icons/view.png");
    $(this).attr("class","allowSee");
    let xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange=function()
    {
        if (xmlhttp.readyState===4 && xmlhttp.status===200)
        {
            alert("Change to Open!");
            location.replace(lastURL);
        }
    };
    xmlhttp.open("POST",application+"/arrangeFavor",true);
    xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xmlhttp.send("method=change&view=open");
});