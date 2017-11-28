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

var countdown = 60;
$(function(){
  $(document).on("click", "#getVerifyBtn", function(e) {
    var form = $(this).parents("form");
    var url = $(this).data("url");
    setVerifyBtnDown();
    var account = $("#account").val();
      $.post(url, {account:account}, function(json) {
            var result = 1;
            $.each(json, function(field, message) {
                if (field != "tourl") {
                    result = 2;
                    //var name = "[data-error='" + field + "']";
          var name = form.find("[data-error='" + field + "']");
                    if (name.length > 0) {
                        name.html(message);
                    } else {
                        Messenger().post({
                            message: message,
                            type: 'error',
                            hideAfter: 2,
                            showCloseButton: true
                        });
                    }
                }
            });
            //console.log(json.tourl);
            if (result == 1 && json.tourl != "")
                window.location.href = json.tourl;
            //window.location.reload();
        }, "json");
  });
});
