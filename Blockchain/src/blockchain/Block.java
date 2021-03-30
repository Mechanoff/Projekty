package blockchain;

import java.io.Serializable;
import java.util.*;
import java.security.MessageDigest;

//class representing a single block in blockchain;
//contains id, timestamp, hash of previous and current block, and arrayList of Customers

public class Block implements Serializable {
    private static final long serialVersionUID = 7L;
    String messages;
    final int id;
    long timestamp;
    String stamp;
    String previousHash;
    String currentHash;
    String securityChange = "stays the same";
    int magicNumber;
    int securityLevel;
    Random random;
    long time;

    public Block(int id, String previousHash, int securityLevel, String messages) {
        random = new Random();
        this.magicNumber = random.nextInt();
        this.id = id + 1;
        this.timestamp = new Date().getTime();
        this.securityLevel = securityLevel;
        this.previousHash = previousHash;
        this.messages = messages;
        //this.currentHash = calculateHash();
    }

    @Override
    public String toString() {
        return ("Block:\nCreated by " + stamp
                + "\n" + stamp + "gets 100 VC"
                + "\nId:" + this.id
                + "\nTimestamp:" + this.timestamp
                + "\nMagic number:" + this.magicNumber
                + "\nHash of the previous block:\n" + this.previousHash
                + "\nHash of the block:\n" + this.currentHash
                + "\nBlock data:\n" + this.messages
                + "\nBlock was generating for " + time + " miliseconds"
                + "\nSecurity level " + securityChange + " " +  securityLevel +"\n" );
    }

    public boolean checkZeros(String hash) {
        for (int i = 0; i < securityLevel; i++) {
            if (hash.charAt(i) != '0') {
                return false;
            }
        }
        return true;
    }

    public long getTime() {
        return time;
    }

}
