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

package colormaps;

import static org.junit.Assert.assertEquals;

import java.awt.geom.AffineTransform;

import org.junit.Test;

/**
 * Transforms a given colormap
 * @author Martin Steiger
 */
public class TransformedColormapTest
{
	private static final Colormap2D cm = new ConstantColormap2D();

	@Test
	public void testIdentity()
	{
		verify(TransformedColormap2D.identity(cm), new AffineTransform());
	}

	@Test
	public void testRotate90()
	{
		AffineTransform at = new AffineTransform();
		at.quadrantRotate(1, 0.5, 0.5);
		verify(TransformedColormap2D.rotated90(cm), at);
	}

	@Test
	public void testRotate180()
	{
		AffineTransform at = new AffineTransform();
		at.quadrantRotate(2, 0.5, 0.5);
		verify(TransformedColormap2D.rotated180(cm), at);
	}

	@Test
	public void testRotate270()
	{
		AffineTransform at = new AffineTransform();
		at.quadrantRotate(3, 0.5, 0.5);
		verify(TransformedColormap2D.rotated270(cm), at);
	}

	@Test
	public void testFlippedX()
	{
		AffineTransform at = new AffineTransform();
		at.translate(1, 0);
		at.scale(-1, 1);
		verify(TransformedColormap2D.flippedX(cm), at);
	}

	@Test
	public void testFlippedY()
	{
		AffineTransform at = new AffineTransform();
		at.translate(0, 1);
		at.scale(1, -1);
		verify(TransformedColormap2D.flippedY(cm), at);
	}

	private void verify(TransformedColormap2D tcm, AffineTransform shouldBe)
	{
		AffineTransform is = tcm.getTransformation();
		
		assertEquals(shouldBe, is);
		
	}

}
