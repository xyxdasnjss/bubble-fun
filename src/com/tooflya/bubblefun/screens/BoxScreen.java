package com.tooflya.bubblefun.screens;

import java.util.ArrayList;
import java.util.List;

import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.input.touch.detector.ClickDetector;
import org.anddev.andengine.input.touch.detector.ClickDetector.IClickDetectorListener;
import org.anddev.andengine.input.touch.detector.ScrollDetector;
import org.anddev.andengine.input.touch.detector.ScrollDetector.IScrollDetectorListener;
import org.anddev.andengine.input.touch.detector.SurfaceScrollDetector;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.bitmap.BitmapTexture.BitmapTextureFormat;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.Screen;
import com.tooflya.bubblefun.entities.ButtonScaleable;
import com.tooflya.bubblefun.entities.Cloud;
import com.tooflya.bubblefun.entities.Sprite;
import com.tooflya.bubblefun.managers.CloudsManager;

/**
 * @author Tooflya.com
 * @since
 */
public class BoxScreen extends Screen implements IOnSceneTouchListener, IScrollDetectorListener, IClickDetectorListener {

	// ===========================================================
	// Constants
	// ===========================================================

	protected static int FONT_SIZE = 28;
	protected static int PADDING = 50;

	protected static int MENUITEMS = 4;

	private final BitmapTextureAtlas mBackgroundTextureAtlas1 = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	private final BitmapTextureAtlas mBackgroundTextureAtlas2 = new BitmapTextureAtlas(1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

	// ===========================================================
	// Fields
	// ===========================================================   

	private BitmapTextureAtlas mMenuTextureAtlas;
	private TextureRegion mMenuLeftTextureRegion;
	private TextureRegion mMenuRightTextureRegion;

	private SurfaceScrollDetector mScrollDetector;
	private ClickDetector mClickDetector;

	private float mMinX = 0;
	private float mMaxX = 0;
	private float mCurrentX = 0;
	private int iItemClicked = -1;
	
	
	private boolean mPostScroll = false;
	private boolean mPostScrollToNear = false;
	private boolean mPostScrollWay;

	private Rectangle scrollBar;
	private List<Sprite> A = new ArrayList<Sprite>();

	private final Sprite mBackground = new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas1, Game.context, "main-bg.png", 0, 0, 1, 1), this);

	private final CloudsManager<Cloud> mClouds = new CloudsManager<Cloud>(10, new Cloud(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas1, Game.context, "cloud.png", 382, 0, 1, 3), this.mBackground));

	private final ButtonScaleable mBackButton = new ButtonScaleable(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas1, Game.context, "back-btn.png", 100, 900, 1, 2), this.mBackground) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.entities.Button#onClick()
		 */
		@Override
		public void onClick() {
			Game.screens.set(Screen.MENU);
		}
	};

	private final Rectangle rectangle = new Rectangle(0, 0, Options.cameraWidth * MENUITEMS, Options.cameraHeight);

	// ===========================================================
	// Constructors
	// ===========================================================

	public BoxScreen() {
		this.loadResources();

		this.mClouds.generateStartClouds();

		this.mBackground.create().setBackgroundCenterPosition();

		this.mBackButton.create().setPosition(10f, Options.cameraHeight - 60f);

		this.mBackground.attachChild(rectangle);
		this.rectangle.setAlpha(0);

		for (int i = 0; i < MENUITEMS; i++) {
			final Sprite sprite = new Sprite(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBackgroundTextureAtlas2, Game.context, "menu" + i + ".png", 0, 0, 1, 1), this.rectangle);
			sprite.create().setCenterPosition(Options.cameraWidth * i + Options.cameraCenterX, Options.cameraCenterY);

			A.add(sprite);
			
			mMaxX = sprite.getWidth() - Options.cameraWidth;
		}

		this.mScrollDetector = new SurfaceScrollDetector(this);
		this.mClickDetector = new ClickDetector(this);

		this.setOnSceneTouchListener(this);
		this.setTouchAreaBindingEnabled(true);
		this.setOnSceneTouchListenerBindingEnabled(true);
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.entity.sprite.AnimatedSprite#onManagedUpdate (float)
	 */
	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

		this.mClouds.update();
		
		if(this.mPostScroll) {
			this.rectangle.setPosition(this.rectangle.getX() + sx, 0);
			
			if(sx > 0) {

				if(sx <= 0) {
					this.mPostScroll = false;
				}
			}
			
			if(sx < 0) {

				if(sx >= 0) {
					this.mPostScroll = false;
				}
			}
			
			if(Math.abs(this.rectangle.getX() % Options.cameraWidth) <= 10) {
				this.mPostScroll = false;
			}
		}
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
	 * @see com.tooflya.airbubblegum.Screen#loadResources()
	 */
	@Override
	public void loadResources() {
		Game.loadTextures(this.mBackgroundTextureAtlas1, this.mBackgroundTextureAtlas2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.airbubblegum.Screen#unloadResources()
	 */
	@Override
	public void unloadResources() {
		Game.unloadTextures(this.mBackgroundTextureAtlas1, this.mBackgroundTextureAtlas2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.Screen#onBackPressed()
	 */
	@Override
	public boolean onBackPressed() {
		Game.screens.set(Screen.MENU);

		return false;
	}

	@Override
	public void onClick(ClickDetector arg0, TouchEvent arg1) {
		//Game.screens.set(Screen.CHOISE);
	}

	@Override
	public boolean onSceneTouchEvent(Scene arg0, TouchEvent pSceneTouchEvent) {
		if (pSceneTouchEvent.isActionDown()) {
			this.mPostScroll = false;
		}
		
		this.mClickDetector.onTouchEvent(pSceneTouchEvent);
		this.mScrollDetector.onTouchEvent(pSceneTouchEvent);
		return false;
	}
	
private float lx, sx;
	@Override
	public void onScroll(ScrollDetector arg0, TouchEvent pTouchEvent, float pDistanceX, float pDistanceY) {
		if (pTouchEvent.isActionDown()) {
			this.mPostScroll = false;
		} else if (pTouchEvent.isActionMove()) {
			this.mPostScroll = false;
			
			mCurrentX -= pDistanceX;
			sx = pDistanceX > 0 ? 5 : -5;

			this.rectangle.setPosition(this.rectangle.getX() + pDistanceX / 2, 0);
		} else if (pTouchEvent.isActionUp()) {
			this.mPostScroll = true;
		}
	}

	// ===========================================================
	// Methods
	// ===========================================================

}