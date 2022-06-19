package tw.edu.pu.s1092790.monsteryu

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView

class MySurfaceView(context: Context?, attrs: AttributeSet?) : SurfaceView(context, attrs) ,SurfaceHolder.Callback{
    lateinit var surfaceHolder: SurfaceHolder
    lateinit var BG: Bitmap

    init {
        surfaceHolder = getHolder()
        BG = BitmapFactory.decodeResource(getResources(), R.drawable.backgroundyu)
        surfaceHolder.addCallback(this)
    }


    override fun surfaceCreated(p0: SurfaceHolder) {
        var canvas: Canvas = surfaceHolder.lockCanvas()
        drawSomething(canvas)
        surfaceHolder.unlockCanvasAndPost(canvas)

    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
        //TODO("Not yet implemented")
    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        //TODO("Not yet implemented")
    }

    fun drawSomething(canvas:Canvas) {
        canvas.drawBitmap(BG, 0f, 0f, null)
        var SrcRect: Rect = Rect(0, 0, BG.width, BG.height) //裁切
        var w:Int = BG.width / 2
        var h:Int = BG.height
        var DestRect:Rect = Rect(0, 0, w, h)
        canvas.drawBitmap(BG, SrcRect, DestRect, null)
    }
}