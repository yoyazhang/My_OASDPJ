let file = document.getElementById('file');
let image = document.getElementsByClassName("PicFromUser")[0];

file.onchange = function() {
    let fileData = this.files[0];//获取到一个FileList对象中的第一个文件( File 对象),是我们上传的文件
    let pettern = /^image/;

    console.info(fileData.type);

    if (!pettern.test(fileData.type)) {
        alert("图片格式不正确");
        return;
    }
    let reader = new FileReader();
    reader.readAsDataURL(fileData);//异步读取文件内容，结果用data:url的字符串形式表示
    /*当读取操作成功完成时调用*/
    reader.onload = function(e) {
        console.log(e); //查看对象
        console.log(this.result);//要的数据 这里的this指向FileReader（）对象的实例reader
        image.setAttribute("src", this.result);
    };
    document.getElementById("placeholder").style.display = "none";
};
$(".modifyConfirm").click(function () {
    let title = document.getElementsByName("UploadPhotoTitle")[0];
    let content = document.getElementsByName("content")[0];

    if(!window.confirm("Are you sure all information is correct?")){
        return;
    }
    if(title.value ===""){
        alert("The title cannot be EMPTY!");
        return;
    }
    if(content.value ===""){
       alert("The content cannot be EMPTY!");
       return;
    }
    if(title.value !=="" && content.value !== ""){
        this.form.submit();
    }
});
$(".uploadConfirm").click(function () {
    let title = document.getElementsByName("UploadPhotoTitle")[0];
    let content = document.getElementsByName("content")[0];

    if(!window.confirm("Are you sure all information is correct?")){
        return;
    }
    if(file.value === ""){
        alert("The photo uploaded cannot be EMPTY!");
        return;
    }
    if(title.value ===""){
        alert("The title cannot be EMPTY!");
        return;
    }
    if(content.value ==="") {
        alert("The content cannot be EMPTY!");
        return;
    }
    if(file.value !== "" && title.value !=="" && content.value !== ""){
        this.form.submit();
    }
});