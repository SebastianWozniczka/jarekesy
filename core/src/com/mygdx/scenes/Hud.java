package com.mygdx.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGame;
import com.mygdx.sprites.Player;



public class Hud extends Actions implements Disposable {


    public static Stage stage;
    private Viewport viewport;
    private Image ekwipunek;
    private BitmapFont font;

    //Mario score/time Tracking Variables
    private Integer worldTimer;
    private boolean timeUp; // true when the world timer reaches 0
    private float timeCount;
    private static Integer score;
    private Integer pierwszaLiczba;
    private Integer drugaLiczba;
    private Integer graczTimer;
    public static Table table;
    public static int zycieGracza1;
    public static int zycieGracza2;
    public static float zycieGracza3;

    //Scene2D widgets
    private static Label countdownLabel;
    private static Label scoreLabel;
    private static Label timeLabel;
    private static Label levelLabel;
    private static Label worldLabel;
    private static Label marioLabel;
    private static Label graczCzas;

    public static Label nazwaGracza1;
    private static Label nazwaGracza2;
    private static Label nazwaGracza3;


    public Hud(SpriteBatch sb){
        //define our tracking variables
        worldTimer = 300;
        graczTimer=60;
        timeCount = 0;
        score = 0;
        pierwszaLiczba=4;
        drugaLiczba=60;
        zycieGracza1=120;
        zycieGracza2=256;
        zycieGracza3=2.50F;
        font = new BitmapFont(Gdx.files.internal("fonts/foo.fnt"));

        sizeTo(80,60);
        //setup the HUD viewport using a new camera seperate from our gamecam
        //define our stage using that viewport and our games spritebatch
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage(viewport, sb);
        ekwipunek=new Image(new Texture("menu/ekwipunek.png"));

        //define a table used to organize our hud's labels
        table = new Table();
        //Top-Align table
        table.top();
        //make the table fill the entire stage
        table.setFillParent(true);

        //define our labels using the String, and a Label style consisting of a font and color
        countdownLabel = new Label("5:00", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        countdownLabel.setSize(Gdx.graphics.getWidth()/5,Gdx.graphics.getHeight()/5);
        scoreLabel =new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        scoreLabel.setSize(Gdx.graphics.getWidth()/5,Gdx.graphics.getHeight()/5);
        timeLabel = new Label("Czas", new Label.LabelStyle(font, Color.BLACK));
        timeLabel.setSize(Gdx.graphics.getWidth()/5,Gdx.graphics.getHeight()/5);
        levelLabel = new Label("1-1", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        levelLabel.setSize(Gdx.graphics.getWidth()/5,Gdx.graphics.getHeight()/5);
        worldLabel = new Label("SWIAT", new Label.LabelStyle(font, Color.BLACK));
        worldLabel.setSize(Gdx.graphics.getWidth()/5,Gdx.graphics.getHeight()/5);
        marioLabel = new Label("PUNKTY", new Label.LabelStyle(font, Color.BLACK));
        marioLabel.setSize(Gdx.graphics.getWidth()/5,Gdx.graphics.getHeight()/5);
        graczCzas = new Label(String.format("%02d",graczTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        graczCzas.setSize(Gdx.graphics.getWidth()/5,Gdx.graphics.getHeight()/5);
        nazwaGracza1 = new Label(("Jarek "+zycieGracza1+" HP"), new Label.LabelStyle(new BitmapFont(), Color.RED));
        nazwaGracza2 = new Label(("Laska "+zycieGracza2+" HP"), new Label.LabelStyle(new BitmapFont(), Color.RED));
        nazwaGracza3 = new Label(("Andrzej "+zycieGracza3+" HP"), new Label.LabelStyle(new BitmapFont(), Color.RED));
        table();


        ekwipunek.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        ekwipunek.setPosition(-800,0);



            //graczCzas.setPosition(600,0);
            //add our labels to our table, padding the top, and giving them all equal width with expandX


    }

    public static void table() {
        table.add(marioLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.add(graczCzas).expandX().padTop(15);
        //add a second row to our table
        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();
        table.row();

        table.add(nazwaGracza1).expandX();
        table.add(nazwaGracza2).expandX();
        table.add(nazwaGracza3).expandX();


        //add our table to the stage
        stage.addActor(table);
    }



    public void update(float dt){


        stage.act();
        timeCount += dt;
        if(timeCount >= 1){
            if (worldTimer > 0) {
                graczTimer--;
                worldTimer--;
            drugaLiczba--;
            if(drugaLiczba<0){
                pierwszaLiczba--;
                drugaLiczba=60;
            }




            if(graczTimer<30){
                graczCzas.setColor(Color.YELLOW);
            }
                if(graczTimer<10){
                    graczCzas.setColor(Color.RED);
                    graczCzas.setText("0"+graczTimer);
                }
            if(graczTimer<5){
               isTimeUp();
            }

            if(graczTimer<0){
                graczTimer=0;
            }


            } else {
                timeUp = true;
            }
            countdownLabel.setText( pierwszaLiczba+":"+drugaLiczba);
           graczCzas.setText( graczTimer);
            timeCount = 0;

            if(drugaLiczba<10){
                countdownLabel.setText( pierwszaLiczba+":0"+drugaLiczba);
            }
        }


      if(Gdx.input.isKeyPressed(Input.Keys.TAB)){
          stage.addActor(ekwipunek);
          react();


      }
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
          react2();
        }


    }

    private void react2() {

        Action testAction= Actions.moveTo(-800,0,1);
        ekwipunek.addAction(
                testAction

        );
    }

    private void react() {



        Action testAction= Actions.moveTo(0,0,1);
        ekwipunek.addAction(
                testAction

        );

    }

    public static void addScore(int value){
        score += value;
        scoreLabel.setText(String.format("%06d", score));
    }

    @Override
    public void dispose() { stage.dispose(); }

    public boolean isTimeUp() {

        MyGame.manager.get("dzwieki/genialnie.mp3", Music.class).play();
        Timer timer=new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                MyGame.manager.get("dzwieki/genialnie.mp3", Music.class).stop();
            }
        },6.5f);
        return timeUp; }
}

