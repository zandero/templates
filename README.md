# templates
Wrapper and helpers around Mustache templates 

## Setup
```xml
<dependency>      
     <groupId>com.zandero</groupId>      
     <artifactId>templates</artifactId>      
     <version>1.3.1</version>      
</dependency>
```

## Mustache utils usage

### Basic use
```java
    MustacheUtils utils = new MustacheUtils();
    Mustache template = utils.load("/templates/hello.html");
    
    Map<String, String> data = new HashMap<>();
    data.put("name", "world");
    
    String result = utils.render(template, data);
```

### Use JSON instead of Map for data
```java
    MustacheUtils utils = new MustacheUtils();
    Mustache template = utils.load("/templates/hello.html");
    String result = utils.renderJson(template, "{\"name\": \"world\"}");
```

### Use name value pairs
```java
    MustacheUtils utils = new MustacheUtils();
    Mustache template = utils.load("/templates/hello.html");
    String result = utils.renderStrings(template, "name", "world");
```

## Templates usage
Class wrapping MustacheUtils to simplify usage.

```java
    Map<String, Object> data = new HashMap<>();
    data.put("name", "world");
    String result = Templates.render("/templates/hello.html", data);
```

```java
    String result = Templates.renderJson("/templates/hello.html", "{\"name\": \"world\"}");
```

## Tamplate inside template
Using a base template to wrap around other templates.