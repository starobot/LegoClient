# LEGO CLIENT
An open-source utility client base for making a well written client/mod as fast and easy as possible.
### Make modules
```java
public class YourModule extends LegoModule {
    public YourModule(Lego lego) {
        super(lego, "YourModule", Category.RENDER, "Does something on your screen");
    }

}
```
### Make commands
```java
public class YourCommand extends LegoCommand {
    public YourCommand(Lego lego) {
        super(lego, "command", "Does something");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(context -> { // execute immediately
            // do something
            return COMPLETED;
        });
    }

}
```

## Why Lego class is the argument in everything?
Lego is the record class with all the necessary managers and the eventbus. It serves as an access point for all of them. Whenever you need a manager instance, you can do this:
```java
      lego.moduleManager().getModules()
```
## How to use the event bus?
```java
      // subscribe the listener class to the eventBus
      lego.eventBus().subscribe(new ListenerClass());
      
      // post the event to the eventBus.
      lego.eventBus().post(new CustomEvent());
```

Using events within the listener classes
```java
    @Listener // for a regular listener
    public void onEvent(CustomEvent event) { // the method MUST BE PUBLIC
        // do something.
    }

    @SafeListener // for a nullsafe listener (the listeer that checks mc.player, mc.world and mc.interactionManager for being null)
    public void onEvent(CustomEvent event) {
        // do something.
    }
```
