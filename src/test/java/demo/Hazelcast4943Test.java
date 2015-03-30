package demo;

import java.io.IOException;
import java.net.URI;
import java.util.Properties;
import javax.cache.CacheManager;
import javax.cache.Caching;

import org.junit.Test;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 *
 * @author Stephane Nicoll
 */
public class Hazelcast4943Test {

	@Test
	public void customUri() throws IOException {
		Resource resource = new ClassPathResource("hazelcast-specific.xml");
		URI configurationURI = resource.getURI();

		Caching.getCachingProvider().getCacheManager(configurationURI,
				Hazelcast4943Test.class.getClassLoader());
	}

	@Test
	public void worksWithExtraProperties() throws IOException {
		Resource resource = new ClassPathResource("hazelcast-specific.xml");
		URI configurationURI = resource.getURI();

		Properties properties = new Properties();
		properties.setProperty("hazelcast.config.location", configurationURI.toString());

		// Fail - call it with "properties" as the third argument -> works
		CacheManager cacheManager = null;
		try {
			cacheManager = Caching.getCachingProvider().getCacheManager(configurationURI,
					Hazelcast4943Test.class.getClassLoader(), properties);
		}
		finally {
			if (cacheManager != null) {
				cacheManager.close();
			}
		}
	}
}
