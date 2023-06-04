$(document).ready(function () {
    isLoggedIn();
});

function isLoggedIn() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            $("#signupform").load("mainpage.html");
            $("#title").html("Welcome back");     
            $("#information").html("");
            $("#booktitle").html("");
        } else if (xhr.status !== 200) {
            showChoices();
        }
    };
    xhr.open('GET', 'login');
    xhr.send();
}

function logout(){
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            showChoices();
            $("#title").html("");
            $("#information").html("");
            $("#booktitle").html("");
        } else if (xhr.status !== 200) {
            
        }
    };
    xhr.open('POST', 'logout');
    xhr.send();
}

function createTableFromJSON(data) {
    var html = "<table><tr><th>Category</th><th>Value</th></tr>";
    for (const x in data) {
        var category = x;
        var value = data[x];
        html += "<tr><td>" + category + "</td><td>" + value + "</td></tr>";
    }
    html += "</table>";
    return html;

}

function registerStudent() {
    
    let myForm = document.getElementById('loginForm');
    let formData = new FormData(myForm);
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            $("#signupform").html("Successful signup<br>");
            $("#signupform").append(createTableFromJSON(JSON.parse(xhr.responseText)));
        } else if (xhr.status !== 200) {
        }
    };
    const data = {};
    formData.forEach((value, key) => (data[key] = value));
    console.log(data);
    xhr.open('POST', 'RegisterStudent');
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send(JSON.stringify(data));
    
}

function registerLibrarian() {
    
    let myForm = document.getElementById('loginForm');
    let formData = new FormData(myForm);
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            console.log(xhr.responseText);
            $("#signupform").html("Successful signup<br>");
            $("#signupform").append(createTableFromJSON(JSON.parse(xhr.responseText)));
        } else if (xhr.status !== 200) {
        }
    };
    const data = {};
    formData.forEach((value, key) => (data[key] = value));
    console.log(data);
    xhr.open('POST', 'RegisterLibrarian');
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send(JSON.stringify(data));
    
}

function getUser() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            $("#ajaxContent").html(createTableFromJSON(JSON.parse(xhr.responseText)));
          //  $("#ajaxContent").html("Successful Login");
        } else if (xhr.status !== 200) {
             $("#ajaxContent").html("User not exists or incorrect password");
        }
    };
    console.log("got in");
    var data = $('#loginForm').serialize();
    xhr.open('GET', 'GetStudent?'+data);
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send();
}

function getExistingUser() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        console.log(xhr.responseText);
        console.log(xhr.readyState + "status:" + xhr.status);
        if (xhr.readyState === 4 && xhr.status === 200) {
             $("#ajaxUsername").html("");
        } else if (xhr.status !== 200) {
             $("#ajaxUsername").html("Username exists");
        }
    };
    var data = $('#loginForm').serialize();
    console.log("got in");
    xhr.open('GET', 'GetExistingUser?'+data);
    xhr.setRequestHeader("Content-type","application/json");
    xhr.send();
}

function getExistingEmail() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        console.log(xhr.responseText);
        console.log(xhr.readyState + "status:" + xhr.status);
        if (xhr.readyState === 4 && xhr.status === 200) {
             $("#emailmessage").html("");
        } else if (xhr.status !== 200) {
             $("#emailmessage").html("Email exists");
        }
    };
    var data = $('#loginForm').serialize();
    console.log("got in");
    xhr.open('GET', 'GetExistingEmail?'+data);
    xhr.setRequestHeader("Content-type","application/json");
    xhr.send();
}

function getExistingID() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        console.log(xhr.responseText);
        console.log(xhr.readyState + "status:" + xhr.status);
        if (xhr.readyState === 4 && xhr.status === 200) {
             $("#ajaxID").html("");
        } else if (xhr.status !== 200) {
             $("#ajaxID").html("ID exists");
        }
    };
    var data = $('#loginForm').serialize();
    console.log("got in");
    xhr.open('GET', 'GetExistingID?'+data);
    xhr.setRequestHeader("Content-type","application/json");
    xhr.send();
}

function showRegistrationForm(){
    $("#signupform").load("signup.html");
}

function showLogin(){
    $("#signupform").load("login.html");
}

function showChoices(){
    $("#signupform").load("buttons.html");
}

function showXSS(){
    $("#signupform").load("xss.html");
}


