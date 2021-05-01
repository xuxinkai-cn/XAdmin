layui.use(['layer', 'element', 'util','form'], function(){
    var $ = layui.$,
    layer = layui.layer,
    element = layui.element,
    util = layui.util,
    form = layui.form;

    // 重写url表单校验
    form.verify({
        v_url: function (value,item) {
            const exp = /(^#)|(^http(s*):\/\/[^\s]+\.[^\s]+)/;
            if(value && !exp.test(value)){
                return "链接格式不正确";
            }
        }
    })
    // 文章评论
    form.on("submit(comment)",function (data) {
       $.ajax({
           url:'/api/v1/comment/publish',
           method:'post',
           data:data.field,
           dataType:'JSON',
           success : function(res) {
               if(res.code === 0){
                   $("#comment-form")[0].reset();
                   EchoComment.cancelReply();
               }
               layer.msg(res.message);
           },
           error : function() {
               layer.msg("评论失败~");
           }
       });
       return false;
    });
    
    $(".nav-btn").on('click', function(){
        $('.nav-btn dl').toggleClass('layui-show');
    });

    //友情链接tips
    $(".link div a").mouseover(function(e) {
        if ($.trim(this.title) !== '') {
            this.Title = this.title;
            this.title = "";
            layer.tips(this.Title, this, {tips: 3});
        }
    }).mouseout(function() {
        if (this.Title != null) {
            this.title = this.Title;
        }
    })

    //文章图片点击事件(如果为pc端才生效)
    var device = layui.device();
    if(!(device.weixin || device.android || device.ios)){
        $(".text img").click(function() {
            $.previewImage(this.src);
        });
        $.previewImage = function (src) {
            var img = new Image(), index = layer.load(2, {time: 0, scrollbar: false, shade: [0.02, '#000']});
            img.style.background = '#fff', img.style.display = 'none';
            img.src = src;
            document.body.appendChild(img), img.onerror = function () {
                layer.close(index);
            }, img.onload = function () {
                layer.open({
                    type: 1, shadeClose: true, success: img.onerror, content: $(img), title: false,
                    area: [img.width > 1140 ? '1140px' : img.width, img.height > 800 ? '800px' : img.height], closeBtn: 1, skin: 'layui-layer-nobg', end: function () {
                        document.body.removeChild(img);
                    }
                });
            };
        };
    }

    //右下角工具箱（返回顶部）
    util.fixbar();
    
});