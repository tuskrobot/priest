require.config({
    bathUrl: "/js",
    paths: {
        "jquery" : ["/plugins/jquery/jquery-3.4.1.min"],
        "layui": "/plugins/layui/layui",
        "index": "index"
    },
    shim:{
        "index": ["jquery", "layui"]
    }
});

require(["jquery", "layui", "index"], function ($, layui, index) {

});