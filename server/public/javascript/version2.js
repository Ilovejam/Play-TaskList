/*
*
*
 */
const validateRoute = $("#validateRoute").val();
const loginRoute = $("#loginRoute").val();
$("#contents").load(loginRoute)

function login() {
    const username = $("#loginName").val();
    const password = $("#loginPass").val()
    $.post(
        validateRoute,
        {username, password},
        data => {
            $("#contents").html(data);
        });
}

function createUser() {
    const username = $("#createName").val();
    const password = $("#createPass").val()
    console.log("login for " + username +" and " + password);
    $("#contents").load("/create2?username=" + username + "&password=" + password);
}

function deleteTask(index) {
    $("#contents").load("/deleteTask2?index=" + index)
}

function addTask() {
    const task = $("#newTask").val();
    $("#contents").load("/addTask2?index=" + encodeURIComponent(task));
}