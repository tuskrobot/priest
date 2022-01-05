let obj = function (userName) {
    this.userName = userName;
};
function getUser() {
    let user = new obj($('#userNam').val());
    return user;
}

function initUser() {
    $('#userName').val("");
}

layui.use(['table', 'laydate', 'layer'], function () {
    let table = layui.table
        , layer = layui.layer;
    //渲染表
    table.render({
        id: 'userTableId'
        , elem: '#userTable'
        , url: '/user/data'
        , title: '用户列表'
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
                , {field: 'userName', title: '用户名', width: 100, fixed: 'left', align: 'center'}
                , {field: 'email', title: '邮箱', width: 180, align: 'center', templet: '<div>{{ layui.util.toDateString(d.createDate, "yyyy-MM-dd HH:mm:ss") }}</div>'}
                , {field: 'statusName', title: '状态', width: 120, align: 'center'}
                , {field: 'createDate', title: '创建时间', width: 180, align: 'center', templet: '<div>{{ layui.util.toDateString(d.registerDate, "yyyy-MM-dd HH:mm:ss") }}</div>'}
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


