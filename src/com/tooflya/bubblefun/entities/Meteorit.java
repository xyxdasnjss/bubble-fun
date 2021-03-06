package com.tooflya.bubblefun.entities;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.tooflya.bubblefun.Options;

public class Meteorit extends Entity {

	public Meteorit(TiledTextureRegion pTiledTextureRegion, org.anddev.andengine.entity.Entity pParentScreen) {
		super(pTiledTextureRegion, pParentScreen);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		
		this.setSpeedX(3f);
		this.setSpeedY(3f);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.entity.Entity#onManagedUpdate(float)
	 */
	@Override
	public void onManagedUpdate(final float pSecondsElapsed) {
		this.mX += this.getSpeedX();
		this.mY += this.getSpeedY();

		if (this.mX > Options.cameraWidth || this.mY > Options.cameraHeight) {
			this.destroy();
		}
	}
}
