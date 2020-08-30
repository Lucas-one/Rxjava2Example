package com.example.rxjava2example;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

class TemperatureManager {

    static class Temperature{
        private int currentTemperature;

        Temperature(int degree){
            this.currentTemperature = degree;
        }

        int getDegree(){
            return this.currentTemperature;
        }
    }

    private PublishSubject<Temperature> subject = PublishSubject.create();

    void setTemperature(Temperature temperature) {
        subject.onNext(temperature);
    }

    Observable<Temperature> updateEvent(){
        return subject.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
