package Lists

import Student
import java.io.File
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import Lists.StudentStrategy

class StudentListYAML: SuperStudentListClass(), StudentStrategy  {

    override fun readFromFile(path:String)
    {
        val mapper = ObjectMapper(YAMLFactory()).registerKotlinModule()
        data = mapper.readValue(File(path), mapper.typeFactory.constructCollectionType(MutableList::class.java, Student::class.java))
    }

    override fun writeToFile(path:String)
    {
        val file = File(path)
        val yamlMapper = ObjectMapper(YAMLFactory())
        yamlMapper.writeValue(file, data)
    }
}