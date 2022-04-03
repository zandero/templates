package com.zandero.template;

import com.github.mustachejava.Mustache;
import com.zandero.utils.ResourceUtils;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MustacheUtilsTest {

	MustacheUtils utils = new MustacheUtils();

	@Test
	public void testLoadTemplate() throws Exception {

		Mustache template = utils.load("/templates/hello.html");

		Map<String, String> data = new HashMap<>();
		data.put("name", "world");

		String result = utils.render(template, data);
		assertEquals("Hello world!", result);
	}

	@Test
	public void renderJsonTest() {

		Mustache template = utils.load("/templates/hello.html");
		String result = utils.renderJson(template, "{\"name\": \"world\"}");
		assertEquals("Hello world!", result);
	}

	@Test
	public void renderNameValuesTest() {

		Mustache template = utils.load("/templates/hello.html");
		String result = utils.renderStrings(template, "name", "world");
		assertEquals("Hello world!", result);
	}

	@Test
	public void testRenderTemplate() throws Exception {

		String content = ResourceUtils.readFileToString(new File("src/test/local.html"));
		Mustache template = utils.compile(content, "myTemplate");

		Map<String, String> data = new HashMap<>();
		data.put("file", "some.file");

		String result = utils.render(template, data);
		assertEquals("I'm not a resource: some.file", result);
	}
}