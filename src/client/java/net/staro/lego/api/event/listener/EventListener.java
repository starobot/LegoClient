package net.staro.lego.api.event.listener;

import lombok.Getter;

import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.function.Consumer;

/**
 * A class representing an event listener.
 * It's not actually generic, but I felt like it suits it the best at this context.
 * Also, that way we can annotate the listeners with {@code @Listener}
 */
@Getter
public class EventListener implements Comparable<EventListener> {
    private final Object instance;
    private final Method method;
    private final int priority;
    private final Consumer<Object> consumer;

    public EventListener(Object instance, Method method, int priority) {
        this.instance = instance;
        this.method = method;
        this.priority = priority;
        this.consumer = createConsumer();
    }

    @SuppressWarnings("unchecked")
    private Consumer<Object> createConsumer() {
        try {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            MethodType methodType = MethodType.methodType(void.class, method.getParameters()[0].getType());
            MethodHandle methodHandle = lookup.findVirtual(method.getDeclaringClass(), method.getName(), methodType);
            MethodType invokedType = MethodType.methodType(Consumer.class, method.getDeclaringClass());
            MethodHandle lambdaFactory = LambdaMetafactory.metafactory(
                    lookup, "accept", invokedType, MethodType.methodType(void.class, Object.class), methodHandle, methodType).getTarget();

            return (Consumer<Object>) lambdaFactory.invoke(instance);
        } catch (Throwable throwable) {
            throw new IllegalStateException(throwable.getMessage());
        }
    }

    /**
     * The invoke method accepting the Event.
     *
     * @param event represents the Event class.
     */
    public void invoke(Object event) {
        consumer.accept(event);
    }

    @Override
    public int compareTo(EventListener other) {
        return Integer.compare(other.getPriority(), this.priority);
    }

}
