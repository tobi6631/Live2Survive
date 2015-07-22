package engine.silnik.base;

import engine.silnik.Option;
import engine.silnik.Vector2i;
import engine.silnik.main.Game;
import engine.silnik.main.Main;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Base {

    private final int x;
    private final int y;
    private Image base;
    public boolean occupied = false;//DEFAULT
    public String occupiedBy;
    public int healthLevel = 100;//DEFAULT
    public int secureLevel = 2;//DEFAULT
    public int currentState = 0;//DEFAULT
    public boolean haveKitchen = false;
    public boolean haveBlacksmithing = false;//ABLE TO MAKE WEAPONS...
    public boolean haveBed = false;//ABLE TO SLEEP...
    public boolean upgradeable = true;//ABLE TO UPGRADE
    public int hoverMenuState = 0;
    public String[] menuText  = new String[4];
    public Shape spriteShape;
    public boolean insidePerformFrame;

    public Base(int x, int y, Image base) {
        this.x = x;
        this.y = y;
        this.base = base;

        spriteShape = new Rectangle(x, y, base.getWidth(), base.getHeight());
    }

    public void render(int uX, int uY, Graphics g) {
        if (insidePerformFrame) {
            base.draw(x * Option.getBitRate() + uX, y * Option.getBitRate() + uY);

            if (Main.mouseOver(new Vector2i(x * Option.getBitRate() + uX, y * Option.getBitRate() + uY), base, Main.inputAll)) {
                g.setColor(Color.orange);
                if (hoverMenuState == 1) {
                    g.drawString(menuText[0]
                            + "\n" + menuText[1]
                            + "\n" + menuText[2]
                            + "\n" + menuText[3], Main.inputAll.getMouseX() + 10, Main.inputAll.getMouseY() + 10);
                }

                if (hoverMenuState == 2) {
                    g.drawString("Destroy! (PRESS SPACE)"
                            + "\n\nOccupied By: " + occupiedBy
                            + "\nCurrent Health: " + healthLevel
                            + "\nSecureLevel: " + secureLevel, Main.inputAll.getMouseX() + 10, Main.inputAll.getMouseY() + 10);
                }
                
                if(hoverMenuState == 3) {
                     g.drawString("Occupie (PRESS SPACE)", Main.inputAll.getMouseX() + 10, Main.inputAll.getMouseY() + 10);
                }
            }
        }
        
        spriteShape.setX(x * Option.getBitRate() + uX);
        spriteShape.setY(y * Option.getBitRate() + uY);
    }

    public void update() {
        if (insidePerformFrame) {
            if (occupied && occupiedBy.equals(Option.getUsername())) {
                hoverMenuState = 1;//show defaultMenu
            } else if (occupied && !occupiedBy.equals(Option.getUsername())) {
                hoverMenuState = 2;//show destoyState
            } else if (!occupied) {
                hoverMenuState = 3;
            }
            
            ////////////////////////
            Option.setUsername("LOL2");
            
            haveBed = true;
            haveBlacksmithing = true;
            haveKitchen = true;
            ////////////////////////
            
            if(hoverMenuState == 2) {
                if(Main.inputAll.isKeyPressed(Input.KEY_SPACE)) {
                    healthLevel -= 20 - secureLevel; //20 is default, when weapons is in the game, it will demand on how much damage the weapon have
                }
            }
            
            if(hoverMenuState == 3) {
                if(Main.inputAll.isKeyPressed(Input.KEY_SPACE)) {
                    occupied = true;
                    occupiedBy = Option.getUsername();
                    hoverMenuState = 0;
                }
            }

            if (upgradeable) {
                menuText[0] = "Upgrade (PRESS T)";

                if (Main.inputAll.isKeyPressed(Input.KEY_T) && hoverMenuState == 1) {
                    Game.isBaseUpgradeShopOpen = true;
                }
            }

            if (haveBed) {
                menuText[1] = "Sleep (PRESS R)";

                if (Main.inputAll.isKeyPressed(Input.KEY_R) && hoverMenuState == 1 && Game.lightState == 1) {
                    Game.lightState = 0;
                    Game.alpha = 0;
                } else if (Main.inputAll.isKeyDown(Input.KEY_R) && hoverMenuState == 1 && Game.lightState == 0) {
                    menuText[1] = "Sleep (U CAN ONLY SLEEP AT NIGHT!)";
                }
            } else {
                menuText[1] = "Sleep (U NEED TO UPGRADE)";
            }

            if (haveKitchen) {
                menuText[2] = "Kitchen (PRESS F)";

                if (Main.inputAll.isKeyDown(Input.KEY_F) && hoverMenuState == 1) {
                    menuText[2] = "Kitchen (NO KITCHEN MENU YET!)";
                }
            } else {
                menuText[2] = "Kitchen (U NEED TO UPGRADE)";
            }

            if (haveBlacksmithing) {
                menuText[3] = "Blacksmithing (PRESS G)";

                if (Main.inputAll.isKeyDown(Input.KEY_G) && hoverMenuState == 1) {
                    menuText[3] = "Blacksmithing (NO BLACKSMITHING MENU YET!)";
                }
            } else {
                menuText[3] = "Blacksmithing (U NEED TO UPGRADE)";
            }
        }
    }
}