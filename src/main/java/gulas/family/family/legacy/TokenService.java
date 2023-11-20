package gulas.family.family.legacy;

import gulas.family.family.token.CryptKeyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class TokenService {
    @Autowired
    private final CryptKeyRepository tokenRepo;

    public String createToken(Long userId, String tokenString) {
        BigInteger p = BigInteger.probablePrime(512, new Random());
        BigInteger q = BigInteger.probablePrime(512, new Random());
        BigInteger n = p.multiply(q);
        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        BigInteger e = findCoPrime(phi);
        BigInteger d = e.modInverse(phi);

        return "Null";
    }

    private BigInteger findCoPrime(BigInteger n) {
        Random rand = new Random();

        BigInteger minCoPrime = BigInteger.valueOf(65537);

        if (minCoPrime.gcd(n).equals(BigInteger.ONE)) {
            return minCoPrime;
        }

        while (true) {
            BigInteger candidate = new BigInteger(n.bitLength(), rand);

            if (candidate.compareTo(minCoPrime) >= 0 && candidate.gcd(n).equals(BigInteger.ONE)) {
                return candidate;
            }
        }
    }

    private List<BigInteger> encryptString(String s, BigInteger n, BigInteger e) {
        List<BigInteger> result = new ArrayList<>();

        for (char c : s.toCharArray()) {
            BigInteger r = new BigInteger(String.valueOf((int) c));
            BigInteger calculation = new BigInteger(String.valueOf(r.modPow(e, n)));
            result.add(calculation);
        }

        return result;
    }

    private String decryptMessage(List<BigInteger> message, BigInteger d, BigInteger n) {
        StringBuilder s = new StringBuilder();

        for (BigInteger value : message) {
            BigInteger calculation = new BigInteger(String.valueOf(value.modPow(d, n)));
            char c = (char) calculation.intValue();
            s.append(c);
        }

        return s.toString();
    }

    private String bigIntToString(BigInteger value) {
        StringBuilder s = new StringBuilder();
        String valueString = value.toString();
        char[] characters = valueString.toCharArray();
        for (char c : characters) {
            s.append(c);
        }
        return s.toString();
    }
}
