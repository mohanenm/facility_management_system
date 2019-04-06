package com.fms.domainLayer.util;

public interface IObserver<ObservedType> {
    void update(IObservable<ObservedType> object, ObservedType data);
}