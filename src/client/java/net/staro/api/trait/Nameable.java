package net.staro.api.trait;

public interface Nameable
{
    String getName();

    default String getNameLowerCase()
    {
        return this.getName().toLowerCase();
    }

    static <T extends Nameable> T getByName(Iterable<T> nameables, String name)
    {
        for (T t : nameables)
        {
            if (name.equals(t.getName()))
            {
                return t;
            }
        }

        return null;
    }

    static <T extends Nameable> T getByNameIgnoreCase(Iterable<T> nameables, String name)
    {
        for (T t : nameables)
        {
            if (name.equalsIgnoreCase(t.getName()))
            {
                return t;
            }
        }

        return null;
    }

}
