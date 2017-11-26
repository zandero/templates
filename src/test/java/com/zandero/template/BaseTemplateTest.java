package com.zandero.template;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 *
 */
public class BaseTemplateTest {

	@Test
	public void render() throws Exception {

		BaseTemplate base = new BaseTemplate("/templates/base.html");

		Map<String, Object> data = new HashMap<>();
		data.put("name", "Genious");

		String output = base.render("marker", "/templates/hello.html", data);
		assertEquals("==== Hello Genious! ====", output);
	}

	@Test
	public void directRenderAutoPlaceholder() {

		Map<String, Object> data = new HashMap<>();
		data.put("name", "Genious");

		String output = BaseTemplate.direct("/templates/base.html", "/templates/hello.html", data);
		assertEquals("==== Hello Genious! ====", output);
	}

	@Test
	public void addTemplateTest() {

		BaseTemplate base = new BaseTemplate();
		base.add("/templates/base.html");

		Map<String, Object> data = new HashMap<>();
		data.put("name", "Genious");

		String output = base.render("marker", "/templates/hello.html", data);
		assertEquals("==== Hello Genious! ====", output);
	}

	@Test
	public void duplicateAddTest() {

		BaseTemplate base = new BaseTemplate();
		base.add("/templates/base.html");
		assertNotNull(base.find("marker"));
		base.clear();

		assertNull(base.find("marker"));

		base.add("/templates/base.html");
		assertNotNull(base.find("marker"));

		try {
			base.add("/templates/base.html");
			fail();
		}
		catch (IllegalArgumentException e) {
			assertEquals("Placeholder: 'marker', already present!", e.getMessage());
		}
	}

	@Test
	public void duplicatePutTest() {

		MustacheUtils mustache= new MustacheUtils();

		BaseTemplate base = new BaseTemplate();
		base.put("/templates/base.html");
		assertNotNull(base.find("marker"));

		base.put("/templates/base.html");
		base.put(mustache.load("/templates/base.html"));

		Map<String, Object> data = new HashMap<>();
		data.put("name", "Genious");

		String output = base.render("marker", "/templates/hello.html", data);
		assertEquals("==== Hello Genious! ====", output);
	}

	@Test
	public void noMarkerInBaseTemplateTest() {

		try {
			new BaseTemplate("/templates/noTemplate.html");
			fail();
		}
		catch (IllegalArgumentException e) {
			assertEquals("No base template marker found in template!", e.getMessage());
		}
	}
}