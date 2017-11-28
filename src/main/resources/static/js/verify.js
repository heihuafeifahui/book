var countdown = 60;
$("#getVerifyBtn").click(function() {
    var account = $("#account").val();
    if (isEmail(account) || isPhone(account)) {
    	Messenger().post({
  		  message: '正在请求,请稍等',
  		  type: 'info',
  		  hideAfter:2,
  		  showCloseButton: true
  		});
    	setVerifyBtnDown();
        $.ajax({
            type: "post",
            url: "/register/vcode/send",
            data: "account=" + account,
            dataType: "json",
            success: function(data) {
            	if (typeof (data.tip) == 'string') {
            		Messenger().post({
          			  message: data.tip,
          			  type: 'error',
          			  hideAfter:2,
          			  showCloseButton: true
          			});
            	} else {
            		Messenger().post({
          			  message: '发送成功,请查收',
          			  type: 'success',
          			  hideAfter:2,
          			  showCloseButton: true
          			});
            	}
            },
            error: function() {
            	Messenger().post({
        			  message: '发送失败',
        			  type: 'error',
        			  hideAfter:2,
        			  showCloseButton: true
        			});
            }
        });
    } else {Messenger().post({
		  message: '用户名格式有误,请检查',
		  type: 'error',
		  hideAfter:2,
		  showCloseButton: true
		});
    }
});

Check = function() {
    $("#btn_register").attr('disabled', false);
    $("#btn_register").html('确定注册');
}

setVerifyBtnDown = function() {
    if (countdown == 0) {
        $("#getVerifyBtn").attr('disabled', false);
        $("#getVerifyBtn").html("点击获取激活码");
        countdown = 60;
        return;
    } else {
        $("#getVerifyBtn").attr('disabled', true);
        $("#getVerifyBtn").html("重新发送(" + countdown + ")");
        countdown--;
    }
    setTimeout(function() {
        setVerifyBtnDown();
    },
    1000);
}

isEmail = function(mail) {
    var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    var result;
    filter.test(mail) ? result = true: result = false
    return result;
}

isPhone = function(phone) {
    var filter = /^1\d{10}$/
    var result;
    filter.test(phone) ? result = true: result = false
    return result;
}

resetSendBtn = function() {
    $("#getVerifyBtn").html("点击获取激活码");
}