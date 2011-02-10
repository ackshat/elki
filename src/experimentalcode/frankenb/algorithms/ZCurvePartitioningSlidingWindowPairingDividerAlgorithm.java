/**
 * 
 */
package experimentalcode.frankenb.algorithms;

import de.lmu.ifi.dbs.elki.utilities.optionhandling.parameterization.Parameterization;
import experimentalcode.frankenb.algorithms.pairing.SlidingWindowPartitionPairing;
import experimentalcode.frankenb.algorithms.partitioning.ZCurvePartitioning;

/**
 * No description given.
 * 
 * @author Florian Frankenberger
 */
public class ZCurvePartitioningSlidingWindowPairingDividerAlgorithm extends AbstractDividerAlgorithm {

  public ZCurvePartitioningSlidingWindowPairingDividerAlgorithm(Parameterization config) {
    this.setPartitioning(new ZCurvePartitioning(config));
    this.setPairing(new SlidingWindowPartitionPairing(config));
  }

}