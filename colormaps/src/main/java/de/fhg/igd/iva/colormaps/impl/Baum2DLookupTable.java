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

package de.fhg.igd.iva.colormaps.impl;

import java.awt.Color;
import java.util.Collections;
import java.util.List;

import de.fhg.igd.iva.colormaps.AbstractKnownColormap;
import de.fhg.igd.iva.colormaps.ColorSpace;

/**
 * @deprecated black-white diagonal.
 * @author JB
 */
@Deprecated
public class Baum2DLookupTable extends AbstractKnownColormap {

	@Override
	public Color getColor(double x, double y) {
		checkRanges(x, y);

		return new Color((float)x, (float)x, (float)y);
	}

	@Override
	public String getName() {
		return "Baum et al. B-Y";
	}

	@Override
	public String getDescription() {
		return "2D color lookup table by Baum et al. Colors: Blue, White, Yellow, Black";
	}

	@Override
	public ColorSpace getColorSpace() {
		return ColorSpace.sRGB;
	}

	@Override
	public List<String> getReferences() {
		return Collections.singletonList("baum2006techniques");
	}
}
