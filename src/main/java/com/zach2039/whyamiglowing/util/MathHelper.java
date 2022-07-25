package com.zach2039.whyamiglowing.util;

import com.zach2039.whyamiglowing.config.WhyAmIGlowingConfig;

public class MathHelper {
	public static final float TOLERANCE_MIN = 0.0001f;

	public static float tol(float value) {
		if (value < TOLERANCE_MIN) {
			return 0f;
		}

		return value;
	}
}
