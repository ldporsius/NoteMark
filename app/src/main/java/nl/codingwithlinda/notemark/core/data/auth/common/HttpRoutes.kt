package nl.codingwithlinda.notemark.core.data.auth.common

object HttpRoutes {
    private const val BASE_URL = "https://notemark.pl-coding.com"
    const val REGISTER_URL = "$BASE_URL/api/auth/register"
    const val LOGIN_URL = "$BASE_URL/api/auth/login"
    const val REFRESH_URL = "$BASE_URL/api/auth/refresh"

    //helper for postman
    //https://notemark.pl-coding.com/api/auth/register

    /*{
        "username":"linda",
        "email":"fake@fake.com",
        "password":"lipo"
    }
*/


}
