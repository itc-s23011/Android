package jp.ac.it_college.std.s23011.hellosample

import android.app.Activity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import jp.ac.it_college.std.s23011.hellosample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

private inner class HelloListener : View.OnClickListener{
    override fun onClick(view: View) {     //view ?
        val input = findViewById<EditText>(R.id.etName)
        val output = findViewById<TextView>(R.id.tvOutput)

//        when (view.id) {
//            //表示ボタン
//            R.id.btClick -> {
//                val inputStr = input.text.toString()
//                output.text = getString(R.string.tv_msg, inputStr)
//            }
//            //クリアボタン
//            R.id.btClear -> {
//                input.text.clear()
//                output.text = null
//            }
//        }

//        val inputStr = input.text.toString()
//        output.text = "${inputStr}さん、こんにちは!"
    }
}
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)       //
        enableEdgeToEdge()
        setContentView(binding.root)        //
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->      //
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//        val btClick = findViewById<Button>(R.id.btClick)
//        val listener = HelloListener()
//        btClick.setOnClickListener(listener)
//
//        val btCleaner = findViewById<Button>(R.id.btClear)
//        btCleaner.setOnClickListener(listener)


        binding.btClick.setOnClickListener {
            val inputStr = binding.etName.text.toString()
            binding.tvOutput.text =getString(R.string.tv_msg, inputStr)
        }

        binding.btClear.setOnClickListener {
            binding.etName.text.clear()
            binding.tvOutput.text = null
        }

    }
}