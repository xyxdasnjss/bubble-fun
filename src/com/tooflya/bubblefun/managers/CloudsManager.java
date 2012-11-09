package com.tooflya.bubblefun.managers;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.entities.Cloud;

public class CloudsManager<T> extends EntityManager<Cloud> {

	public CloudsManager(int capacity, Cloud element) {
		super(capacity, element);
	}

	public void generateStartClouds() {
		for (int i = 0; i < 5; i++) {
			this.generateCloud(true);
		}
	}

	public void generateCloud(final boolean isStart) {
		final int frame = Game.random.nextInt(4);
		final Cloud cloud = this.create();



		cloud.setPosition(isStart ? Game.random.nextInt((int) (Options.cameraWidth + cloud.getWidth())) - cloud.getWidth() : Options.cameraWidth,
				Game.random.nextInt((int) (Options.cameraHeight / 3 * 2 - cloud.getHeight())));
		//cloud.setAlpha(0.4f + Game.random.nextFloat() * (1f - 0.4f));
		cloud.setSpeedX(0.2f + Game.random.nextFloat() * (2f - 0.2f));
		cloud.setCurrentTileIndex(frame);
	}

	public void update() {
		if (this.getCount() < 8) {
			this.generateCloud(false);
		}
	}
}
