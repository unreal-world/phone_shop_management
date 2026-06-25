package com.mycompany.phonestore.observer.Subject;

import com.mycompany.phonestore.observer.Observer.OrderObserver;

public interface OrderSubject {

    void addObserver(OrderObserver observer);

    void removeObserver(OrderObserver observer);

    void notifyObservers(String message);
}