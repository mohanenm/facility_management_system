package com.fms.domainLayer.util;

public interface IObservable<ObservedType> {
    void addObserver(IObserver<ObservedType> obs);

    void notifyObservers(ObservedType data);
}
