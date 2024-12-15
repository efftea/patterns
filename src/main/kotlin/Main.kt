fun main(args: Array<String>) {
    var manager = StudentListStrategy(StudentListTxt())
    manager.readFromFile("src/main/kotlin/input.txt")
    println(manager.data)
    println(manager.getById(1))
    manager.writeToFile("src/main/kotlin/output.txt")

    manager.setStrategy(StudentListJSON())
    manager.readFromFile("src/main/kotlin/input.json")
    println(manager.data)
    println(manager.getById(2))
    manager.writeToFile("src/main/kotlin/output.json")

    manager.setStrategy(StudentListYAML())
    manager.readFromFile("src/main/kotlin/input.yaml")
    println(manager.data)
    println(manager.getById(3))
    manager.writeToFile("src/main/kotlin/output.yaml")
}