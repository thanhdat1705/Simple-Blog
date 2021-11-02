function popupCommentWindow(id) {
    var session = document.getElementById("hdnSession");
//    console.log(session.value);
    if (session.value === "") {
        document.getElementById(id).style.height = "100%";
        document.getElementById("warningMessage").style.height = "100%";
    }
}
function closeCommentWindow(id) {
    document.getElementById(id).style.height = "0%";
    document.getElementById("warningMessage").style.height = "0%";
}

function checkSession() {
    var session = document.getElementById("hdnSession");
//    console.log(session.value);
    if (session !== null) {
        if (session.value === "") {
            console.log("session empty");
        } else {
            console.log("session not empty");
            document.getElementById("commentButton").type = "submit";
            document.getElementById("commentButton").setAttribute("onClick", false);
        }
    }
}

function changePageSize(pageSize, pageIndex, searchTittle, searchContent, status) {
//    var pageSize = document.getElementById("cbPageSize").value;

    console.log(pageSize.value + " - " + pageIndex + " - " + searchTittle + " - " + searchContent + " - " + status);
    var form = document.getElementById("pageSizeForm");
//    form.action = "/J3.L.P0004_SimpleBlog/changePageAction?txtPageIndex=" + pageIndex + "&txtSearchTittle=" + searchTittle + "&txtSearchContent=" + searchContent + "&cbStatusAr=" + status + "&txtPageSize=" + pageSize.value;
    form.submit();
}