package Part1;

import java.util.concurrent.*;

public class Client {

    private Argument argument;
    private String url;
    private CountDownLatch totalThreads;
    private ResultData resultData;

    public Client(Argument argument, String url, CountDownLatch totalThreads, ResultData resultData) {
        this.argument = argument;
        this.url = url;
        this.totalThreads = totalThreads;
        this.resultData = resultData;
    }
    public void testSingleRequestLatency() throws InterruptedException {
        final CountDownLatch count = new CountDownLatch(1);
        MyThread thread = new MyThread(1, 20000, 1, 90, 10000, argument.getNumLifts(), url, count, totalThreads, resultData, 1);
        thread.start();
        count.await();
    }

    public void executePhase1() throws InterruptedException {
        int launchThreads = argument.getNumThreads() / 4;
        int skierIdsPerThread = argument.getNumSkiers() / launchThreads;
        int requestPerThread = (int)((argument.getNumRuns() * 0.2) * skierIdsPerThread);
        // once 20% of threads finish, we stop this phase
        final CountDownLatch count = new CountDownLatch(launchThreads / 5);
        for(int i = 0; i < launchThreads; i++) {
            int startSkierId = i * skierIdsPerThread;
            int endSkierId = (i+1) * skierIdsPerThread - 1;
            MyThread thread = new MyThread(startSkierId, endSkierId, 1, 90, requestPerThread, argument.getNumLifts(), url, count, totalThreads, resultData, 1);
            thread.start();
        }
        count.await();
    }
    public  void executePhase2() throws InterruptedException {
        int launchThreads = argument.getNumThreads();
        int skierIdsPerThread = argument.getNumSkiers() / launchThreads;
        int requestPerThread = (int)((argument.getNumRuns() * 0.6) * skierIdsPerThread);
        // once 20% of threads finish, we stop this phase
        final CountDownLatch count = new CountDownLatch(launchThreads / 5);
        for(int i = 0; i < launchThreads; i++) {
            int startSkierId = i * skierIdsPerThread;
            int endSkierId = (i+1) * skierIdsPerThread - 1;

            MyThread thread = new MyThread(startSkierId, endSkierId, 91, 360, requestPerThread, argument.getNumLifts(), url, count, totalThreads, resultData, 2);
            new Thread(thread).start();
        }
        count.await();
    }
    public  void executePhase3() throws InterruptedException {
        int launchThreads = argument.getNumThreads() / 10;
        int skierIdsPerThread = argument.getNumSkiers() / launchThreads;
        int requestPerThread = (int)(argument.getNumRuns() * 0.1) ;
        // once 20% of threads finish, we stop this phase
        final CountDownLatch count = new CountDownLatch(launchThreads);
        for(int i = 0; i < launchThreads; i++) {
            int startSkierId = i * skierIdsPerThread;
            int endSkierId = (i+1) * skierIdsPerThread - 1;

            MyThread thread = new MyThread(startSkierId, endSkierId, 361, 420, requestPerThread, argument.getNumLifts(), url, count, totalThreads, resultData, 3);
            new Thread(thread).start();
        }
        count.await();
    }

}
