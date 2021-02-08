package ru.asshands.softwire.androidacademy2020.persistency

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.asshands.softwire.androidacademy2020.data.Actor
import ru.asshands.softwire.androidacademy2020.data.Genre
import java.util.*

class TConverters {

    // GenreConverter
        @TypeConverter
        fun stringToGenreList(data: String?): List<Genre> {
            if (data == null) {
                return Collections.emptyList()
            }
            return Json.decodeFromString(data)
        }

        @TypeConverter
        fun genreListToString(objects: List<Genre>?): String {
            return Json.encodeToString(objects)
        }


   // ActorConverter
    @TypeConverter
    fun stringToActorList(data: String?): List<Actor> {
        if (data == null) {
            return Collections.emptyList()
        }
        return Json.decodeFromString(data)
    }

    @TypeConverter
    fun actorListToString(objects: List<Actor>?): String {
        return Json.encodeToString(objects)
    }
}

/*
*  Более правильная идея для реализации:
* public class GithubTypeConverters {

    Gson gson = new Gson();

    @TypeConverter
    public static List<SomeObject> stringToSomeObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<SomeObject>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String someObjectListToString(List<SomeObject> someObjects) {
        return gson.toJson(someObjects);
    }
}
*
* */