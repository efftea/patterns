package view
import view.Params

data class Filter(
    val nameFilter: String,
    val gitFilter: String,
    val emailFilter: String,
    val phoneFilter: String,
    val telegramFilter: String,
    val gitSearch: Params,
    val phoneSearch: Params,
    val telegramSearch: Params,
    val emailSearch: Params,
)