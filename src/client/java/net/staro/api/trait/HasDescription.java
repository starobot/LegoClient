package net.staro.api.trait;

/**
 * A trait for something that has a description. Useful to let users know what certain object do.
 */
public interface HasDescription {
    /**
     * Gets the description of the object.
     * @return description as a String.
     */
    String getDescription();

}
