package com.example.rxjavademo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rxjavademo.utils.LogUtils
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.lang.NullPointerException

class MainActivity : AppCompatActivity() {

    private var mDisposable:Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val list1 = listOf(1,2)
//        val list2 = listOf(3,4,5)
//        Observable.just(list1,list2)
//            .flatMap<String> {
//                LogUtils.e(it.size.toString())
//                return@flatMap Observable.fromIterable(it.map { "{$it} —-flatMap" })
//            }.subscribe {
//                LogUtils.e(it)
//            }

        //自动调用onComplete()方法
//        Observable.fromArray(1,2,3).subscribe({
//            LogUtils.e(it.toString())
//        },{
//            LogUtils.e(it?.message,it)
//        },{
//            LogUtils.e("onComplete")
//        },{
//            LogUtils.e("onSubscribe")
//        })

        //自动调用onComplete()方法
//        Observable.just(1,2,3).subscribe({
//            LogUtils.e(it.toString())
//        },{
//            LogUtils.e(it?.message,it)
//        },{
//            LogUtils.e("onComplete")
//        },{
//            LogUtils.e("onSubscribe")
//        })

//        try {
//            Observable.create<String> {
//                it.onNext("a")
//                it.onNext("b")
//                it.onNext("c")
//
//                //如果观察者没有取消订阅的话，这里报出的异常将会被观察者的onError()方法处理；
//                it.onError(NullPointerException("测试"))
//                it.onComplete()
//            }.subscribe(object :Observer<String>{
//                override fun onSubscribe(d: Disposable) {
//                    mDisposable = d
//                    LogUtils.e("onSubscribe")
//                }
//
//                override fun onNext(t: String) {
//                    LogUtils.e(t)
//                    if(t == "b"){
//                        LogUtils.e(mDisposable?.isDisposed?.toString())
//                        if(mDisposable?.isDisposed == false){
//                            //任何事件都不会接收，包括完成和异常事件；但被观察者还可以继续发送事件
//                            //如果取消了订阅后被观察者还爆出了异常，将引起程序崩溃,就算在外面加try-catch也咩用
//                            mDisposable?.dispose()
//                        }
//                        LogUtils.e(mDisposable?.isDisposed?.toString())
//                    }
//                }
//
//                override fun onError(e: Throwable) {
//                    LogUtils.e(e?.message,e)
//                }
//
//                override fun onComplete() {
//                    LogUtils.e("onComplete")
//                }
//
//            })
//        } catch (e: Throwable) {
//            LogUtils.e(e.message,e)
//        }
    }
}
