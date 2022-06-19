package tw.edu.pu.s1092790.monsteryu

import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import tw.edu.pu.s1092790.monsteryu.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance()
    lateinit var binding: ActivityMainBinding
    lateinit var job: Job
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
        lateinit var job: Job
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)





        /*db.collection("monsteryu")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    var msg: String = ""
                    for (document in task.result!!) {
                        msg +=document.data["怪獸防禦力"].toString()
                    }
                    if (msg != "") {
                        txv2.text = msg
                    } else {
                        txv2.text = "查無資料"
                    }
                }
            }*/


        binding.img1.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                check = !check  //check是布林值，要先在前面設var check: Boolean = false
                if (check == false) {
                    img1.setImageResource(R.drawable.start)
                    startt = 87
                    db.collection("monsteryu")
                        .get()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                for (document in task.result!!) {
                                    mhealth += document.data["怪獸血量"].toString()
                                    matk += document.data["怪獸攻擊力"].toString()
                                    mdef = document.data["怪獸防禦力"].toString()
                                    hhealth += document.data["主角血量"].toString()
                                    hatk = document.data["主角攻擊力"].toString()
                                    hdef += document.data["主角防禦力"].toString()

                                }
                                txv2.text = hdef
                            }
                        }
                }
                else {
                    img1.setImageResource(R.drawable.stop)
                    startt = 1
                }
            }
        })

    job = GlobalScope.launch(Dispatchers.Main) {
            while (true) {
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
                            num = (Math.random()*2).toInt()
                        }
                    }
                })

                if (check && attack && num == 1) {
                    val canvas: Canvas = binding.mysv.holder.lockCanvas()
                    binding.mysv.drawSomething(canvas)
                    binding.mysv.holder.unlockCanvasAndPost(canvas)
                    img2.setImageResource(R.drawable.yu2)
                    attack = false
                }
                if (check && attack && num == 0) {
                    val canvas: Canvas = binding.mysv.holder.lockCanvas()
                    binding.mysv.drawSomething(canvas)
                    binding.mysv.holder.unlockCanvasAndPost(canvas)
                    img2.setImageResource(R.drawable.yu2)
                    delay(300)
                    img2.setImageResource(R.drawable.mid)
                    attack = false
                }
                if (check && defence && num == 1) {
                    val canvas: Canvas = binding.mysv.holder.lockCanvas()
                    binding.mysv.drawSomething(canvas)
                    binding.mysv.holder.unlockCanvasAndPost(canvas)
                    img2.setImageResource(R.drawable.yu3)
                    defence = false
                }
                delay(300)
                img2.setImageResource(R.drawable.yu1)

            }

        }

    }
}