package com.example.myretrofit;


import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.plugins.RxJavaPlugins;

public class CallEnqueueObservable<T> extends Observable<Response<T>> {

    private final Call originalCall;

    CallEnqueueObservable(Call originalCall){
        this.originalCall = originalCall;
    }

    @Override
    protected void subscribeActual(Observer<? super Response<T>> observer) {
        try {
            Response<T> response = new Response<>((T)originalCall.execute());
            observer.onNext(response);
        } catch (Throwable t) {
            try{
                observer.onError(t);
            } catch (Throwable inner) {
                Exceptions.throwIfFatal(inner);
                RxJavaPlugins.onError(new CompositeException(t, inner));
            }
        }
    }
}
