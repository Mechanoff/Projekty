package blockchain;

import java.io.Serializable;
import java.util.*;

//class representing chain of blocks
//contains functions for creating blocks and validating
//blockchain

public class Blockchain implements Serializable {
    volatile List<Block> blockList = new ArrayList<>();
    private static final long serialVersionUID = 7L;
    private volatile int securityLevel;
    private final List<Message> pendingMessages = new LinkedList<>();
    private IFormat formatter;

    /*//Initializing as singleton
    private static final Blockchain blockchain = new Blockchain(1);
    */

    public Blockchain(IFormat formatter, int securityLevel) {
        this.formatter = formatter;
        this.securityLevel = securityLevel;
    }

    private Blockchain(int securityLevel) {
        this.securityLevel = securityLevel;
    }


    public int getSecurityLevel() {
        return securityLevel;
    }

    // Add validating for custom number of 0
    public boolean validateBlockchain() {
        if (blockList.size() == 0) {
            return false;
        }
        for (int i = 1; i < blockList.size(); i++) {
            if (blockList.get(i).previousHash != blockList.get(i - 1).currentHash && blockList.get(i).checkZeros(blockList.get(i).currentHash)) {
                return false;
            }
        }
        return true;
    }

    public boolean validateNewBlock(Block block) {
        int size = blockList.size();
        if (size == 0) {
            return block.checkZeros(block.currentHash);
        }
        else return block.previousHash == blockList.get(size - 1).currentHash || !block.checkZeros(block.currentHash);
    }

    public synchronized boolean put(Block newBlock, String miner) {
        if (validateNewBlock(newBlock)) {
            newBlock.stamp = miner;
            /*if (blockList.size() > 1) {
                long timeGenerateDif = newBlock.getTime() - blockList.get(blockList.size() - 1).getTime();
                if (timeGenerateDif < 10) {
                    securityLevel++;
                } else if (timeGenerateDif > 15) {
                    securityLevel--;
                }
            } else {
                securityLevel++;
            }*/
            blockList.add(newBlock);
            pendingMessages.clear();
            System.out.println(newBlock);
            return true;
        }
        return false;
    }

    public synchronized void appendData(Message data) {
        pendingMessages.add(data);
    }

    public synchronized String pendingData() {
        StringJoiner stringJoiner = new StringJoiner("\n");
        if(pendingMessages.isEmpty()) {
            return "no messages";
        }
        pendingMessages.stream()
                .map(formatter::format)
                .forEach(stringJoiner::add);
        return stringJoiner.toString();
    }
    public synchronized  void print() {
        for (Block b : blockList) {
            System.out.println(b);
        }
    }

}