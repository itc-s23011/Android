package jp.ac.it_college.std.s23011.asyncsample

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.annotation.UiThread
import androidx.annotation.WorkerThread
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import jp.ac.it_college.std.s23011.asyncsample.databinding.ActivityMainBinding
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.URL
import java.util.concurrent.Callable
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val DEBUG_TAG = "AsyncSample"
        private const val WEATHER_INFO_URL = "https://api.openweathermap.org/data/2.5/weather?lang=ja"
        private const val APP_ID = BuildConfig.apiKey
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.apply {
            lvCityList.apply {
                adapter = CityListAdapter(City.list) {
                    val urlString = "$WEATHER_INFO_URL&q=${it.q}&appid=$APP_ID"
                }
                val manager = LinearLayoutManager(this@MainActivity)
                layoutManager = manager
                addItemDecoration(
                    DividerItemDecoration(this@MainActivity, manager.orientation)
                )
            }
        }
    }

    @UiThread
    private fun receiveWetherInfo(urlString: String) {
        val backgroundRecycler = WeatherInfoBackgroundReceiver(urlString)
        val executeService = Executors.newSingleThreadExecutor()
        val future = executeService.submit(backgroundRecycler)
        val result = future.get()
        showWeatherInfo(result)
    }

    @UiThread
    private fun showWeatherInfo(result: String) {
        val root = JSONObject(result)
        val cityName = root.getString("name")
        val coord = root.getJSONObject("coord")
        val latitude = coord.getString("lat")
        val longitude = coord.getString("lon")
        val weatherArray = root.getJSONArray("weather")
        val weather = weatherArray.getJSONObject(0)
        val descriprion = weather.getString("descriprion")

        binding.tvWeatherTelop.text = "${cityName}の天気"
        binding.tvWeatherDesc.text = """
            現在は${descriprion}です。
            緯度は${latitude}度で軽度は${longitude}度です。
        """.trimIndent()
    }

    private class WeatherInfoBackgroundReceiver(private val urlString: String) : Callable<String> {
        @WorkerThread
        override fun call(): String {
            val url = URL(urlString)
            val conn = (url.openConnection() as HttpURLConnection).apply { 
                connectTimeout = 1000
                readTimeout = 1000
                requestMethod = "GET"
            }
            return try {
                conn.connect()
                val result = conn.inputStream.reader().readText()
                result
            } catch (ex: SocketTimeoutException) {
                Log.w(DEBUG_TAG, "通信タイムアウト", ex)
                ""
            } finally {
                conn.disconnect()
            }
        }
    }
}