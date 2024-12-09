class DataTable(private val data: List<List<Any?>>) {

    fun getElementBy(row:Int,column:Int):Any?
    {
        return data.getOrNull(row)?.getOrNull(column)
    }

    fun getRowsNum():Int
    {
        return data.size
    }

    fun getColumnsNum():Int
    {
        return data[0].size
    }
}