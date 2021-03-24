package com.example.rxjavademo.backpressure

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rxjavademo.R
import com.example.rxjavademo.utils.LogUtils
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class BackPressureActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_back_press)

        simulateBackPressure()

    }

    /**
     * 异步订阅时：发送事件的速度快于接收事件的速度
     * 同步订阅必须消费完事件后才会产生事件，不存在生产和消费速度不匹配的情况
     */
    private fun simulateBackPressure(){

        //使用Flowable 默认缓存区大小为128 缓存事件超过128之后将会报MissingBackpressureException: Queue is full?!
        val subscribe = Flowable.create<Int>({
            LogUtils.e("request = ${it.requested()}")//异步订阅：128 + 设置的request值  同步订阅：Int.MAX_VALUE
            var i = 0
            while (i < 129) {
                it.onNext(i++)
                LogUtils.e("发送事件$i")
            }
            it.onComplete()
        }, BackpressureStrategy.LATEST)//ERROR/MISSING/BUFFER 缓存区满了异常 todo 不符合期待
            .subscribeOn(Schedulers.io())
            //不设置request 会报MissingBackpressureException 设置为0也会报异常且Rxjava2无法捕捉那个错误
            .doOnSubscribe { it.request(4);it.request(8) }//如设置了it.request(3)，这就说明观察者能够接收3个事件（多出的事件存放在缓存区）
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ LogUtils.e(it.toString()) },
                { LogUtils.e(it.message, it) },
                { LogUtils.e("onComplete") })

        //模拟事件发送速度大于事件接收速度，最终内存溢出
//            Observable.create<Int> {
//                var i = 0
//                while (true){
//                    it.onNext(i++)
//                }
//            }.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe {
//                    LogUtils.e(it.toString())
//                    Thread.sleep(1000)
//                }

    }
}
