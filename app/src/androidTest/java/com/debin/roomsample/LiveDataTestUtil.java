package com.debin.roomsample;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class LiveDataTestUtil {

    //Get the value from a LiveData object. We're waiting for LiveData to emit, for 2 seconds.
    // Once we got a notification via onChanged, we stop observing.

    public static <T> T getValue(LiveData<T> liveData) throws InterruptedException{
        Object[] data = new Object[1];
        CountDownLatch latch = new CountDownLatch(1);
        Observer<T> observer = new Observer<T>() {
            @Override
            public void onChanged(T t) {
                data[0] = t;
                latch.countDown();
                liveData.removeObserver(this);
            }
        };
        liveData.observeForever(observer);
        latch.await(2, TimeUnit.SECONDS);
        return (T) data[0];

    }

}
