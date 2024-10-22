package net.staro.api;

import net.staro.lego.Lego;

/**
 * Represents a manager responsible for creating instances of a certain aspect of the project.
 */
public interface Manager {
    /**
     * Initializes the manager with the provided Lego instance
     *
     * @param lego The Lego instance providing access to various components and functionalities
     */
    void initialize(Lego lego);

}
