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

package views;

import java.awt.Color;

import de.fhg.igd.iva.colormaps.Colormap;
import de.fhg.igd.iva.colorspaces.CIELABLch;
import de.fhg.igd.iva.colorspaces.RGB;

/**
 * Transforms a given colormap by filtering a channel
 * @author Martin Steiger
 */
public class SimpleColormapView implements ColormapView
{
	private Colormap colormap;

	public enum ViewType
	{
		REAL("The original colormap"),
		LUM("Luma"),
		RED("Red channel"),
		GREEN("Green channel"),
		BLUE("Blue channel"),
		HUE("Hue"),
		SAT("Saturation"),
		BRIGHT("Brightness"),
		ATT("Attention");
		
		private String desc;
		
		ViewType(String desc)
		{
			this.desc = desc;
		}
		
		/**
		 * @return a description of the view
		 */
		public String getDescription()
		{
			return desc;
		}
	}
	
	private ViewType viewType;

	/**
	 * @param colormap the original color map
	 * @param viewType the filter type
	 */
	public SimpleColormapView(Colormap colormap, ViewType viewType)
	{
		this.colormap = colormap;
		this.viewType = viewType;
	}
	
	@Override
	public Color getColor(double mx, double my)
	{
		Color color = colormap.getColor(mx, my);
		
		int red = color.getRed();
		int green = color.getGreen();
		int blue = color.getBlue();
		float[] hsb;
		
		switch (viewType)
		{
		case REAL:
			return color;
		case LUM:
			int y = RGB.getLumaByte(color);
			return new Color(y, y ,y);
		case RED:
			return new Color(red, 0, 0);
		case GREEN:
			return new Color(0, green, 0);
		case BLUE:
			return new Color(0, 0, blue);
		case HUE:
			hsb = Color.RGBtoHSB(red, green, blue, null);
			Color rgb = new Color(Color.HSBtoRGB(hsb[0], 1, 1));
			return new Color(rgb.getRed(), rgb.getGreen(), rgb.getBlue());
		case SAT:
			hsb = Color.RGBtoHSB(red, green, blue, null);
			return new Color(hsb[1], hsb[1], hsb[1]);
		case BRIGHT:
			hsb = Color.RGBtoHSB(red, green, blue, null);
			return new Color(hsb[2], hsb[2], hsb[2]);
		case ATT:
		{
			double[] lch = new CIELABLch().fromColor(color);

			float attention = (float) ((float) Math.sqrt(lch[0]*lch[0]+lch[1]*lch[1]) / Math.sqrt(100*100 + 150*150));
			return new Color(attention, attention, attention);
		}
		
		default:
			return Color.LIGHT_GRAY;
		}
	}

	@Override
	public String getDescription()
	{
		return viewType.getDescription();
	}
	
	@Override
	public double getReliability(double mx, double my)
	{
		switch (viewType)
		{
		case HUE:
		{
			Color color = colormap.getColor(mx, my);
			int red = color.getRed();
			int green = color.getGreen();
			int blue = color.getBlue();
			float[] hsv = Color.RGBtoHSB(red, green, blue, null);
			return hsv[1];
		}
		default:
			return 1.0;
		}
	}
}
