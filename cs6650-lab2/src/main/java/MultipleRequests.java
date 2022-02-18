import java.util.concurrent.CountDownLatch;

public class MultipleRequests {
    final static private int NUMTHREADS = 100;
    private int count = 0;

    synchronized public  void inc() {
        count++;
    }

    public int getVal() {
        return this.count;
    }

    public static void main(String[] args) throws InterruptedException {
        final MultipleRequests counter = new MultipleRequests();
        CountDownLatch completed = new CountDownLatch(NUMTHREADS);
        long s = System.currentTimeMillis();
        for (int i = 0; i < NUMTHREADS; i++) {
            // lambda runnable creation - interface only has a single method so lambda works fine
            Runnable thread =  () -> {
                long start = System.currentTimeMillis();
                counter.inc();
                MyHttpClient.execute();
                long end = System.currentTimeMillis();

                System.out.println(Thread.currentThread().getName() + " "+ (end - start));
                completed.countDown();

            };
            new Thread(thread).start();
        }

        completed.await();
        long e = System.currentTimeMillis();
        System.out.println("Value should be equal to " + NUMTHREADS + " It is: " + counter.getVal() + " total time is :" + (e - s));
    }
}
