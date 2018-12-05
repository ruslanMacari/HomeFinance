'use strict';
(function () {

    function Selector() {};

    Selector.prototype.getElement = function (query) {
        return document.querySelector(query);
    };

    var selector = new Selector();

    // Unlock
    var disabled = "disabled";
    var unlock = selector.getElement("#unlock");
    var unlockEvent = function () {
        unlock.className = "hidden";
        selector.getElement("#name").removeAttribute(disabled);
        selector.getElement("#changePassword").removeAttribute(disabled);
        selector.getElement("#admin").removeAttribute(disabled);
        selector.getElement("#save").classList.remove("hidden");

    };

    if (unlock !== null) {
        unlock.addEventListener("click", unlockEvent);
    }

    if (selector.getElement(".form__error") !== null) {
        unlockEvent();
    }

    var passwordItem = selector.getElement("#password");
    var changePassword = selector.getElement("#changePassword");
    var changePasswordEvent = function () {
        if (changePassword.checked) {
            passwordItem.removeAttribute(disabled);
        } else {
            passwordItem.setAttribute(disabled, "true");
        }
    };

    changePassword.addEventListener("click", changePasswordEvent);
})();

