package com.example.jason.myapplication;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by jason on 10/24/17.
 */

public class Generator {

    private Random random;

    public Generator(){
        random = new Random(System.currentTimeMillis());
    }

    private int getRandom(){
        return -99 + random.nextInt(198);
    }

    public int[] generateFirst(){
        int[] res = new int[3];

        for(int i = 0; i < res.length; ++i)
            res[i] = getRandom();

        return res;
    }

    public double solveFirst(int[] input) throws WrongException {
        if(input.length != 3)throw new WrongException("Wrong parameter number");
        return (double)(input[2] - input[1]) / input[0];
    }

    public int[] generateSecond(){
        int[] res = new int[4];

        for(int i = 0; i < res.length; ++i)
            res[i] = getRandom();
        
        while(res[0] == 0)res[0] = getRandom();

        while(res[1] * res[1] - 4 * res[0] * (res[2] - res[3]) < 0)res[2] = getRandom();

        return res;
    }

    public double[] solveSecond(int[] input) throws WrongException {
        if(input.length != 4)throw new WrongException("Wrong parameter number");

        int a = input[0];
        int b = input[1];
        int c = input[2] - input[3];

        if(a == 0){
            int[] newArray = Arrays.copyOfRange(input, 1, 4);
            double[] res = new double[2];
            res[0] = solveFirst(newArray);
            res[1] = res[0];
            return res;
        }

        if(b * b - 4 * a * c < 0)throw new WrongException("Not root");

        double tem = Math.sqrt(b * b - 4 * a * c);
        double[] res = new double[2];
        res[0] = (-b + tem)/(2*a);
        res[1] = (-b - tem)/(2*a);

        return res;
    }

    class WrongException extends Exception{
        public WrongException(String message){
            super(message);
        }
    }
}
