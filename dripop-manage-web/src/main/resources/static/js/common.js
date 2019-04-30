$(function() {
    var pathname = location.pathname;
    var t = $(".nav-level2 a[allHref*=',"+pathname+",']");
    if($(t).size() > 0) {
        $(t).addClass("active");
        $(t).parent().parent().addClass("active");
        var index = $(t).parent().parent().attr("index");
        $(".nav-level1 a").eq(index).addClass("active");
    }

    $(".nav-level1 a").click(function() {
        $(".nav-level1 a").removeClass("active");
        $(".nav-level2 ul").removeClass("active");
        $(".nav-level2 a").removeClass("active");
        $(this).addClass("active");
        var index = $(this).parent().parent().attr("index");
        var secondItem = $(".nav-level2 ul").eq(index);
        secondItem.addClass("active");
        secondItem.children().children().eq(0).addClass("active");
        location.href = secondItem.children().children().eq(0).attr("href");
    });

    $(document).ajaxComplete(function(event, xhr, settings) {
        var data = JSON.parse(xhr.responseText);
        if(data.status == 499) {
            location.href = "/";
        }else if(data.status == 498) {
            location.href = "/common/noPermission";
        }
    });
});

function exportExcel(url) {
    $("#downloadIframe").attr("src", url);
}