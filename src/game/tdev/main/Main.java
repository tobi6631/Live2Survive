/*
 * (C) Tobias Development 2014
 */
package game.tdev.Main;

import engine.silnik.ContentLoader;
import engine.silnik.Mixer;
import engine.silnik.Option;
import engine.silnik.Sound;
import engine.silnik.Vector2i;
import game.tdev.main.Game;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Main extends BasicGame {

    public Image introPresent;
    public Image introGameName;
    public Image introSilnikEngine;
    public Image menuBackground;

    public SpriteSheet menuPlayButton;
    public SpriteSheet menuOptionsButton;
    public SpriteSheet menuExitButton;
    public SpriteSheet spriteImage;

    public int spriteX;
    public int spriteY;
    public int introCountDown;
    public int introStat = 0;

    public Mixer mixer;
    public Game game;

    public boolean menuButtonHoverStat[] = new boolean[4];

    /////////////////////////////////////////////// !!!!WARNING!!! 
    public int spriteDat = 260;//////////////////// !!!!DO NOT!!!!
    public Image sprite[] = new Image[spriteDat];// !!!!DELETE!!!!
    /////////////////////////////////////////////// !!TUB ER SEJ!!

    public Main(String title) {
        super(Option.getGameName());
    }

    private boolean mouseOver(Vector2i pos, Image button, Input gcInput) {
        return gcInput.getMouseX() > pos.getX()
                && gcInput.getMouseX() < pos.getX() + button.getWidth()
                && gcInput.getMouseY() > pos.getY()
                && gcInput.getMouseY() < pos.getY() + button.getHeight();
    }

    @Override
    public void init(GameContainer gc) throws SlickException {

        mixer = new Mixer();

        //intro - load EVERYTHING while displaying intro
        if (Option.getScreen() == 0) {
            mixer.loopSound(Sound.INTRO, 10);
            introPresent = new Image(ContentLoader.texturePath + "2.png");
            introGameName = new Image(ContentLoader.texturePath + "3.png");
            introSilnikEngine = new Image(ContentLoader.texturePath + "8.png");
            introCountDown = 380 * 3;
            menuBackground = new Image(ContentLoader.texturePath + "4.png");
            menuPlayButton = new SpriteSheet(ContentLoader.texturePath + "5.png", 156, 36);
            menuOptionsButton = new SpriteSheet(ContentLoader.texturePath + "6.png", 276, 36);
            menuExitButton = new SpriteSheet(ContentLoader.texturePath + "7.png", 156, 36);
            game = new Game();
            game.init();
            spriteImage = new SpriteSheet(ContentLoader.texturePath + "sprite.png", 64, 64);
            loadSpriteAsset(spriteImage);
        }
    }

    private void loadSpriteAsset(SpriteSheet sprite) {
        for (int x = 0; x < spriteDat; x++) {

            if (x == sprite.getWidth() / 64) {
                spriteY += 1;
                x = 0;
                if (spriteY == sprite.getHeight() / 64) {
                    break;
                }
            }

            this.sprite[x] = sprite.getSubImage(x, spriteY);
            spriteX++;
            System.out.println("LOAD: " + this.sprite[x] + " - AT [X,Y]: " + x + ", " + spriteY);
        }
    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException {

        //intro
        if (Option.getScreen() == 0) {
            if (introStat == 0) {
                introCountDown -= 1;
                if (introCountDown == 0) {
                    introCountDown = 380 * 3;
                    introStat = 1;
                }
            } else if (introStat == 1) {
                introCountDown -= 1;
                if (introCountDown == 0) {
                    introCountDown = 380 * 3;
                    introStat = 2;
                }
            } else if (introStat == 2) {
                introCountDown -= 1;
                if (introCountDown == 0) {
                    introCountDown = 0;
                    introStat = 0;
                    introPresent.destroy();
                    introGameName.destroy();
                    Option.setScreen(1);
                }
            }
        }

        //menu
        if (Option.getScreen() == 1) {

            if (mouseOver(new Vector2i(Option.getWidth() / 2 - menuPlayButton.getWidth() / 2,
                    Option.getHeight() / 2 + 90),
                    menuPlayButton.getSubImage(0, 0), gc.getInput())) {

                menuButtonHoverStat[0] = true;

                if (gc.getInput().isMouseButtonDown(0)) {
                    Option.setScreen(2);
                }
            } else {
                menuButtonHoverStat[0] = false;
            }

            if (mouseOver(new Vector2i(Option.getWidth() / 2 - menuOptionsButton.getWidth() / 2,
                    Option.getHeight() / 2 + 80 * 2),
                    menuOptionsButton.getSubImage(0, 0), gc.getInput())) {

                menuButtonHoverStat[1] = true;

                if (gc.getInput().isMouseButtonDown(0)) {
                    System.err.println("NO OPTION SCREEN!");
                }
            } else {
                menuButtonHoverStat[1] = false;
            }

            if (mouseOver(new Vector2i(Option.getWidth() / 2 - menuExitButton.getWidth() / 2,
                    Option.getHeight() / 2 + 78 * 3),
                    menuExitButton.getSubImage(0, 0), gc.getInput())) {

                menuButtonHoverStat[2] = true;

                if (gc.getInput().isMouseButtonDown(0)) {
                    gc.exit();
                }
            } else {
                menuButtonHoverStat[2] = false;
            }
        }

        //Game
        if (Option.getScreen() == 2) {
            game.update(gc, delta);
        }
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {

        //intro
        if (Option.getScreen() == 0) {
            if (introStat == 0) {
                introPresent.draw(0, 0, Option.getWidth(), Option.getHeight());
            } else if (introStat == 1) {
                introGameName.draw(0, 0, Option.getWidth(), Option.getHeight());
            } else if (introStat == 2) {
                introSilnikEngine.draw(0, 0, Option.getWidth(), Option.getHeight());
            }

            if (Option.getDebugMode()) {
                g.setColor(Color.green);
                g.drawString("\nLOADING - Content: DONE!"
                        + "\nLOADING - GameData & Version 'inDev0.1': DONE!", 10, 10);
                g.setColor(Color.red);
                g.drawString("\n\n\nLOADING - tdev.com/mysql/V.?inDev0.1/: FAILURE!", 10, 10);
            } else {
                g.setColor(Color.red);
                g.drawString("LOADING - L2S inDev 0.1", 10, 10);
            }
        }

        //menu
        if (Option.getScreen() == 1) {
            menuBackground.draw(0, 0);

            if (!menuButtonHoverStat[0]) {
                menuPlayButton.getSubImage(0, 0).draw(
                        Option.getWidth() / 2 - menuPlayButton.getWidth() / 2,
                        Option.getHeight() / 2 + 90);
            } else {
                menuPlayButton.getSubImage(0, 1).draw(
                        Option.getWidth() / 2 - menuPlayButton.getWidth() / 2,
                        Option.getHeight() / 2 + 90);
            }

            if (!menuButtonHoverStat[1]) {
                menuOptionsButton.getSubImage(0, 0).draw(
                        Option.getWidth() / 2 - menuOptionsButton.getWidth() / 2,
                        Option.getHeight() / 2 + 80 * 2);
            } else {
                menuOptionsButton.getSubImage(0, 1).draw(
                        Option.getWidth() / 2 - menuOptionsButton.getWidth() / 2,
                        Option.getHeight() / 2 + 80 * 2);
            }

            if (!menuButtonHoverStat[2]) {
                menuExitButton.getSubImage(0, 0).draw(
                        Option.getWidth() / 2 - menuExitButton.getWidth() / 2,
                        Option.getHeight() / 2 + 78 * 3);
            } else {
                menuExitButton.getSubImage(0, 1).draw(
                        Option.getWidth() / 2 - menuExitButton.getWidth() / 2,
                        Option.getHeight() / 2 + 78 * 3);
            }
        }

        //Game
        if (Option.getScreen() == 2) {
            game.render(gc, g);
        }

        if (Option.getDebugMode()) {
            g.setColor(Color.white);
            g.drawString("FPS: " + gc.getFPS(), 10, 10);
        }
    }

    public static void main(String[] args) {
        try {
            AppGameContainer game = new AppGameContainer(new Main(Option.getGameName()));

            game.setDisplayMode(Option.getWidth(), Option.getHeight(), Option.getFullscreen());
            game.setUpdateOnlyWhenVisible(true);
            game.setFullscreen(Option.getFullscreen());
            game.setAlwaysRender(Option.getAllwaysRender());
            game.setClearEachFrame(Option.getClearEatchFrame());
            game.setForceExit(Option.getForceExit());
            game.setShowFPS(Option.getShowFps());
            game.setResizable(Option.getResizabel());
            game.setTargetFrameRate(Option.getFpsLimit());
            game.setMouseGrabbed(false);
            game.setIcon(ContentLoader.texturePath + "1.png");

            game.start();
        } catch (SlickException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
