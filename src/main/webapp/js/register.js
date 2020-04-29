var inviteUserId = 0;
$(function () {
    inviteUserId = getUrl('userId');
});


var source = 'user';


function register() {
    var phone = $('input[name="phone"]').val();
    var verify = $('input[name="verify"]').val();
    if (!inviteUserId || !phone || !verify || !source) {
        alert('请将填写表单！！！！');
        return;
    }
    $.ajax({
        url: '/user/register',
        type: 'POST',
        dataType: 'json',
        data: {
            inviteUserId: inviteUserId,
            phone: phone,
            verify: verify,
            source: source
        },
        success: function (response) {
            alert(response.msg);
        }
    })
}

function send() {
    var phone = $('input[name="phone"]').val();
    console.log(phone);
    if (phone) {
        $.ajax({
            url: '/user/send',
            type: 'POST',
            dataType: 'json',
            data: {
                phone: phone,
                type: 'amend'
            },
            success: function (response) {
                console.log(response);
            }
        })
    } else {
        alert('填上你的手机号！！！！')
    }
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

