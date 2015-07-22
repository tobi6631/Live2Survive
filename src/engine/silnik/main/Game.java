/*
 * (C) Tobias Development 2015                               SEGOI
 *  * 2015 faktisk... LOL tub er sej......... keder mig xD 
 *                                                         ^^    SEGOI
 *                                           SEGOI         -- KAWAIIIII    
 */
package engine.silnik.main;

import static org.lwjgl.opengl.GL11.*;

import engine.silnik.ContentLoader;
import engine.silnik.Option;
import engine.silnik.Sound;
import engine.silnik.Vector2i;
import engine.silnik.base.Base;
import engine.silnik.gui.ShopGUI;
import engine.silnik.gui.ShopItemGUI;
import engine.silnik.item.ItemSlot;
import engine.silnik.tiles.StaticTile;
import engine.silnik.tiles.TreeTile;
import engine.silnik.tiles.util.TileType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.opengl.SlickCallable;

/**
 *
 * @author TKB
 */
public class Game {

    //Game Settings...
    public Image worldPixelData[] = new Image[2];
    public boolean renderWorldOne = true;
    public int worldGenY = 0;
    public ArrayList<StaticTile> staticTiles = new ArrayList<>();
    public ArrayList<TreeTile> treeTiles = new ArrayList<>();
    public SpriteSheet worldSprite;
    public static Shape worldPerformShape;
    public static float alpha;
    public Image lightCycle;
    public static int lightState = 0;//0 day, 1 night

    //Player
    public float playerX;
    public float playerY;
    public int r_playerX;
    public int r_playerY;
    public Animation player[] = new Animation[5];
    public Animation cur_Player;
    public Image playerIdle;
    public static int movement;
    public static boolean inFront = true;
    public static Shape playerShape;
    public static boolean moveable[] = new boolean[4];
    public float healthLevel = 96;//
    public float waterLevel = 96;//  MAX 96, MIN 7
    public float foodLevel = 96;//
    public static int coins = 0x5445FF;

    //InGameGUI
    public Image playerStatus;
    public Image healthlvl;
    public Image waterlvl;
    public Image foodlvl;
    public int maxBarLvl = 96;
    public int minBarLvl = 7;
    public Image sideBar;
    public static int itemSlot_bar = 7;
    public static ItemSlot itemSlotBar[] = new ItemSlot[itemSlot_bar];
    public static int tree_activeSlot;
    public static boolean tree_haveActiveSlot = false;
    public int currentSlot = 1;
    public Image pointer;

    //Inventory
    public Image inventory;
    public Image invButton_normal;
    public Image invButton_pressed;
    public boolean openinv = false;
    public boolean mouseOver_invButton = false;
    public int inventorySlot_count = 45;
    public int row = 1;
    public ItemSlot inventorySlot[] = new ItemSlot[inventorySlot_count];

    //Base
    public Image baseImg;
    public int MAX_BASE_SPAWN_ALLOWD = 10;//?
    public ArrayList<Base> base = new ArrayList<>();

    //BaseUpgradeGUI
    public Image testItemLogo;
    public Image baseGUIItemFrame;
    public Image baseGUIItemButton_n;
    public Image baseGUIItemButton_h;
    public Image baseGUIFrame;
    public ShopGUI baseUpgradeGUI;
    public static boolean isBaseUpgradeShopOpen = false;

    //Rain & Thunder Effect
    private RainDrop[] drops;
    private int dropCount = 7000;
    public static boolean isRaining = true;
    public static boolean isThunder = true;
    private boolean rainLoop = true;
    public static int stormSize = 700;// Smaller = bigger storm... Bigger = .... Smaller storm
    private LightingBoltEffect lightingBoltEffect;
    private int lightingCount = 100;

