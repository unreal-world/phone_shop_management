package com.mycompany.phonestore.observer.ConcreteObserver;

import com.mycompany.phonestore.observer.Observer.OrderObserver;

public class AdminObserver implements OrderObserver {

    @Override
    public void update(String message) {
        System.out.println("[ADMIN] " + message);
    }
}