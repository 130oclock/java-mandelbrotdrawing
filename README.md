# java-mandelbrotdrawing
This is a simple mandelbrot set image renderer written in java using Eclipse IDE. 

The default window created will be the mandelbrot set in its entirety, drawn from (-2,-2) to (2,2). You can change the initial image setting by changing the initial values of the variables `x0, x1, y0, y1` in Drawing.java.

### Features
* You can zoom in closer by clicking and dragging a rectangle over the window. This will make that rectangle the focus of the new image.
* You can change the max number of iterations by changing `static int max = 250;` in Drawing.java.
* You can see the Julia set for a position on the mandelbrot set by right clicking a location.

### History of the Mandelbrot Set 
The Mandelbrot set has its origin in complex dynamics, a field first investigated by the French mathematicians Pierre Fatou and Gaston Julia at the beginning of the 20th century. This fractal was first defined and drawn in 1978 by Robert W. Brooks and Peter Matelski as part of a study of Kleinian groups. On 1 March 1980, at IBM's Thomas J. Watson Research Center in Yorktown Heights, New York, Benoit Mandelbrot first saw a visualization of the set.

Mandelbrot studied the parameter space of quadratic polynomials in an article that appeared in 1980. The mathematical study of the Mandelbrot set really began with work by the mathematicians Adrien Douady and John H. Hubbard (1985), who established many of its fundamental properties and named the set in honor of Mandelbrot for his influential work in fractal geometry.

The mathematicians Heinz-Otto Peitgen and Peter Richter became well known for promoting the set with photographs, books (1986), and an internationally touring exhibit of the German Goethe-Institut (1985).

The cover article of the August 1985 Scientific American introduced a wide audience to the algorithm for computing the Mandelbrot set. The cover featured an image located at −0.909 + −0.275 i and was created by Peitgen et al. The Mandelbrot set became prominent in the mid-1980s as a computer graphics demo, when personal computers became powerful enough to plot and display the set in high resolution.

The work of Douady and Hubbard coincided with a huge increase in interest in complex dynamics and abstract mathematics, and the study of the Mandelbrot set has been a centerpiece of this field ever since. An exhaustive list of all who have contributed to the understanding of this set since then is long but would include Jean-Christophe Yoccoz, Mitsuhiro Shishikura, Curt McMullen, John Milnor and Mikhail Lyubich.

Read more at ([https://en.wikipedia.org/wiki/Mandelbrot_set](https://en.wikipedia.org/wiki/Mandelbrot_set))

### Examples of Mandelbrot Images
<img src="./mandelbrot-whole.png">
<img src="./mandelbrot-zoom-1.png">
<img src="./mandelbrot-zoom-2.png">
<img src="./mandelbrot-zoom-3.png">

### Examples of Julia Set Images
<img src="./julia-1.png">
