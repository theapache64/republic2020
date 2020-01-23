package com.theapache64.republic2020

import android.content.DialogInterface
import android.graphics.PixelFormat
import android.media.AudioManager
import android.os.Bundle
import android.view.SurfaceHolder
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.theapache64.republic2020.fireworks.NightScene

class MainActivity : AppCompatActivity() {

    lateinit var fireworks: NightScene
    private var mSurfaceHolder: SurfaceHolder? = null
    var isSurfaceCreated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.fireworks = findViewById(R.id.fireworks)
        prepareFireworks()

        val flag = findViewById<IndianFlag>(R.id.flag)
        flag.setOnClickListener {
            Toast.makeText(this, "Fire in the hole!! 🙉", Toast.LENGTH_SHORT).show();
            fireworks.randomFire()
        }

        // Instruction
        supportActionBar?.subtitle = getString(R.string.main_sub_title_tap)

        // Show dialog
        val dialog = AlertDialog.Builder(this)
            .setTitle(R.string.title_main_wish)
            .setMessage(R.string.msg_main_tap)
            .setCancelable(false)
            .setPositiveButton(R.string.action_got_it) { dialogInterface: DialogInterface, i: Int ->
                dialogInterface.dismiss()
            }
            .create()

        dialog.show()
    }

    private fun prepareFireworks() {
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
