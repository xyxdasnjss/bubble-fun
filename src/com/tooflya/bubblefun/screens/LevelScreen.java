package com.tooflya.bubblefun.screens;

import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.bitmap.BitmapTexture.BitmapTextureFormat;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Screen;
import com.tooflya.bubblefun.entities.Bubble;
import com.tooflya.bubblefun.entities.Cloud;
import com.tooflya.bubblefun.entities.Entity;
import com.tooflya.bubblefun.entities.Particle;
import com.tooflya.bubblefun.managers.CloudsManager;

/**
 * @author Tooflya.com
 * @since
 */
public class LevelScreen extends Screen implements IOnSceneTouchListener {

	// ===========================================================
	// Constants
	// ===========================================================

	public final static BitmapTextureAtlas mBackgroundTextureAtlas0 = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	private final static BitmapTextureAtlas mBackgroundTextureAtlas1 = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	private final static BitmapTextureAtlas mBackgroundTextureAtlas2 = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

	private final static Entity mBackground = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas1, Game.context, "main_par1.png", 0, 0, 1, 1), false) {

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.tooflya.bouncekid.entity.Entity#deepCopy()
		 */
		@Override
		public Entity deepCopy() {
			return null;
		}
	};

	private final static Entity mDottedLine = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas2, Game.context, "line.png", 0, 1016, 1, 1), false) {

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.tooflya.bouncekid.entity.Entity#deepCopy()
		 */
		@Override
		public Entity deepCopy() {
			return null;
		}
	};

	// ===========================================================
	// Fields
	// ===========================================================

	private Bubble lastAirgum = null;

	private CloudsManager clouds;

	// ===========================================================
	// Constructors
	// ===========================================================

	public LevelScreen() {
		this.setOnSceneTouchListener(this);
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.airbubblegum.Screen#init()
	 */
	@Override
	public void init() {
		this.attachChild(mBackground);

		mBackground.create().setCenterPosition(Options.cameraCenterX, Options.cameraCenterY);
		mDottedLine.create().setPosition(0, Options.cameraHeight / 3 * 2);

		this.clouds = new CloudsManager(10, new Cloud(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas2, Game.context, "clouds.png", 0, 0, 1, 4), Screen.LEVEL));
		this.clouds.generateStartClouds();

		this.attachChild(mDottedLine);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.Screen#onDetached()
	 */
	@Override
	public void onAttached() {
		super.onAttached();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.Screen#onDetached()
	 */
	@Override
	public void onDetached() {
		super.onDetached();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.entity.sprite.AnimatedSprite#onManagedUpdate (float)
	 */
	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

		this.clouds.update();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.airbubblegum.Screen#loadResources()
	 */
	@Override
	public void loadResources() {
		Game.loadTextures(mBackgroundTextureAtlas0, mBackgroundTextureAtlas1, mBackgroundTextureAtlas2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.airbubblegum.Screen#unloadResources()
	 */
	@Override
	public void unloadResources() {
		Game.unloadTextures(mBackgroundTextureAtlas0, mBackgroundTextureAtlas1, mBackgroundTextureAtlas2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.Screen#onBackPressed()
	 */
	@Override
	public boolean onBackPressed() {
		PreloaderScreen.isTheGame = false;
		Game.screens.set(Screen.LOAD);

		return true;
	}

	@Override
	public boolean onSceneTouchEvent(Scene arg0, TouchEvent pTouchEvent) {
		switch (pTouchEvent.getAction()) {
		case TouchEvent.ACTION_DOWN:
			if (this.lastAirgum == null && pTouchEvent.getY() > Options.cameraHeight - Options.cameraHeight / 3)
			{
				this.lastAirgum = (Bubble) Game.world.airgums.create();
				this.lastAirgum.setCenterPosition(pTouchEvent.getX(), pTouchEvent.getY());
				this.lastAirgum.setScaleCenterY(0);
			}
			if (this.lastAirgum != null) {
				this.lastAirgum.setIsScale(true);
			}

			break;
		case TouchEvent.ACTION_UP:
			if (this.lastAirgum != null) {
				final float koef = 10f;
				if (this.lastAirgum.getCenterY() - pTouchEvent.getY() > 10f) {
					this.lastAirgum.setSpeedY(this.lastAirgum.getSpeedY() + (this.lastAirgum.getCenterY() - pTouchEvent.getY()) / koef);
					this.lastAirgum.setSpeedX((pTouchEvent.getX() - this.lastAirgum.getCenterX()) / (koef * 5));

					Particle particle;
					for (int i = 0; i < Options.particlesCount / 2; i++) {
						particle = ((Particle) Game.world.particles.create());
						if (particle != null) {
							particle.Init().setCenterPosition(this.lastAirgum.getCenterX(), this.lastAirgum.getCenterY());
						}
					}
				}
				this.lastAirgum.setIsScale(false);
				this.lastAirgum = null;
			}
			break;
		}

		return false;
	}
	// ===========================================================
	// Methods
	// ===========================================================

}