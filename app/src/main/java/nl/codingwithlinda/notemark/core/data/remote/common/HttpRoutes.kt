package nl.codingwithlinda.notemark.core.data.remote.common

object HttpRoutes {
    private const val BASE_URL = "https://notemark.pl-coding.com"
    const val REGISTER_URL = "$BASE_URL/api/auth/register"
    const val LOGIN_URL = "$BASE_URL/api/auth/login"
    const val REFRESH_URL = "$BASE_URL/api/auth/refresh"
    const val CREATE_NOTE_URL = "$BASE_URL/api/notes"
    const val FETCH_NOTES_URL = "$BASE_URL/api/notes"

    //helper for postman
    //https://notemark.pl-coding.com/api/auth/register

    /*{
        "username":"linda",
        "email":"fake@fake.com",
        "password":"lipo"
    }
*/


}
