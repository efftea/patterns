interface StudentStrategy {
    var data:MutableList<Student>
    fun readFromFile(path: String)
    fun writeToFile(path: String)
}

class StudentListStrategy(private var strategy: StudentStrategy):SuperStudentListClass() {
    fun setStrategy(strategy: StudentStrategy) {
        this.strategy = strategy
    }

    fun readFromFile(path: String) {
        strategy.readFromFile(path)
        this.data = strategy.data
    }

    fun writeToFile(path:String) {
        this.data = strategy.data
        strategy.writeToFile(path)
    }
}