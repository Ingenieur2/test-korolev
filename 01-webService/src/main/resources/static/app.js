let stompClient = null;

const setConnected = (connected) => {
    $('#connect').prop("disabled", connected);
    $('#disconnect').prop("disabled", !connected);
    if (connected) {
        $('#usersList').show();

    } else {
        $('#usersList').hide();
        $('#create-form')[0].reset();
    }
    $('#usersTable > tbody').empty();

}

const connect = () => {
    stompClient = Stomp.over(new SockJS('/websocket'));
    stompClient.connect({}, (frame) => {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/getAllUsers', (message) => showAllUsers(JSON.parse(message.body)));
        stompClient.subscribe('/topic/users', (message) => showMessage(JSON.parse(message.body)));
    });
}

const disconnect = () => {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendMsgRegister() {
    stompClient.send("/app/chat.addUser", {}, JSON.stringify({
        id: $('#userId').val(),
        name: $('#userName').val(),
        password: $('#userPassword').val(),
        email: $('#userEmail').val()
    }));
}

function sendMsgUpdate() {
    stompClient.send("/app/chat.updateUser", {}, JSON.stringify({
        id: $('#userId').val(),
        name: $('#userName').val(),
        password: $('#userPassword').val(),
        email: $('#userEmail').val()
    }));
}

function sendMsgDelete() {
    stompClient.send("/app/chat.deleteUser", {}, JSON.stringify({
        id: $('#userId').val(),
        name: $('#userName').val(),
        password: $('#userPassword').val(),
        email: $('#userEmail').val()
    }));
}

function sendMsgAll() {
    stompClient.send("/app/chat.getAllUsers", {}, {});
}

const showMessage = (user) => {
    if ((user.id === 0) ||
        (user.name === "") ||
        (user.password === "")) {
        $('#usersStr').append();
    } else {
        $('#usersStr').append('<tr>' +
            '<td>' + user.id + '</td>' +
            '<td>' + user.name + '</td>' +
            '<td>' + user.password + '</td>' +
            '<td>' + user.email + '</td>' +
            '</tr>');
    }
}

const showAllUsers = (users) => {
    $('#usersTable > tbody').empty();
    if (users.length > 0) {
        for (let i = 0; i < users.length; i++) {
            let user = users[i];
            $('#usersStr').append('<tr>' +
                '<td >' + user.id + '</td>' +
                '<td>' + user.name + '</td>' +
                '<td>' + user.password + '</td>' +
                '<td>' + user.email + '</td>' +
                '</tr>')
        }
    }
}