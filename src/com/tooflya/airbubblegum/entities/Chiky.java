package com.tooflya.airbubblegum.entities;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.annotation.SuppressLint;
import android.util.FloatMath;

import com.tooflya.airbubblegum.Options;

/**
 * @author Tooflya.com
 * @since
 */
public class Chiky extends Entity {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private float time = 0;
	private float timeStep = 1;
	private float offsetTime = 0;

	// ===========================================================
	// Constructors
	// ===========================================================

	/**
	 * @param pTiledTextureRegion
	 */
	public Chiky(TiledTextureRegion pTiledTextureRegion) {
		super(pTiledTextureRegion);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Setters
	// ===========================================================

	public void setOffsetTime(final float offsetTime) {
		this.offsetTime = offsetTime;
		this.setCenterPosition(this.getCalculatedX(), this.getCalculatedY());
	}

	// ===========================================================
	// Getters
	// ===========================================================

	private float getCalculatedX() {
		return FloatMath.sin(this.time + this.offsetTime) * (Options.cameraWidth - 2 * this.getWidth()) + this.getWidth();
	}

	private float getCalculatedY() {
		return FloatMath.cos(this.time + this.offsetTime) * (Options.cameraWidth - 2 * this.getHeight()) + this.getHeight());
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.entity.Entity#onManagedUpdate(float)
	 */
	@Override
	public void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

		this.time += this.timeStep;
		this.setCenterX(this.getCalculatedX());
		this.setCenterY(this.getCalculatedY());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.entity.Entity#deepCopy()
	 */
	@Override
	public Entity deepCopy() {
		return new Chiky(getTextureRegion());
	}
}
