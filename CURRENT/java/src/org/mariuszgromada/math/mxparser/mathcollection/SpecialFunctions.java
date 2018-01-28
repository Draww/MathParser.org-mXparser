/*
 * @(#)SpecialFunctions.java        4.2.0    2018-01-28
 *
 * You may use this software under the condition of "Simplified BSD License"
 *
 * Copyright 2010-2018 MARIUSZ GROMADA. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY <MARIUSZ GROMADA> ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of MARIUSZ GROMADA.
 *
 * Some parts of the SpecialFunctions class were adopted from Math.NET Numerics project
 * Copyright (c) 2002-2015 Math.NET   http://numerics.mathdotnet.com/
 * http://numerics.mathdotnet.com/License.html
 *
 * If you have any questions/bugs feel free to contact:
 *
 *     Mariusz Gromada
 *     mariuszgromada.org@gmail.com
 *     http://mathparser.org
 *     http://mathspace.pl
 *     http://janetsudoku.mariuszgromada.org
 *     http://github.com/mariuszgromada/MathParser.org-mXparser
 *     http://mariuszgromada.github.io/MathParser.org-mXparser
 *     http://mxparser.sourceforge.net
 *     http://bitbucket.org/mariuszgromada/mxparser
 *     http://mxparser.codeplex.com
 *     http://github.com/mariuszgromada/Janet-Sudoku
 *     http://janetsudoku.codeplex.com
 *     http://sourceforge.net/projects/janetsudoku
 *     http://bitbucket.org/mariuszgromada/janet-sudoku
 *     http://github.com/mariuszgromada/MathParser.org-mXparser
 *
 *                              Asked if he believes in one God, a mathematician answered:
 *                              "Yes, up to isomorphism."
 */
package org.mariuszgromada.math.mxparser.mathcollection;

/**
 * SpecialFunctions - special (non-elementary functions).
 *
 * @author         <b>Mariusz Gromada</b><br>
 *                 <a href="mailto:mariuszgromada.org@gmail.com">mariuszgromada.org@gmail.com</a><br>
 *                 <a href="http://mathspace.pl" target="_blank">MathSpace.pl</a><br>
 *                 <a href="http://mathparser.org" target="_blank">MathParser.org - mXparser project page</a><br>
 *                 <a href="http://github.com/mariuszgromada/MathParser.org-mXparser" target="_blank">mXparser on GitHub</a><br>
 *                 <a href="http://mxparser.sourceforge.net" target="_blank">mXparser on SourceForge</a><br>
 *                 <a href="http://bitbucket.org/mariuszgromada/mxparser" target="_blank">mXparser on Bitbucket</a><br>
 *                 <a href="http://mxparser.codeplex.com" target="_blank">mXparser on CodePlex</a><br>
 *                 <a href="http://janetsudoku.mariuszgromada.org" target="_blank">Janet Sudoku - project web page</a><br>
 *                 <a href="http://github.com/mariuszgromada/Janet-Sudoku" target="_blank">Janet Sudoku on GitHub</a><br>
 *                 <a href="http://janetsudoku.codeplex.com" target="_blank">Janet Sudoku on CodePlex</a><br>
 *                 <a href="http://sourceforge.net/projects/janetsudoku" target="_blank">Janet Sudoku on SourceForge</a><br>
 *                 <a href="http://bitbucket.org/mariuszgromada/janet-sudoku" target="_blank">Janet Sudoku on BitBucket</a><br>
 *
 * @version        4.2.0
 */
public final class SpecialFunctions {

