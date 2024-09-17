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



function retrieveDataFromCheckout() {
    let firstName = document.getElementById('first-name').value;
    let lastName = document.getElementById('last-name').value;
    let email = document.getElementById('email').value;
    let address = document.getElementById('address').value;
    let city = document.getElementById('city').value;
    let postalCode = document.getElementById('postal-code').value;
    let country = document.getElementById('country').value;

    let order = {
        firstName: firstName,
        lastName: lastName,
        email: email,
        address: address,
        city: city,
        postalCode: postalCode,
        country: country
    };

    fetch('/saveOrder', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(order)
    })
        .then(response => response.text())
        .then(data => console.log(data))
        .catch(error => console.error('Error:', error));
}





