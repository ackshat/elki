/*
 * This file is part of ELKI:
 * Environment for Developing KDD-Applications Supported by Index-Structures
 * 
 * Copyright (C) 2017
 * ELKI Development Team
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package de.lmu.ifi.dbs.elki.datasource.filter.selection;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.lmu.ifi.dbs.elki.data.type.TypeUtil;
import de.lmu.ifi.dbs.elki.datasource.AbstractDataSourceTest;
import de.lmu.ifi.dbs.elki.datasource.bundle.MultipleObjectsBundle;
import de.lmu.ifi.dbs.elki.utilities.ClassGenericsUtil;
import de.lmu.ifi.dbs.elki.utilities.optionhandling.parameterization.ListParameterization;

/**
 * Test the random sampling filter.
 *
 * @author Matthew Arcifa
 */
public class RandomSamplingStreamFilterTest extends AbstractDataSourceTest {
  /**
   * Test with parameters p as the probability and seed as the random seed.
   */
  @Test
  public void parameters() {
    String filename = UNITTEST + "normalization-test-1.csv";
    // Allow loading test data from resources.
    ListParameterization config = new ListParameterization();
    config.addParameter(RandomSamplingStreamFilter.Parameterizer.PROB_ID, .5);
    config.addParameter(RandomSamplingStreamFilter.Parameterizer.SEED_ID, 0);
    RandomSamplingStreamFilter filter = ClassGenericsUtil.parameterizeOrAbort(RandomSamplingStreamFilter.class, config);
    MultipleObjectsBundle filteredBundle = readBundle(filename, filter);
    // Load the test data again without a filter.
    MultipleObjectsBundle unfilteredBundle = readBundle(filename);
    // Ensure the first column are the vectors.
    assertEquals("Dimensionality", getFieldDimensionality(unfilteredBundle, 0, TypeUtil.NUMBER_VECTOR_FIELD), getFieldDimensionality(filteredBundle, 0, TypeUtil.NUMBER_VECTOR_FIELD));

    // Verify that approximately p% of the values were sampled.
    assertEquals("Unexpected bundle length", unfilteredBundle.dataLength() * .5, filteredBundle.dataLength(), .05 * filteredBundle.dataLength());
  }
}
