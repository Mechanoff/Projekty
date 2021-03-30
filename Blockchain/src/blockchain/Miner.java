package blockchain;

import java.util.Date;

public class Miner extends Thread {
    Block block;
    private String miner;
    Blockchain blockchain;
    int VC = 0;

    public Miner(Blockchain blockchain) {
        this.blockchain = blockchain;
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            String[] s = Thread.currentThread().getName().split("-");
            this.miner = s[s.length - 1];
            block = generateBlock();
        }
    }

    public Block generateBlock() {
        this.VC += 100;
        int size = blockchain.blockList.size();
            if (size == 0) {
                block = new Block(0, "0", blockchain.getSecurityLevel(), "no messages");
            } else {
                block = new Block(blockchain.blockList.get(size - 1).id, blockchain.blockList.get(size - 1).currentHash, blockchain.getSecurityLevel(), blockchain.pendingData());
            }
        block.currentHash = calculateHash();
        if (size < 5) blockchain.put(block,miner);
        return block;
    }

    // add creation of hash with custom number of zeros
    public String calculateHash() {
        long start = new Date().getTime();
        String zeros = "0".repeat(block.securityLevel);
        String customers = "";
        String hash;
        /*for (Customer c : block.customerList) {
            customers += c.name + c.balanceChange + "/n";
        }*/
        do {
            block.magicNumber = block.random.nextInt();
            hash = block.id + Long.toString(block.timestamp) + block.previousHash + customers + block.magicNumber;
            hash = StringUtil.applySha256(hash);
        } while ( !hash.startsWith(zeros) );
        block.time = new Date().getTime() - start;
        return hash;
    }
}

