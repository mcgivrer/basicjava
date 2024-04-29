# Configuration

The configuration module in MainApp consists into 3 methods:

1. `initalization()` parsing CLI and load configuration
2. `loadConfiugration()` loading a properties file to populate the configuration attributes
3. `extractConfigAttributes()` extract values from `Map<String,String>` to set configuration values.

let's look at the `loadConfiguration` method.

## loadConfiguration

Load a Properties file from the `configFilePath`, then parse all the Property item to extract value.

```java
public class MainApp{
    //...
    protected void loadConfigurationFrom(String configFilePath) {
        try {
            info(MainApp.class, "Load configuration from file %s", configFilePath);
            config.load(MainApp.class.getResourceAsStream(configFilePath));
            Map<String, String> maps = config.entrySet().stream()
                .collect(Collectors.toMap(e -> (String) e.getKey(), e -> (String) e.getValue()));
            extractConfigAttributes(maps);
        } catch (IOException e) {
            error(MainApp.class, "Unable to read Configuration file");
        }
    }
//...
}
```

## extractConfigAttributes

From  Map<String,String>, extract all the values