var default_info = {
    user_id: -1,
    username: '',
    password: '',
    full_name: '',
    birth_date: '',
    mobile: '',
    email: '',
    access: {}
}
(function($) {
    $.fn.renderUserDetail = function(options) {
        var file = 'templates/admin.user.info.html';
        $.when($.get(file))
                .done(function(tmplData) {
            $.templates({tmpl: tmplData});
            $(this).html($.render.tmpl(options.data));
        });
    }
}(jquery))