    public void init(Image[] world, SpriteSheet worldSprite) {
        worldPixelData = world;
        this.worldSprite = worldSprite;
        worldPerformShape = new Rectangle(1, 1, Option.getWidth() - 1, Option.getHeight() - 2);

        try {
            player[0] = new Animation(new SpriteSheet(new Image(ContentLoader.texturePath + "player\\playerLeft.png"), 32, 48), 100);
            player[1] = new Animation(new SpriteSheet(new Image(ContentLoader.texturePath + "player\\playerRight.png"), 32, 48), 100);
            player[2] = new Animation(new SpriteSheet(new Image(ContentLoader.texturePath + "player\\playerFront.png"), 32, 50), 100);
            player[3] = new Animation(new SpriteSheet(new Image(ContentLoader.texturePath + "player\\playerBack.png"), 32, 50), 100);
            player[4] = new Animation(new SpriteSheet(new Image(ContentLoader.texturePath + "player\\playerIdle.png"), 32, 50), 100);

            moveable[0] = true;
            moveable[1] = true;
            moveable[2] = true;
            moveable[3] = true;

            lightCycle = new Image(ContentLoader.texturePath + "filter\\light.png");

            playerStatus = new Image(ContentLoader.texturePath + "gui\\playerStatus.png");
            healthlvl = new Image(ContentLoader.texturePath + "gui\\healthlvl.png");
            waterlvl = new Image(ContentLoader.texturePath + "gui\\waterlvl.png");
            foodlvl = new Image(ContentLoader.texturePath + "gui\\foodlvl.png");
            sideBar = new Image(ContentLoader.texturePath + "gui\\sideBar.png");

            pointer = new Image(ContentLoader.texturePath + "gui\\pointer.png");

            for (int i = 0; i < itemSlot_bar; i++) {
                itemSlotBar[i] = new ItemSlot(Option.getWidth() - 44, i * 44 + Option.getHeight() / 2 - sideBar.getHeight() / 2 + 17);
            }

            inventory = new Image(ContentLoader.texturePath + "gui\\inventory.png");
            invButton_normal = new Image(ContentLoader.texturePath + "gui\\invbutton_nm.png");
            invButton_pressed = new Image(ContentLoader.texturePath + "gui\\invbutton_ps.png");

            baseImg = new Image(ContentLoader.texturePath + "houses\\home_s1.png");
            baseGUIItemButton_h = new Image(ContentLoader.texturePath + "gui\\Store\\buy_h.png");
            baseGUIItemButton_n = new Image(ContentLoader.texturePath + "gui\\Store\\buy_n.png");
            baseGUIItemFrame = new Image(ContentLoader.texturePath + "gui\\Store\\storeItem.png");
            testItemLogo = new Image(ContentLoader.texturePath + "1.png");
            baseGUIFrame = new Image(ContentLoader.texturePath + "gui\\Store\\window.png");

            baseUpgradeGUI = new ShopGUI(Option.getWidth() / 2 - baseGUIFrame.getWidth() / 2,
                    Option.getHeight() / 2 - baseGUIFrame.getHeight() / 2, baseGUIFrame);

            //adding stuff....
            addInvSlots();
            addShopItems();

            //Rain
            initializeRain();
            generateLightingBolt(new Vector2f(50, 240), new Vector2f(290, 240), 100);

        } catch (Exception ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }

        playerX = Option.getWidth() / 2 - 32;
        playerY = Option.getHeight() / 2 - 32;

        playerShape = new Rectangle(playerX + 16, playerY, 32, 50);
    }

