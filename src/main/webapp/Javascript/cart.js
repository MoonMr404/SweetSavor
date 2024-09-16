document.addEventListener('DOMContentLoaded', function () {
    var quantityInputs = document.querySelectorAll('input[type=number]');

    quantityInputs.forEach(function (input) {
        input.addEventListener('keydown', function (e) {
            e.preventDefault();
        });
    });
});


// admin.js
$(document).ready(function () {
    // Carica la sezione di default
    loadSection('jsp/addProduct.jsp');

    // Gestisce il clic sui link della sidebar
    $('.sidebar-link').on('click', function (e) {
        e.preventDefault();
        var url = $(this).data('url');
        loadSection(url);
    });
});

function loadSection(url) {
    $.ajax({
        url: url,
        type: 'GET',
        success: function (response) {
            $('#content').html(response);
        },
        error: function (xhr, status, error) {
            console.error('Errore caricamento:', error);
        }
    });
}


document.addEventListener('DOMContentLoaded', function () {
    var quantityInputs = document.querySelectorAll('.quantity-input');

    // Aggiorna il totale dell'importo quando cambia la quantità dei prodotti
    quantityInputs.forEach(function (input) {
        input.addEventListener('change', function () {
            var index = this.getAttribute('data-index');
            var newQuantity = parseInt(this.value);
            var prezzoProdotto = parseFloat(document.querySelector('.product-info:nth-child(' + (index + 1) + ') .prezzo-prodotto').textContent.trim());
            var nuovoImporto = newQuantity * prezzoProdotto;
            document.getElementById('total-amount').textContent = nuovoImporto.toFixed(2);
        });
    });

    // Funzione per rimuovere un prodotto
    window.removeProduct = function (button) {
        var productItem = button.closest('.product-item');
        productItem.remove();
        // Eventualmente aggiungi qui la logica per rimuovere il prodotto dal carrello sul backend
    };
});


function removeProduct(productName) {
    if (confirm('Sei sicuro di voler rimuovere questo prodotto dal carrello?')) {
    var form = document.createElement('form');
    form.method = 'post';
    form.action = '<%=request.getContextPath()%>/RemoveProductFromCart';

    var input = document.createElement('input');
    input.type = 'hidden';
    input.name = 'nome';
    input.value = productName;

    form.appendChild(input);
    document.body.appendChild(form);
    form.submit();
}
}