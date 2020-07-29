let application = document.getElementById("application").value;

$(":submit").click(function checkAndAlert() {
    let salt = document.getElementsByName("salt")[0];
    let pass1 = document.getElementsByName("password")[0];
    let pass2 = document.getElementsByName("password2")[0];
    let email = document.getElementsByName("email")[0];
    let username = document.getElementsByName("username")[0];
    let codeInput = document.getElementById("Code");
    let xmlhttp = new XMLHttpRequest();
    let usernamePattern = /^[a-zA-Z0-9_]{4,15}$/g;
    let emailPattern = /^[A-Za-z\d]+([-_.][A-Za-z\d]+)*@([A-Za-z\d]+[-.])+[A-Za-z\d]{2,4}$/g;
    let passwordPattern =/^[0-9A-Za-z#@!~%^&*]{6,12}$/g;
    if(!usernamePattern.test(username.value)){
        username.setCustomValidity("非法用户名,长度应在4-15之间");
    }
    else{
        username.setCustomValidity("");
    }

    let name = username.value;
    let result;
    xmlhttp.onreadystatechange=function()
    {
        if (xmlhttp.readyState===4 && xmlhttp.status===200)
        {
            result = xmlhttp.responseText;
            if(result === "repeat"){
                //alert("用户名重复");
                username.setCustomValidity("用户名重复！");
            }else{
                username.setCustomValidity("");
            }
        }
    };
    xmlhttp.open("POST",application+"/checkName",false);
    xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xmlhttp.send("method=name&name="+name);

    if(result === "repeat"){
        return;
    }


    if(!emailPattern.test(email.value)){
        email.setCustomValidity("邮箱无效！");
    }
    else{
        email.setCustomValidity("");
    }
    if(!passwordPattern.test(pass1.value)){
        pass1.setCustomValidity("密码长度应为6-12，可包含数字、字母、特殊字符");
    }
    else{
        pass1.setCustomValidity("");
    }
    if(pass1.value !== pass2.value)
        pass2.setCustomValidity("两次输入的密码不匹配");
    else{
        pass2.setCustomValidity("");
    }
    let code = codeInput.value;
    let result2;
    xmlhttp.onreadystatechange=function()
    {
        if (xmlhttp.readyState===4 && xmlhttp.status===200)
        {
            result2 = xmlhttp.responseText;
            if(result2 === "wrong"){
                codeInput.setCustomValidity("验证码错误！");
            }else{
                codeInput.setCustomValidity("");
            }
        }
    };
    xmlhttp.open("POST",application+"/checkName",false);
    xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xmlhttp.send("method=code&code="+code);
    if(result2 === "wrong"){
        return;
    }
    salt.value = getRandom();
    pass1.value = md5(pass2.value + salt.value);
    pass2.value = md5(pass2.value + salt.value);
});

function getRandom() {
    let str = "";
    let k="1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    //添加10个不同的随机字符串
    for(let i=0;i<10;i++){
        let number = Math.round(Math.random()*k.length);
        str += k.charAt(number);
    }
    return str;
}

$("#password").blur(function showPasswordStrength() {
    $("[role='progressbar']").attr("class","progress-bar");
    let weakPattern1 = /^[0-9]{6,12}$/g;
    let weakPattern2 = /^[a-zA-Z]{6,12}$/g;
    let weakPattern3 = /^[#@!~%^&*]{6,12}$/g;
    let normalPattern = /((?=.*[a-z])(?=.*\d)|(?=[a-z])(?=.*[#@!~%^&*])|(?=.*\d)(?=.*[#@!~%^&*]))[a-z\d#@!~%^&*]{6,12}/g;
    let strongPattern = /(?=.*[a-zA-Z])(?=.*\d)(?=.*[#@!~%^&*])[a-zA-Z\d#@!~%^&*]{6,12}/g;
    let password = document.getElementsByName("password")[0].value;
    if(strongPattern.test(password)){
        $("[role='progressbar']").addClass("progress-bar-success");
        $("[role='progressbar']").css("width","100%");
        $("[role='progressbar']").text("Strong");
    }else if(normalPattern.test(password)){
        $("[role='progressbar']").addClass("progress-bar-info");
        $("[role='progressbar']").css("width","60%");
        $("[role='progressbar']").text("Safe");
    }else if(weakPattern1.test(password)||weakPattern2.test(password)||weakPattern3.test(password)){
        $("[role='progressbar']").addClass("progress-bar-warning");
        $("[role='progressbar']").css("width","20%");
        $("[role='progressbar']").text("Weak");
    }else{
        $("[role='progressbar']").addClass("progress-bar-danger");
        $("[role='progressbar']").css("width","100%");
        $("[role='progressbar']").text("!!Invalid!!");
    }
});