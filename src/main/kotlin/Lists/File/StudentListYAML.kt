package Lists.File

import Lists.SuperStudentListClass
import model.Student
import java.io.File
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.DumperOptions

class StudentListYAML : SuperStudentListClass(), StudentListFileInterface {

    private val yaml = Yaml()

    override fun readFromFile(path: String): MutableList<Student> {
        val file = File(path)
        if (!file.exists() || !file.isFile) {
            throw IllegalArgumentException("Некорректный адрес файла: $path")
        }

        return try {
            yaml.loadAs(file.inputStream().reader(), MutableList::class.java) as MutableList<Student>
        } catch (e: Exception) {
            throw IllegalArgumentException("Ошибка при чтении файла YAML: ${e.message}")
        }
    }

    override fun writeToFile(students: MutableList<Student>, directory: String, fileName: String) {
        val file = File(directory, fileName)

        try {
            file.parentFile.mkdirs() // Создание всех необходимых директорий
            file.writeText(yaml.dump(students)) // Запись данных в файл
        } catch (e: Exception) {
            throw IllegalArgumentException("Ошибка при записи в файл YAML: ${e.message}")
        }
    }
}