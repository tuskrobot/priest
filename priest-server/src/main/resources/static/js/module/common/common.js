// 初始化table高度
let $ = layui.$;
let rowHeight = $('.layui-row').css('height');
let bodyHeight = $('.layui-body').css('height');
let tableHeight = parseInt(bodyHeight) - parseInt(rowHeight) - 40;

// 表格回车搜索事件
document.onkeydown=function(event){
    let e = event || window.event || arguments.callee.caller.arguments[0];
    if(e && e.keyCode==13){ // enter 键
        $("#search").trigger("click");
    }
};