	/**
	 * Exponential integral function Ei(x)
	 * @param x    Point at which function will be evaluated.
	 * @return Exponential integral function Ei(x)
	 */
	public static double exponentialIntegralEi(double x) {
		if (Double.isNaN(x))
			return Double.NaN;
		if (x < -5.0)
			return continuedFractionEi(x);
		if (x == 0.0)
			return -Double.MAX_VALUE;
		if (x < 6.8)
			return powerSeriesEi(x);
		if (x < 50.0)
			return argumentAdditionSeriesEi(x);
		return continuedFractionEi(x);
	}
	/**
	 * Constants for Exponential integral function Ei(x) calculation
	 */
	private static final double EI_DBL_EPSILON = Math.ulp(1.0);
	private static final double EI_EPSILON = 10.0 * EI_DBL_EPSILON;
	/**
	 * Supporting function
	 * while Exponential integral function Ei(x) calculation
	 */
	private static double continuedFractionEi(double x) {
		double Am1 = 1.0;
		double A0 = 0.0;
		double Bm1 = 0.0;
		double B0 = 1.0;
		double a = Math.exp(x);
		double b = -x + 1.0;
		double Ap1 = b * A0 + a * Am1;
		double Bp1 = b * B0 + a * Bm1;
		int j = 1;
		a = 1.0;
		while (Math.abs(Ap1 * B0 - A0 * Bp1) > EI_EPSILON * Math.abs(A0 * Bp1)) {
			if (Math.abs(Bp1) > 1.0) {
				Am1 = A0 / Bp1;
				A0 = Ap1 / Bp1;
				Bm1 = B0 / Bp1;
				B0 = 1.0;
			} else {
				Am1 = A0;
				A0 = Ap1;
				Bm1 = B0;
				B0 = Bp1;
			}
			a = -j * j;
			b += 2.0;
			Ap1 = b * A0 + a * Am1;
			Bp1 = b * B0 + a * Bm1;
			j += 1;
		}
		return (-Ap1 / Bp1);
	}
	/**
	 * Supporting function
	 * while Exponential integral function Ei(x) calculation
	 */
	private static double powerSeriesEi(double x) {
		double xn = -x;
		double Sn = -x;
		double Sm1 = 0.0;
		double hsum = 1.0;
		final double g = MathConstants.EULER_MASCHERONI;
		double y = 1.0;
		double factorial = 1.0;
		if (x == 0.0)
			return -Double.MAX_VALUE;
		while (Math.abs(Sn - Sm1) > EI_EPSILON * Math.abs(Sm1)) {
			Sm1 = Sn;
			y += 1.0;
			xn *= (-x);
			factorial *= y;
			hsum += (1.0 / y);
			Sn += hsum * xn / factorial;
		}
		return (g + Math.log(Math.abs(x)) - Math.exp(x) * Sn);
	}

