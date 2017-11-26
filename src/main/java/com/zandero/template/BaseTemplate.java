package com.zandero.template;

import com.github.mustachejava.Code;
import com.github.mustachejava.Mustache;
import com.zandero.utils.Assert;
import com.zandero.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Base template holds a Mustache template that wraps around other templates
 */
public class BaseTemplate {

	MustacheUtils mustache = new MustacheUtils();

	// storage of templates
	Map<String, Mustache> cache = new HashMap<>();

	public BaseTemplate() {
		// empty
	}

	/**
	 * Initializes base template with resource and stores it under placeholder name
	 *
	 * @param resource template with single mustache placeholder inside
	 */
	public BaseTemplate(String resource) {

		add(resource);
	}

	/**
	 * Removes all stored templates from cache
	 */
	public void clear() {
		cache.clear();
	}

	private void add(Mustache template, String placeholder) {

		Assert.isNull(find(placeholder), "Placeholder: '" + placeholder + "', already present!");
		init(template, placeholder);
	}

	/**
	 * Adds new template
	 *
	 * @param template template with placeholder
	 * @throws IllegalArgumentException in case template already present
	 */
	public void add(Mustache template) {

		String placeholder = findPlaceholder(template);
		add(template, placeholder);
	}

	/**
	 * Adds new template
	 *
	 * @param resource template with placeholder
	 * @throws IllegalArgumentException in case template already present
	 */
	public void add(String resource) {

		Mustache template = mustache.load(resource);
		add(template);
	}

	private void put(Mustache template, String placeholder) {

		init(template, placeholder);
	}

	/**
	 * Adds or overwrites existing template
	 *
	 * @param template template with placeholder
	 */
	public void put(Mustache template) {

		String placeholder = findPlaceholder(template);
		put(template, placeholder);
	}

	/**
	 * Adds or overwrites existing template
	 *
	 * @param resource template with placeholder
	 */
	public void put(String resource) {

		Mustache template = mustache.load(resource);
		String placeholder = findPlaceholder(template);
		put(template, placeholder);
	}

	private static String findPlaceholder(Mustache template) {
		Assert.notNull(template, "Missing template!");

		for (Code code : template.getCodes()) {
			if (code.getName() != null) {
				return code.getName();
			}
		}

		throw new IllegalArgumentException("No base template marker found in template!");
	}

	/**
	 * Gets stored template by placeholder name
	 *
	 * @param placeholder name
	 * @return found template or throws exception
	 * @throws IllegalArgumentException in case placeholder is not present
	 */
	public Mustache get(String placeholder) {
		Mustache base = find(placeholder);
		Assert.notNull(base, "Missing base template: '" + placeholder + "'!");
		return base;
	}

	/**
	 * Finds stored template by placeholder name
	 *
	 * @param placeholder name
	 * @return found template or null if not present
	 */
	public Mustache find(String placeholder) {

		Assert.notNullOrEmptyTrimmed(placeholder, "Missing placeholder name!");
		placeholder = StringUtils.trim(placeholder);
		return cache.get(placeholder);
	}

	private void init(Mustache template, String placeholder) {
		Assert.notNull(template, "Missing base template");
		Assert.notNullOrEmptyTrimmed(placeholder, "Missing placeholder name!");
		placeholder = StringUtils.trim(placeholder);
		cache.put(placeholder, template);
	}

	/**
	 * Renders given template and inserts it into base template with placeholder
	 * @param placeholder of base template
	 * @param resource template
	 * @param data data for template
	 * @return base template with inner template
	 */
	public String render(String placeholder, String resource, Map<String, Object> data) {

		return render(placeholder, mustache.load(resource), data);
	}

	/**
	 * Renders given template and inserts it into base template with placeholder
	 * @param placeholder of base template
	 * @param template template
	 * @param data data for template
	 * @return base template with inner template
	 */
	public String render(String placeholder, Mustache template, Map<String, Object> data) {

		Mustache base = get(placeholder);

		// inner content
		String content = mustache.render(template, data);

		Map<String, Object> baseData = new HashMap<>();
		baseData.put(placeholder, content);
		return mustache.render(base, baseData);
	}

	/**
	 * Renders given baseTemplate with inner template
	 * Does not store baseTemplate into cache
	 *
	 * @param baseResource base template resource
	 * @param resource template resource to insert into base template
	 * @param data data for template
	 * @return base template with inner template
	 */
	public static String direct(String baseResource, String resource, Map<String, Object> data) {

		MustacheUtils mustache = new MustacheUtils();

		Mustache baseTemplate = mustache.load(baseResource);
		Mustache template = mustache.load(resource);

		return direct(baseTemplate, template, data);
	}

	/**
	 * Renders given baseTemplate with inner template
	 * Does not store baseTemplate into cache
	 *
	 * @param baseTemplate base template
	 * @param template template to insert into base template
	 * @param data data for template
	 * @return base template with inner template
	 */
	public static String direct(Mustache baseTemplate, Mustache template, Map<String, Object> data) {

		MustacheUtils mustache = new MustacheUtils();

		String placeholder = findPlaceholder(baseTemplate);

		// inner content
		String content = mustache.render(template, data);

		Map<String, Object> baseData = new HashMap<>();
		baseData.put(placeholder, content);
		return mustache.render(baseTemplate, baseData);
	}
}
