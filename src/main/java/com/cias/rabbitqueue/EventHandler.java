package com.cias.rabbitqueue;

public class EventHandler {
    public static void handler(Events event, Object message) {
        switch (event) {
		default:
			break;
       /* case Event1:
            System.out.println("publish 1 event" + message);
            break;
        case Event2:
            System.out.println("publish event 2" + message);
            break;*/
        }
    }
}