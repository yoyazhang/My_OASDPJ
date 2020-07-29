let str = location.href;
let lastURL = window.location.href;
let application = document.getElementById("info").value;
let addButton = document.getElementById("kudos");
let removeButton = document.getElementById("collected");
let loginButton = document.getElementById("noUidKudos");
let comments;
let uid = document.getElementById("uid");

function getID(){
    let hasID = false;
    let charList = [];
    for(let i = 0;i < str.length;i++){
        charList[i] = str.substring(i,i+1);
    }
    let index = 0;
    for(let i = 0;i < charList.length-2;i++){
        if(charList[i] === "i"&&charList[i+1]==="d"){
            hasID = true;
            index = i+3;
        }
    }
    if(hasID){
        return str.substring(index);
    }
}
if(removeButton){
    removeButton.onclick = function(){
        let xmlhttp = new XMLHttpRequest();
        xmlhttp.onreadystatechange=function()
        {
            if (xmlhttp.readyState===4 && xmlhttp.status===200)
            {
                alert("取消收藏成功！");
                location.replace(location.href);
            }
        };
        xmlhttp.open("GET",application+"/handleLike?way=remove&id="+getID(),true);
        xmlhttp.send();
    };
}
if(addButton){
    addButton.onclick = function(){
        let xmlhttp = new XMLHttpRequest();
        xmlhttp.onreadystatechange=function()
        {
            if (xmlhttp.readyState===4 && xmlhttp.status===200)
            {
                alert("收藏成功！");
                location.replace(location.href);
            }
        };
        xmlhttp.open("GET",application+"/handleLike?way=add&id="+getID(),true);
        xmlhttp.send();
    };
}
if(loginButton){
    loginButton.onclick = function () {
        location.replace(application+"/jspFiles/login.jsp?last="+getID());
    }
}
$(".like").bind("click",like);
$(".likeYet").bind("click",likeYet);

function like(){
    $(this).attr("src",application+"/resources/images/icons/已赞.png");
    $(this).attr("class","likeYet");
    $(".likeYet").bind("click",likeYet);
    let xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange=function()
    {
        if (xmlhttp.readyState===4 && xmlhttp.status===200)
        {
            location.replace(lastURL);
        }
    };
    xmlhttp.open("POST",application+"/picInfo",true);
    xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xmlhttp.send("method=like&commentID="+$(this).attr("alt")+"&id="+getID());
}

function deleteComment() {
    if(!window.confirm("Are you sure you want to delete this comment?")){
        return;
    }
    let xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange=function()
    {
        if (xmlhttp.readyState===4 && xmlhttp.status===200)
        {
            location.replace(lastURL);
        }
    };
    xmlhttp.open("POST",application+"/changeComment",true);
    xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xmlhttp.send("commentID="+$(this).attr("value")+"&ImageID="+getID());
}
function likeYet(){
    $(this).attr("src",application+"/resources/images/icons/赞.png");
    $(this).attr("class","like");
    $(".like").bind("click",like);
    let xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange=function()
    {
        if (xmlhttp.readyState===4 && xmlhttp.status===200)
        {

        }
    };
    xmlhttp.open("POST",application+"/picInfo",true);
    xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xmlhttp.send("method=removeLike&commentID="+$(this).attr("alt")+"&id="+getID());
}

