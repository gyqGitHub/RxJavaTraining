package com.example.rxjavademo.utils

import android.util.Log

/**
 *
 * @author gyq
 * @date 2020-04-05
 */
object LogUtils {
   private const val TAG = "RxJava"

    fun e(msg:String?,ex:Throwable?=null){
        if(ex == null){
            Log.e(TAG,"$msg--${Thread.currentThread().name}")
        }else{
            Log.e(TAG,msg,ex)
        }
    }
}