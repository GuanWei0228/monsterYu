package tw.edu.pu.s1092790.monsteryu

import android.graphics.Canvas
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import tw.edu.pu.s1092790.monsteryu.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() ,GestureDetector.OnGestureListener,View.OnTouchListener{
    val db = FirebaseFirestore.getInstance()
    lateinit var binding: ActivityMainBinding
    lateinit var job: Job
    lateinit var mper: MediaPlayer
    lateinit var gDetector: GestureDetector
    var num = 87
    var check:Boolean = false
    var attack:Boolean = false
    var defence:Boolean = false
    var mhealth: String = ""
    var matk: String = ""
    var mdef: String = ""
    var hhealth: String = ""
    var hatk: String = ""
    var hdef: String = ""
    var startt = 87

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mper = MediaPlayer()

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        binding.img1.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                check = !check  //check是布林值，要先在前面設var check: Boolean = false
                if (check == false) {
                    img1.setImageResource(R.drawable.start)
                    startt = 87
                }
                else {
                    img1.setImageResource(R.drawable.stop)
                    startt = 1
                    db.collection("monsteryu")
                        .get()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                for (document in task.result!!) {
                                    mhealth += document.data["怪獸血量"].toString().toInt()
                                    matk += document.data["怪獸攻擊力"].toString().toInt()
                                    mdef += document.data["怪獸防禦力"].toString().toInt()
                                    hhealth += document.data["主角血量"].toString().toInt()
                                    hatk += document.data["主角攻擊力"].toString().toInt()
                                    hdef += document.data["主角防禦力"].toString().toInt()

                                }


                            }
                        }
                }
            }
        })

    job = GlobalScope.launch(Dispatchers.Main) {
            while (true) {

                mper.reset()
                if(startt == 1)
                {
                    sec.text = "3"
                    delay(1000)
                    sec.text = "2"
                    delay(1000)
                    sec.text = "1"
                    delay(1000)
                    sec.text = "GO!"
                    delay(1000)
                    sec.text = ""
                    matkk.text = "癐務攻擊力:"
                    mheal.text = "癐務血量:"
                    mdeff.text = "癐務防禦力:"
                    hatkk.text = "主角攻擊力:"
                    hheal.text = "主角血量:"
                    hdeff.text = "主角防禦力:"

                    txv2.text = mhealth
                    txv3.text = matk
                    txv4.text = mdef
                    txv5.text = hhealth
                    txv6.text = hatk
                    txv7.text = hdef

                    startt = 0
                }
                if(startt == 87) {
                    txv2.text = ""
                    txv3.text = ""
                    txv4.text = ""
                    txv5.text = ""
                    txv6.text = ""
                    txv7.text = ""
                    matkk.text = ""
                    mheal.text = ""
                    mdeff.text = ""
                    hatkk.text = ""
                    hheal.text = ""
                    hdeff.text = ""
                }

                binding.btn.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(p0: View?) {
                        attack = !attack  //check是布林值，要先在前面設var check: Boolean = false
                        if(attack == true)
                        {
                            num = (Math.random()*2).toInt()
                        }
                    }
                })
                binding.btn2.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(p0: View?) {
                        defence = !defence //check是布林值，要先在前面設var check: Boolean = false
                        if(defence == true)
                        {
                            num = (Math.random()*5).toInt()
                        }
                    }
                })

                if (check && attack && num == 1) {
                    val canvas: Canvas = binding.mysv.holder.lockCanvas()
                    binding.mysv.drawSomething(canvas)
                    binding.mysv.holder.unlockCanvasAndPost(canvas)
                    img2.setImageResource(R.drawable.yu2)
                    mhealth = (Integer.parseInt(mhealth)-(Integer.parseInt(hatk)-Integer.parseInt(mdef))).toString()
                    txv2.text = Integer.parseInt(mhealth).toString()

                    delay(500)
                    if(Integer.parseInt(mhealth) == 0) {
                        img2.setImageResource(R.drawable.yu4)
                        check = false
                        img1.setImageResource(R.drawable.start)
                        attack = false
                    }
                    else{
                        img2.setImageResource(R.drawable.yu1)
                        attack = false
                    }
                }
                if (check && attack && num == 0) {
                    val canvas: Canvas = binding.mysv.holder.lockCanvas()
                    binding.mysv.drawSomething(canvas)
                    binding.mysv.holder.unlockCanvasAndPost(canvas)
                    img2.setImageResource(R.drawable.yu2)
                    mhealth = (Integer.parseInt(mhealth)-(Integer.parseInt(hatk)-Integer.parseInt(mdef))).toString()
                    txv2.text = Integer.parseInt(mhealth).toString()

                    delay(500)

                    if(Integer.parseInt(mhealth) == 0) {
                        img2.setImageResource(R.drawable.yu4)
                        check = false
                        img1.setImageResource(R.drawable.start)
                        attack = false
                    }
                    else{
                        img2.setImageResource(R.drawable.yu3)
                        hhealth = (Integer.parseInt(hhealth)-(Integer.parseInt(matk)-Integer.parseInt(hdef))).toString()
                        txv5.text = Integer.parseInt(hhealth).toString()
                        delay(500)
                        img2.setImageResource(R.drawable.yu1)
                        attack = false
                    }
                }

                if (check && defence && (num == 0 || num == 1 || num == 2 || num == 3)) {
                    val canvas: Canvas = binding.mysv.holder.lockCanvas()
                    binding.mysv.drawSomething(canvas)
                    binding.mysv.holder.unlockCanvasAndPost(canvas)
                    img2.setImageResource(R.drawable.yu3)
                    hhealth = (Integer.parseInt(hhealth)-(Integer.parseInt(matk)-Integer.parseInt(hdef))).toString()
                    txv5.text = Integer.parseInt(hhealth).toString()
                    delay(500)
                    if(Integer.parseInt(hhealth) != 0) {
                        img2.setImageResource(R.drawable.yu1)
                        defence = false
                    }
                    else{
                        img2.setImageResource(R.drawable.yu1)
                        defence = false
                        check = false
                        img1.setImageResource(R.drawable.start)
                    }

                }
                if (check && defence && num == 4) {
                    val canvas: Canvas = binding.mysv.holder.lockCanvas()
                    binding.mysv.drawSomething(canvas)
                    binding.mysv.holder.unlockCanvasAndPost(canvas)
                    img2.setImageResource(R.drawable.yu3)

                    if(Integer.parseInt(hhealth) != 0) {
                        if(Integer.parseInt(matk) != Integer.parseInt(hdef)){
                            hdef = ((Integer.parseInt(hdef)+1)).toString()
                            txv7.text = Integer.parseInt(hdef).toString()
                            defscs.text = "格檔成功，防禦力+1"
                            delay(500)
                            defscs.text = ""
                        }
                        img2.setImageResource(R.drawable.yu1)
                        defence = false
                    }
                    else{
                        delay(500)
                        img2.setImageResource(R.drawable.yu1)
                        defence = false
                        check = false
                        img1.setImageResource(R.drawable.start)
                    }
                }

                delay(500)




            }

        }

    }

    override fun onDown(p0: MotionEvent?): Boolean {
        return true
    }

    override fun onShowPress(p0: MotionEvent?) {
    }

    override fun onSingleTapUp(p0: MotionEvent?): Boolean {
        return true
    }

    override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        return true
    }

    override fun onLongPress(p0: MotionEvent?) {
        //TODO("Not yet implemented")
    }

    override fun onFling(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        return true
    }

    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        return true
    }
}