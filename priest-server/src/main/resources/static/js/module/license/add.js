let $ = layui.$;

let obj = function (projectName, registerDate, expireDate, token) {
    this.projectName = projectName;
    this.registerDate =  registerDate;
    this.expireDate = expireDate;
    this.token = token;
};

let registerDate = new Date();
let expireDate = new Date().setDate(registerDate.getDate() + 90);



function getLicense() {
    let license = new obj($('#projectName').val(), $('#registerDate').val(), $('#expireDate').val(), $('#token').val());
    return license;
}

layui.use(['form', 'layer'], function () {
    $ = layui.jquery;
    var form = layui.form
        , layer = layui.layer
        , laydate = layui.laydate;

    //日期
    laydate.render({
        elem: '#registerDate'
        , type: 'datetime'
        // 解决时间选择框在iframe层一闪而过
        , trigger: 'click'
        , format: 'yyyy-MM-dd HH:mm:ss'
        , value: registerDate
    });

    laydate.render({
        elem: '#expireDate'
        , type: 'datetime'
        , trigger: 'click'
        , format: 'yyyy-MM-dd HH:mm:ss'
        , value: layui.util.toDateString(expireDate, "yyyy-MM-dd HH:mm:ss")

    });

    $('#close').click(function () {
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    });

    //监听提交
    form.on('submit(add)', function () {
        $.ajax({
            url: '/license/save',
            type: 'post',
            contentType: 'application/json',
            data: JSON.stringify(getLicense()),
            success: function (d) {
                var index = parent.layer.getFrameIndex(window.name);
                if (d.code == 200) {
                    parent.layer.close(index);
                    window.parent.layui.table.reload('licenseTableId');
                    // window.top.layer.msg(d.msg,{icon:6,offset: 'rb',area:['200px','80px'],anim:2});
                    window.top.layer.msg(d.msg, {icon: 6});

                } else {
                    layer.msg(d.msg, {icon: 5});
                }
            }, error: function (e) {
                layer.alert("发生错误", {icon: 6}, function () {
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                });
            }
        });
        return false;
    });

    form.render();
});
