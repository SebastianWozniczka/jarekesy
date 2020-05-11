package com.mygdx.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGame;
import com.mygdx.scenes.Hud;
import com.mygdx.sprites.Player;
import com.mygdx.tools.ContactListener;
import com.mygdx.tools.Controller;

import static com.mygdx.sprites.Player.b2body;
import static com.mygdx.sprites.Player.stateTimer;

public class GamePlay implements Screen {
    private MyGame game;
    private Player player;
    private Controller controller;
    private Hud hud;
    private float timer,timer2,timer3;
    private float czasStart;
    private final float UPDATE_TIME = 1 /10F;
    public static boolean shift;
    private float zycie1;
    FixtureDef fdef;



    public OrthographicCamera cam;
    private Viewport gamePort;
    private World world;

    private TiledMap map;
    private TmxMapLoader loader;
    private Box2DDebugRenderer b2dr;
    private OrthogonalTiledMapRenderer renderer;
    private String mapka="mapy/mapa1.tmx";

    public boolean isShift() {
        return shift;
    }

    public void setShift(boolean shift) {
        this.shift = shift;
    }

    public GamePlay(MyGame game) {
        this.game=game;
        timer=0;
        timer2=0;
        timer3=0;
        czasStart=0;
        zycie1=Hud.zycieGracza1;

        initCam();
        initMap();


        controller=new Controller(game.batch);
        ziemia();
        getPlayer();
        hud=new Hud(game.batch);




    }

    private void ziemia(){

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
       fdef = new FixtureDef();
        Body body;
        for (MapObject object : map.getLayers().get("strus").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) , (rect.getY() + rect.getHeight() / 2) );
            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth() / 2 , rect.getHeight() / 2 );
            fdef.shape = shape;
            fdef.filter.categoryBits=MyGame.GROUND_BIT;
            if(Gdx.input.isKeyJustPressed(Input.Keys.S)){
              fdef.filter.maskBits=MyGame.PLAYER_BIT;
            }
            //fdef.filter.maskBits=MyGame.PLAYER_BIT;

            //fdef.filter.maskBits=MyGame.ENEMY_BIT;
            body.createFixture(fdef).setUserData("ziemia");
        }
        world.setContactListener(new ContactListener());
    }

    private void initCam() {
        cam=new OrthographicCamera();
        gamePort = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), cam);

        if(Gdx.app.getType()== Application.ApplicationType.Android)
            gamePort = new StretchViewport(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, cam);
        cam.position.set(4000 , 3000 , 0);

        world=new World(new Vector2(0,-20f),true);
    }

    private void initMap() {
        loader=new TmxMapLoader();
        map = loader.load(mapka);
        renderer = new OrthogonalTiledMapRenderer(map);

    }

    public void getPlayer(){
        final Rectangle rectangle=map.getLayers().get("player").getObjects().getByType(RectangleMapObject.class).get(0).getRectangle();
        player=new Player(rectangle.getX(),rectangle.getY(),world,this);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(delta);
        renderer.render();
        game.batch.setProjectionMatrix(cam.combined);
        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();
        hud.stage.draw();
        if(Gdx.app.getType()== Application.ApplicationType.Android)
        controller.draw();

    }

    public void update(float dt){
        world.step(UPDATE_TIME,6,2);

        cam.update();
        renderer.setView(cam);
        czasStart+=dt;
        player.update(dt);
        cam.position.x= b2body.getPosition().x;
        cam.position.y= b2body.getPosition().y;

        shift=true;
        if(shift)
        handleInput(dt);

        if(b2body.getPosition().x>6130||b2body.getPosition().x<20){
            shift=false;
            zycie1--;


            hud.nazwaGracza1.setText((int) zycie1);
            Timer timer=new Timer();
            timer.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    hud.nazwaGracza1.setVisible(false);
                }
            },0.2f);


            b2body.setLinearVelocity(0,-1500);
           Player.region= (TextureRegion) Player.playerLot.getKeyFrame(stateTimer, true);

        }

        if(czasStart>2)
            hud.update(dt);

    }

    public void handleInput(float dt){
        cam.zoom=10f;
        timer+=dt;
        MyGame.manager.get("dzwieki/dum_dum.mp3", Music.class).play();
        if (timer > 0.3f) {
            cam.zoom=8;
        }
        if (timer > 0.6f) {
            cam.zoom=6;
        }
        if (timer > 0.9f) {
            cam.zoom=4;
        }
        if (timer > 1.2f) {
            cam.zoom=2;
        }

        if (timer > 1.5f) {
            cam.zoom=1;
            MyGame.manager.get("dzwieki/dum_dum.mp3", Music.class).stop();
            MyGame.manager.get("dzwieki/tak_jest.mp3", Music.class).play();
        }
        if (timer > 3) {
            MyGame.manager.get("dzwieki/tak_jest.mp3", Music.class).stop();

            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && b2body.getLinearVelocity().x <= 2) {
                MyGame.manager.get("dzwieki/kic_kic.mp3", Music.class).play();
                b2body.applyLinearImpulse(new Vector2(40f, 0), b2body.getWorldCenter(), true);

                Timer timer=new Timer();
                timer.scheduleTask(new Timer.Task() {
                    @Override
                    public void run() {
                        MyGame.manager.get("dzwieki/kic_kic.mp3", Music.class).stop();
                    }
                },1);
            }


            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && b2body.getLinearVelocity().x >= -2) {
                MyGame.manager.get("dzwieki/kic_kic.mp3", Music.class).play();

                Timer timer=new Timer();
                timer.scheduleTask(new Timer.Task() {
                    @Override
                    public void run() {
                        MyGame.manager.get("dzwieki/kic_kic.mp3", Music.class).stop();
                    }
                },1);
                b2body.applyLinearImpulse(new Vector2(-40, 0), b2body.getWorldCenter(), true);
            }

            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                player.jump();
            }

            if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)){

              shift=true;
              timer2+=dt;

              if(timer2>0.5f&&timer2<1&&Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)){
                  player.lot();
                  timer2=0;
              }



            }

            if(controller.iswLewo()){
                MyGame.manager.get("dzwieki/kic_kic.mp3", Music.class).play();

                Timer timer=new Timer();
                timer.scheduleTask(new Timer.Task() {
                    @Override
                    public void run() {
                        MyGame.manager.get("dzwieki/kic_kic.mp3", Music.class).stop();
                    }
                },1);
                b2body.applyLinearImpulse(new Vector2(-40, 0), b2body.getWorldCenter(), true);
            }

            if(controller.iswPrawo()){
                MyGame.manager.get("dzwieki/kic_kic.mp3", Music.class).play();
                b2body.applyLinearImpulse(new Vector2(40f, 0), b2body.getWorldCenter(), true);

                Timer timer=new Timer();
                timer.scheduleTask(new Timer.Task() {
                    @Override
                    public void run() {
                        MyGame.manager.get("dzwieki/kic_kic.mp3", Music.class).stop();
                    }
                },1);
            }

            if(controller.iswGore()){
                player.jump();
            }

        }





    }



    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
        controller.resize();
    }

    @Override
    public void pause() {
        game.setPaused(true);
    }

    @Override
    public void resume() {
        game.setPaused(false);
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        b2dr.dispose();
    }
}