    public void addInvSlots() {
        inventorySlot[0] = new ItemSlot(0 * 36 + 260 - sideBar.getWidth() / 2, 190);
        inventorySlot[1] = new ItemSlot(1 * 36 + 260 - sideBar.getWidth() / 2, 190);
        inventorySlot[2] = new ItemSlot(2 * 36 + 260 - sideBar.getWidth() / 2, 190);
        inventorySlot[3] = new ItemSlot(3 * 36 + 260 - sideBar.getWidth() / 2, 190);
        inventorySlot[4] = new ItemSlot(4 * 36 + 260 - sideBar.getWidth() / 2, 190);
        inventorySlot[5] = new ItemSlot(5 * 36 + 260 - sideBar.getWidth() / 2, 190);
        inventorySlot[6] = new ItemSlot(6 * 36 + 260 - sideBar.getWidth() / 2, 190);
        inventorySlot[7] = new ItemSlot(7 * 36 + 260 - sideBar.getWidth() / 2, 190);
        inventorySlot[8] = new ItemSlot(8 * 36 + 260 - sideBar.getWidth() / 2, 190);

        inventorySlot[9] = new ItemSlot(0 * 36 + 260 - sideBar.getWidth() / 2, 226);
        inventorySlot[10] = new ItemSlot(1 * 36 + 260 - sideBar.getWidth() / 2, 226);
        inventorySlot[11] = new ItemSlot(2 * 36 + 260 - sideBar.getWidth() / 2, 226);
        inventorySlot[12] = new ItemSlot(3 * 36 + 260 - sideBar.getWidth() / 2, 226);
        inventorySlot[13] = new ItemSlot(4 * 36 + 260 - sideBar.getWidth() / 2, 226);
        inventorySlot[14] = new ItemSlot(5 * 36 + 260 - sideBar.getWidth() / 2, 226);
        inventorySlot[15] = new ItemSlot(6 * 36 + 260 - sideBar.getWidth() / 2, 226);
        inventorySlot[16] = new ItemSlot(7 * 36 + 260 - sideBar.getWidth() / 2, 226);
        inventorySlot[17] = new ItemSlot(8 * 36 + 260 - sideBar.getWidth() / 2, 226);

        inventorySlot[18] = new ItemSlot(0 * 36 + 260 - sideBar.getWidth() / 2, 262);
        inventorySlot[19] = new ItemSlot(1 * 36 + 260 - sideBar.getWidth() / 2, 262);
        inventorySlot[20] = new ItemSlot(2 * 36 + 260 - sideBar.getWidth() / 2, 262);
        inventorySlot[21] = new ItemSlot(3 * 36 + 260 - sideBar.getWidth() / 2, 262);
        inventorySlot[22] = new ItemSlot(4 * 36 + 260 - sideBar.getWidth() / 2, 262);
        inventorySlot[23] = new ItemSlot(5 * 36 + 260 - sideBar.getWidth() / 2, 262);
        inventorySlot[24] = new ItemSlot(6 * 36 + 260 - sideBar.getWidth() / 2, 262);
        inventorySlot[25] = new ItemSlot(7 * 36 + 260 - sideBar.getWidth() / 2, 262);
        inventorySlot[26] = new ItemSlot(8 * 36 + 260 - sideBar.getWidth() / 2, 262);

        inventorySlot[27] = new ItemSlot(0 * 36 + 260 - sideBar.getWidth() / 2, 298);
        inventorySlot[28] = new ItemSlot(1 * 36 + 260 - sideBar.getWidth() / 2, 298);
        inventorySlot[29] = new ItemSlot(2 * 36 + 260 - sideBar.getWidth() / 2, 298);
        inventorySlot[30] = new ItemSlot(3 * 36 + 260 - sideBar.getWidth() / 2, 298);
        inventorySlot[31] = new ItemSlot(4 * 36 + 260 - sideBar.getWidth() / 2, 298);
        inventorySlot[32] = new ItemSlot(5 * 36 + 260 - sideBar.getWidth() / 2, 298);
        inventorySlot[33] = new ItemSlot(6 * 36 + 260 - sideBar.getWidth() / 2, 298);
        inventorySlot[34] = new ItemSlot(7 * 36 + 260 - sideBar.getWidth() / 2, 298);
        inventorySlot[35] = new ItemSlot(8 * 36 + 260 - sideBar.getWidth() / 2, 298);

        inventorySlot[36] = new ItemSlot(0 * 36 + 260 - sideBar.getWidth() / 2, 334);
        inventorySlot[37] = new ItemSlot(1 * 36 + 260 - sideBar.getWidth() / 2, 334);
        inventorySlot[38] = new ItemSlot(2 * 36 + 260 - sideBar.getWidth() / 2, 334);
        inventorySlot[39] = new ItemSlot(3 * 36 + 260 - sideBar.getWidth() / 2, 334);
        inventorySlot[40] = new ItemSlot(4 * 36 + 260 - sideBar.getWidth() / 2, 334);
        inventorySlot[41] = new ItemSlot(5 * 36 + 260 - sideBar.getWidth() / 2, 334);
        inventorySlot[42] = new ItemSlot(6 * 36 + 260 - sideBar.getWidth() / 2, 334);
        inventorySlot[43] = new ItemSlot(7 * 36 + 260 - sideBar.getWidth() / 2, 334);
        inventorySlot[44] = new ItemSlot(8 * 36 + 260 - sideBar.getWidth() / 2, 334);
    }

