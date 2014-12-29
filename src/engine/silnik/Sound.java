/*
 * (C) Tobias Development 2014
 */

package engine.silnik;


/**
 * HOW TO ADD A SOUND 
 * 
 * GO TO THIS ENUM AND ADD THE SOUND NAME ETC: JOHN
 * 
 * THE GO TO THE MIXER CLASS AND PUT (Sounds.put(Sound.JOHN, new Sound(res + "FILENAME_OF_THE_SOUND.wav"));)
 * 
 * THEN USE:
 * 
 * Mixer m = new Mixer();
 * try {
 * 		m.PlaySound(Sound.JOHN, VOLUME);
 * } catch(Exception e) {
 * 		e.PrintStack();
 * }
 * 
 * TO PLAY A SOUND
 */

/**
 * Sounds
 * 
 * @author Tobias
 *
 */
public enum Sound {
	INTRO,
        STOP_ALL,
}
