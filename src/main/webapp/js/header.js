$(document).ready(function() {

//    $.ajax({
//        type: 'POST',
//        async: false,
//        data: $("#header_form").serialize(),
//        url: getAbsolutePath() + "/menu.htm",
//        success: function(msg) {
//            $("#header_form").append(msg);
//        }
//    });

    accounting.settings.currency.format = {
        pos: "%v ", // for positive values, eg. "$1.00" (required)
        neg: "(%v)", // for negative values, eg. "$(1.00)" [optional]
        zero: "-"  // for zero values, eg. "$  --" [optional]
    };
    //changeProfilePicture();
});

function changeProfilePicture() {
    $('.profile_picture').attr('src', getProfilePictureUrl());
}

function getProfilePictureUrl()
{
    var src;
    $.ajax({
        type: 'POST',
        async: false,
        data: {l_action: "get_profile_picture_url"},
        url: getAbsolutePath() + "/" + "get_profile_picture_url.htm",
        success: function(msg) {
            if (msg.trim() !== "") {
                src = msg;
            }
        }
    });
    return getAbsolutePath() + "/" + src;
}
//function goTo(url) {
//    redirect(url);
//}

function deleteAllCookies() {
    var cookies = document.cookie.split(";");

    for (var i = 0; i < cookies.length; i++) {
        var cookie = cookies[i];
        var eqPos = cookie.indexOf("=");
        var name = eqPos > -1 ? cookie.substr(0, eqPos) : cookie;
        document.cookie = name + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT";
    }
}

function logout() {
    deleteAllCookies();
    window.location = getAbsolutePath() + "/login.htm";
}

