'use strict';

function Selector() {
    
}

Selector.prototype.getElement = function(query) {
    return document.querySelector(query);
};

var selector = new Selector();

// Unlock
var unlock = selector.getElement("#unlock");

if (unlock !== null) {
    unlock.addEventListener("click", function () {
        unlock.className = "hidden";
        selector.getElement("#name").removeAttribute("disabled");
        selector.getElement("#password").removeAttribute("disabled");
        selector.getElement("#save").classList.remove("hidden");

    });
}

// Go back
var back = selector.getElement(".back");
if (back !== null) {
    back.setAttribute("href", document.referrer);
}


