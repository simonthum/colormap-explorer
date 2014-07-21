/*
 * Copyright 2014 Fraunhofer IGD
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package colormaps.impl;

import java.awt.Color;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import colormaps.AbstractColormap2D;
import colormaps.ColorSpace;

public class BaumOrangeCyan extends AbstractColormap2D {

	@Override
	public Color getColor(float x, float y) {
		checkRanges(x, y);

		// red
		float r = 0.547322350173516f + 0.692515727985112f * x
				+ 255.732690566668f * y;
//		if (r > 255)
//			System.out.println("Red: " + r);
		r = Math.max(0, Math.min(255, r));

		// green
		float g = -2.38928709411272f + 173.55305419884f * x + 86.4399107353958f
				* y;
//		if (g > 255)
//			System.out.println("Green: " + g);
		g = Math.max(0, Math.min(255, g));

		// blue
		float b = -0.0456529636621121f + 256.889116983918f * x
				+ 1.16866281852388f * y;
//		if (b > 255)
//			System.out.println("Blue: " + b);
		b = Math.max(0, Math.min(255, b));

		return new Color(r / 255f, g / 255f, b / 255f);
	}

	@Override
	public String getName() {
		return "Baum et al. Orange-Cyan";
	}

	@Override
	public String getDescription() {
		return "2D color lookup table by Baum et al. with Orange-Cyan as a first principal diagonal";
	}

	@Override
	public ColorSpace getColorSpace() {
		return ColorSpace.sRGB;
	}

	@Override
	public List<String> getReferences() {
		return Arrays.asList("baum2006genetic", "baum2007investigation");
	}
}
