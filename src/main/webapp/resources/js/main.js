'use strict';

function Selector() {
    
}

Selector.prototype.getElement = function(query) {
    return document.querySelector(query);
};

var selector = new Selector();
var unlock = selector.getElement("#unlock");

unlock.addEventListener("click", function () {
    unlock.className ="hidden";
    selector.getElement("#name").removeAttribute("disabled");
    selector.getElement("#password").removeAttribute("disabled");
    selector.getElement("#save").classList.remove("hidden");
    
});


