var stompClient = null;
let messageList = "";

function connect() {
    var socket = new SockJS('http://localhost:8080/paperwings');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/allMessages', function(message){
            messageList = message.body;
        });
    });
}

connect();



// Cuando el contenido del documento esté cargado y listo
document.addEventListener('DOMContentLoaded', function () {

    // Obtenemos el formulario de mensajes
    const messageForm = document.getElementById('messageForm');

    // Añadimos un evento "submit" al formulario
    messageForm.addEventListener('submit', async function (event) {
        // Evitamos que el formulario realice la acción por defecto (recargar la página)
        event.preventDefault();


        // Obtenemos el contenido del campo de texto de mensaje
        const contentInput = document.getElementById('content');
        const content = contentInput.value.trim();

        // Si el contenido del mensaje está vacío, mostramos una alerta y no continuamos
        if (content === '') {
            alert('Message content cannot be empty.');
            return;
        }

        // Creamos un objeto con el contenido del mensaje
        const message = {
            senderId: 1,
            content: content
        };

        // Enviamos el mensaje al servidor mediante una solicitud POST
        stompClient.send("/app/sendMessage", {}, JSON.stringify(message));

        // Limpiamos el campo de texto y actualizamos el iframe
        contentInput.value = '';
        await refreshIframe();

    });
});


// Función que actualiza el contenido del iframe con los mensajes
async function refreshIframe() {
    const messageListIframe = document.getElementById("messageList");
    stompClient.send("/app/getAllMessages", {}, "")
    const iframeDocument = messageListIframe.contentDocument;
    iframeDocument.open();
    iframeDocument.write(messageList);
    iframeDocument.close();
}

// Actualizamos el iframe cada 1 segundo (1000 ms)
setInterval(refreshIframe, 25);
