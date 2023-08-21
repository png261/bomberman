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

    socket.on("placeBomb", function() {
        io.emit("placeBomb", socket.id);
    })
    socket.on("moveUp", function() {
        io.emit("moveUp", socket.id);
    })
    socket.on("moveDown", function() {
        io.emit("moveDown", socket.id);
    })
    socket.on("moveRight", function() {
        io.emit("moveRight", socket.id);
    })
    socket.on("moveLeft", function() {
        io.emit("moveLeft", socket.id);
    })
    socket.on("idle", function() {
        io.emit("idle", socket.id);
    })
    socket.on("dead", function() {
        io.emit("dead", socket.id);
    })
});

io.listen(3000);

function Client(id, name) {
    this.id = id;
    this.name = name;
    this.ready = false;
}

