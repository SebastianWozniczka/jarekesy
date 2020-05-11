package com.mygdx.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.MyGame;
import com.mygdx.scenes.Hud;
import com.mygdx.screens.GamePlay;
import com.mygdx.tools.ContactListener;

import java.net.HttpURLConnection;

import static com.mygdx.scenes.Hud.table;
import static com.mygdx.screens.GamePlay.shift;

public class Player extends  Przedmioty {

    private float timer;
    private float fallingTimer;
    short zmienna;
    private boolean koniec;
    public static TextureRegion region;
    static FixtureDef fdef;
    private short filter;
    public static boolean a;
    public void lot() {

        MyGame.manager.get("dzwieki/hop.mp3", Music.class).play();


            if (runningRight)
                b2body.applyLinearImpulse(new Vector2(-10, 1000), b2body.getWorldCenter(), true);

            if (!runningRight)
                b2body.applyLinearImpulse(new Vector2(10, 1000), b2body.getWorldCenter(), true);
            lot = true;






    }


    public static void koniec() {
        setCategoryFilter(MyGame.DESTROYED_BIT);
    }


    public enum State {FALLING, JUMPING, STANDING, RUNNING, DEAD, KRZYK, SEN, ATAK, ATAK2,BUL,LOT}
    public static State currentState;
    public static State previousState;
    public static float stateTimer;
    public static boolean runningRight;

    private TextureAtlas jarekes,jarekesRun,jarekesJump,jarekesLot,jarekesUpadek,jarekesSurrender,jarekesUpadek2;
    public static World world;
    private GamePlay screen;
    public static Body b2body;
    float positionX;
    float positionY;
    private TextureRegion playerStand,playerUpadek,playerSurrender,playerUpadek2;
    public static Animation playerRun,playerJump,playerLot;
    public static boolean lot;
    public static float timerLot;

    public Player(float positionX, float positionY, World world, GamePlay screen) {
        this.world = world;
        this.screen = screen;
        this.positionX = positionX;
        this.positionY = positionY;
        fdef = new FixtureDef();

        currentState = State.STANDING;
        previousState = State.STANDING;


        stateTimer = 0;
        fallingTimer=0;
        timerLot=0;
        runningRight = true;
        lot=false;
        koniec=false;
        filter=8;
        a=false;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        jarekes = new TextureAtlas("postacie/jarekes.pack");
        jarekesRun=new TextureAtlas("postacie/jarekes_run.pack");
        jarekesJump=new TextureAtlas("postacie/jarekes_jump.pack");
        jarekesLot=new TextureAtlas("postacie/jarekes_lot.pack");
        jarekesUpadek=new TextureAtlas("postacie/jarekes_upadek.pack");
        jarekesSurrender=new TextureAtlas("postacie/jarekes_surrender.pack");
        jarekesUpadek2=new TextureAtlas("postacie/jarekes_upadek2.pack");


        playerStand = new TextureRegion(jarekes.findRegion("jarekes"), 0, 0, 128, 128);
        playerUpadek = new TextureRegion(jarekesUpadek.findRegion("jarekes_upadek"), 0, 0, 128, 128);
        playerUpadek2 = new TextureRegion(jarekesUpadek2.findRegion("jarekes_upadek2"), 0, 0, 128, 128);
        playerSurrender = new TextureRegion(jarekesSurrender.findRegion("jarekes_surrender"), 0, 0, 128, 128);
       for (int i = 1; i < 3; i++)
            frames.add(new TextureRegion(jarekesRun.findRegion("jarekes_run"), i * 128, 0, 128, 128));
        playerRun = new Animation(0.3f, frames);

        frames.clear();

        for (int i = 1; i < 2; i++)
            frames.add(new TextureRegion(jarekesJump.findRegion("jarekes_jump"), i * 128, 0, 128, 128));
        playerJump = new Animation(0.3f, frames);

        frames.clear();

        for (int i = 1; i < 5; i++)
            frames.add(new TextureRegion(jarekesLot.findRegion("jarekes_lot"), i * 128, 0, 128, 128));
        playerLot = new Animation(0.1f, frames);

        frames.clear();


if(ContactListener.ziemia!=0)
            setRegion(playerUpadek2);
        setBounds(0, 0, 128, 128);

        definePlayer();
      setColor(Color.GREEN);


    }

