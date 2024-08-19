package jp.ac.it_college.std.s23011.listviewsample2

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import jp.ac.it_college.std.s23011.listviewsample2.databinding.ActivityMainBinding

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

        val menuList = mutableListOf(
            "から揚げ定食","ハンバーグ定食","生姜焼き定食","ステーキ定食","野菜炒め定食","とんかつ定食","ミンチカツ定食","チキンカツ定食","コロッケ定食",
            "回鍋肉定食","麻婆豆腐定食","青椒肉絲定食","八宝菜定食","酢豚定食","豚の角煮定食","焼き鳥定食","焼き魚定食","焼き肉定食"
        )
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, menuList)

        binding.lvMenu.adapter = adapter

        binding.lvMenu.setOnItemClickListener { _, _, _, _ ->
            val dialogFragment = OrderConfirmDialogFragment()
            dialogFragment.show(supportFragmentManager, "OrderConfirmDialogFragment")
        }
    }
}