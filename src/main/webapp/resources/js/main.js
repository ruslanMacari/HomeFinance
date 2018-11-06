'use strict';

function Selector() {
    
}

Selector.prototype.getElement = function(query) {
    return document.querySelector(query);
};

var selector = new Selector();

// Unlock
var unlock = selector.getElement("#unlock");
var unlockEvent = function () {
        unlock.className = "hidden";
        selector.getElement("#name").removeAttribute("disabled");
        selector.getElement("#password").removeAttribute("disabled");
        selector.getElement("#admin").removeAttribute("disabled");
        selector.getElement("#save").classList.remove("hidden");

    };

if (unlock !== null) {
    unlock.addEventListener("click", unlockEvent);
}

if (selector.getElement(".form__error") !== null) {
    unlockEvent();
}

// Go back
var back = selector.getElement(".back");
if (back !== null) {
    back.setAttribute("href", document.referrer);
}


