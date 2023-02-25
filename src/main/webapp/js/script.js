document.addEventListener("DOMContentLoaded", function () {
    var button = document.getElementById("remove_checked_contacts_button");
    button.addEventListener("click", function () {
        var form = createBlankForm();
        copyCheckedRowsIdsToAndSubmit(form);
    });

    var mainCheckbox = document.getElementById("main_checkbox");
    mainCheckbox.addEventListener("change", function () {
        var checkboxes = document.getElementsByClassName("select-me");
        for (var checkbox of checkboxes) {
            checkbox.checked = mainCheckbox.checked;
        }
    });

    function createBlankForm(method = "POST", action = "remove") {
        var form = document.createElement("form");
        form.method = method
        form.action = action;
        return form;
    }

    function copyCheckedRowsIdsToAndSubmit(form) {
        var checked = Array
            .from(document.getElementsByClassName("select-me"))
            .filter(function (el) {
                return el.checked
            });

        if (checked.length === 0) {
            alert("Не выбрано ни одного контакта.");
            return;
        }

        checked.forEach(function (el) {
            var input = document.createElement("input");
            input.name = el.name;
            input.type = "text";
            input.value = el.value;
            form.append(input);
        });

        document.getElementsByTagName('body')[0].appendChild(form);
        form.submit();
    }
});