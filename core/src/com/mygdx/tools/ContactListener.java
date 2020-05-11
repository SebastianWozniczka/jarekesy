package com.mygdx.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.sprites.Player;
import com.mygdx.sprites.Przedmioty;

public class ContactListener implements com.badlogic.gdx.physics.box2d.ContactListener {

    public static int ziemia=0;

    @Override
    public void beginContact(Contact contact) {
        Gdx.app.log("begin","");


        Fixture fixA=contact.getFixtureA();
        Fixture fixB=contact.getFixtureB();


        if(Player.currentState== Player.State.LOT) {
            if (fixA.getUserData() == "ziemia" && fixB.getUserData() == "player") {
                Gdx.app.log("ziemia", "");
                ziemia++;
            }
        }
            if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
               Player.koniec();
            }



    }

    @Override
    public void endContact(Contact contact) {
        Gdx.app.log("end","");
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