function waitForElement(elementPath, callBack){
  window.setTimeout(function(){
    if($(elementPath).length){
      callBack(elementPath, $(elementPath));
    }else{
      waitForElement(elementPath, callBack);
    }
  },500)
}


function login(){
    
    let myForm = document.getElementById('loginForm');
    let formData = new FormData(myForm);
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            $("#signupform").load("mainpage.html");
            $("#title").html("You are logged in");
            $("#information").html("");
            $("#booktitle").html("");
            console.log(xhr.responseText);
            /*if(document.getElementById("test1")){
               alert("DIV exists.");
            } else {
               alert("DIV does not exist.");
            }*/
            waitForElement("#test1",function(){
                document.getElementById("test1").innerHTML="LALA";
                console.log("done");
            });
        } else if (xhr.status !== 200) {
             $("#error").html("Username, password combo doesn't exist");
             console.log(xhr.responseText);
        }
    };
    
    const data = {};
    formData.forEach((value, key) => (data[key] = value));
    console.log(data);
    xhr.open('POST', 'login');
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send(JSON.stringify(data));
   
}

function getBooks(){
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            $("#booktitle").html("<br>Books");
            console.log(JSON.parse(xhr.responseText));       
            html = "<table><tr>";        
            for (const x in JSON.parse(xhr.responseText)[0]) {
                html += "<th>"+ x +"</th>";
            }         
            html += "</tr>";          
            for (let i = 0; i < JSON.parse(xhr.responseText).length; i++) {
                html += "<tr>"
                for (const x in JSON.parse(xhr.responseText)[i]) {
                    var value = JSON.parse(xhr.responseText)[i][x];
                    html += "<td>" + value + "</td>";
                }
                html += "</tr>"
            }
            html += "</table>";
            $("#information").html("<br>"+html);
            $("#signupform").load("back.html");
            $("#title").html("");   
        } else if (xhr.status !== 200) {
            $("#title").append("N/A");
        }
    };
    xhr.open('GET', 'GetBooks');
    xhr.send();
}    

function goBack(){
    $("#booktitle").html("");
    $("#information").html("");
    $("#title").html("");   
    $("#signupform").load("mainpage.html");
}

function edituser(){
    $("#information").load("edituser.html");
    $("#signupform").load("back.html");
    $("#title").html("Edit Information");    
    
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        const myObj = JSON.parse(xhr.responseText);
        console.log(myObj);     
        if(myObj.hasOwnProperty('student_id')){
            $("#finalIDnumber").html(myObj.student_id);
            $("#finalstudent_id_from_date").html(myObj.student_id_from_date);
            $("#finalstudent_id_to_date").html(myObj.student_id_to_date);
            $("#finaluniversity").html(myObj.university);
            $("#finaldepartment").html(myObj.department);
        }
        else{
            collection = document.getElementsByClassName("studentinfo"); 
            librarycollection = document.getElementsByClassName("libraryinfo");
            for (var i = 0; i < collection.length; i++)
                collection[i].style.display = "none";
            for (var i = 0; i < librarycollection.length; i++)
                librarycollection[i].style.display = "inline-block";
            document.getElementById('address_change').innerHTML = "Library address";
            document.getElementById("libraryinfo").defaultValue = myObj.libraryinfo;
            document.getElementById("libraryname").defaultValue = myObj.libraryname;
        }      
        $("#finalusername").html(myObj.username);
        $("#finalemail").html(myObj.email);
        document.getElementById("password").defaultValue = myObj.password;
        document.getElementById("firstname").defaultValue = myObj.firstname;
        document.getElementById("lastname").defaultValue = myObj.lastname;
        document.getElementById("birthdate").defaultValue = myObj.birthdate;
        document.getElementById(myObj.gender).checked = true;
        document.getElementById("personalpage").defaultValue = myObj.personalpage;
        document.getElementById("city").defaultValue = myObj.city;
        document.getElementById("telephone").defaultValue = myObj.telephone;
        document.getElementById("address").defaultValue = myObj.address;
        
    };
    xhr.open('POST', 'GetStudentInfo');
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send();

}

function editfields(){ 
    let myForm = document.getElementById('editForm');
    let formData = new FormData(myForm);
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            goBack();
        } else if (xhr.status !== 200) {
        }
    };
    const data = {};
    formData.forEach((value, key) => (data[key] = value));
    console.log(data);
    xhr.open('POST', 'updateuser');
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send(JSON.stringify(data));
    
}