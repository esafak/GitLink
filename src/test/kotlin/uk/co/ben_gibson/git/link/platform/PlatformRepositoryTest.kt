package uk.co.ben_gibson.git.link.platform

import com.intellij.openapi.components.service
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import org.junit.jupiter.api.Assertions.assertInstanceOf
import uk.co.ben_gibson.git.link.settings.ApplicationSettings
import uk.co.ben_gibson.git.link.settings.ApplicationSettings.CustomHostSettings
import uk.co.ben_gibson.url.Host
import java.util.UUID

class PlatformRepositoryTest : BasePlatformTestCase() {

    fun testCanGetByDomainWithPort() {
        val settings = service<ApplicationSettings>()

        val id = UUID.randomUUID()

        settings.customHosts = listOf(
            CustomHostSettings(
                id.toString(),
                "My Git Server",
                "http://gitea.com:3000",
                "foo",
                "bar",
                "baz"
            )
        )

        val repository = PlatformRepository()

        val platform = repository.getByDomain(Host("gitea.com"))

        assertInstanceOf(Custom::class.java, platform)
    }
}
