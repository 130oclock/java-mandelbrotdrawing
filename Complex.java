package graphics2D;

public class Complex {
	private final double re, im;
	
	public Complex(double real, double imaginary) {
		re = real;
		im = imaginary;
	}
	
	public double real() {
		return re;
	}
	
	public double imaginary() {
		return im;
	}
	
	// modulus
	public double mod() {
		if (re != 0 || im != 0) return Math.hypot(re, im);
		else return 0d;
	}
	
	// argument
	public double arg() {
		return Math.atan2(im, re);
	}
	
	public Complex conj() {
		Complex a = this;
		return new Complex (a.re, -a.im);
	}
	
	// addition
	public Complex add(Complex z) {
		Complex a = this;
		double real = a.re + z.re;
		double imaginary = a.im + z.im;
		return new Complex(real, imaginary);
	}
	
	// addition by a real number
		public Complex add(double z) {
			Complex a = this;
			double real = a.re + z;
			double imaginary = a.im;
			return new Complex(real, imaginary);
		}
	
	// subtraction
	public Complex sub(Complex z) {
		Complex a = this;
		double real = a.re - z.re;
		double imaginary = a.im - z.im;
		return new Complex(real, imaginary);
	}
	
	// subtraction by real number
		public Complex sub(double z) {
			Complex a = this;
			double real = a.re - z;
			double imaginary = a.im;
			return new Complex(real, imaginary);
		}
	
	// multiplication
	public Complex mult(Complex z) {
		Complex a = this;
		double real = a.re * z.re - a.im * z.im;
		double imaginary = a.re * z.im + a.im * z.re;
		
		return new Complex(real, imaginary);
	}
	
	// multiplication by real number
	public Complex mult(double z) {
		Complex a = this;
		double real = a.re * z;
		double imaginary = a.im * z;
		
		return new Complex(real, imaginary);
	}
	
	// division
	public Complex divi(Complex z) {
		Complex a = this;
		double zreal = (z.re * z.re + z.im * z.im);
		
		double real = (a.re * z.re + a.im * z.im) / zreal;
		double imaginary = (a.im * z.re - a.re * z.im) / zreal;
		
		return new Complex(real, imaginary);
	}
	
	// division by real number
	public Complex divi(double z) {
		Complex a = this;
		double real = a.re / z;
		double imaginary = a.im / z;
		
		return new Complex(real, imaginary);
	}
	
	// signum
	public Complex sgn() {
		Complex a = this;
		return a.divi(a.mod());
	}
	
	// square
	public Complex sqr() {
		Complex a = this;
		double real = a.re * a.re - a.im * a.im;
		double imaginary = 2 * a.re * a.im;
		
		return new Complex(real, imaginary);
	}
	
	// square root
	public Complex sqrt() {
		Complex a = this;
		double r = Math.sqrt(a.mod());
		double theta = a.arg()/2;
		double real = r * Math.cos(theta);
		double imaginary = r * Math.sin(theta);
		
		return new Complex(real, imaginary);
	}
	
	// cubic
	public Complex cube() {
		Complex a = this;
		
		
		return a.sqr().mult(a);
	}
	
	// qurtic
		public Complex quar() {
			Complex a = this;
			
			
			return a.sqr().sqr();
		}
	
	// real cosh
	private double cosh(double theta) {
        return (Math.exp(theta) + Math.exp(-theta)) / 2;
    }
	
	// real sinh
	private double sinh(double theta) {
        return (Math.exp(theta) - Math.exp(-theta)) / 2;
    }
	
	// sine of this complex number
	public Complex sin() {
		Complex a = this;
		double real = a.cosh(a.im) * Math.sin(a.re);
		double imaginary = a.sinh(a.im) * Math.cos(a.re);
		
		return new Complex(real, imaginary);
	}
	
	// cosine of this complex number
	public Complex cos() {
		Complex a = this;
		double real = a.cosh(a.im) * Math.cos(a.re);
		double imaginary = -(a.sinh(a.im) * Math.sin(a.re));
		
		return new Complex(real, imaginary);
	}
	
	public String toString() {
        if (re != 0 && im > 0) {
            return re + " + " + im +"i";
        }
        if (re != 0 && im < 0) {
            return re + " - " + (-im) + "i";
        }
        if (im == 0) {
            return String.valueOf(re);
        }
        if (re == 0) {
            return im + "i";
        }
        // shouldn't get here (unless Inf or NaN)
        return re + " + i*" + im;
        
    } 
}
