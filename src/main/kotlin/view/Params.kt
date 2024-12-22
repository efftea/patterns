package view

enum class Params {
    YES,
    NO,
    DONT_MATTER;
    companion object {
        @JvmStatic
        fun create(searchParam: String): Params {
            return when (searchParam) {
                "Да" -> YES
                "Нет" -> NO
                else -> DONT_MATTER
            }
        }
    }
}