package blockchain;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        //move to blockchain class into method
        /*File file = new File("C:\\Users\\Admin\\IdeaProjects\\Blockchain\\Blockchain\\task\\Blockchain\\chainFile.txt");

        if (!file.exists()) {
            file.createNewFile();
        }

        if (file.length() != 0) {
            //blockChain = (Blockchain) SerializationUtils.deserialize(file.getAbsolutePath());
        }*/

        Blockchain blockchain = new Blockchain(new MessageFormat(), 5);
        int poolSize = Runtime.getRuntime().availableProcessors();
        Thread[] miners = new Thread[poolSize];
        for (int i = 0; i < poolSize; i++) {
            miners[i] = new Thread(new Miner(blockchain));
        }
        for (Thread miner : miners) {
            miner.start();
        }
        /*Thread.sleep(100);
        blockchain.appendData(new Message("Tom", "Hey, I'm first!"));
        blockchain.appendData(new Message("Sarah", "It's not fair!"));
        Thread.sleep(200);
        blockchain.appendData(
                new Message("Sarah", "You always will be first because it is your blockchain!"));
        Thread.sleep(100);
        blockchain.appendData(new Message("Sarah", "Anyway, thank you for this amazing chat."));
        Thread.sleep(200);
        blockchain.appendData(new Message("Tom", "You're welcome :)"));
        blockchain.appendData(new Message("Nick", "Hey Tom, nice chat"));*/
        while(blockchain.blockList.size() < 15) {
            Thread.sleep(100);
        }
        for (Thread miner : miners) {
            miner.interrupt();
        }
        //************************previous solution*****************
        /*ExecutorService executorMiner = Executors.newFixedThreadPool(poolSize);


        while (Blockchain.getBlockchain().blockList.size() < 5) {
            List<String> messages = new ArrayList<>();
            executorMiner.submit(new Miner());
            Thread.sleep(1000L);        //works more stable ( no accidental 6th blocks)
        }
        executorMiner.shutdownNow();
        */


        //SerializationUtils.serialize(Blockchain.getBlockchain(), file.getAbsolutePath());

    }
}
