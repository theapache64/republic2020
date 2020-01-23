package com.theapache64.republic2020

import android.graphics.PixelFormat
import android.media.AudioManager
import android.os.Bundle
import android.os.Handler
import android.view.SurfaceHolder
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.theapache64.republic2020.fireworks.NightScene

class MainActivity : AppCompatActivity() {

    lateinit var fireworks: NightScene
    private var mSurfaceHolder: SurfaceHolder? = null
    var isSurfaceCreated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.fireworks = findViewById<NightScene>(R.id.fireworks)
        fireworks.setZOrderOnTop(true)
        this.mSurfaceHolder = fireworks.holder
        this.mSurfaceHolder?.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                holder.setFormat(PixelFormat.TRANSPARENT)

                fireworks.init()
                fireworks.play()
                isSurfaceCreated = true
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                isSurfaceCreated = false
                fireworks.stop()
            }
        })

        val flag = findViewById<IndianFlag>(R.id.flag)
        flag.setOnClickListener {
            Toast.makeText(this, "Fire in the hole!! 🙉", Toast.LENGTH_SHORT).show();
            fireworks.randomFire()
        }
    }

    private val handler = Handler()
    private fun startFirework() {
        val randomTime = (5..10).random()
        handler.postDelayed({
            fireworks.randomFire()
            startFirework()
        }, randomTime * 1000L)
    }

    override fun onResume() {
        super.onResume()
        this.volumeControlStream = AudioManager.STREAM_MUSIC
        //generate the sparks
        if (isSurfaceCreated) {
            fireworks.postDelayed(Runnable { fireworks.play() }, 2000)
        }
    }

    override fun onPause() {
        super.onPause()
        fireworks.stop()

    }

    override fun onDestroy() {
        super.onDestroy()
        fireworks.onDestroy()
    }
}
