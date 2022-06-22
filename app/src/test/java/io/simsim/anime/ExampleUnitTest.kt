package io.simsim.anime

import io.simsim.anime.data.entity.AnimeFullResponse
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testReflection() {
        println(AnimeFullResponse.AnimeFullData::class.typeParameters)
    }
}
