package com.desitum.library

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.desitum.library.animation.MovementAnimator
import com.desitum.library.drawing.Drawable
import com.desitum.library.drawing.Drawing
import com.desitum.library.game.AssetManager
import com.desitum.library.game.GameScreen
import com.desitum.library.game_objects.GameObject
import com.desitum.library.interpolation.Interpolation
import com.desitum.library.logging.Log
import com.desitum.library.particles.ParticleBuilder
import com.desitum.library.view.*

/**
 * Created by kody on 12/12/15.
 * can be used by kody and people in [kody}]
 */
class MenuScreen internal constructor() : GameScreen(150f, 100f, MenuScreen.SCREEN_WIDTH, MenuScreen.SCREEN_HEIGHT, GameScreen.ASPECT_FILL) {


    private var progressBar: ProgressBar? = null
    private var circularProgressBar: ProgressBar? = null
    private var seekBar: SeekBar? = null
    private var layout: LinearLayout? = null

    init {
        //        super(getScreenWidth(), getScreenHeight());
        setClearColor(Color(0.5f, 0f, 0.5f, 1f))

        setupWorld()
        Log.d(this, "Hello world")
        Log.d(this.javaClass, "Hello world")
        Log.d("Hello I am a muffin", "Hello world")
    }

    private fun setupWorld() {
        val mAssetManager = AssetManager.instance
        mAssetManager.addTexture("big_picture_a_1.png")
        mAssetManager.addDrawable(BUTTON_HOVER, Drawable(TextureRegion(mAssetManager.getTexture(0), 0, 0, 1000, 100)))
        mAssetManager.addDrawable(BUTTON_REST, Drawable(TextureRegion(mAssetManager.getTexture(0), 0, 100, 1000, 100)))
        mAssetManager.addDrawable(BUTTON_DOWN, Drawable(TextureRegion(mAssetManager.getTexture(0), 0, 200, 500, 50)))
        mAssetManager.addDrawable(BADLOGIC, Drawable(TextureRegion(mAssetManager.getTexture(0), 500, 200, 256, 256)))
        mAssetManager.addDrawable(CIRCLE_SHADOW, Drawable(TextureRegion(mAssetManager.getTexture(0), 756, 200, 200, 200)))
        mAssetManager.addDrawable(PROGRESS_BAR, Drawable(NinePatch(TextureRegion(mAssetManager.getTexture(0), 0, 250, 200, 200), 66, 66, 66, 66)))
        mAssetManager.addDrawable(PROGRESS_BG, Drawable(NinePatch(TextureRegion(mAssetManager.getTexture(0), 200, 250, 200, 200), 66, 66, 66, 66)))
        mAssetManager.addDrawable(SLIDER, Drawable(TextureRegion(mAssetManager.getTexture(0), 756, 400, 200, 200)))
        mAssetManager.addDrawable(GRAPHIKS2D, Drawable(TextureRegion(mAssetManager.getTexture(0), 0, 450, 128, 128)))
        mAssetManager.addDrawable(CIRCULAR_PROGRESS, Drawable(NinePatch(TextureRegion(mAssetManager.getTexture(0), 400, 250, 100, 100), 48, 49, 49, 48)))
        mAssetManager.addDrawable(CIRCULAR_PROGRESS_BAR, Drawable(NinePatch(TextureRegion(mAssetManager.getTexture(0), 956, 200, 46, 46), 21, 22, 21, 21)))
        mAssetManager.addDrawable(PARTICLE, Drawable(NinePatch(TextureRegion(mAssetManager.getTexture(0), 1000, 0, 10, 10), 3, 3, 3, 3)))


        //        getWorld().addGameObject(new GameObject(new TextureRegion(Drawing.getFilledRectangle(1, 1, Color.BLUE), 2000, 1500, -50, -50), getWorld()));

        //        GameObject gameObject = new GameObject(new TextureRegion(Drawing.getFilledRectangle(1, 1, Color.BLUE)), getWorld());
        //        gameObject.setSize(100, 100);
        //        getWorld().addGameObject(gameObject);
        //        getWorld().addParticleEmitter(ParticleBuilder.buildParticleEmitter(Gdx.files.internal("wallParticles.prt")));
        //        getWorld().getParticleEmitters().get(0).turnOn();

        //        Button button = new Button(getWorld());
        //        button.setSize(200, 200);
        //        button.setPosition(10, 10);
        //        button.setRestDrawable(mAssetManager.getDrawable(PARTICLE));
        //        button.setHoverDrawable(mAssetManager.getDrawable(BADLOGIC));
        //        button.setOriginCenter();
        //        getWorld().addView(button);

        val linearLayoutConstraints = LayoutConstraints(100f, 100f, 800f, 600f)
        layout = LinearLayout(world, linearLayoutConstraints)
        layout!!.backgroundDrawable = Drawable(Drawing.getFilledRectangle(1, 1, Color.CHARTREUSE))
        world.addView(layout!!)

        seekBar = SeekBar(world)
        seekBar!!.progress = 0.5f
        seekBar!!.progressBackgroundDrawable = mAssetManager.getDrawable(CIRCULAR_PROGRESS)
        seekBar!!.seekerDrawable = Drawable(Drawing.getFilledCircle(200, Color.RED))
        seekBar!!.progressDrawable = mAssetManager.getDrawable(CIRCULAR_PROGRESS_BAR)
        seekBar!!.setSize(600f, 200f)
        seekBar!!.progressBarHeight = 100f
        layout!!.addView(seekBar!!)


        progressBar = ProgressBar(world)
        progressBar!!.progress = 0.5f
        progressBar!!.progressBackgroundDrawable = mAssetManager.getDrawable(CIRCULAR_PROGRESS)
        //        progressBar.getProgressBackgroundDrawable().setColor(Color.BLUE);
        //        progressBar.setProgressDrawable(Drawable.loadDrawable("progress.png", true));
        progressBar!!.progressDrawable = mAssetManager.getDrawable(PROGRESS_BAR)
        progressBar!!.progressBarHeight = 200f
        progressBar!!.setSize(800f, 200f)
        //        layout.addView(progressBar);


        circularProgressBar = CircularProgressBar(world)
        circularProgressBar!!.progress = 0.5f
        circularProgressBar!!.progressBackgroundDrawable = mAssetManager.getDrawable(CIRCULAR_PROGRESS)
        circularProgressBar!!.progressDrawable = mAssetManager.getDrawable(CIRCULAR_PROGRESS_BAR)
        circularProgressBar!!.progressBarHeight = 100f
        circularProgressBar!!.setSize(800f, 100f)
        layout!!.addView(circularProgressBar!!)

        val textView = TextView(world, null,
                BitmapFont(Gdx.files.internal("cartoon.fnt"), TextureRegion(Texture("cartoon.png"))))
        textView.setSize(View.MATCH_PARENT, 100f)
        textView.backgroundDrawable = mAssetManager.getDrawable(PARTICLE)
        layout!!.addView(textView)

        val editText = EditText(world, null,
                BitmapFont(Gdx.files.internal("cartoon.fnt"), TextureRegion(Texture("cartoon.png"))))
        editText.setSize(View.MATCH_PARENT, 100f)
        editText.backgroundDrawable = mAssetManager.getDrawable(PARTICLE)
        editText.hint = "Hello"
        layout!!.addView(editText)

        layout!!.startAnimator(MovementAnimator(layout!!, 0f, 400f, 4f, 0f, Interpolation.DECELERATE_INTERPOLATOR, true, true))

        val view = LayoutInflater.inflate(FileHandle("view.json"), null)
        Log.d(MenuScreen::class.java, "{$view}")

        Thread(Runnable {
            val endTime = System.currentTimeMillis() + 4000
            while (System.currentTimeMillis() < endTime) {
                progressBar!!.progress = 1 - (endTime - System.currentTimeMillis()) / 4000.0f
                circularProgressBar!!.progress = 1 - (endTime - System.currentTimeMillis()) / 4000.0f
                //                    layout.setPosition(layout.getX() + 1, layout.getY() + 1);
                try {
                    Thread.sleep(10)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

            }
            progressBar!!.progress = 1f
        }).start()
    }

    override fun show() {

    }


    override fun pause() {

    }

    override fun resume() {

    }

    override fun hide() {

    }

    override fun dispose() {
        AssetManager.dispose()
    }

    companion object {

        val SCREEN_WIDTH = 1920.0f
        val SCREEN_HEIGHT = 1080.0f

        val BUTTON_HOVER = 1
        val BUTTON_REST = 2
        val BUTTON_DOWN = 3
        val BADLOGIC = 4
        val CIRCLE_SHADOW = 5
        val PROGRESS_BAR = 6
        val PROGRESS_BG = 7
        val SLIDER = 8
        val GRAPHIKS2D = 9
        val CIRCULAR_PROGRESS = 10
        val CIRCULAR_PROGRESS_BAR = 11
        val PARTICLE = 12
    }
}