    public void addShopItems() {
        //Base Upgrade
        for (int i = 0; i < 10; i++) {
            baseUpgradeGUI.addMenuItem(new ShopItemGUI(0, 0, testItemLogo, baseGUIItemFrame,
                    baseGUIItemButton_n, baseGUIItemButton_h,
                    "LOL LEVEL" + i, "(LOL) + " + i, i + 1258, "$"));
        }
    }

    public void updatePlayer(GameContainer gc, int delta) throws SlickException {

        //Player
        if (gc.getInput().isKeyDown(Input.KEY_W) && moveable[0]) {
            playerY += 0.12F * delta;
            movement = 1;
            moveable[1] = true;
            moveable[2] = true;
            moveable[3] = true;
        } else if (gc.getInput().isKeyDown(Input.KEY_A) && moveable[1]) {
            playerX += 0.12F * delta;
            movement = 2;
            moveable[0] = true;
            moveable[2] = true;
            moveable[3] = true;
        } else if (gc.getInput().isKeyDown(Input.KEY_S) && moveable[2]) {
            playerY -= 0.12F * delta;
            movement = 3;
            moveable[0] = true;
            moveable[1] = true;
            moveable[3] = true;
        } else if (gc.getInput().isKeyDown(Input.KEY_D) && moveable[3]) {
            playerX -= 0.12F * delta;
            movement = 4;
            moveable[0] = true;
            moveable[2] = true;
            moveable[1] = true;
        } else {
            movement = 0;
        }

        if (gc.getInput().isKeyPressed(Input.KEY_DOWN)) {
            if (currentSlot < 8) {
                currentSlot += 1;
            }
        }

        if (gc.getInput().isKeyPressed(Input.KEY_UP)) {
            if (currentSlot <= 8 && currentSlot > 1) {
                currentSlot -= 1;
            }
        }

        //GUI
        updateInventorry(gc);
        updateBaseUpgradeGUI(gc);

        //Update all dynamic states
        if (!gc.isPaused()) {
            //food and water will decrese over time...
            foodLevel -= 0.002F;
            waterLevel -= 0.005F;

            //round up x and y float - for zero block lag
            r_playerX = Math.round(playerX);
            r_playerY = Math.round(playerY);

            //Day and night cycle
            if (lightState == 0) {
                alpha += 0.00005F;

                if (alpha >= 1) {
                    lightState = 1;
                }
            }

            if (lightState == 1) {
                alpha -= 0.00005F;

                if (alpha <= 0) {
                    lightState = 0;
                }
            }
        }

        //Update Rain
        if (isRaining) {
            updateRain();
            if (isThunder) {
                updateThunder(gc, delta);
            }
            alpha = 0.65F;
        }
    }

