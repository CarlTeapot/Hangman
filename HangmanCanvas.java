

import acm.graphics.*;


import java.awt.*;
import java.util.ArrayList;

public class HangmanCanvas extends GCanvas {
	ArrayList<GObject> parts;
	ArrayList<GObject> head;
	ArrayList<GObject> cane;
	private GLabel word;
	private GLabel guesses;
	private String chars = "";
	private static final int SCAFFOLD_Y_OFFSET = 35;
	private static final int WIDTH = 385;
	public HangmanCanvas() {
		parts = new ArrayList<>();
		head = new ArrayList<>();
		cane = new ArrayList<>();
		draw();
		drawHead();
		drawParts();
		drawCane();
		drawLabels();


	}

	/**
	 * drawLabels() method displays the incomplete word and the incorrect letters on the display
	 */
	private void drawLabels() {
		word = new GLabel("");
		word.setLocation(WIDTH / 2 - ROPE_LENGTH - 50, SCAFFOLD_Y_OFFSET + SCAFFOLD_HEIGHT + 30);
		add(word);
		guesses = new GLabel(chars);
		guesses.setLocation(WIDTH / 2 - ROPE_LENGTH, SCAFFOLD_Y_OFFSET + SCAFFOLD_HEIGHT + 30);
		add(guesses);
	}

	/**
	 * This method Resets the display
	 * removes the body parts and resets the labels
	 */
	public void reset() {
		for (GObject part : parts) {
			remove(part);
		}
		for (GObject part : head) {
			remove(part);
		}
		for (GObject part : cane) {
			remove(part);
		}
		chars = "";
		remove(guesses);
	}
	// draws objects that do not disappear when starting the game again
	private void draw() {
		GLine scaffold = new GLine(WIDTH / 2 - BEAM_LENGTH, SCAFFOLD_Y_OFFSET, WIDTH / 2 - BEAM_LENGTH, SCAFFOLD_Y_OFFSET + SCAFFOLD_HEIGHT);
		add(scaffold);
		GLine beam = new GLine(WIDTH / 2 - BEAM_LENGTH, SCAFFOLD_Y_OFFSET, WIDTH / 2, SCAFFOLD_Y_OFFSET);
		add(beam);
		GLine rope = new GLine(WIDTH / 2, SCAFFOLD_Y_OFFSET, WIDTH / 2, SCAFFOLD_Y_OFFSET + ROPE_LENGTH);
		add(rope);
	}
	// draws the body parts and adds them to an arraylist,
	// but does not display them on the canvas, that is handled by another method
	private void drawParts() {

		GLine body = new GLine(WIDTH / 2, SCAFFOLD_Y_OFFSET + ROPE_LENGTH + 2 * HEAD_RADIUS, WIDTH / 2, SCAFFOLD_Y_OFFSET + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH);
		parts.add(body);

		GLine hand1 = new GLine(WIDTH / 2 - UPPER_ARM_LENGTH, ARM_HEIGHT, WIDTH / 2 - UPPER_ARM_LENGTH, ARM_HEIGHT + LOWER_ARM_LENGTH);
		parts.add(hand1);

		GLine arm1 = new GLine(WIDTH / 2, ARM_HEIGHT,
				WIDTH / 2 - UPPER_ARM_LENGTH, ARM_HEIGHT);
		parts.add(arm1);

		GLine hand2 = new GLine(WIDTH / 2 + UPPER_ARM_LENGTH, ARM_HEIGHT, WIDTH / 2 + UPPER_ARM_LENGTH, ARM_HEIGHT + LOWER_ARM_LENGTH);
		parts.add(hand2);

		GLine arm2 = new GLine(WIDTH / 2, ARM_HEIGHT,
				WIDTH / 2 + UPPER_ARM_LENGTH, ARM_HEIGHT);
		parts.add(arm2);

		GLine hip1 = new GLine(WIDTH / 2, FOOT_OFFSET_FROM_SCAFFOLDBASE - LEG_LENGTH, WIDTH / 2 - HIP_WIDTH, FOOT_OFFSET_FROM_SCAFFOLDBASE - LEG_LENGTH);
		parts.add(hip1);

		GLine leg1 = new GLine(WIDTH / 2 - HIP_WIDTH, FOOT_OFFSET_FROM_SCAFFOLDBASE - LEG_LENGTH, WIDTH / 2 - HIP_WIDTH, FOOT_OFFSET_FROM_SCAFFOLDBASE);
		parts.add(leg1);

		GLine hip2 = new GLine(WIDTH / 2, FOOT_OFFSET_FROM_SCAFFOLDBASE - LEG_LENGTH, WIDTH / 2 + HIP_WIDTH, FOOT_OFFSET_FROM_SCAFFOLDBASE - LEG_LENGTH);
		parts.add(hip2);

		GLine leg2 = new GLine(WIDTH / 2 + HIP_WIDTH, FOOT_OFFSET_FROM_SCAFFOLDBASE - LEG_LENGTH, WIDTH / 2 + HIP_WIDTH, FOOT_OFFSET_FROM_SCAFFOLDBASE);
		parts.add(leg2);

		GLine foot1 = new GLine(WIDTH / 2 - HIP_WIDTH, FOOT_OFFSET_FROM_SCAFFOLDBASE, WIDTH / 2 - HIP_WIDTH - FOOT_LENGTH, FOOT_OFFSET_FROM_SCAFFOLDBASE);
		parts.add(foot1);

		GLine foot2 = new GLine(WIDTH / 2 + HIP_WIDTH, FOOT_OFFSET_FROM_SCAFFOLDBASE, WIDTH / 2 + HIP_WIDTH + FOOT_LENGTH, FOOT_OFFSET_FROM_SCAFFOLDBASE);
		parts.add(foot2);


	}

