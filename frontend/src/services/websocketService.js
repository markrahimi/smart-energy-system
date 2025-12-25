import { HOST } from '../config.js'

let socket = null;
let observers = [];

export function connect() {
    if (socket) return;

    const wsUrl = HOST.replace('http', 'ws');
    socket = new WebSocket(wsUrl);

    socket.onmessage = (event) => {
        const data = JSON.parse(event.data);
        observers.forEach(observer => observer(data));
    };

    socket.onerror = (err) => {
        console.error('WebSocket error:', err);
    };

    socket.onclose = () => {
        socket = null;
    };
}

export function onMessage(observer) {
    observers.push(observer);
}

export function removeObserver(observerToRemove) {
    observers = observers.filter(obs => obs !== observerToRemove);
}

export function disconnect() {
    if (socket) {
        socket.close();
        socket = null;
    }
}
