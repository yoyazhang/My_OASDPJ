let application = $("#application").val();
$(".deletePic").click(function () {
    if(!window.confirm("Are you sure you want to delete this photo? All likes related will be removed too!")){
        return;
    }
    let id = $(this).attr("alt");
    let xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange=function()
    {
        if (xmlhttp.readyState===4 && xmlhttp.status===200)
        {
            alert("删除照片成功");
            location.replace(location.href);
        }
    };
    xmlhttp.open("POST",application+"/myPhoto",true);
    xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xmlhttp.send("method=delete&id="+id);
});

$(".modifyPic").click(function () {
    let id = $(this).attr("alt");
    location.replace(application+"/upload?id="+id);
});
