const io = require("socket.io")();

const clients = [];

io.on("connection", socket => {
    socket.emit('clientId', socket.id);

    socket.on("join", function(name) {
        console.log("Client " + name + " joined");
        const client = new Client(socket.id, name);
        clients.push(client);
        io.emit("updateClients", clients);
    })

    socket.on("message", function(message) {
        const clientName = clients.find(client => client.id == socket.id).name;
        io.emit("newMessage", clientName, message);
    })

    socket.on("toggleReady", function() {
        const isReady = clients.find(client => client.id == socket.id).ready;
        clients.find(client => client.id == socket.id).ready = !isReady;
        io.emit("updateClients", clients);

        if (clients.length > 1) {
            const gameReady = clients.every(client => client.ready);

            if (gameReady) {
                io.emit("gameReady");
            } else {
                io.emit("gameCanceled");
            }
        }
    });
});

io.listen(3000);

function Client(id, name) {
    this.id = id;
    this.name = name;
    this.ready = false;
}

