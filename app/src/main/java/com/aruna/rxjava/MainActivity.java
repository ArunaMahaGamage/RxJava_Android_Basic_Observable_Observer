package com.aruna.rxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    TextView tv_rx;
    String text = "";

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_rx = (TextView) findViewById(R.id.tv_rx);

        // observable
        Observable<String> animalsObservable = getAnimalsObservable();

        // observer
        Observer<String> animalsObserver = getAnimalsObserver();

        // observer subscribing to observable
        animalsObservable
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(animalsObserver);

    }

    private Observer<String> getAnimalsObserver() {
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe");
                text += "onSubscribe\n";
                tv_rx.setText(text);
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "Name: " + s);
                text += "Name : " + s + "\n";
                tv_rx.setText(text);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e.getMessage());
                text += "onError : " + e.getMessage() + "\n";
                tv_rx.setText(text);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "All items are emitted!");
                text += "All items are emitted!";
                tv_rx.setText(text);
            }
        };
    }

    private Observable<String> getAnimalsObservable() {
        return Observable.just("Ant", "Bee", "Cat", "Dog", "Fox");
    }
}
