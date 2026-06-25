package com.mycompany.phonestore.observer.ConcreteSubject;

import java.util.ArrayList;
import java.util.List;

import com.mycompany.phonestore.observer.Observer.OrderObserver;
import com.mycompany.phonestore.observer.Subject.OrderSubject;

public class OrderNotificationSubject implements OrderSubject {

    private final List<OrderObserver> observers = new ArrayList<>();

    @Override
    public void addObserver(OrderObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(OrderObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String message) {

        for (OrderObserver observer : observers) {
            observer.update(message);
        }

    }
}