let due = document.getElementById("dueDate")
due.onfocus = function (event) {
    this.type = 'datetime-local';
    this.focus();
}

due.onblur = function (event) {
    this.type = 'text';
}

document.addEventListener("DOMContentLoaded", function () {
    var deleteModal = document.getElementById("deleteModal");

    deleteModal.addEventListener("show.bs.modal", function (event) {
        var button = event.relatedTarget; // Button that triggered the modal
        var todoId = button.getAttribute("data-id"); // Get todoId from button

        var form = document.getElementById("deleteForm");
        form.action = "/todo/delete/" + todoId; // Update form action with the correct ID
    });
});
