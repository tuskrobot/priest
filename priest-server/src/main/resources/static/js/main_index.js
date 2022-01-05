require.config({
    bathUrl: "/js",
    paths: {
        "jquery" : ["/plugins/jquery/jquery-3.4.1.min"],
        "layui": "/plugins/layui/layui",
        "register": "module/register/index"
    },
    shim:{
        "register": ["jquery", "layui"]
    }
});

require(["jquery", "layui", "register"], function ($, layui, register) {

});
