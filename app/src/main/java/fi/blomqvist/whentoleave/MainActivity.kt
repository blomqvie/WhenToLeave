package fi.blomqvist.whentoleave

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var disposable: Disposable
    private val mtaInterface = Retrofit.Builder()
            .baseUrl("https://mta-backend.herokuapp.com")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(MTAInterface::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        disposable = Observable.interval(15, TimeUnit.SECONDS).startWith(0)
                .flatMap { t -> mtaInterface.getSchedule("16", "R32", "N") }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    minutes_number.text = it.first().arrivalText
                    },{
                    Log.e("MainActivity", it.message, it)
                })
    }

    override fun onStop() {
        super.onStop()
        if(!disposable.isDisposed) {
            disposable.dispose()
        }
    }
}
