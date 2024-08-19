package jp.ac.it_college.std.s23011.intentsample

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import jp.ac.it_college.std.s23011.intentsample.databinding.ActivityMainBinding
import kotlinx.serialization.json.Json

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

        // load json
        val setMealList = Json.decodeFromString<List<SetMeal>>(
            resources.openRawResource(R.raw.set_meal).reader().readText()
        )

        // setup RecyclerView
        binding.lvMenu.apply {
            val manager = LinearLayoutManager(this@MainActivity)
            layoutManager = manager
            addItemDecoration(
                DividerItemDecoration(this@MainActivity, manager.orientation)
            )
            adapter = SetMealAdapter(setMealList) { item ->
                val menuName = item.name
                val menuPrice = item.price
                // Intentオブジェクトを作る
                val intent2MenuThanks = Intent(
                    this@MainActivity,
                    MenuThanksActivity::class.java
                )
                intent2MenuThanks.putExtra("menuName", menuName)
                intent2MenuThanks.putExtra("menuPrice", menuPrice)

                // 次の画面を起動
                startActivity(intent2MenuThanks)
            }
        }
    }
}