$(".deleteComment").bind("click",deleteComment);
$(".notRankByHot").bind("click",changeCommentHot);
$(".notRankByTime").bind("click",changeCommentTime);
function changeCommentTime() {
    $(this).attr("src",application+"/resources/images/icons/时间-on.png");
    $(this).attr("class","rankByTime");
    $(".rankByHot").attr("src",application+"/resources/images/icons/热度.png");
    $(".rankByHot").attr("class","notRankByHot");
    $(".notRankByHot").bind("click",changeCommentHot);
    let xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange=function()
    {
        if (xmlhttp.readyState===4 && xmlhttp.status===200)
        {
            $(".comment").remove();
            comments = xmlhttp.response;
            let decodedComments = JSON.parse(comments);
            for(let i = 0;i < decodedComments.length;i++){
                let comment = document.createElement("div");
                comment.classList.add("comment");
                let h4Name = document.createElement("h4");
                h4Name.classList.add("name");
                h4Name.innerText = decodedComments[i].userName;
                let img = document.createElement("img");
                img.alt = decodedComments[i].commentId;
                if(decodedComments[i].statusOwn == "0"){
                    img.src = application+"/resources/images/icons/赞.png";
                    img.classList.add("like");
                }else if(decodedComments[i].statusOwn == "1"){
                    img.src = application+"/resources/images/icons/已赞.png";
                    img.classList.add("likeYet");
                }else{
                    img.src = application+"/resources/images/icons/赞.png";
                    img.classList.add("icon");
                }

                let pLikeNum = document.createElement("p");
                pLikeNum.classList.add("likeNum");
                pLikeNum.innerText = decodedComments[i].kudos;
                let pPicComment = document.createElement("p");
                pPicComment.classList.add("PicComment");
                pPicComment.innerText = decodedComments[i].concreteContent;

                if(decodedComments[i].uid == uid.value){
                    let deleteButton = document.createElement("button");
                    deleteButton.innerText = "delete";
                    deleteButton.classList.add("deleteComment");
                    deleteButton.value = decodedComments[i].commentId;
                    comment.append(h4Name,img,pLikeNum,deleteButton,pPicComment);
                }else{
                    comment.append(h4Name,img,pLikeNum,pPicComment);
                }
                $("#point").before(comment);
            }
        }
    };
    xmlhttp.open("GET",application+"/changeComment?rankMethod=RankByTime&id="+getID(),false);
    xmlhttp.send();
    $(".like").bind("click",like);
    $(".likeYet").bind("click",likeYet);
    $(".deleteComment").bind("click",deleteComment);
}
function changeCommentHot() {
    $(this).attr("src",application+"/resources/images/icons/热度-on.png");
    $(this).attr("class","rankByHot");
    $(".rankByTime").attr("src",application+"/resources/images/icons/时间.png");
    $(".rankByTime").attr("class","notRankByTime");
    $(".notRankByTime").bind("click",changeCommentTime);
    let xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange=function()
    {
        if (xmlhttp.readyState===4 && xmlhttp.status===200)
        {
            $(".comment").remove();
            comments = xmlhttp.response;
            let decodedComments = JSON.parse(comments);
            for(let i = 0;i < decodedComments.length;i++){
                let comment = document.createElement("div");
                comment.classList.add("comment");
                let h4Name = document.createElement("h4");
                h4Name.classList.add("name");
                h4Name.innerText = decodedComments[i].userName;
                let img = document.createElement("img");
                img.alt = decodedComments[i].commentId;
                if(decodedComments[i].statusOwn == "0"){
                    img.src = application+"/resources/images/icons/赞.png";
                    img.classList.add("like");
                }else if(decodedComments[i].statusOwn == "1"){
                    img.src = application+"/resources/images/icons/已赞.png";
                    img.classList.add("likeYet");
                }else{
                    img.src = application+"/resources/images/icons/赞.png";
                    img.classList.add("icon");
                }

                let pLikeNum = document.createElement("p");
                pLikeNum.classList.add("likeNum");
                pLikeNum.innerText = decodedComments[i].kudos;
                let pPicComment = document.createElement("p");
                pPicComment.classList.add("PicComment");
                pPicComment.innerText = decodedComments[i].concreteContent;

                if(decodedComments[i].uid == uid.value){
                    let deleteButton = document.createElement("button");
                    deleteButton.innerText = "delete";
                    deleteButton.classList.add("deleteComment");
                    deleteButton.value = decodedComments[i].commentId;
                    comment.append(h4Name,img,pLikeNum,deleteButton,pPicComment);
                }else{
                    comment.append(h4Name,img,pLikeNum,pPicComment);
                }
                $("#point").before(comment);
            }
        }
    };
    xmlhttp.open("GET",application+"/changeComment?rankMethod=RankByHot&id="+getID(),false);
    xmlhttp.send();
    $(".like").bind("click",like);
    $(".likeYet").bind("click",likeYet);
    $(".deleteComment").bind("click",deleteComment);
}