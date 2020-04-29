$(function () {
    var id = getUrl('id');
    if(id){
        getDataInfo(id);
    }
})

function getDataInfo(id) {
    $.ajax({
        url: '/Special/selectSpecialBOById',
        type: 'POST',
        data: {specialId: id,source:"html"},
        dataType: 'json',
        success: function (res) {
            $('title').text(res.data.specialBO.title);
            for(var item in res.data.specialBO){
                $('#'+item).html(res.data.specialBO[item]);
            }
            $('video').attr({poster:'./image/default.jpg'});
        }
    })
}

function getUrl(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (name) {
        if (r != null)
            return decodeURI(r[2]);
        return null;
    } else {
        r = window.location.search.substr(1);
        if (r != null)
            return decodeURI(r);
        return null;
    }
}