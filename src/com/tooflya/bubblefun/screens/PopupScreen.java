package com.tooflya.bubblefun.screens;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.entity.modifier.AlphaModifier;
import org.anddev.andengine.entity.modifier.ScaleModifier;
import org.anddev.andengine.entity.primitive.Rectangle;

import com.tooflya.bubblefun.Options;

public class PopupScreen extends Screen {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	protected boolean mAnimationRunning = false;

	protected final Rectangle mRectangle = this.makeColoredRectangle(0, 0, 0f, 0f, 0f);

	protected final AlphaModifier mRectangleAlphaModifierOn = new AlphaModifier(0.7f, 0f, 0.6f);
	protected final AlphaModifier mRectangleAlphaModifierOff = new AlphaModifier(0.2f, 0.6f, 0f);

	protected final ScaleModifier modifier1 = new ScaleModifier(0.3f, 0f, Options.cameraRatioFactor + 0.1f * Options.cameraRatioFactor) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.modifiers.ScaleModifier#onFinished()
		 */
		@Override
		public void onFinished() {
			modifier2.reset();
		}

		/* (non-Javadoc)
		 * @see org.anddev.andengine.util.modifier.BaseDurationModifier#reset()
		 */
		@Override
		public void reset() {
			super.reset();

			mRectangleAlphaModifierOn.reset();
			mAnimationRunning = true;
		}
	};

	protected final ScaleModifier modifier2 = new ScaleModifier(0.2f, Options.cameraRatioFactor + 0.1f * Options.cameraRatioFactor, Options.cameraRatioFactor - 0.1f * Options.cameraRatioFactor) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.modifiers.ScaleModifier#onFinished()
		 */
		@Override
		public void onFinished() {
			modifier3.reset();
		}
	};

	protected final ScaleModifier modifier3 = new ScaleModifier(0.2f, Options.cameraRatioFactor - 0.1f * Options.cameraRatioFactor, Options.cameraRatioFactor) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.modifiers.ScaleModifier#onFinished()
		 */
		@Override
		public void onFinished() {
			mAnimationRunning = false;

			onShow();
		}
	};

	protected final ScaleModifier modifier4 = new ScaleModifier(0.2f, Options.cameraRatioFactor, 0f) {

		/* (non-Javadoc)
		 * @see com.tooflya.bubblefun.modifiers.ScaleModifier#onFinished()
		 */
		@Override
		public void onFinished() {
			onClose();
		}

		/* (non-Javadoc)
		 * @see org.anddev.andengine.util.modifier.BaseDurationModifier#reset()
		 */
		@Override
		public void reset() {
			super.reset();

			mRectangleAlphaModifierOff.reset();
		}
	};

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Virtual methods
	// ===========================================================

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.Screen#onDetached()
	 */
	@Override
	public void onAttached() {
		super.onAttached();

		this.modifier1.reset();
	}

	/* (non-Javadoc)
	 * @see com.tooflya.bubblefun.screens.Screen#onPostAttached()
	 */
	@Override
	public void onPostAttached() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tooflya.bouncekid.Screen#onDetached()
	 */
	@Override
	public void onDetached() {
		if (!this.mAnimationRunning) {
			this.modifier4.reset();
		}
	}

	@Override
	public void onBackPressed() {
	}

	// ===========================================================
	// Methods
	// ===========================================================

	public void onShow() {

	}

	public void onClose() {

	}

	private Rectangle makeColoredRectangle(final float pX, final float pY, final float pRed, final float pGreen, final float pBlue) {
		final Rectangle coloredRect = new Rectangle(pX, pY, Options.cameraWidth, Options.cameraHeight);
		coloredRect.setColor(pRed, pGreen, pBlue);
		coloredRect.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		coloredRect.setAlpha(0f);

		this.attachChild(coloredRect);

		return coloredRect;
	}
}
