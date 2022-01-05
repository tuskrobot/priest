
layui.use(['table', 'laydate', 'layer'], function () {
    let table = layui.table
        , layer = layui.layer;
    //渲染表
    table.render({
        id: 'lotharTable'
        , elem: '#lotharTable'
        , url: '/register/data'
        , title: '用户列表'
        , height: 40
        , page: false
        , method: 'post'
        , contentType: 'application/json'
        , parseData: function (res) {
            return {
                "code": res.code,
                "count": res.total,
                "data": res.data
            }
        }
        , cols: [
            [
                {type: 'numbers', fixed: 'left'}
                , {field: 'id', title: 'ID', width: 180, align: 'center'}
                , {field: 'host', title: 'HOST', width: 180, align: 'center'}
                , {field: 'port', title: 'PORT', width: 120, align: 'center'}
                , {field: 'createDate', title: '创建时间', width: 180, align: 'center', templet: '<div>{{ layui.util.toDateString(d.createDate, "yyyy-MM-dd HH:mm:ss") }}</div>'}
                , {fixed: 'right', title: '操作', toolbar: ''}
            ]
        ]
        , where: {
            type: 'LOTHAR'
        }
        , done: function (res, curr, count) {
            document.getElementsByClassName('layui-table-body')[0].style.height = res.count * 40 + 'px'
        }
    });

    table.render({
        id: 'paladinTable'
        , elem: '#paladinTable'
        , url: '/register/data'
        , title: '用户列表'
        , height: 40
        , page: false
        , method: 'post'
        , contentType: 'application/json'
        , parseData: function (res) {
            return {
                "code": res.code,
                "count": res.total,
                "data": res.data
            }
        }
        , cols: [
            [
                {type: 'numbers', fixed: 'left'}
                , {field: 'id', title: 'ID', width: 180, align: 'center'}
                , {field: 'host', title: 'HOST', width: 180, align: 'center'}
                , {field: 'port', title: 'PORT', width: 120, align: 'center'}
                , {field: 'createDate', title: '创建时间', width: 180, align: 'center', templet: '<div>{{ layui.util.toDateString(d.createDate, "yyyy-MM-dd HH:mm:ss") }}</div>'}
                , {fixed: 'right', title: '操作', toolbar: ''}
            ]
        ]
        , where: {
            type: 'PALADIN'
        }
        , done: function (res, curr, count) {
            document.getElementsByClassName('layui-table-body')[3].style.height = res.count * 40 + 'px'
        }
    });

});






