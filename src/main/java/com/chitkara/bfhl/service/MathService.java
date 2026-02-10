package com.chitkara.bfhl.service;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class MathService {

    public List<Integer> fibonacci(int n) {
        List<Integer> fib = new ArrayList<>();
        if (n <= 0) return fib;

        fib.add(0);
        if (n == 1) return fib;

        fib.add(1);
        for (int i = 2; i < n; i++) {
            fib.add(fib.get(i - 1) + fib.get(i - 2));
        }
        return fib;
    }

    public List<Integer> prime(List<Integer> nums) {
        List<Integer> primes = new ArrayList<>();
        for (int num : nums) {
            if (isPrime(num)) primes.add(num);
        }
        return primes;
    }

    private boolean isPrime(int n) {
        if (n < 2) return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    public int lcm(List<Integer> nums) {
        return nums.stream().reduce(1, this::lcmTwo);
    }

    private int lcmTwo(int a, int b) {
        return a * (b / gcd(a, b));
    }

    public int hcf(List<Integer> nums) {
        return nums.stream().reduce(this::gcd).orElse(0);
    }

    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}
