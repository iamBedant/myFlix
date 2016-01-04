package com.myflix.bebi2.myflix.Utils;

import com.squareup.otto.Bus;

/**
 * Created by bebi2 on 1/3/2016.
 */
public class EventBus extends Bus {
    private static final EventBus bus = new EventBus();

    public static Bus getInstance() {
        return bus;
    }

    private EventBus() {

    }
}