    public void render(GameContainer gc, Graphics g) throws SlickException {

        //render worlds
        if (renderWorldOne) {
            runWorldGenerator(worldPixelData[0]);
            renderWorldOne = false;
        }

        staticTiles.stream().forEach((staticTile) -> {
            staticTile.render(r_playerX, r_playerY, g);
        });

        if (!inFront) {
            //Player
            if (movement == 0) {
                player[4].draw(Option.getWidth() / 2 - 16, Option.getHeight() / 2 - 32);
            } else if (movement == 1) {
                player[3].draw(Option.getWidth() / 2 - 16, Option.getHeight() / 2 - 32);
            } else if (movement == 2) {
                player[0].draw(Option.getWidth() / 2 - 16, Option.getHeight() / 2 - 32);
            } else if (movement == 3) {
                player[2].draw(Option.getWidth() / 2 - 16, Option.getHeight() / 2 - 32);
            } else if (movement == 4) {
                player[1].draw(Option.getWidth() / 2 - 16, Option.getHeight() / 2 - 32);
            }
        }

        treeTiles.stream().forEach((treeTile) -> {
            treeTile.render(r_playerX, r_playerY, g);
        });

        base.stream().forEach((base1) -> {
            base1.render(r_playerX, r_playerY, g);
        });

        if (inFront) {
            //Player
            if (movement == 0) {
                player[4].draw(Option.getWidth() / 2 - 16, Option.getHeight() / 2 - 32);
            } else if (movement == 1) {
                player[3].draw(Option.getWidth() / 2 - 16, Option.getHeight() / 2 - 32);
            } else if (movement == 2) {
                player[0].draw(Option.getWidth() / 2 - 16, Option.getHeight() / 2 - 32);
            } else if (movement == 3) {
                player[2].draw(Option.getWidth() / 2 - 16, Option.getHeight() / 2 - 32);
            } else if (movement == 4) {
                player[1].draw(Option.getWidth() / 2 - 16, Option.getHeight() / 2 - 32);
            }
        }

        lightCycle.setAlpha(alpha);
        lightCycle.draw(0, 0, Option.getWidth(), Option.getHeight());

        //Render rain infront of world but not gui
        if (isRaining) {
            renderRain(gc, g);
            if (isThunder) {
                renderThunder(gc, g);
            }

            if (rainLoop) {
                Main.mixer.loopSound(Sound.RAIN, 0.3F);
                rainLoop = false;
            }
        } else {
            Main.mixer.stopSound(Sound.RAIN);
            rainLoop = true;
        }

        //inGameGUI
        playerStatus.draw();//Default 0, 0
        renderBaseUpgradeGUI(g);

        if (healthLevel >= minBarLvl && healthLevel <= maxBarLvl) {
            healthlvl.draw(86, 8, Math.round(healthLevel), 14);
        }

        if (waterLevel >= minBarLvl && waterLevel <= maxBarLvl) {
            waterlvl.draw(86, 28, Math.round(waterLevel), 14);
        }

        if (foodLevel >= minBarLvl && foodLevel <= maxBarLvl) {
            foodlvl.draw(86, 48, Math.round(foodLevel), 14);
        }

        sideBar.draw(Option.getWidth() - 48, Option.getHeight() / 2 - sideBar.getHeight() / 2);

        for (int i = 0; i < itemSlot_bar; i++) {
            itemSlotBar[i].render(g, gc);
        }

        inFront = true;

        pointer.draw(Option.getWidth() - 78, currentSlot * 44 + 86);

        renderInventory(g, gc);

        if (Option.getDebugMode()) {
            g.setColor(Color.red);
            g.draw(worldPerformShape);
            g.drawString("M" + movement, Option.getWidth() / 2 - 32, Option.getHeight() / 2 - 32);
            g.draw(playerShape);
            g.setColor(Color.orange);
            g.drawString("Current Time: " + alpha + ", TM: " + lightState
                    + ", RT: " + gc.getTime(), 10, 80);
            g.drawString("\nRain: " + isRaining + ", Thunder: " + isThunder + 
                    "\nStorm Size: " + stormSize + ", Thunder Countdown: " + lightingCount, 10, 80);
            g.drawString("\n\n\nFPS: " + gc.getFPS(), 10, 80);
            g.drawString("\n\n\n\nPL M: " + movement, 10, 80);
            g.drawString("\n\n\n\n\nPL H: " + Math.round(healthLevel)
                    + ", W: " + Math.round(waterLevel)
                    + ", F: " + Math.round(foodLevel), 10, 80);
            g.drawString("\n\n\n\n\n\nMX: " + gc.getInput().getMouseX() + ", MY: "
                    + gc.getInput().getMouseY(), 10, 80);

            worldPixelData[0].draw(Option.getWidth() - 96, 32, 64, 64);
        }
    }

    public void runWorldGenerator(Image world) {
        Image pixelData = world;
        int worldGenWidth = world.getWidth();
        int worldGenHeight = world.getHeight();
        int tiles = 0;//for debuging

        for (int worldGenx = 0; worldGenx < worldGenWidth; worldGenx++) {

            if (worldGenx == worldGenWidth - 1) {
                worldGenY += 1;
                worldGenx = 0;
                if (worldGenY == worldGenHeight) {
                    System.out.println("World generated successfully!, TILES: " + tiles);
                    renderWorldOne = false;
                    break;
                }
            }

            tiles += 1;

            if (pixelData.getColor(worldGenx, worldGenY).equals(new Color(0, 255, 0))) {
                addStaticTile(worldGenx, worldGenY, TileType.GRÆS000, worldSprite);
            }

            if (pixelData.getColor(worldGenx, worldGenY).equals(new Color(127, 51, 0))) {
                addStaticTile(worldGenx, worldGenY, TileType.JORD001, worldSprite);
            }

            if (pixelData.getColor(worldGenx, worldGenY).equals(new Color(64, 64, 64))) {
                addStaticTile(worldGenx, worldGenY, TileType.STEN002, worldSprite);
            }

            if (pixelData.getColor(worldGenx, worldGenY).equals(new Color(0, 127, 14))) {
                addStaticTile(worldGenx, worldGenY, TileType.GRÆS000, worldSprite);
                addTreeTile(worldGenx, worldGenY, TileType.TRÆ003, worldSprite);
            }

            if (pixelData.getColor(worldGenx, worldGenY).equals(new Color(0, 0, 255))) {
                addStaticTile(worldGenx, worldGenY, TileType.VAND005, worldSprite);
            }

            if (pixelData.getColor(worldGenx, worldGenY).equals(new Color(0, 0, 0))) {
                addBase(worldGenx, worldGenY, baseImg);
            }
        }
    }

