package gui.util;

import java.util.ArrayList;
import java.util.List;

public interface Subject {
    List<Listener> listeners = new ArrayList<>();

    public default void addListener(Listener listener) {
        this.listeners.add(listener);
    }

    public default Listener removeListener(Listener listener) {
        this.listeners.remove(listener);
        return listener;
    }

    public default void notify(Object obj) {
        for (Listener listener: listeners) {
            listener.update(obj);
        }
    }
}
