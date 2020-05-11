package com.mygdx.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Controller implements Disposable {


    public Stage stage;
    private OrthographicCamera cam;
    private Viewport viewport;

    public boolean wLewo;
    public boolean wPrawo;
    public boolean wGore;

    public boolean iswGore() {
        return wGore;
    }

    public void setwGore(boolean wGore) {
        this.wGore = wGore;
    }

    public boolean iswPrawo() {
        return wPrawo;
    }

    public void setwPrawo(boolean wPrawo) {
        this.wPrawo = wPrawo;
    }

    private Image lewo;
    private Image prawo;
    private Image gora;



    public boolean iswLewo() {
        return wLewo;
    }

    public void setwLewo(boolean wLewo) {
        this.wLewo = wLewo;
    }

    public Controller(SpriteBatch sb){

        wLewo=false;
        cam=new OrthographicCamera();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), cam);
        stage = new Stage(viewport, sb);

        lewo=new Image(new Texture("menu/w_lewo.png"));
        prawo=new Image(new Texture("menu/w_prawo.png"));
       gora=new Image(new Texture("menu/w_gore.png"));

        lewo.setPosition(Gdx.graphics.getWidth()/6,Gdx.graphics.getHeight()/3);
        lewo.setSize(Gdx.graphics.getWidth()/10,Gdx.graphics.getHeight()/10);

        prawo.setPosition(Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/3);
        prawo.setSize(Gdx.graphics.getWidth()/10,Gdx.graphics.getHeight()/10);

        gora.setPosition(Gdx.graphics.getWidth()/5,Gdx.graphics.getHeight()/2);
        gora.setSize(Gdx.graphics.getWidth()/10,Gdx.graphics.getHeight()/10);

        stage.addActor(lewo);
        stage.addActor(prawo);
        stage.addActor(gora);

       stage.addListener(new InputListener(){
           @Override
           public boolean keyDown(InputEvent event, int keycode) {
               switch (keycode) {
                   case Input.Keys.LEFT:
                       wLewo = true;
                    break;

                   case Input.Keys.RIGHT:
                       wPrawo=true;
                       break;

                   case Input.Keys.UP:
                       wGore=true;
                       break;


               }


               return true;
           }


           @Override
           public boolean keyUp(InputEvent event, int keycode) {


               switch (keycode){
                   case Input.Keys.LEFT:
                       wLewo=false;
                break;

                   case Input.Keys.RIGHT:
                       wPrawo=false;
                       break;

                   case Input.Keys.UP:
                       wGore=false;
                       break;
               }
               return true;
           }
       });



        Gdx.input.setInputProcessor(stage);


        lewo.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                wLewo=true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                wLewo=false;
            }
        });

       prawo.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
               wPrawo=true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                wPrawo=false;
            }
        });

       gora.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
               wGore=true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
               wGore=false;
            }
        });


    }

    public void draw(){
        stage.draw();
    }

    public void resize(){
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }


    @Override
    public void dispose() {

    }
}
