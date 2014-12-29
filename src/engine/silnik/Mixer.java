/*
 * (C) Tobias Development 2014
 */

package engine.silnik;

import java.util.HashMap;
import java.util.Map;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

public class Mixer {

    /**
     * Theme Music
     */
    public static Music theme;

    /**
     * Sound Map
     */
    private static Map<engine.silnik.Sound, org.newdawn.slick.Sound> sounds;

    /**
     * Res - were the sounds is
     */
    private static String res = "C:\\Tobias Development\\Games\\Live2Survive\\sound\\";


    public Mixer() {

        /**
         * Sound HasMap
         */
        sounds = new HashMap<>();

        /**
         * Put sound in here
         *
         * 'sounds.put(Game.Sound.Sound.SOUND_NAME, new Sound(res +
         * "SOUND_NAME.wav"));'
         */
        try {
            sounds.put(Sound.INTRO, new org.newdawn.slick.Sound(res + "intro.wav"));
        } catch (SlickException e1) {
            org.newdawn.slick.util.Log.warn(e1.getLocalizedMessage(), e1);
        }
    }

    /**
     * Plays a sound
     *
     * @param sound
     */
    public void playSound(Sound sound) {
        sounds.get(sound).play();
    }

    /**
     * loop a Sound
     *
     * @param sound
     * @param volume
     */
    public void loopSound(Sound sound, float volume) {
        sounds.get(sound).loop(1, volume);
    }

    /**
     * Stop a Sound
     *
     * @param sound
     */
    public void stopSound(Sound sound) {
        sounds.get(sound).stop();
        if (sound == Sound.STOP_ALL) {
            sounds.get(Sound.INTRO).stop();
        }
    }

    /**
     * Pitch a Sound
     *
     * @param sound
     * @param volume
     * @param pitch
     */
    public void PitchSound(Sound sound, float volume, float pitch) {
        sounds.get(sound).play(pitch, volume);
    }

    /**
     * test for Pitching
     *
     * @param sound
     * @param volume
     * @param pitch
     */
    public void racing(Sound sound, float volume, float pitch) {
        sounds.get(sound).loop(pitch, volume);
    }
}
