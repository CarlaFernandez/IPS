package CapaDatos;

import java.util.Random;

public class GeneradorIDRandom {
	public static Long generarID() {
		Random random = new Random();
		return random.nextLong();
	}
}
