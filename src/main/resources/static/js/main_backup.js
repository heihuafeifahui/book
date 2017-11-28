$(function() {
    
});

refresh = function(url) {
    $("#vcodeImg").attr("src", "/reg_vericode.jpg?" + Math.random());
}

$('#agree').click(function() {
    $('input[name="agree"]').prop("checked") ? Check() : unCheck();
});

unCheck = function() {
    $("#btn_register").attr('disabled', true);
    $("#btn_register").html('还未同意服务条款');
}

Check = function() {
    $("#btn_register").attr('disabled', false);
    $("#btn_register").html('确定注册');
}
