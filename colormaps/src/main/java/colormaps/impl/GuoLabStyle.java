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

import static java.lang.Math.hypot;

import java.awt.Color;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import colormaps.AbstractColormap2D;
import colorspaces.CIELAB;

import com.google.common.base.Function;

/**
 * Guo-style Lab 2D color spaces, based on functions that reflect the choices in the paper.
 * @author simon
 */
public abstract class GuoLabStyle extends AbstractColormap2D
{
	private static final Logger logger = LoggerFactory.getLogger(GuoLabStyle.class);
	
	private Function<Float, Float> lightnessFunc;

	private Function<Map.Entry<Float, Float>, Map.Entry<Float, Float>> chromaFunc;

	/**
	 * @param lightnessFunc the lightness function, defined based on distance from the mid point 0..sqrt 2
	 * @param chromaFunc The chroma function, defined based on x and y as in {@link colormaps.Colormap2D#getColor(float, float)}
	 */
	public GuoLabStyle(Function<Float, Float> lightnessFunc, Function<Entry<Float, Float>, Entry<Float, Float>> chromaFunc)
	{
		this.lightnessFunc = lightnessFunc;
		this.chromaFunc = chromaFunc;
	}
	
	public static Function<Float, Float> constant1f(final float constant) {
		return new Function<Float, Float>() {
			
			@Override
			public Float apply(Float input) {
				return 55f;  // guessed from poster
			}
		};
	}

	public static Function<Float, Float> cone(final float factor)
	{
		return new Function<Float, Float>()
		{
			@Override
			public Float apply(Float input)
			{
				return 100.0f - (input * factor);
			}
		};
	}

	public static Function<Float, Float> pseudoGaussian(float sigma)
	{
		final float sigmasquare = sigma * sigma;
		return new Function<Float, Float>()
		{
			@Override
			public Float apply(Float input)
			{
				return (float) (100 * Math.exp(-((input * input) / 2 * sigmasquare)));
			}
		};
	}

	public static Function<Map.Entry<Float, Float>, Map.Entry<Float, Float>> linearAB(final float xOffset,
			final float xFactor, final float yOffset, final float yFactor)
	{
		return new Function<Map.Entry<Float, Float>, Map.Entry<Float, Float>>()
		{
			@Override
			public Entry<Float, Float> apply(Entry<Float, Float> input)
			{
				return new AbstractMap.SimpleEntry<>(
						xOffset + input.getKey() * xFactor,
						yOffset + input.getValue() * yFactor);
			}
		};
	}

	public static Function<Map.Entry<Float, Float>, Map.Entry<Float, Float>> linearBa(
			final float xOffset,
			final float xFactor,
			final float yOffset,
			final float yFactor) {
		return new Function<Map.Entry<Float,Float>, Map.Entry<Float,Float>>() {
			@Override
			public Entry<Float, Float> apply(Entry<Float, Float> input) {
				return new AbstractMap.SimpleEntry<>(
						yOffset + input.getValue() * yFactor,
						xOffset + input.getKey() * xFactor);
			}
		};
	}
	
	@Override
	public Color getColor(float x, float y)
	{
		// normalize to -1..1
		float nx = x * 2 - 1;
		float ny = y * 2 - 1;
		float dist = (float) hypot(nx, ny);
		float light = lightnessFunc.apply(dist);
		Map.Entry<Float, Float> ab = chromaFunc.apply(new AbstractMap.SimpleEntry<>(nx, ny));
		double[] lab = new double[] { light, ab.getKey(), ab.getValue() };

		double[] rgb = CIELAB.lab2rgb(lab);

		for (int i = 0; i < rgb.length; i++)
		{
			// TODO clamp is not the best idea but it's unclear what Guo did
			// also ATM we do not have a way of evaluating the delta E in the UI
			// meaning we should have a color class supporting further analysis.
			if (rgb[i] < 0)
			{
				rgb[i] = 0;
				if (rgb[i] < -0.01)
				{
					logger.warn("Color channel {} was clamped from {} to 0", i, rgb[i]);
				}
			}

			if (rgb[i] > 1)
			{
				rgb[i] = 1;
				if (rgb[i] > 1.01)
				{
					logger.warn("Color channel {} was clamped from {} to 1", i, rgb[i]);
				}
			}

		}
		
		return new Color((float)rgb[0], (float)rgb[1], (float)rgb[2]);
	}
	
	@Override
	public colormaps.ColorSpace getColorSpace() {
		return colormaps.ColorSpace.CIE_Lab;
	}
	
}
