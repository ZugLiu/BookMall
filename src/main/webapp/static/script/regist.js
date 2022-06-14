function $(id){
    return document.getElementById(id);
}

function preRegist(){
    var unameTxt = $("unameTxt");
    var pwdTxt = $("pwd");
    var pwdConfirmTxt = $("pwdConfirm");
    var emailTxt = $("email");

    //用户名不能为空，且是6-16位数字和字母组成
    var unameReg = /[0-9a-zA-Z]{6,16}/;
    if(!unameReg.test(unameTxt.value)){
        $("unameSpan").style.visibility = "visible";
        return false;
    }else{
        $("unameSpan").style.visibility = "hidden";
    }

    // 密码长度至少为8位，且必须是数字和字母的组合
    var pwdReg = /^[a-zA-Z\d_\-@#&*]{8,}$/;
    if(!pwdReg.test(pwdTxt.value)){
        $("pwdSpan").style.visibility = "visible";
        return false;
    }else{
        $("pwdSpan").style.visibility = "hidden";
    }

    // 两次密码必须要一致
    if(pwdTxt.value !== pwdConfirmTxt.value){
        $("pwdConfirmSpan").style.visibility = "visible";
        return false;
    }else{
        $("pwdConfirmSpan").style.visibility = "hidden";
    }

    // 邮箱格式要正确
    var emailReg = /\b[\w.%+-]+@[\w.-]+\.[a-zA-Z]{2,6}\b/g;
    if(!emailReg.test(emailTxt.value)){
        $("emailSpan").style.visibility = "visible";
        return false;
    }else{
        $("emailSpan").style.visibility = "hidden";
    }

    return true;
}