	/**
	 * displayWord() method displays the incomplete word
	 * @param str - the incomplete word that is displayed on the canvas
	 */
	public void displayWord(String str) {
		remove(word);
		word = new GLabel(str);
		word.setFont(new Font(str, Font.PLAIN, 40));
		word.setLocation(WIDTH / 2 - ROPE_LENGTH - 100, SCAFFOLD_Y_OFFSET + SCAFFOLD_HEIGHT + 30);
		add(word);
	}

	/**
	 * The method below adds the new incorrect letter to the arraylist of the incorrect letters
	 * this method is used for noteIncorrectGuess Method
	 */
	private void incorrectChars(char letter) {
		remove(guesses);
		chars += "" + letter;
		guesses.setLabel(chars);
		guesses.setFont(new Font(chars, Font.PLAIN, 25));
		guesses.setLocation(WIDTH / 2 - ROPE_LENGTH - 30, SCAFFOLD_Y_OFFSET + SCAFFOLD_HEIGHT + 70);
		add(guesses);
	}
	/**
	 * this method displays the incorrect letters that the user used
	 * @param letter parameter for displaying the incorrect letter
	 * @param guess parameter for determining which body part to add
	 */
	public void noteIncorrectGuess(char letter, int guess) {
		incorrectChars(letter);
		if (guess == 8) {
			for (GObject obj : head)
				add(obj);
		}
		else if (guess == 7)
			add(parts.get(0));
		else if (guess <= 2)
			add(parts.get(11 - guess));
		else if (guess == 6) {
			add(parts.get(1));
			add(parts.get(2));
			for (GObject obj : cane)
				add(obj);
		} else if (guess == 5) {
			add(parts.get(3));
			add(parts.get(4));
		} else if (guess == 4) {
			add(parts.get(5));
			add(parts.get(6));
		} else {
			add(parts.get(7));
			add(parts.get(8));
		}
	}


