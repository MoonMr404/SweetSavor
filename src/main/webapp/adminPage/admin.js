document.addEventListener('DOMContentLoaded', function() {
    // Carica la sezione di default
    loadSection('jsp/addProduct.jsp');

    // Gestisce il clic sui link della sidebar
    document.querySelectorAll('.sidebar-link').forEach(function(link) {
        link.addEventListener('click', function(e) {
            e.preventDefault();
            var url = this.getAttribute('data-url');
            loadSection(url);
        });
    });
});

function loadSection(url) {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', url, true);

    xhr.onload = function() {
        if (xhr.status >= 200 && xhr.status < 300) {
            document.getElementById('content').innerHTML = xhr.responseText;
        } else {
            console.error('Errore caricamento:', xhr.statusText);
        }
    };

    xhr.onerror = function() {
        console.error('Errore caricamento:', xhr.statusText);
    };

    xhr.send();
}


document.addEventListener('DOMContentLoaded', () => {
    fetch('/getOrder')
        .then(response => response.json())
        .then(data => {
            let details = `
                <p>First Name: ${data.firstName}</p>
                <p>Last Name: ${data.lastName}</p>
                <p>Email: ${data.email}</p>
                <p>Address: ${data.address}</p>
                <p>City: ${data.city}</p>
                <p>Postal Code: ${data.postalCode}</p>
                <p>Country: ${data.country}</p>
                
            `;
            document.getElementById('order-details').innerHTML = details;
        })
        .catch(error => console.error('Error:', error));
});
