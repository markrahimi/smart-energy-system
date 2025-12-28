let observers = []

export function onNewMessage(observerToRegister) {
  observers.push(observerToRegister)
}

export function removeObserver(observerToRemove) {
  observers = observers.filter(obs => obs !== observerToRemove)
}

export function sendMessage(message) {
  observers.forEach(observer => observer(message))
}
