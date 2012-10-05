package com.tooflya.airbubblegum.screens;

import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.bitmap.BitmapTexture.BitmapTextureFormat;

import com.tooflya.airbubblegum.Game;
import com.tooflya.airbubblegum.Options;
import com.tooflya.airbubblegum.Screen;
import com.tooflya.airbubblegum.entities.Entity;

/**
 * @author Tooflya.com
 * @since
 */
public class LoadingScreen extends Screen {

	// ===========================================================
	// Constants
	// ===========================================================

	private final static BitmapTextureAtlas mBackgroundTextureAtlas = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

	private final static Entity mBackground = new Entity(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "preload_screen.png", 0, 0, 1, 1), false) {

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

	private final static Entity mProgressBar = new Entity(0, 0, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas, Game.context, "preload_line.png", 0, 735, 1, 1), false) {

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

	private final static TimerHandler mTimer = new TimerHandler(1f / 15.0f, true, new ITimerCallback() {
		@Override
		public void onTimePassed(TimerHandler pTimerHandler) {

			/** Changing size of progressbar */
			if (mProgressBar.getWidthScaled() < mProgressBar.getBaseWidth() * Options.CAMERA_RATIO_FACTOR) {
				mProgressBar.getTextureRegion().setWidth((int) (mProgressBar.getTextureRegion().getWidth() + 3));
				mProgressBar.setWidth(mProgressBar.getWidth() + 3);
			} else {
				/** If progressbar is full */
				Game.isGameLoaded = true;
			}
		}
	});

	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================

	public LoadingScreen() {
		Game.loadTextures(mBackgroundTextureAtlas);

		this.setBackground(new ColorBackground(1f, 1f, 1f, 1f));

		mBackground.create().setCenterPosition(Options.cameraCenterX, Options.cameraCenterY);
		this.attachChild(mBackground);

		mProgressBar.create().setPosition(Options.cameraCenterX - mProgressBar.getWidthScaled() / 2, 843f * Options.CAMERA_RATIO_FACTOR);
		mProgressBar.setWidth(1);
		mProgressBar.getTextureRegion().setWidth(1);
		this.attachChild(mProgressBar);

		/** Register timer of loading progressbar changes */
		registerUpdateHandler(mTimer);
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.Screen#onDetached()
	 */
	@Override
	public void onDetached() {
		super.onDetached();

		clearUpdateHandlers();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.Screen#onBackPressed()
	 */
	@Override
	public boolean onBackPressed() {
		return false;
	}

	// ===========================================================
	// Methods
	// ===========================================================

}