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


package algorithms.quality;

import java.util.List;

import algorithms.sampling.SamplingStrategy;
import de.fhg.igd.pcolor.CIEXYZ;
import de.fhg.igd.pcolor.PColor;
import de.fhg.igd.pcolor.colorspace.ViewingConditions;
import de.fhg.igd.pcolor.util.ColorTools;

/**
 * Compute global statistics for a color maps
 * @author simon
 */
public class ColorDynamicDistBlack extends ColorDynamic
{
	private static final ViewingConditions VIEW_ENV = ViewingConditions.sRGB_typical_envirnonment;
	private static final CIEXYZ BLACK = CIEXYZ.fromCIEXYZ100(0, 0, 0);

	/**
	 * @param sampling the sampling strategy to use
	 */
	public ColorDynamicDistBlack(SamplingStrategy sampling)
	{
		super(sampling);
	}
	
	@Override
	protected double getResult(List<PColor> colors)
	{
		float distBlack = Float.MAX_VALUE;

		for (PColor color : colors)
		{
			distBlack = Math.min(distBlack, ColorTools.distance(color, BLACK, VIEW_ENV));
		}
	
		return distBlack;
	}
	
	@Override
	public boolean moreIsBetter()
	{
		return true;
	}

	@Override
	public String getName()
	{
		return "BlackDistance";
	}

	@Override
	public String getDescription()
	{
		return "The smallest found JND distance to black";
	}

}
