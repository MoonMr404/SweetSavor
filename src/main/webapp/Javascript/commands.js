
//funnzione per la gestione della compatibilità
function createXMLHttprequest(){
    var request;
    try{
        request = new XMLHttpRequest();
    } catch(e){
        try{
            request = new ActiveXObject("Msxml2.XMLHTTP");
        } catch (e){
            try{
                request = new ActiveXObject("Microsoft.XMLHTTP");
            } catch (e){
                alert("AJAX non supportato dal browser");
                return null
            }
        }
    }
    return request;
}



function validateRegistrationForm() {
    var username = document.getElementById("username").value;
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;

    if (email.trim() === '' && password.trim() === '' && username.trim() === '') {
        alert("Completare tutti i campi");
        return false; // Non inviare la richiesta
    }

    return true; // Invia la richiesta POST
}

function validateLoginForm() {
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;

    if (email.trim() === '' || password.trim() === '') {
        alert("Completare tutti i campi");
        return false; // Non inviare la richiesta
    }

    return true; // Invia la richiesta POST
}



//funzione  per addToCart
function addToCart() {
    const productName = "<%= prodotto..getNomeProdotto() %>";
    const url = `./CartInteraction?action=addProdotto&name=${productName}&page=prodotto.jsp`;

    window.location.href = url;
}


document.addEventListener("DOMContentLoaded", function() {
    const basePath = "<%= request.getContextPath() %>/prodotto.jsp?nome=";
    document.querySelectorAll('.main-product-item').forEach(item => {
        const productName = item.getAttribute('data-nome');
        const productLink = item.querySelector('.main-product-link');
        productLink.href = basePath + encodeURIComponent(productName);
        productLink.addEventListener('click', function(event) {
            event.preventDefault(); // Previene il comportamento predefinito del link
            window.location.href = productLink.href; // Reindirizza alla pagina del prodotto
        });
    });
});