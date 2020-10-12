
$(document).ready(function () {


    $('#search-button').click(function (event) {
        $('#errorMessage').empty();
        var searchTerm = $('#search-term').val();
        var category = 'hashtag';
        var sortBy = $('input[name="sortBy"]:checked').val();
        var errorMessage = $('#errorMessage');
        var isAdmin = $('#username').val(); 
        if (sortBy === "hashtag") {

            if (category === "hashtag") {
                hashtag = $('#search-term').val();
            } else {
                hashtag = "";
            }

            $.ajax({
                type: 'POST',
                url: 'search/blogs',
                data: JSON.stringify({
                    hashtag: hashtag,
                    isAdmin: isAdmin
                }),
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                'dataType': 'json',
                success: function (data) {
                    errorMessage.empty();
                    fillBlogTable(data);
                   showUndoButton(isAdmin);

                },
                error: function () {
                    alert("error");
                }
            });
        }
        ;
        if (sortBy == "recent") {
            $.ajax({
                type: 'POST',
                url: 'search/recentBlogs',
                data: JSON.stringify({
                    isAdmin: isAdmin
                }),
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                'dataType': 'json',
                success: function (data) {
                    errorMessage.empty();
                    fillBlogTable(data);
                },
                error: function () {
                    alert("error");
                }
            });
        }

        if (sortBy === "oldest") {
            $.ajax({
                type: 'POST',
                url: 'search/olderBlogs',
                data: JSON.stringify({
                    isAdmin: isAdmin
                }),
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                'dataType': 'json',
                success: function (data) {
                    errorMessage.empty();
                    fillBlogTable(data);
                },
                error: function () {
                    alert("error");
                }
            });
        }
        if (sortBy == null) {
            errorMessage.append("Must make a selection to sort by");
        }
    });

     $('#userViewButton').click(function (event) {
         var userView = $('#userView');
         userView.classList.add('active');
     });
});
function fillBlogTable(data) {
    clearBlogTable();
    var contentRows = $('#blog-table');
    $.each(data, function (index, blog) {
        var blogId = blog.blogId;
        var title = blog.title;
        var display_date = blog.displayDate;
        var expiration_date = blog.expirationDate;
        var content = blog.content;
        var hashtags = blog.hashtags.values();
        var row = '<tr>';
        row += '<th><h4>' + title + '</h4>';
        row += '<tr><td>' + content + '</tr></td>';
        row += '<tr><td><a href=viewBlog?blogId=' + blogId + '>Read more</a></td></tr><td><hr/></td>';
        contentRows.append(row);
    });
}

function showUndoButton(isAdmin){
    if(isAdmin == "true"){
        $('#undo-search-admin').show();
    } else {
        $('#undo-search').show();
    }
}

function clearBlogTable() {
    $('#blog-table').empty();
}