    private void definePlayer() {
        BodyDef bdef;

        bdef = new BodyDef();
        bdef.position.set(positionX, positionY);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        CircleShape shape = new CircleShape();
        shape.setRadius(40);
        fdef.shape = shape;
        fixture=b2body.createFixture(fdef);
        fixture.setUserData("player");
        setCategoryFilter(MyGame.PLAYER_BIT);

        fdef.filter.maskBits= (short) (MyGame.ENEMY_BIT|filter);

    }

    public void update(float dt) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
    }

    public static void setCategoryFilter(short filterBit){
        Filter filter=new Filter();
        filter.categoryBits=filterBit;

        fixture.setFilterData(filter);
    }





    public void jump() {
        if (currentState != State.JUMPING) {


            if(runningRight)
            b2body.applyLinearImpulse(new Vector2(30,300),b2body.getWorldCenter(), true);
            if(!runningRight)
                b2body.applyLinearImpulse(new Vector2(-30,300), b2body.getWorldCenter(), true);
            currentState = State.JUMPING;


            MyGame.manager.get("dzwieki/hop.mp3", Music.class).play();
            Timer timer=new Timer();
            timer.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    MyGame.manager.get("dzwieki/hop.mp3", Music.class).stop();
                }
            },1.3f);
        }

    }

    public TextureRegion getFrame(Float dt) {
        currentState = getState(dt);

         region = playerStand;


        switch (currentState) {
            case JUMPING:
                region = (TextureRegion) playerJump.getKeyFrame(stateTimer, false);
                break;
            case RUNNING:
                region = (TextureRegion) playerRun.getKeyFrame(stateTimer, true);
                break;

            case LOT:
                region = (TextureRegion) playerLot.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
                region = playerUpadek;
                break;

            case DEAD:
                    region=playerSurrender;
                break;

            case STANDING:
            default:
                region = playerStand;
                if(ContactListener.ziemia!=0) {
                    region = playerUpadek2;
                    shift = false;
                }
                break;
        }


        if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight = false;
        }

        else if((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }


        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }






    public State getState(float dt) {


       if ((currentState!=State.RUNNING&&b2body.getLinearVelocity().y > 0 && currentState == State.JUMPING) || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING)&&b2body.getLinearVelocity().x != 0) {
            return State.JUMPING;


        } else if (b2body.getLinearVelocity().y < 0) {
           fallingTimer += dt;
           if (fallingTimer > 3) {
               shift=true;
               return State.LOT;

           }
           return State.FALLING;
       }

        else if (b2body.getLinearVelocity().x != 0)
            return State.RUNNING;

       else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            {
                if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
                    Gdx.app.log("huj", "");
                   setAlpha(0.5f);
                    int zycie1=Hud.zycieGracza1;
                    zycie1--;
                    shift=false;


                    Hud.nazwaGracza1.setText((int) zycie1);
                    Timer timer=new Timer();
                    timer.scheduleTask(new Timer.Task() {
                        @Override
                        public void run() {
                            Hud.nazwaGracza1.setVisible(false);


                            shift=false;
                        }
                    },0.2f);
                   Timer timer2=new Timer();
                   timer2.scheduleTask(new Timer.Task() {
                       @Override
                       public void run() {
                         world.destroyBody(b2body);
                           Hud.nazwaGracza1.remove();
                           setAlpha(0);
                       }
                   },5);






                }
            }
           return State.DEAD;
        }

   if (lot&&currentState != State.RUNNING &&currentState != State.STANDING&&currentState != State.FALLING) {

           return State.LOT;

        }

        else
            return State.STANDING;


    }


}
