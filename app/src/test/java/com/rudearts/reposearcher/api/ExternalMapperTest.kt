package com.rudearts.reposearcher.api

import android.content.Context
import com.nhaarman.mockitokotlin2.*
import com.rudearts.reposearcher.model.external.OwnerExternal
import com.rudearts.reposearcher.model.external.RepoExternal
import com.rudearts.reposearcher.util.ExternalMapper
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Spy
import org.mockito.junit.MockitoJUnit
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class ExternalMapperTest {

    @Rule
    @JvmField
    val mockitoRule = MockitoJUnit.rule()

    @Mock lateinit var context:Context

    @InjectMocks @Spy lateinit var mapper: ExternalMapper

    @Test
    fun track2local_WithRest() {
        val owner = mock<OwnerExternal> {
            on { login } doReturn "none"
        }

        val repo = mock<RepoExternal> {
            on { name } doReturn "none"
            on { description } doReturn "none"
            on { this.owner } doReturn owner
        }

        mapper.repo2local(repo)

        verify(mapper, times(2)).text2unknown(anyOrNull())
    }
}