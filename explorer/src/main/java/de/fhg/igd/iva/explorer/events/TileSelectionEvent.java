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

package de.fhg.igd.iva.explorer.events;

import java.util.Set;

import views.ColormapView;

import com.google.common.base.Preconditions;

import de.fhg.igd.iva.explorer.tiling.Tile;
import de.fhg.igd.iva.explorer.tiling.TileModel;

/**
 * Fired when the tile selection is changed
 * @author Martin Steiger
 */
public class TileSelectionEvent
{
	private final Set<Tile> selection;
	private final ColormapView colormap;
	private final TileModel model;

	/**
	 * @param tiles the selected tiles, never <code>null</code>
	 * @param colormap the corresponding colormap, never <code>null</code>
	 * @param model the corresponding tile model, never <code>null</code>
	 */
	public TileSelectionEvent(Set<Tile> tiles, ColormapView colormap, TileModel model)
	{
		// prefer NPE over IAE - Effective Java 2nd Edition, Item 60
		Preconditions.checkNotNull(colormap, "colormap must not be null");
		Preconditions.checkNotNull(model, "model must not be null");
		Preconditions.checkNotNull(tiles, "tiles must not be null");
		
		this.selection = tiles;
		this.model = model;
		this.colormap = colormap;
	}

	/**
	 * @return the corresponding colormap, never <code>null</code>
	 */
	public ColormapView getColormap()
	{
		return colormap;
	}

	/**
	 * @return the corresponding tile model, never <code>null</code>
	 */
	public TileModel getTileModel()
	{
		return model;
	}

	/**
	 * @return the selected tiles, never <code>null</code>
	 */
	public Set<Tile> getSelection()
	{
		return selection;
	}
}
