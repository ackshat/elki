package de.lmu.ifi.dbs.wrapper;

import de.lmu.ifi.dbs.algorithm.AbstractAlgorithm;
import de.lmu.ifi.dbs.algorithm.KDDTask;
import de.lmu.ifi.dbs.algorithm.clustering.DBSCAN;
import de.lmu.ifi.dbs.algorithm.clustering.FourC;
import de.lmu.ifi.dbs.database.connection.FileBasedDatabaseConnection;
import de.lmu.ifi.dbs.distance.LocallyWeightedDistanceFunction;
import de.lmu.ifi.dbs.normalization.AttributeWiseDoubleVectorNormalization;
import de.lmu.ifi.dbs.pca.AbstractLocalPCA;
import de.lmu.ifi.dbs.preprocessing.RangeQueryBasedCorrelationDimensionPreprocessor;
import de.lmu.ifi.dbs.utilities.optionhandling.OptionHandler;
import de.lmu.ifi.dbs.utilities.optionhandling.UnusedParameterException;

import java.util.ArrayList;

/**
 * A wrapper for the 4C algorithm.
 *
 * @author Arthur Zimek (<a
 *         href="mailto:zimek@dbs.ifi.lmu.de">zimek@dbs.ifi.lmu.de</a>)
 */
public class FourCWrapper extends AbstractWrapper {
  /**
   * Default value for the big value (kappa).
   */
  public static final String BIG_DEFAULT = "50";

  /**
   * Default value for the small value.
   */
  public static final String SMALL_DEFAULT = "1";

  /**
   * Parameter for epsilon.
   */
  public static final String EPSILON_P = "epsilon";

  /**
   * Description for parameter epsilon.
   */
  public static final String EPSILON_D = "<epsilon>an epsilon value suitable to the distance function: " + LocallyWeightedDistanceFunction.class.getName();

  /**
   * Parameter minimum points.
   */
  public static final String MINPTS_P = "minpts";

  /**
   * Description for parameter minimum points.
   */
  public static final String MINPTS_D = "<int>minpts";

  /**
   * Epsilon.
   */
  protected String epsilon;

  /**
   * Minimum points.
   */
  protected String minpts;

  /**
   * Parameter lambda.
   */
  protected static final String LAMBDA_P = "lambda";

  /**
   * Description for parameter lambda.
   */
  protected static final String LAMBDA_D = "<lambda>(integer) correlation dimensionality";

  /**
   * Holds lambda.
   */
  protected String lambda;


  /**
   * Provides a wrapper for the 4C algorithm.
   */
  public FourCWrapper() {
    super();
    parameterToDescription.put(EPSILON_P + OptionHandler.EXPECTS_VALUE, EPSILON_D);
    parameterToDescription.put(MINPTS_P + OptionHandler.EXPECTS_VALUE, MINPTS_D);
    parameterToDescription.put(LAMBDA_P + OptionHandler.EXPECTS_VALUE, LAMBDA_D);
    optionHandler = new OptionHandler(parameterToDescription, getClass().getName());
  }

  /**
   * Runs 4C setting default parameters.
   */
  public void run4C(String[] args) {
    ArrayList<String> params = getRemainingParameters();

    // 4C algorithm
    params.add(OptionHandler.OPTION_PREFIX + KDDTask.ALGORITHM_P);
    params.add(FourC.class.getName());

    // distance function
    params.add(OptionHandler.OPTION_PREFIX + DBSCAN.DISTANCE_FUNCTION_P);
    params.add(LocallyWeightedDistanceFunction.class.getName());

    // epsilon for 4C
    params.add(OptionHandler.OPTION_PREFIX + DBSCAN.EPSILON_P);
    params.add(epsilon);

    // minpts for 4C
    params.add(OptionHandler.OPTION_PREFIX + DBSCAN.MINPTS_P);
    params.add(minpts);

    // lambda for 4C
    params.add(OptionHandler.OPTION_PREFIX + FourC.LAMBDA_P);
    params.add(lambda);

    // preprocessor
    params.add(OptionHandler.OPTION_PREFIX + LocallyWeightedDistanceFunction.PREPROCESSOR_CLASS_P);
    params.add(RangeQueryBasedCorrelationDimensionPreprocessor.class.getName());

    // epsilon for preprocessor
    params.add(OptionHandler.OPTION_PREFIX + RangeQueryBasedCorrelationDimensionPreprocessor.EPSILON_P);
    params.add(epsilon);

    // big value for PCA
    params.add(OptionHandler.OPTION_PREFIX + AbstractLocalPCA.BIG_VALUE_P);
    params.add(BIG_DEFAULT);

    // small value for PCA
    params.add(OptionHandler.OPTION_PREFIX + AbstractLocalPCA.SMALL_VALUE_P);
    params.add(SMALL_DEFAULT);

    // normalization
    params.add(OptionHandler.OPTION_PREFIX + KDDTask.NORMALIZATION_P);
    params.add(AttributeWiseDoubleVectorNormalization.class.getName());
    params.add(OptionHandler.OPTION_PREFIX + KDDTask.NORMALIZATION_UNDO_F);

    // input
    params.add(OptionHandler.OPTION_PREFIX + FileBasedDatabaseConnection.INPUT_P);
    params.add(input);

    // output
    params.add(OptionHandler.OPTION_PREFIX + KDDTask.OUTPUT_P);
    params.add(output);

    if (time) {
      params.add(OptionHandler.OPTION_PREFIX + AbstractAlgorithm.TIME_F);
    }
    if (verbose) {
      params.add(OptionHandler.OPTION_PREFIX + AbstractAlgorithm.VERBOSE_F);
    }

    KDDTask task = new KDDTask();
    task.setParameters(params.toArray(new String[params.size()]));
    task.run();

  }

  /**
   * @see de.lmu.ifi.dbs.utilities.optionhandling.Parameterizable#setParameters(String[])
   */
  public String[] setParameters(String[] args) throws IllegalArgumentException {
    super.setParameters(args);
    try {
      epsilon = optionHandler.getOptionValue(EPSILON_P);
      minpts = optionHandler.getOptionValue(MINPTS_P);
      lambda = optionHandler.getOptionValue(LAMBDA_P);
    }
    catch (UnusedParameterException e) {
      System.err.println(optionHandler.usage(e.getMessage()));
    }
    return new String[0];
  }

  public void run(String[] args) {
    String[] remainingParameters = this.setParameters(args);
    this.run4C(remainingParameters);
  }

  public static void main(String[] args) {
    FourCWrapper wrapper = new FourCWrapper();
    wrapper.run(args);
  }
}
