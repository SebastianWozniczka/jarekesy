package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.screens.GamePlay;

public class MyGame extends Game {
	public static short DESTROYED_BIT = 1;
	public static short PLAYER_BIT = 2;
	public static  short ENEMY_BIT = 4;
	public static  short GROUND_BIT = 8;
	public SpriteBatch batch;
	public static AssetManager manager;
	private boolean paused;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new GamePlay(this));

		manager = new AssetManager();
		manager.load("dzwieki/dum_dum.mp3", Music.class);
		manager.load("dzwieki/kic_kic.mp3", Music.class);
		manager.load("dzwieki/tak_jest.mp3", Music.class);
		manager.load("dzwieki/hop.mp3", Music.class);
		manager.load("dzwieki/genialnie.mp3", Music.class);
		manager.finishLoading();
	}

	@Override
	public void render () {
		super.render();
	}

	public void setPaused ( boolean paused){
		this.paused = paused;
	}

	public boolean isPaused () {
		return paused;
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
