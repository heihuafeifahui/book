function post(form, url, data) {
	//$.post()方法通过 HTTP POST 方式向服务器发送请求并获取返回的数据。是 $.ajax()的简化版。
	$.post(url, data, function(json) {
            var result = 1;
            //遍历json，field为对象的成员或数组索引,message为变量或内容
            $.each(json, function(field, message) {
            //点击返回值不为tourl时
            	if (field != "tourl") {
                    result = 2;
                    //var name = "[data-error='" + field + "']";
                    //
					var name = form.find("[data-error='" + field + "']");
                    if (name.length > 0) {
                    	//html() 方法返回或设置被选元素的内容 (inner HTML)。
                        name.html(message);
                    }
                }
            });
            //console.log(json.tourl);
            //结果为 1(没有错误)和返回的 返回的数据不为空时
            if (result == 1 && json.tourl != "")
                window.location.href = json.tourl;
            //window.location.reload();
        }, "json");
	}


    /** 异步表单提交 **/
    $(document).on("click", "[name='submit']", function(e) {
		var form = $(this).parents("form");
        var url = form.attr("action");
        var data = form.serialize();
        $("[data-error]").html("");
        post(form, url, data);
    });



