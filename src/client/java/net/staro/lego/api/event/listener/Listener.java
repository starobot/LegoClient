package net.staro.lego.api.event.listener;

import net.staro.lego.api.event.bus.Priority;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark methods as event listeners.
 * The methods are required to be public.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Listener {
    Priority priority() default Priority.DEFAULT;

}
