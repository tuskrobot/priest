layui.use(['element', 'form', 'layer', 'upload'], function () {
    let $ = layui.jquery;
    let element = layui.element; //加载element模块
    let form = layui.form; //加载form模块
    let layer = layui.layer; //加载layer模块
    let upload = layui.upload;  //加载upload模块

    /* 侧边栏开关 */
    $(".side-toggle").on("click", function (e) {
        e.preventDefault();
        let to = $(".layui-layout-admin");
        to.toggleClass("layui-side-shrink");
        to.attr("toggle") === 'on' ? to.attr("toggle", "off") : to.attr("toggle", "on");
    });
    $(".layui-side").on("click", function () {
        let to = $(".layui-layout-admin");
        if (to.attr("toggle") === 'on') {
            to.attr("toggle", "off");
            to.removeClass("layui-side-shrink");
        }
    });

    /* 最大化窗口 */
    $(".eio-screen-full").on("click", function (e) {
        e.preventDefault();
        if (!$(this).hasClass("full-on")) {
            let docElm = document.documentElement;
            let full = docElm.requestFullScreen || docElm.webkitRequestFullScreen ||
                docElm.mozRequestFullScreen || docElm.msRequestFullscreen;
            "undefined" !== typeof full && full && full.call(docElm);
        } else {
            document.exitFullscreen ? document.exitFullscreen()
                : document.mozCancelFullScreen ? document.mozCancelFullScreen()
                : document.webkitCancelFullScreen ? document.webkitCancelFullScreen()
                    : document.msExitFullscreen && document.msExitFullscreen()
        }
        $(this).toggleClass("full-on");
    });

    /* 新建或切换标签栏 */
    let tabs = function (url) {
        let item = $('[lay-url="' + url + '"]');
        if (url !== undefined && url !== '#' && item.length > 0) {
            let bootLay = $('[lay-id="' + url + '"]');
            if (bootLay.length === 0) {
                let title = item.attr("lay-icon") === 'true' ? item.html()
                    : item.children(".layui-nav-title").text();
                let iframeUrl = (window.location.pathname + url).replace('//','/');
                element.tabAdd('iframe-tabs', {
                    title: title
                    , content: '<iframe src="' + iframeUrl + '" frameborder="0" class="layui-layout-iframe"></iframe>'
                    , id: url
                });
            }
            element.tabChange('iframe-tabs', url);
        }
    };

    /* 监听导航栏事件，实现标签页的切换 */
    element.on("nav(layui-nav-side)", function ($this) {
        let url = $this.attr('lay-url');
        tabs(url);
    });

    /* 监听标签栏事件，实现导航栏高亮显示 */
    element.on("tab(iframe-tabs)", function () {
        let layId = $(this).attr("lay-id");
        $(".layui-side .layui-this").removeClass("layui-this");
        $('[lay-url="' + layId + '"]').parent().addClass("layui-this");
        // 改变地址hash值
        location.hash = this.getAttribute('lay-id');
    });

    /* 监听hash来切换选项卡*/
    window.onhashchange = function (e) {
        let url = location.hash.replace(/^#/, '');
        let index = $(".layui-layout-admin .layui-side .layui-nav-item")[0];
        $(index).children("a").attr("lay-icon", "true");
        if (url === "" || url === undefined) {
            url = $(index).children("[lay-url]").attr("lay-url");
        }
        tabs(url);
    };
    window.onhashchange();

    /* 初始化时展开子菜单 */
    $("dd.layui-this").parents(".layui-nav-child").parent()
        .addClass("layui-nav-itemed");

    /* 刷新iframe页面 */
    $(".refresh-btn").click(function () {
        location.reload();
    });

    /* AJAX请求默认选项，处理连接超时问题 */
    $.ajaxSetup({
        complete: function (xhr, status) {
            if (xhr.status === 401) {
                layer.confirm('session连接超时，是否重新登录?', {
                    btn: ['是', '否']
                }, function () {
                    if (window.parent.window !== window) {
                        window.top.location = window.location.pathname + '/login';
                    }
                });
            }
        }
    });

    /*  漂浮消息 */
    $.fn.Messager = function (result) {
        if (result.code === 200) {
            layer.msg(result.msg, {offset: 'auto', time: 3000, icon: 1});
            setTimeout(function () {
                if (result.data === 'submit[refresh]') {
                    parent.location.reload();
                    return;
                }
                if (result.data == null) {
                    window.location.reload();
                } else {
                    window.location.href = result.data
                }
            }, 2000);
        } else {
            layer.msg(result.msg, {offset: 'auto', time: 3000, icon: 2});
        }
    };

    /* 提交表单数据 */
    $(document).on("click", ".ajax-submit", function (e) {
        e.preventDefault();
        let form = $(this).parents("form");
        let url = form.attr("action");
        let serializeArray = form.serializeArray();
        $.post(url, serializeArray, function (result) {
            if (result.data == null) {
                result.data = 'submit[refresh]';
            }
            $.fn.Messager(result);
        });
    });

    /* get方式异步 */
    $(document).on("click", ".ajax-get", function (e) {
        e.preventDefault();
        let msg = $(this).data("msg");
        if (msg !== undefined) {
            layer.confirm(msg + '？', {
                title: '提示',
                btn: ['确认', '取消']
            }, function () {
                $.get(e.target.href, function (result) {
                    $.fn.Messager(result);
                });
            });
        } else {
            $.get(e.target.href, function (result) {
                $.fn.Messager(result);
            });
        }
    });

    // post方式异步-操作状态
    $(".ajax-status").on("click", function (e) {
        e.preventDefault();
        let checked = [];
        let tdcheckbox = $(".eio-table td .eio-checkbox :checkbox:checked");
        if (tdcheckbox.length > 0) {
            tdcheckbox.each(function (key, val) {
                checked.push("ids=" + $(val).attr("value"));
            });
            $.post(e.target.href, checked.join("&"), function (result) {
                $.fn.Messager(result);
            });
        } else {
            layer.msg('请选择一条记录');
        }
    });

    /* 添加/修改弹出层 */
    $(document).on("click", ".open-popup, .open-popup-param", function () {
        let title = $(this).data("title");
        let url = $(this).attr("data-url");
        if ($(this).hasClass("open-popup-param")) {
            let tdcheckbox = $(".eio-table td .eio-checkbox :checkbox:checked");
            let param = '';
            if (tdcheckbox.length === 0) {
                layer.msg('请选择一条记录');
                return;
            }
            if (tdcheckbox.length > 1 && $(this).data("type") === 'radio') {
                layer.msg('只允许选中一个');
                return;
            }
            tdcheckbox.each(function (key, val) {
                param += "ids=" + $(val).attr("value") + "&";
            });
            param = param.substr(0, param.length - 1);
            url += "?" + param;
        }
        let size = $(this).attr("data-size");
        if (size === undefined || size === "auto") {
            size = ['50%', '80%'];
        }else if (size === "max") {
            size = ['100%', '100%'];
        }else if (size.indexOf(',') !== -1) {
            let split = size.split(",");
            size = [split[0] + 'px', split[1] + 'px'];
        }
        window.layerIndex = layer.open({
            type: 2,
            title: title,
            shadeClose: true,
            maxmin: true,
            area: size,
            content: [url, 'on']
        });
    });

    /* 关闭弹出层 */
    $(".close-popup").click(function (e) {
        e.preventDefault();
        parent.layer.close(window.parent.layerIndex);
    });

    /* 下拉按钮组 */
    $(".btn-group").click(function (e) {
        e.stopPropagation();
        $this = $(this);
        $this.toggleClass("show");
        $(document).one("click", function () {
            if ($this.hasClass("show")) {
                $this.removeClass("show");
            }
        });
    });

    // 展示数据列表-多选框
    let thcheckbox = $(".eio-table th .eio-checkbox :checkbox");
    thcheckbox.on("change", function () {
        let tdcheckbox = $(".eio-table td .eio-checkbox :checkbox");
        if (thcheckbox.is(':checked')) {
            tdcheckbox.prop("checked", true);
        } else {
            tdcheckbox.prop("checked", false);
        }
    });

    // 检测列表数据是否为空
    let timoTable = $(".eio-table tbody");
    if (timoTable.length > 0) {
        let children = timoTable.children();
        if (children.length === 0) {
            let length = $(".eio-table thead th").length;
            let trNullInfo = "<tr><td class='eio-table-null' colspan='"
                + length + "'>没有找到匹配的记录</td></tr>";
            timoTable.append(trNullInfo);
        }
    }

    // 携带参数跳转
    let paramSkip = function () {
        let getSearch = "";
        // 搜索框参数
        $('.eio-search-box [name]').each(function (key, val) {
            if ($(val).val() !== "" && $(val).val() !== undefined) {
                getSearch += $(val).attr("name") + "=" + $(val).val() + "&";
            }
        });

        // 页数参数
        let pageSize = $(".page-number").val();
        if (pageSize !== undefined && pageSize !== "") {
            getSearch += "size=" + pageSize + "&";
        }

        // 排序参数
        let asc = $(".sortable.asc").data("field");
        if(asc !== undefined){
            getSearch += "orderByColumn=" + asc + "&isAsc=asc&";
        }
        let desc = $(".sortable.desc").data("field");
        if(desc !== undefined){
            getSearch += "orderByColumn=" + desc + "&isAsc=desc&";
        }

        if (getSearch !== "") {
            getSearch = "?" + getSearch.substr(0, getSearch.length - 1);
        }
        window.location.href = window.location.pathname + getSearch;
    };

    /* 展示列表数据搜索 */
    $(document).on("click", ".eio-search-btn", function () {
        paramSkip();
    });
    /* 改变显示页数 */
    $(document).on("change", ".page-number", function () {
        paramSkip();
    });
    /* 触发字段排序 */
    $(document).on("click", ".sortable", function () {
        $(".sortable").not(this).removeClass("asc").removeClass("desc");
        if($(this).hasClass("asc")){
            $(this).removeClass("asc").addClass("desc");
        }else {
            $(this).removeClass("desc").addClass("asc");
        }
        paramSkip();
    });

    /* 参数化字段排序 */
    let getSearch = function(name) {
        let reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        let r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]);
        return null;
    };
    let field = getSearch("orderByColumn");
    let isAsc = getSearch("isAsc");
    if(field != null){
        $("[data-field='"+ field +"']").addClass(isAsc);
    }

    /** 上传图片操作 */
    upload.render({
        elem: '.upload-image' //绑定元素
        ,url: $('.upload-image').attr('up-url') //上传接口
        ,field: 'image' //文件域的字段名
        ,acceptMime: 'image/*' //选择文件类型
        ,exts: 'jpg|jpeg|png|gif' //支持的图片格式
        ,multiple: true //开启多文件选择
        ,choose: function (obj) {
            obj.preview(function (index, file, result) {
                let upload = $('.upload-image');
                let name = upload.attr('name');
                let show = upload.parents('.layui-form-item').children('.upload-show');
                show.append("<div class='upload-item'><img src='"+ result +"'/>" +
                    "<input id='"+ index +"' type='hidden' name='"+name+"'/>" +
                    "<i class='upload-item-close layui-icon layui-icon-close'></i></div>");
            });
        }
        ,done: function(res, index, upload){
            let field = $('.upload-image').attr('up-field') || 'id';
            // 解决节点渲染和异步上传不同步问题
            let interval = window.setInterval(function(){
                let hide = $("#"+index);
                if(hide.length > 0){
                    let item = hide.parent('.upload-item');
                    if (res.code === 200) {
                        hide.val(res.data[field]);
                        item.addClass('succeed');
                    }else {
                        hide.remove();
                        item.addClass('error');
                    }
                    clearInterval(interval);
                }
            }, 100);
        }
    });

    // 删除上传图片展示项
    $(document).on("click", ".upload-item-close", function () {
        $(this).parent('.upload-item').remove();
    });

});
