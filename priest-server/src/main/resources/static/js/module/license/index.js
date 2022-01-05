let obj = function (projectName, registerDate, expireDate) {
    this.projectName = projectName;
    this.registerDate =  registerDate;
    this.expireDate = expireDate;
};
function getLicense() {
    let license = new obj($('#projectName').val(),  $('#registerDate').val(), $('#expireDate').val());
    return license;
}

function initLicense() {
    $('#projectName').val("");
    $('#registerDate').val("");
    $('#expireDate').val("")
}
// 项目名称实时提示
$("#projectName").bind('input propertychange', function() {
    $.ajax({
        url: '/license/getProjectNames',
        type: 'get',
        success: function (data) {
            if (data.code == 200) {
                $("#projectName").autocomplete({
                    source: eval("(" + data.data + ")")
                });
            }
        }
    });
})

layui.use(['table', 'laydate', 'layer'], function () {
    let table = layui.table
        , laydate = layui.laydate
        , layer = layui.layer;
    //日期
    laydate.render({
        elem: '#registerDate'
        , type: 'datetime'
        , format: 'yyyy-MM-dd'
    });

    laydate.render({
        elem: '#expireDate'
        , type: 'datetime'
        , format: 'yyyy-MM-dd'
    });

    //渲染表
    table.render({
        id: 'licenseTableId'
        , elem: '#licenseTable'
        , url: '/license/data'
        , title: '授权列表'
        , height: tableHeight
        , page: true
        , method: 'post'
        , contentType: 'application/json'
        , limit: 15
        , limits: [15, 30, 60]
        , parseData: function (res) {
            return {
                "code": res.code,
                "count": res.total,
                "data": res.data
            }
        }
        , cols: [
            [
                {type: 'checkbox', fixed: 'left'}
                , {field: 'projectName', title: '项目名称', width: 100, fixed: 'left', align: 'center'}
                , {field: 'createDate', title: '创建日期', width: 180, align: 'center', templet: '<div>{{ layui.util.toDateString(d.createDate, "yyyy-MM-dd HH:mm:ss") }}</div>'}
                , {field: 'registerDate', title: '注册日期', width: 180, align: 'center', templet: '<div>{{ layui.util.toDateString(d.registerDate, "yyyy-MM-dd HH:mm:ss") }}</div>'}
                , {field: 'expireDate', title: '到期日期', width: 180, align: 'center', templet: '<div>{{ layui.util.toDateString(d.expireDate, "yyyy-MM-dd HH:mm:ss") }}</div>'}
                , {field: 'expiryTimeName', title: '有效期', width: 100, align: 'center'}
                , {field: 'updateDate', title: '更新日期', width: 180, align: 'center', templet: '<div>{{ layui.util.toDateString(d.updateDate, "yyyy-MM-dd HH:mm:ss") }}</div>'}
                , {field: 'statusName', title: '状态', width: 120, align: 'center'}
                // , {field: 'token', title: '令牌', width: 150, align: 'center'}
                // , {field: 'register', title: '注册码', width: 150, align: 'center'}
                , {field: 'remark', title: '注释', width: 120, align: 'center'}
                , {fixed: 'right', title: '操作', toolbar: '#licenseBar'}
            ]
        ]
    });

    //查询栏事件
    $('.queryDiv .layui-btn').on('click', function () {
        let type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

    active = {
        reload: function () {
            table.reload('licenseTableId', {
                page: {
                    curr:1
                },
                where: {
                    param: getLicense()
                },
                method: 'post'
            }, 'data');

            initLicense();
        },

        add: function () {
            //弹出一个iframe层
            layer.open({
                id: 'license-add',
                type: 2,
                area: ['500px', '500px'],
                fix: false,
                maxmin: true,
                shadeClose: false,
                shade: 0.4,
                title: '添加项目',
                content: '/license/add'
            });
        },

        reset: function () {
            initLicense();
        }
    };

    //行工具栏事件
    table.on('tool(licenseFilter)', function (obj) {
        var projectName = obj.data.projectName;
        var id = obj.data.id;
        switch (obj.event) {
            case 'edit':
                //弹出一个iframe层
                layer.open({
                    id: 'license-eidt',
                    type: 2,
                    area: ['500px', '500px'],
                    fix: false,
                    maxmin: true,
                    shadeClose: false,
                    shade: 0.4,
                    title: '编辑项目',
                    content: '/license/edit?id=' + id
                });
                break;
            case 'del':
                $.ajax({
                    url: '/license/delete?id=' + id,
                    type: 'get',
                    success: function (res) {
                        window.top.layer.msg(res.msg,{icon:6});
                        table.reload('licenseTableId');
                    }
                });
                break;
            case 'genRegister':
                $.ajax({
                    url: '/license/genRegister?id=' + id,
                    type: 'get',
                    success: function (res) {
                        window.top.layer.msg(res.msg,{icon:6});
                        table.reload('licenseTableId');
                    }
                });
                break;
            case 'disable':
                $.ajax({
                    url: '/license/disable?id=' + id,
                    type: 'get',
                    success: function (res) {
                        window.top.layer.msg(res.msg,{icon:6});
                        table.reload('licenseTableId');
                    }
                });
                break;
        }
        ;
    });

});


