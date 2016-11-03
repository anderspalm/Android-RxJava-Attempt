package com.anders.reactivexlibrarytest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Random;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

import static android.R.attr.alertDialogIcon;
import static android.R.attr.x;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    Observable<Integer> mObservable;
    int mRandomNum;
    Subscriber<Integer> mSubscriber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRandomNum = 0;

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


            }
        });


//        ************************************************************

//        Integer Subscription in three statements

//        ************************************************************

        Observable<String> myObservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {
                        sub.onNext("Hello, world!");
                        sub.onCompleted();
                    }
                }
        );

        Subscriber<String> mySubscriber = new Subscriber<String>() {
            @Override
            public void onNext(String s) {
                Log.i(TAG, "onNext: " + s);
            }

            @Override
            public void onCompleted() {
                Log.i(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError: ");
            }
        };

        myObservable.subscribe(mySubscriber);


//        ************************************************************

//        Integer Subscription with button

//        ************************************************************

        Button randButton = (Button) findViewById(R.id.randomize_button);
        randButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random random = new Random();
                mRandomNum = random.nextInt(100);

            }
        });

        mObservable.just(mRandomNum).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.i(TAG, "call: changed number " + integer);
            }
        });

//        ************************************************************

//        Subscription in one statement with Map operator and Integer

//        ************************************************************

        Observable.just(20)
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {

                        return integer + " points gained today";
                    }

                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.i(TAG, s);
                    }
                });


//        ************************************************************

//        Subscription in one statement with Map operator and String

//        ************************************************************

        Observable.just("Hello World")
                .map(new Func1<String, Object>() {
                    @Override
                    public Object call(String s) {
                        return s + " remember remember the 4 of november";
                    }
                })
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        Log.i(TAG, "call: " + o);
                    }
                });


//        ************************************************************

//        Subscription in one statement with array list

//        ************************************************************


        ArrayList<Integer> array = new ArrayList<>();
        array.add(11);
        array.add(20);
        array.add(32);
        array.add(40);
        Observable.just(array)
                .all(new Func1<ArrayList<Integer>, Boolean>() {
                    @Override
                    public Boolean call(ArrayList<Integer> integers) {
                        Boolean arrayBoolean = true;
//                      predictate
                        for (Integer integer : integers) {
                            if ((integer < 10) || (integer > 100)) {
                                arrayBoolean = false;
                            }
                        }
                        return arrayBoolean;
                    }
                })
                .map(new Func1<Boolean, Object>() {
                    @Override
                    public Object call(Boolean aBoolean) {

                        return "Your array has an error: " + aBoolean;
                    }
                })
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        Log.i(TAG, "" + o);
                    }
                });

//        ************************************************************

//        Subscription in one statement chain of observable functions

//        ************************************************************

        Observable.just("Hello, world!")
                .map(new Func1<String, Object>() {

                    @Override
                    public Object call(String s) {
                        String temp1 = s + " Where ";
                        return temp1;
                    }
                })
                .map(new Func1<Object, Object>() {
                    @Override
                    public Object call(Object o) {

                        return o + " Am ";
                    }
                })
                .map(new Func1<Object, Object>() {
                    @Override
                    public Object call(Object o) {

                        return o + " I?";
                    }
                })
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        Log.i(TAG, "call: long observable action: " + o);
                    }
                });
    }


//        ************************************************************

//        Options Menu

//        ************************************************************

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
