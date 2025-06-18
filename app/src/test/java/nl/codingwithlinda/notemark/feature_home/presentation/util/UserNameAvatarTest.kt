package nl.codingwithlinda.notemark.feature_home.presentation.util

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.Test

class UserNameAvatarTest {

    @Test
    fun testAvatar(){
        val res = userNameAvatar("Linda Porsius")
        assertThat(res).isEqualTo("LP")

        val res2 = userNameAvatar("Linda D. Porsius")
        assertThat(res2).isEqualTo("LP")

        val res3 = userNameAvatar("LindaPorsius")
        assertThat(res3).isEqualTo("LI")

        val res4 = userNameAvatar("Linda")
        assertThat(res4).isEqualTo("LI")

        val res5 = userNameAvatar("l")
        assertThat(res5).isEqualTo("L")
    }
}