package diaballik.resource;

import com.github.hanleyt.JerseyExtension;
import diaballik.serialization.DiabalikJacksonProvider;
import java.net.URI;
import javafx.scene.paint.Color;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestGameResource {
	@SuppressWarnings("unused") @RegisterExtension JerseyExtension jerseyExtension = new JerseyExtension(this::configureJersey);

	private Application configureJersey() {
		return new ResourceConfig(GameResource.class).
				register(MyExceptionMapper.class).
				register(JacksonFeature.class).
				register(DiabalikJacksonProvider.class).
				property("jersey.config.server.tracing.type", "ALL");
	}

	@BeforeEach
	void setUp() {
	}

	@Test
	void testTemplate(final Client client, final URI baseUri) {
		client.register(JacksonFeature.class).register(DiabalikJacksonProvider.class);

		final Response res = client.
			target(baseUri).
			path("foo/bar"). //TODO
			request().
			put(Entity.text(""));

	}
}
