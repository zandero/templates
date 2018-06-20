package com.zandero.template;

import com.github.mustachejava.Mustache;

import java.util.Map;

/**
 * Wrapper around mustache utils to simplify template rendering
 */
public final class Templates {

	static final MustacheUtils mustache = new MustacheUtils();

	private Templates() {
		// hide constructor
	}

	/**
	 * Directly renders given template
	 *
	 * @param template mustache template
	 * @param name     to store template into cache
	 * @param data     objects
	 * @return rendered output
	 */
	public static String render(String template, String name, Map<String, Object> data) {

		Mustache mustacheTemplate = mustache.compile(template, name);
		return mustache.render(mustacheTemplate, data);
	}

	/**
	 * Loads resource file and renders it
	 *
	 * @param resource to be loaded
	 * @param data     objects
	 * @return rendered output
	 */
	public static String render(String resource, Map<String, Object> data) {

		Mustache mustacheTemplate = mustache.load(resource);
		return mustache.render(mustacheTemplate, data);
	}

	/**
	 * Loads resource file and renders it
	 *
	 * @param resource   to be loaded
	 * @param dataAsJson json map
	 * @return rendered output
	 */
	public static String renderJson(String resource, String dataAsJson) {

		Mustache mustacheTemplate = mustache.load(resource);
		return mustache.renderJson(mustacheTemplate, dataAsJson);
	}

	/**
	 * Loads resource file and renders it
	 *
	 * @param resource to be loaded
	 * @param data     name value pairs
	 * @return rendered output
	 */
	public static String renderStrings(String resource, String... data) {

		Mustache mustacheTemplate = mustache.load(resource);
		return mustache.renderStrings(mustacheTemplate, data);
	}

	/**
	 * Loads resource file and renders it
	 *
	 * @param file to be loaded
	 * @param data objects
	 * @return rendered output
	 */
	public static String renderFile(String file, Map<String, Object> data) {

		Mustache mustacheTemplate = mustache.loadFile(file);
		return mustache.render(mustacheTemplate, data);
	}

	/**
	 * Loads resource file and renders it
	 *
	 * @param file       to be loaded
	 * @param dataAsJson json map
	 * @return rendered output
	 */
	public static String renderFileJson(String file, String dataAsJson) {

		Mustache mustacheTemplate = mustache.loadFile(file);
		return mustache.renderJson(mustacheTemplate, dataAsJson);
	}

	/**
	 * Loads resource file and renders it
	 *
	 * @param file to be loaded
	 * @param data name value pairs
	 * @return rendered output
	 */
	public static String renderFileStrings(String file, String... data) {

		Mustache mustacheTemplate = mustache.load(file);
		return mustache.renderStrings(mustacheTemplate, data);
	}
}
