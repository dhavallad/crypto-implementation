package com.shamir;
import com.shamir.SecretShare;

/**
 * Created by dhavallad on 4/27/16.
 */
import java.math.BigInteger;
//import java.math;
import java.util.Random;


public class Shamir {

    private  int k;
    private  int n;
    private static BigInteger prime = new BigInteger(("257"));

    public Shamir( int k,  int n) {
        this.k = k;
        this.n = n;
    }

    public SecretShare[] generateShares( BigInteger secret) {


        // Create ccoefficient less than k where k is Shamir's(k,n).
        BigInteger[] coefficient = new BigInteger[k - 1];

        System.out.println("Prime Number: " + prime);

        // Generate random number for coefficient less than prime
        int modLength = prime.bitLength() - 1;
        for (int i = 0; i < k - 1; i++) {
            coefficient[i] = new BigInteger(modLength,new Random());;
            System.out.println("coefficient[" + (i + 1) + "]: " + coefficient[i]);
        }

        SecretShare[] shares = new SecretShare[n];
        for (int i = 1; i <= n; i++) {
            BigInteger temp = secret;
            for (int j = 1; j < k; j++) {
                BigInteger t1 = BigInteger.valueOf(i).modPow(BigInteger.valueOf(j), prime);
                BigInteger t2 = coefficient[j - 1].multiply(t1).mod(prime);
                temp = temp.add(t2).mod(prime);
            }
            shares[i - 1] = new SecretShare(i - 1, temp);
            System.out.println(shares[i - 1]);
        }
        return shares;
    }


    public BigInteger combineShares( SecretShare[] shares,  BigInteger primeNum) {
        BigInteger temp = BigInteger.ZERO;
        for (int i = 0; i < k; i++) {
            BigInteger num = BigInteger.ONE;
            BigInteger den = BigInteger.ONE;

            for (int j = 0; j < k; j++) {
                if (i != j) {
                    num = num.multiply(BigInteger.valueOf(-j - 1)).mod(primeNum);
                    den = den.multiply(BigInteger.valueOf(i - j)).mod(primeNum);
                }
            }
            BigInteger value = shares[i].getShare();
            BigInteger tmp = value.multiply(num).multiply(den.modInverse(primeNum)).mod(primeNum);
            temp = temp.add(primeNum).add(tmp).mod(primeNum);
        }
        System.out.println("The secret is: " + temp);

        return temp;
    }



    public static SecretShare[] createShares(int k,int n,BigInteger secret) {

        final Shamir shamir = new Shamir(k, n);
        final SecretShare[] shares = shamir.generateShares(secret);
//        for (SecretShare a  : shares) {
//            System.out.println("INSIDE CREATESHARES --- "+a);
//        }
//        System.out.println("shares--->>"+shares[0].toString());
//        System.out.println("shares bro"+data);
        return shares;
    }

    public static BigInteger reConstruct(int k,int n,SecretShare[] shares) {

        final Shamir shamir2 = new Shamir(k, n);
        final BigInteger result = shamir2.combineShares(shares,prime);
        return result;
    }

}