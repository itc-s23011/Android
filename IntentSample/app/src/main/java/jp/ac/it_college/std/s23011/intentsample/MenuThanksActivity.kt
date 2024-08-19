package jp.ac.it_college.std.s23011.intentsample

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import jp.ac.it_college.std.s23011.intentsample.databinding.ActivityMenuThanksBinding

class MenuThanksActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuThanksBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuThanksBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // MainActivity からデータを受け取る
        val menuName = intent.getStringExtra("menuName")
        val menuPrice = intent.getIntExtra("menuPrice", 0)

        // ビューに定食情報をセットする
        binding.tvMenuName.text = menuName
        binding.tvMenuPrice.text = menuPrice.toString()

        // 戻るボタンのイベント設定
        binding.btThxBack.setOnClickListener {
            finish()
        }
    }
}}