	/**
	 * draws the head and the cylinder
	 * note: some numbers are not kept in constant variables and are used directly, which is a bad practice,
	 * but these numbers serve no purpose other than to tweak the positions and sizes so that they look good on display,
	 * also considering that we already have a lot of constant variables, there is no point to make these numbers constants
	 */
	private void drawHead() {
		GOval face = new GOval(WIDTH / 2 - HEAD_RADIUS, SCAFFOLD_Y_OFFSET + ROPE_LENGTH, 2 * HEAD_RADIUS, 2 * HEAD_RADIUS);
		head.add(face);
		GOval eye1 = new GOval(WIDTH / 2 - 2.8 * EYE_RADIUS, SCAFFOLD_Y_OFFSET + ROPE_LENGTH + 1.5 * EYE_RADIUS, 2 * EYE_RADIUS, 2 * EYE_RADIUS);
		head.add(eye1);
		GOval eye2 = new GOval(WIDTH / 2 + 0.8 *  EYE_RADIUS, SCAFFOLD_Y_OFFSET + ROPE_LENGTH + 1.5 * EYE_RADIUS, 2 * EYE_RADIUS, 2 * EYE_RADIUS);
		head.add(eye2);
		GOval retina1 = new GOval(WIDTH / 2 - 2.8 * EYE_RADIUS+ (EYE_RADIUS - RETINA_RADIUS), SCAFFOLD_Y_OFFSET + ROPE_LENGTH + 1.5 * EYE_RADIUS + (EYE_RADIUS - RETINA_RADIUS), 2 * RETINA_RADIUS, 2 * RETINA_RADIUS);
		retina1.setFilled(true);
		head.add(retina1);
		GOval retina2 = new GOval(WIDTH / 2 + 0.8 *  EYE_RADIUS + (EYE_RADIUS - RETINA_RADIUS), SCAFFOLD_Y_OFFSET + ROPE_LENGTH + 1.5 * EYE_RADIUS + (EYE_RADIUS - RETINA_RADIUS), 2 * RETINA_RADIUS, 2 * RETINA_RADIUS);
		retina2.setFilled(true);
		head.add(retina2);

		GLine mouth = new GLine(WIDTH / 2 - MOUTH_LENGTH/2, MOUTH_Y_POS,WIDTH / 2 + MOUTH_LENGTH, MOUTH_Y_POS);
		head.add(mouth);

		GRect cylinder = new GRect(WIDTH / 2 - CYLINDER_WIDTH/2, SCAFFOLD_Y_OFFSET + ROPE_LENGTH - CYLINDER_HEIGHT + 10,CYLINDER_WIDTH, CYLINDER_HEIGHT);
		cylinder.setFilled(true);
		head.add(cylinder);
		GRect cylinderBase = new GRect(WIDTH / 2 - 0.8 * CYLINDER_WIDTH, SCAFFOLD_Y_OFFSET + ROPE_LENGTH,1.6 * CYLINDER_WIDTH, CYLINDER_HEIGHT/5);
		cylinderBase.setFilled(true);
		head.add(cylinderBase);

		GOval glass = new GOval(WIDTH / 2 + 0.2 * GLASS_RADIUS, SCAFFOLD_Y_OFFSET + ROPE_LENGTH + 0.8 * GLASS_RADIUS, 2 * GLASS_RADIUS, 2 * GLASS_RADIUS);
		head.add(glass);
		GLine glassLine = new GLine(WIDTH / 2 + 0.2 * GLASS_RADIUS + 2 * GLASS_RADIUS, SCAFFOLD_Y_OFFSET + ROPE_LENGTH + 1.8 * GLASS_RADIUS, WIDTH/2 + HEAD_RADIUS, SCAFFOLD_Y_OFFSET + ROPE_LENGTH + HEAD_RADIUS);
		head.add(glassLine);
	}
	// draws the cane
	private void drawCane() {
		GRect caneHandle = new GRect(WIDTH / 2 - UPPER_ARM_LENGTH - CANE_HANDLE_LENGTH/2, ARM_HEIGHT + LOWER_ARM_LENGTH,
				CANE_HANDLE_LENGTH, CANE_HANDLE_HEIGHT);
		caneHandle.setFilled(true);
		cane.add(caneHandle);

		GRect caneBase = new GRect(WIDTH / 2 - UPPER_ARM_LENGTH, ARM_HEIGHT + LOWER_ARM_LENGTH,
				5,FOOT_OFFSET_FROM_SCAFFOLDBASE - (LOWER_ARM_LENGTH + ARM_HEIGHT));
		caneBase.setFilled(true);
		cane.add(caneBase);
	}
	private static final int CANE_HANDLE_HEIGHT = 5;
	private static final int CANE_HANDLE_LENGTH = 20;
	private static final int CYLINDER_HEIGHT = 50;
	private static final int GLASS_RADIUS = 12;
	private static final int EYE_RADIUS = 8;
	private static final int MOUTH_LENGTH = 20;
	private static final int RETINA_RADIUS = 3;
	/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;

	private static final int FOOT_OFFSET_FROM_SCAFFOLDBASE = SCAFFOLD_Y_OFFSET + ROPE_LENGTH + (2 * HEAD_RADIUS) + BODY_LENGTH + LEG_LENGTH;
	private static final int ARM_HEIGHT = SCAFFOLD_Y_OFFSET + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD;
	private static final int MOUTH_Y_POS = SCAFFOLD_Y_OFFSET + ROPE_LENGTH + 2 * HEAD_RADIUS - 20;
	private static final double CYLINDER_WIDTH = 1.3 * HEAD_RADIUS;



}
