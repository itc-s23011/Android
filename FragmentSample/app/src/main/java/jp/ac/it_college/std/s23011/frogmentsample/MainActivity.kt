package jp.ac.it_college.std.s23011.frogmentsample

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import jp.ac.it_college.std.s23011.frogmentsample.databinding.ActivityMainBinding

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

        supportFragmentManager.apply {
            setFragmentResultListener("selectedMenu", this@MainActivity, ::onSelectedMenu)

            setFragmentResultListener("backToList", this@MainActivity, ::onBackToList)
        }
    }

    private fun onBackToList(requestKey: String, bundle: Bundle) {
        if (binding.fragmentMainContainer != null) {
            supportFragmentManager.popBackStack()
        } else {
            supportFragmentManager.commit {
                binding.fragmentThanksContainer?.let { container ->
                    remove(container.getFragment())
                }
            }
        }
    }

    private fun onSelectedMenu(requestKey: String, bundle: Bundle) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)

            if (binding.fragmentMainContainer != null) {
                addToBackStack("Only List")
                replace(R.id.fragmentMainContainer, MenuThanksFragment::class.java, bundle)
            } else {
                replace(R.id.fragmentThanksContainer, MenuThanksFragment::class.java, bundle)
            }
        }
    }
}