	/**
	 * Supporting function
	 * while Exponential integral function Ei(x) calculation
	 */
	private static double argumentAdditionSeriesEi(double x) {
		final int k = (int) (x + 0.5);
		int j = 0;
		final double xx = k;
		final double dx = x - xx;
		double xxj = xx;
		final double edx = Math.exp(dx);
		double Sm = 1.0;
		double Sn = (edx - 1.0) / xxj;
		double term = Double.MAX_VALUE;
		double factorial = 1.0;
		double dxj = 1.0;
		while (Math.abs(term) > EI_EPSILON * Math.abs(Sn)) {
			j++;
			factorial *= j;
			xxj *= xx;
			dxj *= (-dx);
			Sm += (dxj / factorial);
			term = (factorial * (edx * Sm - 1.0)) / xxj;
			Sn += term;
		}
		return Coefficients.EI[k - 7] + Sn * Math.exp(xx);
	}
	/**
	 * Logarithmic integral function li(x)
	 * @param x   Point at which function will be evaluated.
	 * @return Logarithmic integral function li(x)
	 */
	public static final double logarithmicIntegralLi(double x) {
		if (Double.isNaN(x))
			return Double.NaN;
		if (x < 0)
			return Double.NaN;
		if (x == 0)
			return 0;
		if (x == 2)
			return MathConstants.LI2;
		return exponentialIntegralEi( MathFunctions.ln(x) );
	}
	/**
	 * Offset logarithmic integral function Li(x)
	 * @param x   Point at which function will be evaluated.
	 * @return Offset logarithmic integral function Li(x)
	 */
	public static final double offsetLogarithmicIntegralLi(double x) {
		if (Double.isNaN(x))
			return Double.NaN;
		if (x < 0)
			return Double.NaN;
		if (x == 0)
			return -MathConstants.LI2;
		return logarithmicIntegralLi(x) - MathConstants.LI2;
	}
	/**
	 * Calculates the error function
	 * @param x   Point at which function will be evaluated.
	 * @return    Error function erf(x)
	 */
	public static final double erf(double x) {
		if (Double.isNaN(x)) return Double.NaN;
		if (x == 0) return 0;
		if (x == Double.POSITIVE_INFINITY) return 1.0;
		if (x == Double.NEGATIVE_INFINITY) return -1.0;
		return erfImp(x, false);
	}
	/**
	 * Calculates the complementary error function.
	 * @param x   Point at which function will be evaluated.
	 * @return    Complementary error function erfc(x)
	 */
	public static final double erfc(double x) {
		if (Double.isNaN(x)) return Double.NaN;
		if (x == 0) return 1;
		if (x == Double.POSITIVE_INFINITY) return 0.0;
		if (x == Double.NEGATIVE_INFINITY) return 2.0;
		return erfImp(x, true);
	}
	/**
	 * Calculates the inverse error function evaluated at x.
	 * @param x   Point at which function will be evaluated.
	 * @return    Inverse error function erfInv(x)
	 */
	public static final double erfInv(double x) {
		if (x == 0.0) return 0;
		if (x >= 1.0) return Double.POSITIVE_INFINITY;
		if (x <= -1.0) return Double.NEGATIVE_INFINITY;
		double p, q, s;
		if (x < 0) {
			p = -x;
			q = 1 - p;
			s = -1;
		} else {
			p = x;
			q = 1 - x;
			s = 1;
		}
		return erfInvImpl(p, q, s);
	}
	/**
	 * Calculates the inverse error function evaluated at x.
	 * @param x
	 * @param invert
	 * @return
	 */
	private static final double erfImp(double z, boolean invert) {
    	if (z < 0) {
        	if (!invert) return -erfImp(-z, false);
        	if (z < -0.5) return 2 - erfImp(-z, true);
            return 1 + erfImp(-z, false);
        }
    	double result;
    	if (z < 0.5) {
    		if (z < 1e-10) result = (z*1.125) + (z*0.003379167095512573896158903121545171688);
    		else result = (z*1.125) + (z*Evaluate.polynomial(z, Coefficients.erfImpAn) / Evaluate.polynomial(z, Coefficients.erfImpAd));
    	}
    	else if ((z < 110) || ((z < 110) && invert)) {
    		invert = !invert;
    		double r, b;
    		if(z < 0.75) {
    			r = Evaluate.polynomial(z - 0.5, Coefficients.erfImpBn) / Evaluate.polynomial(z - 0.5, Coefficients.erfImpBd);
    			b = 0.3440242112F;
    		}
    		else if (z < 1.25) {
    			r = Evaluate.polynomial(z - 0.75, Coefficients.erfImpCn) / Evaluate.polynomial(z - 0.75, Coefficients.erfImpCd);
    			b = 0.419990927F;
    		} else if (z < 2.25) {
    			r = Evaluate.polynomial(z - 1.25, Coefficients.erfImpDn) / Evaluate.polynomial(z - 1.25, Coefficients.erfImpDd);
    			b = 0.4898625016F;
    		} else if (z < 3.5) {
    			r = Evaluate.polynomial(z - 2.25, Coefficients.erfImpEn) / Evaluate.polynomial(z - 2.25, Coefficients.erfImpEd);
    			b = 0.5317370892F;
    		} else if (z < 5.25) {
    			r = Evaluate.polynomial(z - 3.5, Coefficients.erfImpFn) / Evaluate.polynomial(z - 3.5, Coefficients.erfImpFd);
    			b = 0.5489973426F;
    		} else if (z < 8) {
    			r = Evaluate.polynomial(z - 5.25, Coefficients.erfImpGn) / Evaluate.polynomial(z - 5.25, Coefficients.erfImpGd);
    			b = 0.5571740866F;
    		} else if (z < 11.5) {
    			r = Evaluate.polynomial(z - 8, Coefficients.erfImpHn) / Evaluate.polynomial(z - 8, Coefficients.erfImpHd);
    			b = 0.5609807968F;
    		} else if (z < 17) {
    			r = Evaluate.polynomial(z - 11.5, Coefficients.erfImpIn) / Evaluate.polynomial(z - 11.5, Coefficients.erfImpId);
    			b = 0.5626493692F;
    		} else if (z < 24) {
    			r = Evaluate.polynomial(z - 17, Coefficients.erfImpJn) / Evaluate.polynomial(z - 17, Coefficients.erfImpJd);
    			b = 0.5634598136F;
    		} else if (z < 38) {
    			r = Evaluate.polynomial(z - 24, Coefficients.erfImpKn) / Evaluate.polynomial(z - 24, Coefficients.erfImpKd);
    			b = 0.5638477802F;
    		} else if (z < 60) {
    			r = Evaluate.polynomial(z - 38, Coefficients.erfImpLn) / Evaluate.polynomial(z - 38, Coefficients.erfImpLd);
    			b = 0.5640528202F;
    		} else if (z < 85) {
    			r = Evaluate.polynomial(z - 60, Coefficients.erfImpMn) / Evaluate.polynomial(z - 60, Coefficients.erfImpMd);
    			b = 0.5641309023F;
    		} else {
    			r = Evaluate.polynomial(z - 85, Coefficients.erfImpNn) / Evaluate.polynomial(z - 85, Coefficients.erfImpNd);
    			b = 0.5641584396F;
    		}
    		double g = MathFunctions.exp(-z*z)/z;
    		result = (g*b) + (g*r);
    	} else {
    		result = 0;
    		invert = !invert;
    	}
        if (invert) result = 1 - result;
        return result;
	}
	/**
	 * Calculates the complementary inverse error function evaluated at x.
	 * @param z   Point at which function will be evaluated.
	 * @return    Inverse of complementary inverse error function erfcInv(x)
	 */
	public static final double erfcInv(double z) {
		if (z <= 0.0) return Double.POSITIVE_INFINITY;
        if (z >= 2.0) return Double.NEGATIVE_INFINITY;
        double p, q, s;
        if (z > 1) {
        	q = 2 - z;
        	p = 1 - q;
        	s = -1;
        } else {
        	p = 1 - z;
        	q = z;
        	s = 1;
        }
        return erfInvImpl(p, q, s);
	}
	/**
	 * The implementation of the inverse error function.
	 * @param p
	 * @param q
	 * @param s
	 * @return
	 */
	private static final double erfInvImpl(double p, double q, double s) {
    	double result;
    	if (p <= 0.5) {
    		final float y = 0.0891314744949340820313f;
    		double g = p*(p + 10);
    		double r = Evaluate.polynomial(p, Coefficients.ervInvImpAn) / Evaluate.polynomial(p, Coefficients.ervInvImpAd);
    		result = (g*y) + (g*r);
    	} else if (q >= 0.25) {
    		final float y = 2.249481201171875f;
    		double g = MathFunctions.sqrt(-2 * MathFunctions.ln(q));
    		double xs = q - 0.25;
    		double r = Evaluate.polynomial(xs, Coefficients.ervInvImpBn) / Evaluate.polynomial(xs, Coefficients.ervInvImpBd);
    		result = g/(y + r);
    	} else {
    		double x = MathFunctions.sqrt(-MathFunctions.ln(q));
    		if (x < 3) {
    			final float y = 0.807220458984375f;
                double xs = x - 1.125;
                double r = Evaluate.polynomial(xs, Coefficients.ervInvImpCn) / Evaluate.polynomial(xs, Coefficients.ervInvImpCd);
                result = (y*x) + (r*x);
    		} else if (x < 6) {
    			final float y = 0.93995571136474609375f;
    			double xs = x - 3;
    			double r = Evaluate.polynomial(xs, Coefficients.ervInvImpDn) / Evaluate.polynomial(xs, Coefficients.ervInvImpDd);
    			result = (y*x) + (r*x);
    		} else if (x < 18) {
    			final float y = 0.98362827301025390625f;
    			double xs = x - 6;
    			double r = Evaluate.polynomial(xs, Coefficients.ervInvImpEn) / Evaluate.polynomial(xs, Coefficients.ervInvImpEd);
    			result = (y*x) + (r*x);
    		} else if (x < 44) {
    			final float y = 0.99714565277099609375f;
    			double xs = x - 18;
    			double r = Evaluate.polynomial(xs, Coefficients.ervInvImpFn) / Evaluate.polynomial(xs, Coefficients.ervInvImpFd);
    			result = (y*x) + (r*x);
            } else {
            	final float y = 0.99941349029541015625f;
            	double xs = x - 44;
            	double r = Evaluate.polynomial(xs, Coefficients.ervInvImpGn) / Evaluate.polynomial(xs, Coefficients.ervInvImpGd);
            	result = (y*x) + (r*x);
            }
    	}
    	return s*result;
	}
	/**
	 * Gamma function for the integers
	 * @param n Integer number
	 * @return  Returns Gamma function for the integers.
	 */
	private static final double gammaInt(long n) {
		if (n == 0) return MathConstants.EULER_MASCHERONI;
		if (n == 1) return 1;
		if (n == 2) return 1;
		if (n == 3) return 1.0*2.0;
		if (n == 4) return 1.0*2.0*3.0;
		if (n == 5) return 1.0*2.0*3.0*4.0;
		if (n == 6) return 1.0*2.0*3.0*4.0*5.0;
		if (n == 7) return 1.0*2.0*3.0*4.0*5.0*6.0;
		if (n == 8) return 1.0*2.0*3.0*4.0*5.0*6.0*7.0;
		if (n == 9) return 1.0*2.0*3.0*4.0*5.0*6.0*7.0*8.0;
		if (n == 10) return 1.0*2.0*3.0*4.0*5.0*6.0*7.0*8.0*9.0;
		if (n >= 11) return MathFunctions.factorial(n-1);
		//if (n == -1) return MathConstants.EULER_MASCHERONI - 1;
		if (n <= -1) {
			long r = -n;
			double factr = MathFunctions.factorial(r);
			double sign = -1;
			if (r % 2 == 0) sign = 1;
			return sign / (r * factr) - (1.0 / r) * gammaInt(n + 1);
		}
		return Double.NaN;
	}
	/**
	 * Real valued Gamma function
	 *
	 * @param x
	 * @return  Returns gamma function value.
	 */
	public static final double gamma(double x) {
		if (Double.isNaN(x)) return Double.NaN;
		if (x == Double.POSITIVE_INFINITY) return Double.POSITIVE_INFINITY;
		if (x == Double.NEGATIVE_INFINITY) return Double.NaN;
		double xabs = MathFunctions.abs(x);
		double xint = Math.round(xabs);
		if ( MathFunctions.abs(xabs-xint) <= BinaryRelations.DEFAULT_COMPARISON_EPSILON ) {
			long n = (long)xint;
			if (x < 0) n = -n;
			return gammaInt(n);
		}
		return lanchosGamma(x);
	}
	/**
	 * Gamma function implementation based on
	 * Lanchos approximation algorithm
	 *
	 * @param x    Function parameter
	 * @return     Gamma function value (Lanchos approx).
	 */
	public static final double lanchosGamma(double x) {
		if (Double.isNaN(x)) return Double.NaN;

		double xabs = MathFunctions.abs(x);
		double xint = Math.round(xabs);
		if (x > BinaryRelations.DEFAULT_COMPARISON_EPSILON) {
			if ( MathFunctions.abs(xabs-xint) <= BinaryRelations.DEFAULT_COMPARISON_EPSILON )
				return MathFunctions.factorial(xint-1);
		} else if (x < -BinaryRelations.DEFAULT_COMPARISON_EPSILON) {
			if ( MathFunctions.abs(xabs-xint) <= BinaryRelations.DEFAULT_COMPARISON_EPSILON )
				return Double.NaN;
		} else return Double.NaN;
		if(x < 0.5) return MathConstants.PI / (MathFunctions.sin(MathConstants.PI * x) * lanchosGamma(1-x));
		int g = 7;
		x -= 1;
		double a = Coefficients.lanchosGamma[0];
		double t = x+g+0.5;
		for(int i = 1; i < Coefficients.lanchosGamma.length; i++){
			a += Coefficients.lanchosGamma[i] / (x+i);
		}
		return MathFunctions.sqrt(2*MathConstants.PI) * MathFunctions.power(t, x+0.5) * MathFunctions.exp(-t) * a;
	}
	/**
	 * Real valued log gamma function.
	 * @param x
	 * @return  Returns log value from gamma function.
	 */
	public static double logGamma(double x) {
		if (Double.isNaN(x)) return Double.NaN;
		if (x == Double.POSITIVE_INFINITY) return Double.POSITIVE_INFINITY;
		if (x == Double.NEGATIVE_INFINITY) return Double.NaN;
		if (MathFunctions.isInteger(x)) {
			if (x >= 0)
				return MathFunctions.ln( Math.abs( gammaInt( (long)(Math.round(x) ) ) ) );
			else
				return MathFunctions.ln( Math.abs( gammaInt( -(long)(Math.round(-x) ) ) ) );
		}
		double p, q, w, z;
		if (x < -34.0) {
			q = -x;
			w = logGamma(q);
			p = Math.floor(q);
			if (p == q) return Double.NaN;
			z = q - p;
			if (z > 0.5) {
				p += 1.0;
				z = p - q;
			}
			z = q * Math.sin( Math.PI * z );
			if (z == 0.0) return Double.NaN;
			z = MathConstants.LNPI - Math.log(z) - w;
			return z;
		}
		if (x < 13.0) {
			z = 1.0;
			while (x >= 3.0) {
				x -= 1.0;
				z *= x;
			}
			while (x < 2.0) {
				if( x == 0.0 ) return Double.NaN;
				z /= x;
				x += 1.0;
			}
			if (z < 0.0) z = -z;
			if (x == 2.0) return Math.log(z);
			x -= 2.0;
			p = x * Evaluate.polevl( x, Coefficients.logGammaB, 5 ) / Evaluate.p1evl( x, Coefficients.logGammaC, 6);
			return Math.log(z) + p;
		}
		if (x > 2.556348e305) return Double.NaN;
		q = (x - 0.5) * Math.log(x) - x + 0.91893853320467274178;
		if (x > 1.0e8) return q;
		p = 1.0/(x*x);
		if (x >= 1000.0)
			q += (( 7.9365079365079365079365e-4 * p - 2.7777777777777777777778e-3 ) * p + 0.0833333333333333333333 ) / x;
		else
			q += Evaluate.polevl( p, Coefficients.logGammaA, 4 ) / x;
		return q;
	}
	/**
	 * Signum from the real valued gamma function.
	 * @param x
	 * @return  Returns signum of the gamma(x)
	 */
	public static final double sgnGamma(double x) {
		if (Double.isNaN(x)) return Double.NaN;
		if (x == Double.POSITIVE_INFINITY) return 1;
		if (x == Double.NEGATIVE_INFINITY) return Double.NaN;
		if (x > 0) return 1;
		if (MathFunctions.isInteger(x)) return MathFunctions.sgn( gammaInt( -(long)(Math.round(-x) ) ) );
		x = -x;
		double fx = Math.floor(x);
		double div2remainder = Math.floor(fx % 2);
		if (div2remainder == 0) return -1;
		else return 1;
	}
	/**
	 * Regularized lower gamma function 'P'
	 * @param s
	 * @param x
	 * @return Value of the regularized lower gamma function 'P'.
	 */
	public static final double regularizedGammaLowerP(double s, double x) {
		if (Double.isNaN(x)) return Double.NaN;
		if (Double.isNaN(s)) return Double.NaN;
		if (MathFunctions.almostEqual(x, 0)) return 0;
		if (MathFunctions.almostEqual(s, 0))
			return 1 + SpecialFunctions.exponentialIntegralEi(-x) / MathConstants.EULER_MASCHERONI;

		if (MathFunctions.almostEqual(s, 1))
			return 1 - Math.exp(-x);

		if (x < 0) return Double.NaN;

		if (s < 0)
			return regularizedGammaLowerP(s + 1, x) + ( Math.pow(x,  s) * Math.exp(-x) ) / ( s * gamma(s) );

		final double epsilon = 0.000000000000001;
		final double bigNumber = 4503599627370496.0;
		final double bigNumberInverse = 2.22044604925031308085e-16;

		double ax = (s * Math.log(x)) - x - logGamma(s);
		if (ax < -709.78271289338399) {
			return 1;
		}

		if (x <= 1 || x <= s) {
			double r2 = s;
			double c2 = 1;
			double ans2 = 1;
			do {
				r2 = r2 + 1;
				c2 = c2 * x / r2;
				ans2 += c2;
			} while ((c2 / ans2) > epsilon);
			return Math.exp(ax) * ans2 / s;
        }

		int c = 0;
		double y = 1 - s;
		double z = x + y + 1;

		double p3 = 1;
		double q3 = x;
		double p2 = x + 1;
		double q2 = z * x;
		double ans = p2 / q2;

		double error;

        do {
        	c++;
        	y += 1;
        	z += 2;
        	double yc = y * c;

        	double p = (p2 * z) - (p3 * yc);
        	double q = (q2 * z) - (q3 * yc);

        	if (q != 0) {
        		double nextans = p / q;
        		error = Math.abs((ans - nextans) / nextans);
        		ans = nextans;
        	} else {
        		// zero div, skip
        		error = 1;
        	}

        	// shift
        	p3 = p2;
        	p2 = p;
        	q3 = q2;
        	q2 = q;

        	// normalize fraction when the numerator becomes large
        	if (Math.abs(p) > bigNumber) {
        		p3 *= bigNumberInverse;
        		p2 *= bigNumberInverse;
        		q3 *= bigNumberInverse;
        		q2 *= bigNumberInverse;
        	}
        } while (error > epsilon);

        return 1 - (Math.exp(ax) * ans);
  	}
	/**
	 * Incomplete lower gamma function
	 * @param s
	 * @param x
	 * @return Value of the incomplete lower gamma function.
	 */
	public static final double incompleteGammaLower(double s, double x) {
		return gamma(s) * regularizedGammaLowerP(s, x);
	}
	/**
	 * Regularized upper gamma function 'Q'
	 * @param s
	 * @param x
	 * @return Value of the regularized upper gamma function 'Q'.
	 */
	public static final double regularizedGammaUpperQ(double s, double x) {
		if (Double.isNaN(x)) return Double.NaN;
		if (Double.isNaN(s)) return Double.NaN;
		if (MathFunctions.almostEqual(x, 0)) return 1;

		if (MathFunctions.almostEqual(s, 0))
			return -SpecialFunctions.exponentialIntegralEi(-x) / MathConstants.EULER_MASCHERONI;

		if (MathFunctions.almostEqual(s, 1))
			return Math.exp(-x);

		if (x < 0) return Double.NaN;

		if (s < 0)
			return regularizedGammaUpperQ(s + 1, x) - ( Math.pow(x,  s) * Math.exp(-x) ) / ( s * gamma(s) );

        double ax = s * Math.log(x) - x - logGamma(s);
        if (ax < -709.78271289338399) {
        	return 0;
        }
		double t;
		final double igammaepsilon = 0.000000000000001;
		final double igammabignumber = 4503599627370496.0;
		final double igammabignumberinv = 2.22044604925031308085 * 0.0000000000000001;

        ax = Math.exp(ax);
        double y = 1 - s;
        double z = x + y + 1;
        double c = 0;
        double pkm2 = 1;
        double qkm2 = x;
        double pkm1 = x + 1;
        double qkm1 = z * x;
        double ans = pkm1 / qkm1;
        do {
        	c = c + 1;
        	y = y + 1;
        	z = z + 2;
        	double yc = y * c;
        	double pk = pkm1 * z - pkm2 * yc;
        	double qk = qkm1 * z - qkm2 * yc;
        	if (qk != 0) {
        		double r = pk / qk;
        		t = Math.abs((ans - r) / r);
        		ans = r;
        	} else {
        		t = 1;
        	}

        	pkm2 = pkm1;
        	pkm1 = pk;
        	qkm2 = qkm1;
        	qkm1 = qk;

        	if (Math.abs(pk) > igammabignumber) {
        		pkm2 = pkm2 * igammabignumberinv;
        		pkm1 = pkm1 * igammabignumberinv;
        		qkm2 = qkm2 * igammabignumberinv;
        		qkm1 = qkm1 * igammabignumberinv;
        	}
        } while (t > igammaepsilon);
        return ans * ax;
	}
	/**
	 * Incomplete upper gamma function
	 * @param s
	 * @param x
	 * @return Value of the incomplete upper gamma function.
	 */
	public static final double incompleteGammaUpper(double s, double x) {
		return gamma(s) * regularizedGammaUpperQ(s, x);
	}
	/**
	 * Digamma function as the logarithmic derivative of the Gamma special function
	 * @param x
	 * @return Approximated value of the digamma function.
	 */
	public static final double diGamma(double x) {
		final double c = 12.0;
		final double d1 = -0.57721566490153286;
		final double d2 = 1.6449340668482264365;
		final double s = 1e-6;
		final double s3 = 1.0/12.0;
		final double s4 = 1.0/120.0;
		final double s5 = 1.0/252.0;
		final double s6 = 1.0/240.0;
		final double s7 = 1.0/132.0;

		if (Double.isNaN(x)) return Double.NaN;
		if (x == Double.NEGATIVE_INFINITY) return Double.NaN;
		if (x <= 0)
			if (MathFunctions.isInteger(x))
				return Double.NaN;

		// Use inversion formula for negative numbers.
		if (x < 0) return diGamma(1.0 - x) + (MathConstants.PI/Math.tan(-Math.PI*x));

		if (x <= s) return d1 - (1/x) + (d2*x);

		double result = 0;
		while (x < c) {
			result -= 1/x;
			x++;
		}

		if (x >= c) {
			double r = 1/x;
			result += Math.log(x) - (0.5*r);
			r *= r;
			result -= r*(s3 - (r*(s4 - (r*(s5 - (r*(s6 - (r*s7))))))));
		}

		return result;
	}
	/*
	 * halleyIteration epsilon
	 */
	private static final double GSL_DBL_EPSILON = 2.2204460492503131e-16;
	/**
	 * Halley iteration used in Lambert-W approximation
	 * @param x         Point at which Halley iteration will be calculated
	 * @param wInitial  Starting point
	 * @param maxIter   Maximum number of iteration
	 * @return          Halley iteration value if succesfull, otherwise Double.NaN
	 */
	private static final double halleyIteration(double x, double wInitial, int maxIter) {
		double w = wInitial;
		for (int i = 0; i < maxIter; i++) {
			double tol;
			double e = Math.exp(w);
			double p = w + 1.0;
			double t = w * e - x;
			if (w > 0) t = (t / p) / e;
			else t /= e * p - 0.5 * (p + 1.0) * t / p;
		    w -= t;
		    tol = GSL_DBL_EPSILON * Math.max(Math.abs(w), 1.0 / (Math.abs(p) * e));
		    if (Math.abs(t) < tol) return w;
		}
		return Double.NaN;
	}
	/**
	 * Private method used in Lambert-W approximation - near zero
	 * @param r
	 * @return Ner zero approximation
	 */
	private static final double seriesEval(double r) {
		double t8 = Coefficients.lambertWqNearZero[8] + r * (Coefficients.lambertWqNearZero[9] + r * (Coefficients.lambertWqNearZero[10] + r * Coefficients.lambertWqNearZero[11]));
		double t5 = Coefficients.lambertWqNearZero[5] + r * (Coefficients.lambertWqNearZero[6] + r * (Coefficients.lambertWqNearZero[7] + r * t8));
		double t1 = Coefficients.lambertWqNearZero[1] + r * (Coefficients.lambertWqNearZero[2] + r * (Coefficients.lambertWqNearZero[3] + r * (Coefficients.lambertWqNearZero[4] + r * t5)));
		return Coefficients.lambertWqNearZero[0] + r * t1;
	}
	/**
	 * W0 - Principal branch of Lambert-W function
	 * @param x
	 * @return Approximation of principal branch of Lambert-W function
	 */
	private static final double lambertW0(double x) {
		if (Math.abs(x) <= BinaryRelations.DEFAULT_COMPARISON_EPSILON) return 0;
		if (Math.abs(x + MathConstants.EXP_MINUS_1) <= BinaryRelations.DEFAULT_COMPARISON_EPSILON) return -1;
		if (Math.abs(x - 1) <= BinaryRelations.DEFAULT_COMPARISON_EPSILON) return MathConstants.OMEGA;
		if (Math.abs(x - MathConstants.E) <= BinaryRelations.DEFAULT_COMPARISON_EPSILON) return 1;
		if (Math.abs(x + MathConstants.LN_SQRT2) <= BinaryRelations.DEFAULT_COMPARISON_EPSILON) return -2 * MathConstants.LN_SQRT2;
		if (x < -MathConstants.EXP_MINUS_1) return Double.NaN;
		double q = x + MathConstants.EXP_MINUS_1;
		if (q < 1.0e-03) return seriesEval(Math.sqrt(q));
		final int MAX_ITER = 100;
		double w;
		if (x < 1) {
			final double p = Math.sqrt(2.0 * MathConstants.E * q);
			w = -1.0 + p * (1.0 + p * (-1.0 / 3.0 + p * 11.0 / 72.0));
		}
		else {
			w = Math.log(x);
			if (x > 3.0) w -= Math.log(w);
		}
		return halleyIteration(x, w, MAX_ITER);
	}
	/**
	 * Minus 1 branch of Lambert-W function
	 * Analytical approximations for real values of the Lambert W-function - D.A. Barry
	 * Mathematics and Computers in Simulation 53 (2000) 95–103
	 * @param x
	 * @return Approxmiation of minus 1 branch of Lambert-W function
	 */
	private static final double lambertW1(double x) {
		if (x >= -BinaryRelations.DEFAULT_COMPARISON_EPSILON) return Double.NaN;
		if (x < -MathConstants.EXP_MINUS_1) return Double.NaN;
		if (Math.abs(x + MathConstants.EXP_MINUS_1) <= BinaryRelations.DEFAULT_COMPARISON_EPSILON) return -1;
		/*
		 * Analytical approximations for real values of the Lambert W-function - D.A. Barry
		 * Mathematics and Computers in Simulation 53 (2000) 95–103
		 */
		double M1 = 0.3361;
		double M2 = -0.0042;
		double M3 = -0.0201;
		double s = -1 - MathFunctions.ln(-x);
		return -1.0 - s - (2.0/M1) * ( 1.0 - 1.0 / ( 1.0 + ( (M1 * Math.sqrt(s/2.0)) / (1.0 + M2 * s * Math.exp(M3 * Math.sqrt(s)) ) ) ) );
	}
	/**
	 * Real-valued Lambert-W function approximation.
	 * @param x      Point at which function will be approximated
	 * @param branch Branch id, 0 for principal branch, -1 for the other branch
	 * @return       Principal branch for x greater or equal than -1/e, otherwise Double.NaN.
	 *               Minus 1 branch for x greater or equal than -1/e and lower than 0, otherwise Double.NaN.
	 */
	public static final double lambertW(double x, double branch) {
		if (Double.isNaN(x)) return Double.NaN;
		if (Double.isNaN(branch)) return Double.NaN;
		if (Math.abs(branch) <= BinaryRelations.DEFAULT_COMPARISON_EPSILON) return lambertW0(x);
		if (Math.abs(branch + 1) <= BinaryRelations.DEFAULT_COMPARISON_EPSILON) return lambertW1(x);
		return Double.NaN;
	}
}