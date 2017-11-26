package com.zandero.template;

import com.zandero.utils.junit.AssertFinalClass;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 *
 */
public class TemplatesTest {

	@Test
	public void isWellDefined() {
		AssertFinalClass.isWellDefined(Templates.class);
	}

	@Test
	public void renderResource() throws Exception {

		Map<String, Object> data = new HashMap<>();
		data.put("name", "world");
		String result = Templates.render("/templates/hello.html", data);

		assertEquals("Hello world!", result);
	}

	@Test
	public void renderLocalFile() throws Exception {

		String absolutePath = new File("src/test/local.html").getAbsolutePath();

		Map<String, Object> data = new HashMap<>();
		data.put("file", absolutePath);
		String result = Templates.renderFile(absolutePath, data);

		assertEquals("I'm not a resource: " + absolutePath, result);
	}

	@Test
	public void renderJson() throws Exception {

		String result = Templates.renderJson("/templates/hello.html", "{\"name\": \"world\"}");
		assertEquals("Hello world!", result);

		result = Templates.renderJson("/templates/template.html", "{\"name\": \"Jack\", \"years\": 13}");
		assertEquals("Hello my name is Jack!\n" +
		             "I'm 13 years old.", result);
	}

	@Test
	public void renderFileJson() throws Exception {

		String absolutePath = new File("src/test/local.html").getAbsolutePath();
		String result = Templates.renderFileJson(absolutePath, "{\"file\": \"" + absolutePath + "\"}");

		assertEquals("I'm not a resource: " + absolutePath, result);
	}

	@Test(expected = IllegalArgumentException.class)
	public void renderNonExistingFile() {
		try {

			Templates.renderFileJson("/notThere", "{\"file\": \"missing\"}");
			fail();
		}
		catch (IllegalArgumentException e) {
			assertEquals("File not found: '/notThere'", e.getMessage());
			throw e;
		}
	}
}