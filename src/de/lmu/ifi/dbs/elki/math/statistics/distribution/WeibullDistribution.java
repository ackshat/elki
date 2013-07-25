package de.lmu.ifi.dbs.elki.math.statistics.distribution;

/*
 This file is part of ELKI:
 Environment for Developing KDD-Applications Supported by Index-Structures

 Copyright (C) 2013
 Ludwig-Maximilians-Universität München
 Lehr- und Forschungseinheit für Datenbanksysteme
 ELKI Development Team

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU Affero General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Affero General Public License for more details.

 You should have received a copy of the GNU Affero General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * INCOMPLETE implementation of the weibull distribution.
 * 
 * TODO: continue implementing, CDF, invcdf and nextRandom are missing
 * 
 * @author Erich Schubert
 */
public class WeibullDistribution implements Distribution {
  /**
   * Shift offset.
   */
  double theta = 0.0;

  /**
   * Shape parameter k.
   */
  double k;

  /**
   * Lambda parameter.
   */
  double lambda;

  /**
   * Constructor.
   * 
   * @param k Shape parameter
   * @param lambda Scale parameter
   */
  public WeibullDistribution(double k, double lambda) {
    super();
    this.k = k;
    this.lambda = lambda;
    this.theta = 0.0;
  }

  /**
   * Constructor.
   * 
   * @param k Shape parameter
   * @param lambda Scale parameter
   * @param theta Shift offset parameter
   */
  public WeibullDistribution(double k, double lambda, double theta) {
    super();
    this.k = k;
    this.lambda = lambda;
    this.theta = theta;
  }

  @Override
  public double pdf(double x) {
    return pdf(x, k, lambda, theta);
  }

  /**
   * PDF of Weibull distribution
   * 
   * @param x Value
   * @param k Shape parameter
   * @param lambda Scale parameter
   * @param theta Shift offset parameter
   * @return PDF at position x.
   */
  public static double pdf(double x, double k, double lambda, double theta) {
    if (x > theta) {
      double xl = (x - theta) / lambda;
      return k / lambda * Math.pow(xl, k - 1) * Math.exp(-Math.pow(xl, k));
    } else {
      return 0.;
    }
  }

  /**
   * CDF of Weibull distribution
   * 
   * @param val Value
   * @param k Shape parameter
   * @param lambda Scale parameter
   * @param theta Shift offset parameter
   * @return CDF at position x.
   */
  public static double cdf(double val, double k, double lambda, double theta) {
    if (val > theta) {
      return 1.0 - Math.exp(-Math.pow((val - theta) / lambda, k));
    } else {
      return 0.0;
    }
  }

  @Override
  public double cdf(double val) {
    return cdf(val, k, lambda, theta);
  }

  /**
   * Quantile function of Weibull distribution
   * 
   * @param val Value
   * @param k Shape parameter
   * @param lambda Scale parameter
   * @param theta Shift offset parameter
   * @return Quantile function at position x.
   */
  public static double quantile(double val, double k, double lambda, double theta) {
    if (val < 0.0 || val > 1.0) {
      return Double.NaN;
    } else if (val == 0) {
      return 0.0;
    } else if (val == 1) {
      return Double.POSITIVE_INFINITY;
    } else {
      return theta + lambda * Math.pow(-Math.log(1.0 - val), 1.0 / k);
    }
  }

  @Override
  public double quantile(double val) {
    return quantile(val, k, lambda, theta);
  }
}