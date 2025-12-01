package hu.kertesi.habittracker.data

data class HabitData(
    val name: String,
    val goal: String,
    val completed: Boolean = false
)
