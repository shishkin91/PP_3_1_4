let userInfo = $('#tableAllUsers')
let allUser = []

getAllUser()

function getAllUser() {
    fetch("/api/users").then((response) => {
        console.log(response.statusText + response.status)
        if (response.ok) {
            response.json().then((users) => {
                users.forEach((user) => {
                    console.log(user)
                    addUserForTable(user)
                    allUser.push(user)
                });
            });
            console.log(allUser)
        } else {
            console.error(response.statusText + response.status)
        }
    });
}

function addUserForTable(user) {
    userInfo.append(
        '<tr>' +
        '<td>' + user.id + '</td>' +
        '<td>' + user.name + '</td>' +
        '<td>' + user.email + '</td>' +
        '<td>' + user.age + '</td>' +
        '<td>' + user.roles.map(roleUser => " " + roleUser.name) + '</td>' + // с name.substring(5) после добавления юзера роли отображаются только с обновлением страницы, пока не нашёл как исправить.
        '<td>' +
        '<button onclick="editUserById(' + user.id + ')" class="btn btn-info edit-btn" data-toggle="modal" data-target="#edit"' +
        '>Edit</button></td>' +
        '<td>' +
        '<button onclick="deleteUserById(' + user.id + ')" class="btn btn-danger" data-toggle="modal" data-target="#delete"' +
        '>Delete</button></td>' +
        '</tr>'
    )
}

function addNewUser() {

    let roleList = () => {
        let array = []
        let options = document.querySelector('#addRole').options
        for (let i = 0; i < options.length; i++) {
            if (options[i].selected) {
                let role = {id: options[i].value, name: options[i].text}
                array.push(role)
            }
        }
        return array;
    }

    let user = {
        name: document.getElementById("addName").value,
        email: document.getElementById("addEmail").value,
        age: document.getElementById("addAge").value,
        password: document.getElementById("addPassword").value,
        roles: roleList()
    }


    let headers = new Headers();
    headers.append('Content-Type', 'application/json; charset=utf-8');
    let request = new Request('/api/users', {
        method: 'POST',
        headers: headers,
        body: JSON.stringify(user)
    });
    console.log(user);

    fetch(request).then((response) => {
        response.json().then((userAdd) => {
            allUser.push(userAdd)
            addUserForTable(userAdd)
            console.log(userAdd)
        })

        console.log(allUser)

        $('#usersTableActive').tab('show');
        userClearModal()
    })
}

function userClearModal() {
    $('#addName').empty().val('');
    $('#addEmail').empty().val('');
    $('#addAge').empty().val('');
    $('#addPassword').empty().val('');
    $('#addRole').val('');

}

function editUserById(id) {
    fetch("/api/users/" + id, {method: "GET", dataType: 'json',})
        .then((response) => {
            response.json().then((user) => {
                $('#editId').val(user.id);
                $('#editName').val(user.name);
                $('#editEmail').val(user.email);
                $('#editAge').val(user.age);
                $('#editPassword').val(user.password);
                $('#editRole').val(user.roles);

                console.log(user)
            })
        })
}

function editButton() {
    let roleList = () => {
        let array = []
        let options = document.querySelector('#editRole').options
        for (let i = 0; i < options.length; i++) {
            if (options[i].selected) {
                let role = {id: options[i].value, name: options[i].text}
                array.push(role)
            }
        }
        return array;
    }

    let editUser = {
        id: document.getElementById("editId").value,
        name: document.getElementById("editName").value,
        email: document.getElementById("editEmail").value,
        age: document.getElementById("editAge").value,
        password: document.getElementById("editPassword").value,
        roles: roleList()
    }
    console.log(editUser);

    let headers = new Headers();
    headers.append('Content-Type', 'application/json; charset=utf-8');
    let request = new Request("api/users", {
        method: 'PUT',
        headers: headers,
        body: JSON.stringify(editUser),
    });

    let userEditId = ($('#editId').val())
    console.log(userEditId)
    fetch(request).then((response) => {
        response.json().then((userEdit) => {
            console.log(userEdit);
            userInfo.empty();
            allUser = allUser.map(user => user.id !== userEdit.id ? user : userEdit)
            console.log(allUser)
            allUser.forEach((user) => {
                addUserForTable(user)
            })
        })
        $('#edit').modal('hide');
    });
}

function deleteUserById(id) {
    fetch("/api/users/" + id, {method: "GET", dataType: 'json',})
        .then((response) => {
            response.json().then((user) => {
                $('#deleteId').val(user.id)
                $('#deleteName').val(user.name)
                $('#deleteEmail').val(user.email)
                $('#deleteAge').val(user.age)
                $('#deletePassword').val(user.password)
                $('#deleteRole').val(user.roles)
            })
        })
}

function deleteButton() {
    let userId = ($('#deleteId').val());
    console.log(userId)
    fetch("/api/users/" + userId, {method: "DELETE"})
        .then((response) => {
            userInfo.empty()
            allUser = allUser.filter(user => user.id !== Number(userId))
            console.log(allUser)

            allUser.forEach((user) => {
                addUserForTable(user)
            })
            $('#delete').modal('hide');
        })
}



