package pl.edu.pwr.lgawron.algo;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ArrayListPermutationGenerator {
    private final List<Integer> listToPermute;
    private BigInteger numLeft;
    private final BigInteger total;

    public ArrayListPermutationGenerator(List<Integer> integerList) {
        int n = integerList.size();
        if (n < 1) {
            throw new IllegalArgumentException("List is empty");
        }
        this.listToPermute = new ArrayList<>(integerList);
        total = getFactorial(n);
        reset();
    }

    public BigInteger getNumLeft() {
        return numLeft;
    }

    public BigInteger getTotal() {
        return total;
    }

    public boolean hasMore() {
        return numLeft.compareTo(BigInteger.ZERO) == 1;
    }

    private static BigInteger getFactorial(int n) {
        BigInteger fact = BigInteger.ONE;
        for (int i = n; i > 1; i--) {
            fact = fact.multiply(new BigInteger(Integer.toString(i)));
        }
        return fact;
    }

    public void reset() {
        for (int i = 0; i < listToPermute.size(); i++) {
            listToPermute.set(i, i);
        }
        numLeft = new BigInteger(total.toString());
    }

    public List<Integer> getNext() {
        if (numLeft.equals(total)) {
            numLeft = numLeft.subtract(BigInteger.ONE);
            return listToPermute;
        }

        int temp;

        int j = listToPermute.size() - 2;
        while (listToPermute.get(j) > listToPermute.get(j + 1)) {
            j--;
        }

        int k = listToPermute.size() - 1;
        while (listToPermute.get(j) > listToPermute.get(k)) {
            k--;
        }

        temp = listToPermute.get(k);
        listToPermute.set(k, listToPermute.get(j));
        listToPermute.set(j, temp);

        int r = listToPermute.size() - 1;
        int s = j + 1;

        while (r > s) {
            temp = listToPermute.get(s);
            listToPermute.set(s, listToPermute.get(r));
            listToPermute.set(r, temp);
            r--;
            s++;
        }

        numLeft = numLeft.subtract(BigInteger.ONE);
        return new ArrayList<>(listToPermute);
    }
}
