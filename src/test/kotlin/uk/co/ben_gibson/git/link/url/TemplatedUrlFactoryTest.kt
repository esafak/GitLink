package uk.co.ben_gibson.git.link.url
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import uk.co.ben_gibson.git.link.git.File
import uk.co.ben_gibson.git.link.platform.Custom
import uk.co.ben_gibson.git.link.ui.Icons
import uk.co.ben_gibson.git.link.url.factory.TemplatedUrlFactory
import uk.co.ben_gibson.git.link.url.template.UrlTemplates
import uk.co.ben_gibson.url.Host
import uk.co.ben_gibson.url.URL
import java.util.UUID
import java.util.stream.Stream
class TemplatedUrlFactoryTest {
    companion object {
        private val REMOTE_BASE_URL = URL.fromString("http://example.com/my/repo")
        private const val BRANCH = "master"
        private val FILE = File("Foo.java", false, "src", false)
        @JvmStatic
        fun urlExpectationsProvider(): Stream<Arguments> = Stream.of(
            Arguments.of(
                "example.com:8080",
                "http://example.com:8080/my/repo"
            ),
            Arguments.of(
                "example.com",
                "http://example.com/my/repo"
            )
        )
    }
    @ParameterizedTest
    @MethodSource("urlExpectationsProvider")
    fun canGenerateUrlForCustomPlatform(host: String, expectedUrl: String) {
        val platform = Custom(
            UUID.randomUUID(),
            "Test",
            Icons.GIT,
            setOf(Host(host))
        )
        val factory = TemplatedUrlFactory(
            UrlTemplates(
                "{remote:url:protocol}://{remote:url:host}/{remote:url:path}",
                "foo",
                "bar"
            ),
            platform
        )
        val options = UrlOptions.UrlOptionsFileAtBranch(FILE, BRANCH)
        val url = factory.createUrl(REMOTE_BASE_URL, options)
        assertEquals(expectedUrl, url.toString())
    }
}
