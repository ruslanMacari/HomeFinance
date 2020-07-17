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
    var saveItem = selector.getElement("#save");
    var unlockEvent = function () {
        unlock.className = "hidden";
        
        var disabledElements = document.querySelectorAll(".disabledJs");
        disabledElements.forEach(element => element.removeAttribute(disabled));
        
        saveItem.classList.remove("hidden");
    };

    if (unlock !== null) {
        unlock.addEventListener("click", unlockEvent);
    }

    if (selector.getElement(".form__error") !== null) {
        unlockEvent();
    }

    var passwordItem = selector.getElement("#password");
    var changePassword = selector.getElement("#passwordChanged");
    var changePasswordEvent = function () {
        if (changePassword.checked) {
            passwordItem.removeAttribute(disabled);
        } else {
            passwordItem.setAttribute(disabled, "true");
        }
    };

    if (changePassword !== null) {
        changePassword.addEventListener("click", changePasswordEvent);
    }
    
    //on save
    var saveEvent = function () {
        if (!changePassword.checked) {
            passwordItem.value = "1111";
            passwordItem.removeAttribute(disabled);
        }
    };
    //document.addEventListener("click", send);
    if (saveItem !== null) {
        saveItem.addEventListener("click", saveEvent);
    }
    
    //var json = [{"numCode": "978", "charCode": "EUR", "currency": "Euro", "rate": 19.5512}];
    
    
})();

//function send()
//    {
//        var urlvariable;
//
//        urlvariable = "text";
//
//        var ItemJSON;
//
//        ItemJSON = '[{"numCode": "978", "charCode": "EUR", "currency": "Euro", "rate": 19.5512}]';
//
//        URL = "http://localhost:8080/HomeFinance/currencies/rates"
//
//        var xmlhttp = new XMLHttpRequest();
//        xmlhttp.onreadystatechange = callbackFunction(xmlhttp);
//        xmlhttp.open("POST", URL, false);
//        xmlhttp.setRequestHeader("Content-Type", "application/json");
//        xmlhttp.setRequestHeader('Authorization', 'Basic ' + window.btoa('apiusername:apiuserpassword')); //in prod, you should encrypt user name and password and provide encrypted keys here instead 
//        xmlhttp.onreadystatechange = callbackFunction(xmlhttp);
//        xmlhttp.send(ItemJSON);
//        alert(xmlhttp.responseText);
//        document.getElementById("div").innerHTML = xmlhttp.statusText + ":" + xmlhttp.status + "<BR><textarea rows='100' cols='100'>" + xmlhttp.responseText + "</textarea>";
//    }
//
//    function callbackFunction(xmlhttp)
//    {
//        alert(xmlhttp.responseXML);
//    }
