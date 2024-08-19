package jp.ac.it_college.std.s23011.recyclerviewsample

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import jp.ac.it_college.std.s23011.recyclerviewsample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
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
        // メニューの JSON データファイルを読み込む
        val jsonString = resources.openRawResource(R.raw.set_meal).reader().readText()
        // jsonString をパース(解析)して、ちゃんとしたオブジェクト(SetMeal)にする。
        val setMealList = Json.decodeFromString<List<SetMeal>>(jsonString)

        // RecyclerView を使うための処理
        binding.setMealList.apply {
            // RecyclerView でデータの表示形式を管理しているレイアウトマネージャを用意。
            // 今回は ListView のような表示形式を実現する LinearLayoutManager を使用。
            val linearLayoutManager = LinearLayoutManager(this@MainActivity)
            // レイアウトマネージャをセット
            layoutManager = linearLayoutManager

            // データを管理するアダプタのインスタンスを生成する
            val setMealAdapter = SetMealAdapter(setMealList)
            adapter = setMealAdapter

            // アイテムの区切り線を入れたい
            addItemDecoration(
                DividerItemDecoration(this@MainActivity, linearLayoutManager.orientation)
            )
        }
    }
}