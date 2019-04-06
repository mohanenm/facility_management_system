package com.fms.domainLayer.util;

import java.util.LinkedList;
import java.util.List;

public class Observable<ObservedType> implements IObservable<ObservedType> {
    private List<IObserver<ObservedType>> _observers =
            new LinkedList<>();

    @Override
    public void addObserver(IObserver<ObservedType> obs) {
        if (obs == null) {
            throw new IllegalArgumentException("Tried to add a null observer");
        }
        if (_observers.contains(obs)) {
            return;
        }
        _observers.add(obs);
    }

    @Override
    public void notifyObservers(ObservedType data) {
        for (IObserver<ObservedType> obs : _observers) {
            obs.update(this, data);
        }
    }
}