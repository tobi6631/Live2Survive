/*
 * (C) Tobias Development 2014
 */

package engine.silnik;

public class Option {
    
    private static int WIDTH                             = 800;//864 --F 1920
    private static int HEIGHT                            = 600;//576 --F 1080
    private static int FPSLIMIT                          = 999999;//420  --F 999999
    private static int MAX_ALLOWED_TILE_RENDERLEVEL      = 1000;//520
    private static int SCREEN                            = 0;
    private static int LEVEL;
    private static int SELECT_PLAYER                     = 0;//?
    public  static int FPS                               = 000;// -- main.cls
    private static float VERSION                         = 0.6F; 
    private static int BITRATE                           = 32;
        
    private static boolean FULLSCREEN                    = false;
    private static boolean ALLWAYSRENDER                 = true;
    private static boolean CLEAREATCHFRAME               = true;
    private static boolean FORCEEXIT                     = true;
    private static boolean SHOWFPS                       = false;
    private static boolean RESIZABEL                     = true;
    private static boolean DEBUGMODE                     = true;
    private static boolean DEVELOPMENTMODE               = true;
    private static boolean STOP;
    private static boolean PAUSE;
    private static boolean RESUME;
    private static boolean PLAYER_DEBUG;
    private static boolean LEVEL_DONE;
    private static boolean SERVER_MODE                   = false;        
    
    //EASTEREGGS:
    private static boolean FIREWORK_EA                   = false;
    private static boolean DISCO_EA                      = false;
        
    private static String GAMENAME                        = "Live2Survive";
    private static String USERNAME                        = "PLAYER";//Default
        
    public static int getWidth()                         {return WIDTH;}
    public static int getHeight()                        {return HEIGHT;}
    public static int getFpsLimit()                      {return FPSLIMIT;}
    public static int getScreen()                        {return SCREEN;}
    public static int getLevel()                         {return LEVEL;}
    public static int getMaxAllowedTileRenderLevel()     {return MAX_ALLOWED_TILE_RENDERLEVEL;}
    public static int getSelectPlayer()                  {return SELECT_PLAYER;}
    public static int getBitRate()                       {return BITRATE;}
    public static float getVersion()                     {return VERSION;}
        
    public static boolean getFullscreen()                {return FULLSCREEN;}
    public static boolean getAllwaysRender()             {return ALLWAYSRENDER;}
    public static boolean getClearEatchFrame()           {return CLEAREATCHFRAME;}
    public static boolean getForceExit()                 {return FORCEEXIT;}
    public static boolean getShowFps()                   {return SHOWFPS;}
    public static boolean getResizabel()                 {return RESIZABEL;}
    public static boolean getDebugMode()                 {return DEBUGMODE;}
    public static boolean getDevelopmentMode()           {return DEVELOPMENTMODE;}
    public static boolean getStop()                      {return STOP;}
    public static boolean getPause()                     {return PAUSE;}
    public static boolean getResume()                    {return RESUME;}
    public static boolean getPlayerDebug()               {return PLAYER_DEBUG;}
    public static boolean getLevelDone()                 {return LEVEL_DONE;}
    public static boolean getServerMode()                {return SERVER_MODE;}
    
    public static String getUsername()                   {return USERNAME;}
    
    //EASTER EGGS
    public static boolean getFireworkEA()                {return FIREWORK_EA;}
    public static boolean getDiscoEA()                   {return DISCO_EA;}

    public static void setScreen(int screen) {
        SCREEN = screen;
       
        /**
         * SCREEN LISE:
         * 
         * 0) Menu
         * 1) Select Player
         * 2) Play
         * 3) Multiplayer
         * 4) Options
         */
    }
    
    public static void setSelectPlayer(int splr) {SELECT_PLAYER = splr;}
    
    public static void setLevel(int level) {
        LEVEL = level;
        
        /**
         * LEVEL LIST:
         * 
         * 0) DebugLevel
         * 1) Throw
         * 2) ToCastle
         * 3) Castle
         */
    }
    
    public static void setDebugMode(boolean debugMode) {
        DEBUGMODE = debugMode;
    }
    
    public static void setPlayerDebug(boolean playerDebug) {
        PLAYER_DEBUG = playerDebug;
    }
    
    public static void setLevelDone(boolean level_done) {
        LEVEL_DONE = level_done;
    }
    
    public static void setStop(boolean bool)         {STOP        = bool;}
    public static void setPause(boolean bool)        {PAUSE       = bool;}
    public static void setResume(boolean bool)       {RESUME      = bool;}
    public static void setServerMode(boolean bool)   {SERVER_MODE = bool;}
    public static void setFullScreen(boolean bool)   {FULLSCREEN  = bool;}
    
    public static void setUsername(String usrn)      {USERNAME    = usrn;}
        
    public static String getGameName()         {return GAMENAME;}
}


      //\\        ========     ========                     ||            ========    ||
     //  \\      ||           ||               =========    ||           ||      ||   ||
    //    \\     ||           ||                            ||           ||      ||   ||
   //      \\     ========     ========                     ||           ||      ||   ||      
  //========\\           ||           ||       =========    ||           ||      ||   ||      
 //          \\          ||           ||                    ||           ||      ||   ||      
//            \\  ========     ========                     ||========    ========    ||========