    public void addStaticTile(float x, float y, TileType type, SpriteSheet spriteSheet) {
        staticTiles.add(new StaticTile(x, y, type, spriteSheet));
    }

    public void addTreeTile(int x, int y, TileType type, SpriteSheet spriteSheet) {
        treeTiles.add(new TreeTile(x, y, type, spriteSheet));
    }

    public void addBase(int x, int y, Image baseImg) {
        base.add(new Base(x, y, baseImg));
    }

    //Inventory
    //510 // 380
    private void renderInventory(Graphics g, GameContainer gc) {
        if (openinv) {
            inventory.draw(Option.getWidth() / 2 - inventory.getWidth() / 2,
                    Option.getHeight() / 2 - inventory.getHeight() / 2);

            renderIneventorySlot(g, gc);

            if (!mouseOver_invButton) {
                invButton_normal.draw(510, 375);
            } else {
                invButton_pressed.draw(510, 375);
                g.setColor(Color.orange);
                g.drawString("* Empty sidebar \n  into inventory",
                        gc.getInput().getMouseX() + 10, gc.getInput().getMouseY());
            }

            for (int i = 0; i < itemSlot_bar; i++) {
                itemSlotBar[i].drawAN(i * 36 + 260 - sideBar.getWidth() / 2, 379, g);
            }
        }
    }

    private void updateInventorry(GameContainer gc) {
        if (gc.getInput().isKeyPressed(Input.KEY_E) && !openinv && !isBaseUpgradeShopOpen) {
            gc.setPaused(true);
            openinv = true;
        }

        if (gc.getInput().isKeyPressed(Input.KEY_ESCAPE) && openinv) {
            gc.setPaused(false);
            openinv = false;
        }

        if (engine.silnik.main.Main.mouseOver(new Vector2i(510, 375), invButton_normal, gc.getInput())) {
            mouseOver_invButton = true;

            if (gc.getInput().isMousePressed(0)) {
                checkR1();
                checkR2();
                checkR3();
                checkR4();
                checkR5();
                checkR6();
                checkR7();
            }
        } else {
            mouseOver_invButton = false;
        }
    }

    public void renderIneventorySlot(Graphics g, GameContainer gc) {
        for (int i = 0; i < inventorySlot_count; i++) {
            inventorySlot[i].render(g, gc);
        }
    }

    public void checkR1() {
        for (int i = 0; i < inventorySlot_count; i++) {
            if (inventorySlot[i].isFree()) {
                inventorySlot[i].addItem(itemSlotBar[0].getItem(), itemSlotBar[0].getAmmount());
                itemSlotBar[0].reset();
            } else if (inventorySlot[i].contains(itemSlotBar[0].getItem())) {
                inventorySlot[i].addItem(itemSlotBar[0].getItem(), itemSlotBar[0].getAmmount());
                itemSlotBar[0].reset();
            }
            break;
        }
    }

    public void checkR2() {
        for (int i = 0; i < inventorySlot_count; i++) {
            if (inventorySlot[i].isFree()) {
                inventorySlot[i].addItem(itemSlotBar[1].getItem(), itemSlotBar[1].getAmmount());
                itemSlotBar[1].reset();
            } else if (inventorySlot[i].contains(itemSlotBar[1].getItem())) {
                inventorySlot[i].addItem(itemSlotBar[1].getItem(), itemSlotBar[1].getAmmount());
                itemSlotBar[1].reset();
            }
            break;
        }
    }

