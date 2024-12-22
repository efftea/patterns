package observer

interface observerServ {
    val observers: MutableList<observer>

    fun addObserver(observer: observer) {
        observers.add(observer)
    }

    fun removeObserver(observer: observer) {
        observers.remove(observer)
    }

    fun notifyObservers() {
        observers.forEach { it.update() }
    }
}