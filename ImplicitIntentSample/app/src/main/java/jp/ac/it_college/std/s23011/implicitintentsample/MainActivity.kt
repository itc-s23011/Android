package jp.ac.it_college.std.s23011.implicitintentsample

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import jp.ac.it_college.std.s23011.implicitintentsample.databinding.ActivityMainBinding
import java.net.URLEncoder

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var latitude = 0.0

    private var longitude = 0.0

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    private val permissionRequestLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(
                Manifest.permission.ACCESS_FINE_LOCATION, false
            ) -> {
                startLocationUpdate()
            }
            permissions.getOrDefault(
                Manifest.permission.ACCESS_FINE_LOCATION, false
            ) -> {
                startLocationUpdate()
            } else -> {

            }
        }
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
            binding.btMapSearch.setOnClickListener(::onMapSerchButtonClick)
            binding.btMapShowCurrent.setOnClickListener(::onMapShowCurrentButtonClick)
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            5000
        ).build()
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let { location ->
                    latitude = location.latitude
                    longitude = location.longitude

                    binding.tvLatitude.text = "$latitude"
                    binding.tvLongitude.text = "$longitude"
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        permissionRequestLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            )
        )
    }

    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    private fun onMapShowCurrentButtonClick(view: View) {
        val uri = Uri.parse("geo:$latitude,$longitude")
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }

    private fun onMapSerchButtonClick(view: View) {
        val searchWords = binding.etSearchWord.text.toString().let { keyword ->
            URLEncoder.encode(keyword, Charsets.UTF_8.name())
        }

        val uri = Uri.parse("geo:0,0?q=$searchWords")
        Intent(Intent.ACTION_VIEW, uri).let { intent ->
            startActivity(intent)
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdate() {
        fusedLocationClient.requestLocationUpdates(
            locationRequest, locationCallback, mainLooper
        )
    }
}