    public void checkR3() {
        for (int i = 0; i < inventorySlot_count; i++) {
            if (inventorySlot[i].isFree()) {
                inventorySlot[i].addItem(itemSlotBar[2].getItem(), itemSlotBar[2].getAmmount());
                itemSlotBar[2].reset();
            } else if (inventorySlot[i].contains(itemSlotBar[2].getItem())) {
                inventorySlot[i].addItem(itemSlotBar[2].getItem(), itemSlotBar[2].getAmmount());
                itemSlotBar[2].reset();
            }
            break;
        }
    }

    public void checkR4() {
        for (int i = 0; i < inventorySlot_count; i++) {
            if (inventorySlot[i].isFree()) {
                inventorySlot[i].addItem(itemSlotBar[3].getItem(), itemSlotBar[3].getAmmount());
                itemSlotBar[3].reset();
            } else if (inventorySlot[i].contains(itemSlotBar[3].getItem())) {
                inventorySlot[i].addItem(itemSlotBar[3].getItem(), itemSlotBar[3].getAmmount());
                itemSlotBar[3].reset();
            }
            break;
        }
    }

    public void checkR5() {
        for (int i = 0; i < inventorySlot_count; i++) {
            if (inventorySlot[i].isFree()) {
                inventorySlot[i].addItem(itemSlotBar[4].getItem(), itemSlotBar[4].getAmmount());
                itemSlotBar[4].reset();
            } else if (inventorySlot[i].contains(itemSlotBar[4].getItem())) {
                inventorySlot[i].addItem(itemSlotBar[4].getItem(), itemSlotBar[4].getAmmount());
                itemSlotBar[4].reset();
            }
            break;
        }
    }

    public void checkR6() {
        for (int i = 0; i < inventorySlot_count; i++) {
            if (inventorySlot[i].isFree()) {
                inventorySlot[i].addItem(itemSlotBar[5].getItem(), itemSlotBar[5].getAmmount());
                itemSlotBar[5].reset();
            } else if (inventorySlot[i].contains(itemSlotBar[5].getItem())) {
                inventorySlot[i].addItem(itemSlotBar[5].getItem(), itemSlotBar[5].getAmmount());
                itemSlotBar[5].reset();
            }
            break;
        }
    }

    public void checkR7() {
        for (int i = 0; i < inventorySlot_count; i++) {
            if (inventorySlot[i].isFree()) {
                inventorySlot[i].addItem(itemSlotBar[6].getItem(), itemSlotBar[6].getAmmount());
                itemSlotBar[6].reset();
            } else if (inventorySlot[i].contains(itemSlotBar[6].getItem())) {
                inventorySlot[i].addItem(itemSlotBar[6].getItem(), itemSlotBar[6].getAmmount());
                itemSlotBar[6].reset();
            }
            break;
        }
    }

    public void updateBaseUpgradeGUI(GameContainer gc) {
        if (isBaseUpgradeShopOpen) {
            baseUpgradeGUI.update();

            //Check for item buying...
            if (baseUpgradeGUI.isItemBuyed(1)) {
                System.out.println("ITEM 01 BUYED");
            }
        }

        if (gc.getInput().isKeyDown(Input.KEY_ESCAPE) && isBaseUpgradeShopOpen) {
            isBaseUpgradeShopOpen = false;
        }
    }

    public void renderBaseUpgradeGUI(Graphics g) {
        if (isBaseUpgradeShopOpen) {
            baseUpgradeGUI.render(g);
        }
    }

    //Rain
    class RainDrop extends Polygon {

        public RainDrop(int size) {
            super();
            /**
             * coordinates of vertices of polygon(rain drop)
             */
            this.addPoint(0, 0);
            this.addPoint(0, size);
        }
    }

    void updateRain() throws SlickException {
        for (int i = 0; i < dropCount; i++) {
            drops[i].setY(drops[i].getY() + 4.0f);
            drops[i].setX(drops[i].getX() - 0.0f);

            if (movement == 2) {
                drops[i].setX(drops[i].getX() + 2.0f);
            }

            if (movement == 4) {
                drops[i].setX(drops[i].getX() - 2.0f);
            }

            if (drops[i].getY() > 786) {
                drops[i].setX((float) ((Math.random() * 2000) - 500));
                drops[i].setY((float) ((Math.random() * 5)));
            }
        }
    }

