layui.use(['form','layer','upload'], function(){
    $ = layui.jquery;
    var form = layui.form
        ,layer = layui.layer
        ,laydate = layui.laydate;

    //日期
    laydate.render({
        elem: '#expireDate'
        , type: 'datetime'
        ,trigger: 'click'
    });

    $('#close').click(function(){
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    });

    // 表单验证
    form.verify({
        projectName: function(value, item){ //value：表单的值、item：表单的DOM对象
            if(!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)){
                return '用户名不能有特殊字符';
            }
            if(/(^\_)|(\__)|(\_+$)/.test(value)){
                return '用户名首尾不能出现下划线\'_\'';
            }
            if(/^\d+\d+\d$/.test(value)){
                return '用户名不能全为数字';
            }
        }

        //我们既支持上述函数式的方式，也支持下述数组的形式
        //数组的两个值分别代表：[正则匹配、匹配不符时的提示文字]
        ,pass: [
            /^[\S]{6,12}$/
            ,'密码必须6到12位，且不能出现空格'
        ]
    });
    // 监听提交
    form.on('submit(edit)', function(data){
        $.ajax({
            url: '/license/update',
            type:'post',
            dateType: 'json',
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify(data.field),
            success:function(res){
                var index = parent.layer.getFrameIndex(window.name);
                if(res.code == 200){
                    parent.layer.close(index);
                    form.val("lincense", {
                        "projectName": ""
                        ,"expireDate": ""
                        ,"token": ""

                    });
                    window.parent.layui.table.reload('licenseTableId',{
                        page: {
                            curr: 1
                        },
                        where:{
                            param: form.val("lincense")
                        }
                    });
                    // window.top.layer.msg(d.msg,{icon:6,offset: 'rb',area:['200px','80px'],anim:2});
                    window.top.layer.msg(res.msg,{icon:6});

                }else{
                    layer.msg(res.msg,{icon:5});
                }
            },error:function(e){
                layer.alert("发生错误", {icon: 6},function () {
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                });
            }
        });
        return false;
    });
    form.render();
});
