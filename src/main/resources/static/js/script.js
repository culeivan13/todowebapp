// Home button handler
let homeButton = (isLoggedIn) => {
    if(isLoggedIn) window.location.href = "/user/home";
    else window.location.href = "/signup";
}

// Go to Home Page Handler
let goHome = () => {
    window.location.href = "/user/home";
}

// Go to Login Page Handler
let goLoginPage = () => {
    window.location.href = "/login";
}

// Due Date field on focus and on blur handler
let due = document.getElementById("dueDate")
due.onfocus = function (event) {
    this.type = 'datetime-local';
    this.focus();
}

due.onblur = function (event) {
    this.type = 'text';
}

// Delete functionality
let handleDelete = (id) => {
    swal({
        title: "Are you sure?",
        text: "Once deleted, you will not be able to view/modify this task!",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    })
        .then((willDelete) => {
        if (willDelete) {
            fetch("/todo/delete/" + id, {
                method: "DELETE"
            })
                .then(response => {
                console.log(response)
                if(response.ok){
                    swal("Poof! Your task has been deleted!", {
                        icon: "success",
                    }).then(() => location.reload());
                } else {
                    return response.text().then(text => {
                        throw new Error(text)
                    });
                }
            }).catch(error => {
                swal("Error!", "An error occurred: " + error.message, "error");
            });
        }
        else {
            swal("Your task is safe!");
        }
    });
}