    void renderRain(GameContainer gc, Graphics g) throws SlickException {
        g.setColor(new Color(188.0f, 227.0f, 229.0f, 0.3f));

        for (int i = 0; i < dropCount; i++) {
            g.draw(drops[i]);
        }
    }

    void initializeRain() {
        drops = new RainDrop[dropCount];

        for (int i = 0; i < dropCount; i++) {
            drops[i] = new RainDrop((int) (Math.random() * 10 + 4));
            drops[i].setX((float) ((Math.random() * 2000) + 30));
            drops[i].setY((float) ((Math.random() * 750)));
        }
    }

    //Lighting Strike Effect
    public static class LightingBoltEffect {

        Collection<Line> segments;

        int totalTime;

        int currentTime;

        private float lineWidth;

        public LightingBoltEffect(int time, Collection<Line> segments, float lineWidth) {
            this.totalTime = time;
            this.segments = segments;
            this.currentTime = time;
            this.lineWidth = lineWidth;
        }

        public void update(int delta) {
            currentTime -= delta;
            if (currentTime <= 0) {
                currentTime = 0;
            }
        }

        public void render() {
            float alpha = (float) currentTime / (float) totalTime;
            glPushMatrix();
            glColor4f(alpha, alpha, alpha, alpha);
            glLineWidth(lineWidth);
            glBegin(GL_LINES);
            {
                for (Line segment : segments) {
                    glVertex(segment.getStart());
                    glVertex(segment.getEnd());
                }
            }
            glEnd();
            glPopMatrix();
        }

        public boolean isDone() {
            return currentTime <= 0;
        }

        public void glVertex(Vector2f v) {
            glVertex3f(v.x, v.y, 0);
        }

    }

    protected void generateLightingBolt(Vector2f p0, Vector2f p1, int duration) {
        Collection<Line> segments = new ArrayList<Line>();

        segments.add(new Line(p0, p1));

        float offset = 200f;
        double probability = 0.3; // probability to generate new partitions  
        float height = 50.0f;

        Random random = new Random();

        int partitions = 4;

        for (int i = 0; i < partitions; i++) {

            Collection<Line> newSegments = new ArrayList<Line>();

            for (Line segment : segments) {

                Vector2f midPoint = segment.getStart().copy().add(segment.getEnd()).scale(0.5f);

                Vector2f perpendicular = midPoint.copy().add(90);
                perpendicular.normalise().scale(random.nextFloat() * offset - (offset / 2));
                midPoint.add(perpendicular);

                if (random.nextFloat() < probability) {
                    // generate new branch  
                    Vector2f direction = midPoint.copy().sub(segment.getStart());
                    direction.add(random.nextFloat() * height);
                    newSegments.add(new Line(midPoint.copy(), midPoint.copy().add(direction)));
                }

                newSegments.add(new Line(segment.getStart().copy(), midPoint.copy()));
                newSegments.add(new Line(midPoint.copy(), segment.getEnd().copy()));
            }

            segments = newSegments;

            offset /= 2;
        }
        lightingBoltEffect = new LightingBoltEffect(duration, segments, 2.0f);
    }

    public void updateThunder(GameContainer container, int delta) throws SlickException {
        Random random = new Random();

        lightingBoltEffect.update(delta);
        if (!lightingBoltEffect.isDone()) {
            return;
        }

        Input input = container.getInput();
        lightingCount--;
        if (lightingCount == 0) {
            int mouseX = input.getMouseX();

            int x = random.nextInt(Option.getWidth());

            int duration = random.nextInt() % 600 + 300;
            generateLightingBolt(new Vector2f(x, 0), new Vector2f((mouseX), 0 + Option.getHeight() - 100), duration);

            Main.mixer.playSound(Sound.THUNDER);
            lightingCount = random.nextInt(stormSize);
        }
    }

    public void renderThunder(GameContainer container, Graphics g) throws SlickException {
        SlickCallable.enterSafeBlock();
        lightingBoltEffect.render();
        SlickCallable.leaveSafeBlock();
    }
}