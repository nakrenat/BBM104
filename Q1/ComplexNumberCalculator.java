/**
 * Represents a complex number with real and imaginary parts.
 */
class ComplexNumber {
    private int real;     // The real part of the complex number
    private int imaginary; // The imaginary part of the complex number

    /**
     * Constructs a ComplexNumber with specified real and imaginary parts.
     *
     * @param real The real part of the complex number.
     * @param imaginary The imaginary part of the complex number.
     */
    public ComplexNumber(int real, int imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    /**
     * Returns the real part of the complex number.
     *
     * @return The real part of the complex number.
     */
    public int getReal() {
        return real;
    }

    /**
     * Sets the real part of the complex number.
     *
     * @param real The new real part to set.
     */
    public void setReal(int real) {
        this.real = real;
    }

    /**
     * Returns the imaginary part of the complex number.
     *
     * @return The imaginary part of the complex number.
     */
    public int getImaginary() {
        return imaginary;
    }

    /**
     * Sets the imaginary part of the complex number.
     *
     * @param imaginary The new imaginary part to set.
     */
    public void setImaginary(int imaginary) {
        this.imaginary = imaginary;
    }

    /**
     * Returns a string representation of the complex number in the format "a+bi" or "a-bi".
     *
     * @return A string representation of the complex number.
     */
    @Override
    public String toString() {
        if (imaginary >= 0) {
            return real + "+" + imaginary + "i";
        } else {
            return real + "" + imaginary + "i";
        }
    }
}

/**
 * Performs operations on two complex numbers.
 */
class ComplexNumberCalculator {
    private ComplexNumber number1; // The first complex number
    private ComplexNumber number2; // The second complex number

    /**
     * Constructs a ComplexNumberCalculator with two specified complex numbers.
     *
     * @param real1 The real part of the first complex number.
     * @param imaginary1 The imaginary part of the first complex number.
     * @param real2 The real part of the second complex number.
     * @param imaginary2 The imaginary part of the second complex number.
     */
    public ComplexNumberCalculator(int real1, int imaginary1, int real2, int imaginary2) {
        this.number1 = new ComplexNumber(real1, imaginary1);
        this.number2 = new ComplexNumber(real2, imaginary2);
    }

    /**
     * Calculates the sum of the two complex numbers.
     *
     * @return A new ComplexNumber representing the sum of the two complex numbers.
     */
    public ComplexNumber sum() {
        int realSum = number1.getReal() + number2.getReal();
        int imaginarySum = number1.getImaginary() + number2.getImaginary();
        return new ComplexNumber(realSum, imaginarySum);
    }

    /**
     * Calculates the difference of the two complex numbers.
     *
     * @return A new ComplexNumber representing the difference of the two complex numbers.
     */
    public ComplexNumber sub() {
        int realSub = number1.getReal() - number2.getReal();
        int imaginarySub = number1.getImaginary() - number2.getImaginary();
        return new ComplexNumber(realSub, imaginarySub);
    }

    /**
     * Calculates the product of the two complex numbers.
     *
     * @return A new ComplexNumber representing the product of the two complex numbers.
     */
    public ComplexNumber mul() {
        int realMul = number1.getReal() * number2.getReal() - number1.getImaginary() * number2.getImaginary();
        int imaginaryMul = number1.getReal() * number2.getImaginary() + number1.getImaginary() * number2.getReal();
        return new ComplexNumber(realMul, imaginaryMul);
    }

    /**
     * Checks if the two complex numbers are equal.
     *
     * @return true if the complex numbers are equal, false otherwise.
     */
    public boolean equal() {
        return number1.getReal() == number2.getReal() && number1.getImaginary() == number2.getImaginary();
    }

    /**
     * The main method to execute the program and handle command line arguments.
     *
     * @param args Command line arguments: real1, imaginary1, real2, imaginary2, operation.
     */
    public static void main(String[] args) {
        if (args.length < 5) {
            System.out.println("Please enter 5 arguments: <real1> <imaginary1> <real2> <imaginary2> <operation>");
            return;
        }

        // Getting arguments.
        int real1 = Integer.parseInt(args[0]);
        int imaginary1 = Integer.parseInt(args[1]);
        int real2 = Integer.parseInt(args[2]);
        int imaginary2 = Integer.parseInt(args[3]);
        String operation = args[4];

        // Create ComplexNumberCalculator object.
        ComplexNumberCalculator calculator = new ComplexNumberCalculator(real1, imaginary1, real2, imaginary2);
        ComplexNumber result;

        // According to operation call the corresponding method.
        switch (operation) {
            case "add":
                result = calculator.sum();
                System.out.println(result);
                break;
            case "sub":
                result = calculator.sub();
                System.out.println(result);
                break;
            case "mul":
                result = calculator.mul();
                System.out.println(result);
                break;
            case "equal":
                boolean isEqual = calculator.equal();
                System.out.println(isEqual);
                break;
            default:
                System.out.println("Invalid operation. Please try again!");
                break;
        }
    }
}
