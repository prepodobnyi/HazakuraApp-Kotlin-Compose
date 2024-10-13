package com.ru.hazakura.util

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import java.util.Base64

fun decodeBase64(url: String): String {
    return try {
        val decodedBytes = Base64.getDecoder().decode(url.toByteArray())
        String(decodedBytes)
    } catch (e: IllegalArgumentException) {
        val decodedBytes = Base64.getDecoder().decode((url + "==").toByteArray())
        String(decodedBytes).replace("https:", "")
    }
}
fun convertChar(char: Char): Char {
    val alph = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    val isLower = char.isLowerCase()
    val upperChar = char.uppercaseChar()
    return if (alph.contains(upperChar)) {
        val newChar = alph[(alph.indexOf(upperChar) + 13) % alph.length]
        if (isLower) newChar.lowercaseChar() else newChar
    } else {
        char
    }
}
fun convert(string: String): String {
    return string.map { convertChar(it) }.joinToString("")
}

class Migration1To2 : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Здесь вы можете добавить SQL-запросы или другие инструкции, чтобы обновить схему базы данных
        // с версии 1 до версии 2.
        // Например, если вы добавляете новое поле в сущность, вы можете добавить следующий запрос:
        database.execSQL("ALTER TABLE AnimeEntity ADD COLUMN progress TEXT NOT NULL DEFAULT 'watch'")
    }
}

class Migration2To3 : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE AnimeEntity ADD COLUMN lastDubbbing_new TEXT NULL DEFAULT NULL")
        database.execSQL("UPDATE AnimeEntity SET lastDubbbing_new = lastDubbbing")
        database.execSQL("ALTER TABLE AnimeEntity RENAME COLUMN lastDubbbing TO lastDubbbing_old")
        database.execSQL("ALTER TABLE AnimeEntity RENAME COLUMN lastDubbbing_new TO lastDubbbing")
        database.execSQL("ALTER TABLE AnimeEntity DROP COLUMN lastDubbbing_old")
    }
}


val genreMap = mapOf(
    "Сёнен" to "27",
    "Сёдзё" to "25",
    "Сэйнэн" to "42",
    "Дзёсей" to "43",
    "Детское" to "15",
    "Экшен" to "1",
    "Авангард" to "5",
    "Приключения" to "2",
    "Комедия" to "4",
    "Этти" to "9",
    "Фэнтези" to "10",
    "Ужасы" to "14",
    "Повседневность" to "36",
    "Триллер" to "117",
    "Тайна" to "7",
    "Романтика" to "22",
    "Гурман" to "543",
    "Сёдзё-ай" to "129",
    "Сёнен-ай" to "133",
    "Спорт" to "30",
    "Сверхъестественное" to "37",
    "Драма" to "8",
    "Фантастика" to "24",
    "Эротика" to "539",
    "Хентай" to "12",
    "Яой" to "33",
    "Юри" to "34"

)

val themeMap = mapOf(
    "Реверс-гарем" to "125",
    "Исэкай" to "130",
    "Забота о детях" to "134",
    "Пародия" to "20",
    "Исполнительское искусство" to "142",
    "Питомцы" to "148",
    "Магическая смена пола" to "135",
    "Антропоморфизм" to "143",
    "Командный спорт" to "102",
    "Романтический подтекст" to "151",
    "Любовный многоугольник" to "107",
    "Супер сила" to "31",
    "Военное" to "38",
    "Вампиры" to "32",
    "Идолы (Жен.)" to "145",
    "Психологическое" to "40",
    "Выживание" to "141",
    "Реинкарнация" to "106",
    "Кроссдрессинг" to "144",
    "CGDCT" to "119",
    "Медицина" to "147",
    "Боевые искусства" to "17",
    "Самураи" to "21",
    "Школа" to "23",
    "Космос" to "29",
    "Гарем" to "35",
    "Меха" to "18",
    "Исторический" to "13",
    "Гонки" to "3",
    "Махо-сёдзё" to "124",
    "Идолы (Муж.)" to "150",
    "Видеоигры" to "103",
    "Образовательное" to "149",
    "Работа" to "139",
    "Шоу-бизнес" to "136",
    "Удостоено наград" to "114",
    "Жестокость" to "105",
    "Иясикэй" to "140",
    "Музыка" to "19",
    "Гэг-юмор" to "112",
    "Игра с высокими ставками" to "146",
    "Мифология" to "6",
    "Спортивные единоборства" to "118",
    "Культура отаку" to "137",
    "Путешествие во времени" to "111",
    "Взрослые персонажи" to "104",
    "Детектив" to "39",
    "Стратегические игры" to "11",
    "Изобразительное искусство" to "108",
    "Организованная преступность" to "138",
    "Хулиганы" to "131"
)