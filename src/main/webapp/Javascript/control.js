// Questo codice viene salvato nel file form-validation.js

document.addEventListener('DOMContentLoaded', function() {
    const forms = document.querySelectorAll('.product-form, .contact-form');

    forms.forEach(form => {
        form.addEventListener('submit', function(event) {
            event.preventDefault();

            if (validateForm(form)) {
                const formData = new FormData(form);

                console.log('Invio del form:', formData);

                form.reset();
            } else {
                alert('Si prega di compilare correttamente tutti i campi.');
            }
        });
    });

    function validateForm(form) {
        const requiredInputs = form.querySelectorAll('[required]');
        let isValid = true;

        requiredInputs.forEach(input => {
            if (input.value.trim() === '') {
                isValid = false;
                return false; // Esci dal loop forEach
            }
        });

        return isValid;
    }
});



function checkLoginStatus() {
    var loginLogoutArea = document.getElementById('loginLogoutArea');
    var isLoggedIn = session.getAttribute("currentSessionUser") != null; // Verifica se l'utente è loggato

    if (isLoggedIn) {
        // Mostra il pulsante di logout
        loginLogoutArea.innerHTML = '<button onclick="logout()">Logout</button>';
    } else {
        // Mostra il pulsante di login
        loginLogoutArea.innerHTML = '<button onclick="location.href=\'<%= request.getContextPath() %>/login.jsp\'">Login</button>';
    }
}

// Funzione per eseguire il logout
function logout() {
    // Effettua il logout inviando una richiesta GET a LogoutServlet
    var xhr = new XMLHttpRequest();
    xhr.open('GET', '<%= request.getContextPath() %>/LogoutServlet', true);
    xhr.onload = function () {
        if (xhr.status === 200) {
            // Reindirizza alla pagina di login dopo il logout
            window.location.href = '<%= request.getContextPath() %>/common/login.jsp';
        }
    };
    xhr.send();
}