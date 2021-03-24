package com.example.rxjavademo.operator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rxjavademo.R
import com.example.rxjavademo.utils.LogUtils
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

class OperatorActivity : AppCompatActivity() {

    var mDisposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opterator)

//        create()
//        functionOperator()
//        filterOperator()
        concatOperator()
//        mapOperator()
//        conditionOperator()
    }


    private fun create() {
        //类似于intervalRange，但是无延迟发送事件，多个数据在主线程几乎同时发送；
        Observable.range(1, 500).subscribe { LogUtils.e("$it ${Thread.currentThread().name}") }

        //类似于interval，但可指定发送事件数量
//        Observable.intervalRange(3,5,5,1,TimeUnit.SECONDS).subscribe { LogUtils.e("$it ${Thread.currentThread().name}") }

        //每隔指定时间发送事件：从0开始，无限递增，常用语网络轮询处理
//        Observable.interval(2,1,TimeUnit.SECONDS,Schedulers.computation()).subscribe { LogUtils.e("$it ${Thread.currentThread().name}") }

        //延迟发送事件onNext(0),常用于测试
//        Observable.timer(2,TimeUnit.SECONDS,Schedulers.computation()).subscribe {  LogUtils.e(it.toString()) }

        //延迟创建被观察者,订阅时自动创建被观察者(如下面的Observable.just(str))，并发送事件
//        var str = "a"
//        val defer = Observable.defer<String> { Observable.just(str) }
//        str = "b"
//        defer.subscribe {
//            LogUtils.e(it)
//        }
//        str = "c"
//        defer.subscribe {
//            LogUtils.e(it)
//        }

//        Observable.empty<String>().subscribe({
//
//        },{
//
//        },{
//            LogUtils.e("onComplete()")
//        })
    }

    private fun functionOperator() {
        //repeat 在接收到onComplete事件后重复发送被观察者事件
        Observable.create<Int> {
            it.onNext(1)
            //发生错误时会打断重复
//            it.onError(Exception("发生错误了"))
            it.onNext(2)
            //没有收到complete事件的话不会重复
            it.onComplete()
        }.repeat(3).doOnSubscribe {
            LogUtils.e("doOnSubscribe")
        }.subscribe({
            LogUtils.e(it.toString())
        }, {
            LogUtils.e("doOnError222" + it.message)
        }, {
            LogUtils.e("doOnComplete222")
        },{
            LogUtils.e("doOnSubscribe222")
        })

        //retryWhen
//        Observable.create<Int> {
//            it.onNext(1)
//            it.onNext(2)
//            it.onError(Exception("发生错误了"))
//            it.onNext(3)
//        }.retryWhen {
//            it.flatMap {
//                LogUtils.e(it.message)
//                //若返回的是onNext事件，则原始的Observable重试
////                Observable.just(4)
//                //若返回的是error事件，则原始的Observable不重试，并走onError()结束
//                Observable.error<Int> { Throwable("再次发生错误，不管了") }
//            }
//        }.doOnSubscribe {
//            LogUtils.e("doOnSubscribe")
//        }.subscribe({
//            LogUtils.e(it.toString())
//        }, {
//            LogUtils.e("doOnError222" + it.message)
//        }, {
//            LogUtils.e("doOnComplete222")
//        },{
//            LogUtils.e("doOnSubscribe222")
//        })


        //retry 当收到onError时，重新订阅和发送事件
//        Observable.create<Int> {
//            it.onNext(1)
//            it.onNext(2)
//            it.onError(Exception("发生错误了"))
//            it.onNext(3)
//        }.retry(3).doOnSubscribe {
//            LogUtils.e("doOnSubscribe")
//        }.subscribe({
//            LogUtils.e(it.toString())
//        }, {
//            LogUtils.e("doOnError222" + it.message)
//        }, {
//            LogUtils.e("doOnComplete222")
//        },{
//            LogUtils.e("doOnSubscribe222")
//        })

        //错误处理 onErrorResumeNext 拦截到一个错误后返回一个新的被观察者
//        Observable.create<Int> {
//            it.onNext(1)
//            it.onError(Exception("发生错误了"))
//            it.onNext(2)
//        }.onErrorResumeNext{t:Throwable->
//            LogUtils.e(t.message)
//            return@onErrorResumeNext Observable.just(3,4)
//        }.onExceptionResumeNext {
//            //只捕获Exception异常;而且如果前面定义了onErrorResumeNext后，这个回调将不会调用
//            it.onNext(3)
//            it.onNext(4)
//            it.onComplete()
//        }.subscribe ({
//            LogUtils.e(it.toString())
//        },{
//            LogUtils.e("doOnError222" + it.message)
//        },{
//            LogUtils.e("doOnComplete222")
//        })


        //错误处理 onErrorReturn
//        Observable.create<Int> {
//            it.onNext(1)
//            it.onNext(2)
//            it.onError(NullPointerException("测试"))
//        }.onErrorReturn {
//            //捕获被观察者的异常.并返回一个onNext()事件并正常结束(调用onComplete())
//            LogUtils.e(it.message)
//            return@onErrorReturn 999
//        }.doOnError {
//            LogUtils.e("doOnError" + it.message)
//        }.doOnComplete {
//            LogUtils.e("doOnComplete")
//        }.subscribe ({
//            LogUtils.e(it.toString())
//        },{
//            LogUtils.e("doOnError222" + it.message)
//        },{
//            LogUtils.e("doOnComplete222")
//        })


        //使被观察者延迟一段时间再发送事件
//        Observable.just(1,2,3).delay(3,TimeUnit.SECONDS).subscribe(object:Observer<Int>{
//            override fun onComplete() {
//                LogUtils.e("onComplete")
//            }
//
//            override fun onSubscribe(d: Disposable) {
//                LogUtils.e("onSubscribe")
//            }
//
//            override fun onNext(t: Int) {
//                LogUtils.e(t.toString())
//            }
//
//            override fun onError(e: Throwable) {
//
//            }
//
//        })

        //在事件的生命周期中的操作
//        Observable.create<Int> {
//            it.onNext(1)
//            it.onNext(2)
//            it.onError(NullPointerException("测试"))
//            it.onNext(3)
////            it.onComplete()
//        }.doOnLifecycle({
//            LogUtils.e("doOnLifecycle isDispose=${it.isDisposed}")
//        },{
//            LogUtils.e("doOnLifecycle onDispose")
//        }).doOnSubscribe {
//            mDisposable = it
//            LogUtils.e("doOnSubscribe")
//        }.doOnDispose {//todo 这个什么会调用呢？虽然可以用doOnLifecycle代替实现
//            LogUtils.e("doOnDispose====")
//        }.doOnNext {
//            LogUtils.e("doOnNext")
//        }.doAfterNext {
//            LogUtils.e("doAfterNext")
//        }.doOnComplete {
//            LogUtils.e("doOnComplete")
//        }.doOnError {//不能代替观察者中的onError回调
//            LogUtils.e("doOnError")
//        }.doOnEach {//注意：如果订阅在doOnNext/doOnComplete/doOnError之后，那么发生的顺序也在这些事件之后
//            LogUtils.e("doOnEach: ${it.isOnNext}")
//        }.doAfterTerminate {//无论正常发送还是异常终止
//            LogUtils.e("doAfterTerminate")
//        }.doFinally { //报异常后不会调用-----doAfterTerminate 和 doFinally一定要在onComplete/onError后才会调用; 需要注意的是dispose()不会调用
//            LogUtils.e("doFinally")
//        }.subscribe({
//            LogUtils.e(it.toString())
//            if(it == 2 &&mDisposable?.isDisposed ==false){
//                mDisposable?.dispose()//todo 调用后没有doOnDispose/doAfterTerminate/doFinally回调
//            }
//        },{
//            LogUtils.e("doOnError222")
//            LogUtils.e(it.message)
//        },{
//            LogUtils.e("doOnComplete222")
//        },{
//            LogUtils.e("doOnSubscribe222")
//        }).dispose()
    }

    private fun filterOperator(){
        //elementAtOrError类似于elementAt，当下标越界时报异常

        //elementAt 仅接收第几个事件 从0开始算起
//        Observable.create<Int> {
//            it.onNext(1)
//            it.onError(Throwable("发生异常了"))
//            it.onComplete(); it.onNext(2)
//            it.onNext(3)}.elementAt(1).subscribe({ LogUtils.e(it.toString()) },{LogUtils.e(it.message)},{LogUtils.e("onSuccess")})

        //firstElement（） / lastElement（） 注意仅选取第一个或最后一个onNext/onError事件,firstElement包括但lastElement不包括onComplete()事件
//        Observable.create<Int> {
////            it.onComplete()
//            it.onNext(1)
//            it.onNext(2)
////            it.onError(Throwable("发生错误了"))
//            it.onNext(3)
//            it.onComplete()
//        }.firstElement().subscribe({ LogUtils.e(it.toString())},{LogUtils.e(it.message)},{LogUtils.e("onComplete")})

        //在指定的时间内，发送了第二次事件，则第一次事件被丢弃;如果超过了指定的时间，则发送最新的一个事件
//        Observable.create<Int> {
//            it.onNext(1)
//            Thread.sleep(400)
//            it.onNext(2)
//            Thread.sleep(400)
//            it.onNext(3)
//            Thread.sleep(400)
//            it.onNext(4)
//            Thread.sleep(400)
//            it.onNext(5)
//            Thread.sleep(1200)
//            it.onNext(6)
////            it.onComplete()//todo 取消注销后6就打印在主线程中???
//        }.debounce(1,TimeUnit.SECONDS).doOnComplete { LogUtils.e("doOnComplete") }.subscribe { LogUtils.e(it.toString()) }

        //throttleFirst（）/ throttleLast（）在指定的时间内只发送第一次或最后一次事件
//        Observable.just(1,2,3).throttleFirst(2,TimeUnit.SECONDS).subscribe { LogUtils.e(it.toString()) }
//        Observable.create<Int> {
//            it.onNext(1)
//            Thread.sleep(300)
//            it.onNext(2)
//            Thread.sleep(300)
//            it.onNext(3)
//            Thread.sleep(300)
//            it.onNext(4)
//            Thread.sleep(300)
//            it.onNext(5)
//            Thread.sleep(300)
//            it.onNext(6)
//        }
//            .sample(1,TimeUnit.SECONDS)//类似于throttleLast 可同时作用
//            .throttleLast(1,TimeUnit.SECONDS)
//            .subscribe { LogUtils.e(it.toString()) }

        //take/takeLast 选择发送onNext()事件的数量
//        Observable.just(1,2,3,"4").take(2).subscribe { LogUtils.e(it.toString()) }
//        Observable.just(1,2,3,"4").takeLast(2).subscribe { LogUtils.e(it.toString()) }

        //distinct 过滤掉事件序列中重复的事件
//        Observable.just(1,2,1,2,2,3).distinct().subscribe { LogUtils.e(it.toString()) }
        ///distinctUntilChanged 过滤掉事件序列中连续重复的事件
//        Observable.just(1,2,1,2,2,3).distinctUntilChanged().subscribe { LogUtils.e(it.toString()) }

        //skip/skipLast 跳过正向或反向的某几项onNext事件
//        Observable.just(1,"2",3,"4").skip(1).skipLast(2).subscribe { LogUtils.e(it.toString()) }

        //ofType 筛选出特定类型的onNext事件
//        Observable.just(1,"2",3,"4").ofType(Integer::class.java).subscribe { LogUtils.e(it.toString()) }

        //filter 筛选指定条件的onNext事件
//        Observable.create<Int> {
//            it.onNext(1)
//            it.onNext(2)
//            it.onNext(3)
//            it.onComplete()
//        }.filter {
//            return@filter it == 2
//        }.subscribe {
//            LogUtils.e(it.toString())
//        }
    }

    private fun concatOperator(){
        //count 事件发送数量
        Observable.just(1,2,3).startWith(0).count().subscribe({LogUtils.e(it.toString())},{LogUtils.e(it.message)})

        //startWith/startWithArray 发送事件前追加事件
//        Observable.just(1,2,3).startWith(0).subscribe({LogUtils.e(it.toString())},{LogUtils.e(it.message)},{LogUtils.e("onComplete")})

        //collect 把所有事件收集到一个数据结构中
//        Observable.just(1,2,3).collect({ArrayList()},{list:ArrayList<Int>,t:Int->
//            list.add(t)
//        }).subscribe({LogUtils.e(it.size.toString())},{LogUtils.e(it.message)})

        //reduce  把一个观察者的所有事件以某种方式聚合成一个事件发送
//        Observable.just(1,2,3)
//            .reduce{t1: Int, t2: Int ->
//                return@reduce t1 * t2
//            }
//            .subscribe({LogUtils.e(it.toString())},{LogUtils.e(it.message)},{LogUtils.e("onComplete")})

        //combineLatest 分别取最后一个Observable之前的所有Observable的最后一个事件和最后一个Observable的所有事件结合
//        Observable.combineLatest(Observable.just(1,2),Observable.just(3,4),Observable.just(5,6)
//            , Function3<Int, Int, Int, String> { t1, t2, t3 -> t1.toString() + t2.toString() + t3.toString()})
//            .subscribe({LogUtils.e(it.toString())},{LogUtils.e(it.message)},{LogUtils.e("onComplete")})

        //zip 合并多个观察者中的事件并发送,没有合并的事件也一样会发送,因为just默认在事件发送完后发送onComplete事件，因此7的事件将不会发送，以最先发送onComplete的事件为准
//        Observable.zip(Observable.just(1,2,3).subscribeOn(Schedulers.io()),Observable.just(4,5,6,7).subscribeOn(Schedulers.computation()),
//            BiFunction<Int, Int, String> { t1, t2 -> t1.toString() + t2.toString()})
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({LogUtils.e(it.toString())},{LogUtils.e(it.message)},{LogUtils.e("onComplete")})

        //使用merge/mergeArray/concat/concatArray 组合多个被观察者时，某个被观察者发生了异常会导致其他被观察者立即停止发送事件
        //concatDelayError/mergeDelayError 可以避免这种情况，等其他被观察者事件发送完毕后才触发onError()事件
//        Observable.concatDelayError(listOf(Observable.create<Int> { it.onNext(1);it.onNext(2);it.onError(Throwable("发生异常了")) },Observable.just(3,4)))
//            .subscribe({LogUtils.e(it.toString())},{LogUtils.e(it.message)},{LogUtils.e("onComplete")})

        //merge/mergeArray 组合多个被观察者，合并后按时间线并行执行
//        Observable.merge(Observable.intervalRange(1,5,1,1,TimeUnit.SECONDS),Observable.intervalRange(1,5,2,1,TimeUnit.SECONDS))
//            .subscribe({LogUtils.e(it.toString())},{LogUtils.e(it.message)},{LogUtils.e("onComplete")})

        //concat/concatArray 组合多个被观察者，合并后按顺序串行执行发送事件,concat最多只能合并4个被观察者
//        Observable.concat(Observable.just(1),Observable.just(2,3)).subscribe({LogUtils.e(it.toString())},{LogUtils.e(it.message)},{LogUtils.e("onComplete")})
    }

    private fun mapOperator(){

        //buffer 指定多少个事件合并成一个列表事件发送，
//        Observable.just(1,2,3,4,5,6).buffer(2,1)
//            .subscribe({LogUtils.e(it.toString())},{LogUtils.e(it.message)},{LogUtils.e("onComplete")})

        //concatMap 一般用于1到多，生成的新的序列有序
//        Observable.just(1,2,3).concatMap { Observable.just("flatMap$it","gyq1","gyq2","gyq3") }
//        .subscribe({LogUtils.e(it.toString())},{LogUtils.e(it.message)},{LogUtils.e("onComplete")})

//        //flatMap 一般用于1到多，生成的新的序列无序
//        Observable.just(1,2,3).flatMap { Observable.just("flatMap$it","gyq1","gyq2","gyq3") }.subscribe({LogUtils.e(it.toString())},{LogUtils.e(it.message)},{LogUtils.e("onComplete")})

        //map 针对每个onNext事件做相应的处理
//        Observable.just(1,2,3).map { "map$it" }.subscribe({LogUtils.e(it.toString())},{LogUtils.e(it.message)},{LogUtils.e("onComplete")})
    }

    private fun conditionOperator(){
        //defaultIfEmpty 在只发送onComplete事件时发送默认的一个事件
        Observable.error<Int>(Throwable("发送了一次")).defaultIfEmpty(9)
            .subscribe({LogUtils.e(it.toString())},{LogUtils.e(it.message)},{LogUtils.e("onComplete")})
        Observable.empty<Int>().defaultIfEmpty(8)
            .subscribe({LogUtils.e(it.toString())},{LogUtils.e(it.message)},{LogUtils.e("onComplete")})

        //amb 只保留第一个发送事件的被观察者，其余丢弃
//        Observable.amb(listOf(Observable.intervalRange(1,5,2,1,TimeUnit.SECONDS),Observable.intervalRange(1,3,1,1,TimeUnit.SECONDS)))
//            .subscribe({LogUtils.e(it.toString())},{LogUtils.e(it.message)},{LogUtils.e("onComplete")})

        //isEmpty 判断发送的事件序列是否为空
//        Observable.empty<Int>().isEmpty.subscribe{t->LogUtils.e(t.toString())}

        //contains 发送的onNext事件值中是否包含了某个值
//        Observable.just(1,2).contains(2).subscribe{t->LogUtils.e(t.toString())}

        //SequenceEqual 判断两个被观察者要发送的事件是否相同
//        Observable.sequenceEqual(Observable.just(1,2),Observable.just(1,2)).subscribe{t->LogUtils.e(t.toString())}

        //skipUntil  todo 这个是不符合预期的
//        Observable.interval(1,TimeUnit.SECONDS).skipUntil(Observable.timer(3*1000,TimeUnit.SECONDS))
//            .subscribe({LogUtils.e(it.toString())},{LogUtils.e(it.message)},{LogUtils.e("onComplete")})

        //takeUntil 直到满足条件时才停止发送事件,并发送onComplete事件
//        Observable.just(1,2,3).takeUntil{ it>1 }
//            .subscribe({LogUtils.e(it.toString())},{LogUtils.e(it.message)},{LogUtils.e("onComplete")})

        //skipWhile 跟all相反
//        Observable.just(1,2,3).skipWhile { it<=3 }
//            .subscribe({LogUtils.e(it.toString())},{LogUtils.e(it.message)},{LogUtils.e("onComplete")})

        //takeWhile 当符合条件时事件才会发送 todo 这个是不符合预期的
//        Observable.just(1,2,3).takeWhile { it>=2 }
//            .subscribe({LogUtils.e(it.toString())},{LogUtils.e(it.message)},{LogUtils.e("onComplete")})

        //所有的onNext事件是否都符合某个条件
//        Observable.just(1,2,3).all { it>=2 }.subscribe({LogUtils.e(it.toString())},{LogUtils.e(it.message)})
    }
}
