package net.staro.api.trait;

import com.google.gson.JsonElement;

/**
 * A trait for anything that ca be converted to a json object.
 */
public interface Jsonable
{
    /**
     * Creates a new json object.
     * @return json object as JsonElement.
     */
    JsonElement toJson();

    /**
     * Reads the json and implements its values to the client.
     * @param element is the object within the json file.
     */
    void fromJson(JsonElement element);

    /**
     * Gets the name of the config file.
     * @return name as a String.
     */
    default String getConfigName() {
        return null;
    }

}
